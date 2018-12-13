package com.zhys.fjzl.handler;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.zhys.core.domain.PageParam;
import com.zhys.core.file.ExcelUtils;
import com.zhys.core.system.SystemConst;
import com.zhys.core.util.StringUtil;
import com.zhys.fjzl.annotation.FileHandler;
import com.zhys.fjzl.domain.DiseaseMapper;
import com.zhys.fjzl.enums.EnableStatus;
import com.zhys.fjzl.enums.FileType;
import com.zhys.fjzl.enums.HisDataStatus;
import com.zhys.fjzl.service.DiseaseMapperService;

@Component
@Transactional
@FileHandler
public class HisDiseaseHandler extends AbstractHandler<DiseaseMapper> {
	
	@Resource(name = "DiseaseMapperService")
	private DiseaseMapperService diseaseMapperService;

	@Override
	public List<DiseaseMapper> read(File file) throws Exception {
		List<DiseaseMapper> list = new ArrayList<>();
		List<String[]> excelList = ExcelUtils.read(file);
		for(int i=0; i<excelList.size(); i++) {
			String[] arr = excelList.get(i);
			String diseaseId = arr[0];
			String icd10 = StringUtil.emptyToNull(arr[1]);
			String diseaseName = arr[2];
			
			DiseaseMapper dm = new DiseaseMapper();
			dm.setOrg_disease_id(diseaseId);
			dm.setOrg_icd_10(icd10);
			dm.setOrg_disease_name(diseaseName);
			dm.setOrg_id(SystemConst.getLoginer().getSo_id());
			dm.setRowNo(i+1);
			list.add(dm);
		}
		return list;
	}

	@Override
	public void doProcess(List<DiseaseMapper> list) throws Exception {
		Date now = new Date();
		String importNo = StringUtil.getRandomNumber(16);
		String currentOrgId = "";
		for(DiseaseMapper dm : list) {
			String orgId = dm.getOrg_id();
			if(StringUtil.isEmpty(currentOrgId)) {
				currentOrgId = orgId;
			}
			String diseaseName = dm.getOrg_disease_name();
			String icd10 = dm.getOrg_icd_10();
			PageParam param = new PageParam();
			param.put("org_id", orgId);
			param.put("org_disease_name", diseaseName);
			param.put("org_icd_10", icd10);
			if(diseaseMapperService.getCount(param) == 0L) {
				dm.setCreate_time(now);
				dm.setImport_no(importNo);
				dm.setCreator(Long.valueOf(SystemConst.getLoginer().getSu_id()));
				dm.set_enable(EnableStatus.ENABLE.getStatus());
				dm.setDisease_status(HisDataStatus.UNMAPER.getStatus());
			}
			diseaseMapperService.create(dm);
		}
		if(StringUtil.isNotEmpty(currentOrgId)) {
			new Thread(new DiseaseAutoMatchThread(diseaseMapperService, currentOrgId, importNo)).start();
		}
	}

	@Override
	public FileType getFileType() {
		return FileType.HIS_DISEASE;
	}
}

class DiseaseAutoMatchThread implements Runnable {

	private String orgId;
	private String importNo;
	private DiseaseMapperService diseaseMapperService;
	
	public DiseaseAutoMatchThread(DiseaseMapperService diseaseMapperService, String orgId, String importNo) {
		this.diseaseMapperService = diseaseMapperService;
		this.orgId = orgId;
		this.importNo = importNo;
	}
	
	@Override
	public void run() {
		diseaseMapperService.autoMatch(orgId, importNo);
	}
	
}
