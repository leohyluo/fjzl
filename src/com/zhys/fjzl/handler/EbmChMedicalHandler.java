package com.zhys.fjzl.handler;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zhys.core.domain.PageParam;
import com.zhys.core.file.ExcelUtils;
import com.zhys.core.system.SystemConst;
import com.zhys.core.util.StringUtil;
import com.zhys.fjzl.annotation.FileHandler;
import com.zhys.fjzl.domain.BChdrug;
import com.zhys.fjzl.enums.EbmStatus;
import com.zhys.fjzl.enums.EnableStatus;
import com.zhys.fjzl.enums.FileType;
import com.zhys.fjzl.service.BChdrugService;

@Component
@Transactional
@FileHandler
/**
 * 知识库中药饮片文件处理器 
 * @author leohyluo
 */
public class EbmChMedicalHandler extends AbstractHandler<BChdrug> {
	
	@Resource(name = "BChdrugService")
	private BChdrugService chDrugService;

	@Override
	public List<BChdrug> read(File file) throws Exception {
		List<BChdrug> list = new ArrayList<>();
		List<String[]> excelList = ExcelUtils.read(file);
		for(int i=0; i<excelList.size(); i++) {
			String[] arr = excelList.get(i);
			String drugCode = arr[0];
			String name = arr[1];
			String usage = arr[2];
			String chargeType = arr[3];
			String chargeGrade = arr[4];
			String atc1 = arr[5];
			String atc2 = arr[6];
			String atc3 = arr[7];
			String atc4 = arr[8];
			String grade = arr[9];
			
			BChdrug ch = new BChdrug();
			ch.setDrug_code(drugCode);
			ch.setName_(name);
			ch.setReposit(usage);
			ch.setCharge_type(chargeType);
			ch.setCharge_grade(chargeGrade);
			ch.setAtc1(atc1);
			ch.setAtc2(atc2);
			ch.setAtc3(atc3);
			ch.setAtc4(atc4);
			ch.setGrade(grade);
			ch.setRowNo(i+1);
			list.add(ch);
		}
		return list;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void doProcess(List<BChdrug> list) throws Exception {
		Date now = new Date();
		for(BChdrug drug : list) {
			String name = drug.getName_();
			PageParam drugParam = new PageParam();
			drugParam.put("drug_code", drug.getDrug_code());
			drugParam.put("name_", name);
			Long count = chDrugService.getCount(drugParam);
			if(count == 0L) {
				drug.setCreate_time(now);
				drug.setCreator(Long.valueOf(SystemConst.getLoginer().getSu_id()));
				drug.setChdrug_status(EbmStatus.UNREVIEW.getStatus());
				drug.set_enable(EnableStatus.ENABLE.getStatus());
				chDrugService.create(drug);
			}
		}
	}

	@Override
	public FileType getFileType() {
		return FileType.EBM_ZYYP;
	}
}
