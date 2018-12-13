package com.zhys.fjzl.handler;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zhys.core.domain.PageParam;
import com.zhys.core.file.ExcelUtils;
import com.zhys.core.system.SystemConst;
import com.zhys.core.util.CollectionUtils;
import com.zhys.core.util.StringUtil;
import com.zhys.fjzl.annotation.FileHandler;
import com.zhys.fjzl.domain.DiseaseIcd9Relation;
import com.zhys.fjzl.domain.EbmDisease;
import com.zhys.fjzl.domain.EbmIcd9;
import com.zhys.fjzl.enums.EbmStatus;
import com.zhys.fjzl.enums.EnableStatus;
import com.zhys.fjzl.enums.FileType;
import com.zhys.fjzl.enums.Source;
import com.zhys.fjzl.service.DiseaseIcd9RelationService;
import com.zhys.fjzl.service.EbmDiseaseService;
import com.zhys.fjzl.service.EbmIcd9Service;

import jxl.read.biff.BiffException;

@Component
@Transactional
@FileHandler
public class EbmDiseaseHandler extends AbstractHandler<EbmDisease>{

	@Resource(name = "DiseaseService")
	private EbmDiseaseService ebmDiseaseService;
	@Resource(name = "Icd9Service")
	private EbmIcd9Service ebmIcd9Service;
	@Resource(name = "DiseaseIcd9Service")
	private DiseaseIcd9RelationService diseaseIcd9RelationService;
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Override
	public List<EbmDisease> read(File file) throws BiffException, IOException {
		List<EbmDisease> list = new ArrayList<>();
		List<String[]> excelList = ExcelUtils.read(file);
		for(int i=0; i<excelList.size(); i++) {
			String[] arr = excelList.get(i);
			String diseaseName = arr[0];//疾病名称
			String icd10 = arr[1];		//icd10主编码
			String icd10Attach = arr[2];//icd10附加编码	
			String icd10Name = arr[3];	//icd10名称
			String disSystem = arr[4];	//对应系统
			String deptLv1 = arr[5];	//一级科室
			String deptLv2 = arr[6];	//二级科室
			String grade = arr[7];		//医院分级
			
			EbmDisease d = new EbmDisease();
			d.setDisease_name(diseaseName);
			d.setIcd_10(icd10);
			d.setIcd10_attach_code(icd10Attach);
			d.setIcd10Name(icd10Name);
			//d.setIcd9(icd9);
			//d.setIcd9Name(icd9Name);
			d.setDis_system(disSystem);
			d.setDept_lv1(deptLv1);
			d.setDept_lv2(deptLv2);
			d.setGrade(grade);
			d.setRowNo(i+1);
			list.add(d);
		}
		return list;
	}

	@Override
	public FileType getFileType() {
		return FileType.EBM_DISEASE;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void doProcess(List<EbmDisease> list) throws Exception {
		Date now = new Date();
		logger.info("process ebm disease start...");
		for(EbmDisease disease : list) {
			String inputIcd9 = disease.getIcd9();
			String diseaseId = "";
			boolean diseaseChange = false;
			
			//查询疾病是否存在,存在则新增,否则不处理
			PageParam diseaseParam = new PageParam();
			diseaseParam.put("disease_name", disease.getDisease_name());
			diseaseParam.put("icd_10", disease.getIcd_10());
			List<EbmDisease> ebmDiseaseList = ebmDiseaseService.list(diseaseParam);
			if(CollectionUtils.isEmpty(ebmDiseaseList)) {
				diseaseChange = true;
				disease.setCreate_time(now);
				disease.setCreator(Long.valueOf(SystemConst.getLoginer().getSu_id()));
				disease.setDisease_status(EbmStatus.UNREVIEW.getStatus());
				disease.set_enable(EnableStatus.ENABLE.getStatus());
				diseaseId = ebmDiseaseService.create(disease);
			}
			
			if(StringUtil.isNotEmpty(disease.getIcd9())) {
				//查询icd9是否存在,不存在则新增,否则不处理
				String[] icd9Arr = inputIcd9.split(",");
				String[] icd9NameArr = disease.getIcd9Name().split(",");
				if(icd9Arr.length != icd9NameArr.length) {
					throw new IllegalArgumentException("文件第"+disease.getRowNo()+"行,icd9编码与icd9名字数量不一致");
				}
				
				Set<String> list4icd9Id = new HashSet<>(); 
				for(int i=0; i<icd9Arr.length; i++) {
					String itemIcd9 = icd9Arr[i];
					String itemIcd9Name = icd9NameArr[i];
					
					PageParam icd9Param = new PageParam();
					icd9Param.put("icd9", itemIcd9);
					List<EbmIcd9> icd9List = ebmIcd9Service.list(icd9Param);
					if(CollectionUtils.isEmpty(icd9List)) {
						EbmIcd9 newIcd9 = new EbmIcd9();
						newIcd9.setIcd9(itemIcd9);
						newIcd9.setIcd9_name(itemIcd9Name);
						newIcd9.setSource(Source.IMPORT.getStatus());
						newIcd9.setCreate_time(now);
						newIcd9.setCreator(Long.valueOf(SystemConst.getLoginer().getSu_id()));
						String dbIcd9Id = ebmIcd9Service.create(newIcd9);
						list4icd9Id.add(dbIcd9Id);
					} else {
						EbmIcd9 dbEbmIcd9 = icd9List.get(0);
						String dbIcd9Id = dbEbmIcd9.getId().toString();
						list4icd9Id.add(dbIcd9Id);
					}
				}
				//如果疾病为新增,则建立疾病与icd9的关联关系
				if(diseaseChange == true) {
					//查询疾病与icd9是否已建立关联关系
					PageParam relationParam = new PageParam();
					relationParam.put("disease_id", diseaseId);
					List<DiseaseIcd9Relation> relationList = diseaseIcd9RelationService.list(relationParam);
					if(CollectionUtils.isNotEmpty(relationList)) {
						diseaseIcd9RelationService.deleteByDiseaseId(diseaseId);
					}
					for(String itemIcd9Id : list4icd9Id) {
						DiseaseIcd9Relation relation = new DiseaseIcd9Relation();
						relation.setDisease_id(diseaseId);
						relation.setIcd9_id(itemIcd9Id);
						relation.setCreate_time(now);
						relation.setCreator(Long.valueOf(SystemConst.getLoginer().getSu_id()));
						diseaseIcd9RelationService.create(relation);
					}
					//如果icd9没完全匹配则更新疾病与icd9的关联关系,暂无用到
					/*if(completeMatch(list4icd9Id, relationList) == false) {
					}*/
				}
			}
		}
		logger.info("process ebm disease end");
	}
	
	//导入的icd9与数据库中的是否完全匹配
	private boolean completeMatch(Set<String> inputIcd9Set, List<DiseaseIcd9Relation> relationList) {
		boolean match = true;
		if(CollectionUtils.isEmpty(relationList)) {
			return false;
		}
		List<String> inputList = new ArrayList<>(inputIcd9Set);
		Collections.sort(inputList, new Comparator<String>() {
			@Override
			public int compare(String o1, String o2) {
				return o1.compareTo(o2);
			}
		});
		Set<String> dbIcd9Set = new HashSet<>();
		for(DiseaseIcd9Relation item : relationList) {
			dbIcd9Set.add(item.getIcd9_id());
		}
		List<String> dbList = new ArrayList<>(dbIcd9Set);
		Collections.sort(dbList, new Comparator<String>() {
			@Override
			public int compare(String o1, String o2) {
				return o1.compareTo(o2);
			}
		});
		
		if(inputList.size() == dbList.size()) {
			for(int i = 0; i<inputList.size(); i++) {
				if(!inputList.get(i).equals(dbList.get(i))) {
					match = false;
					break;
				}
			}
		}
		return match;
	}
}
