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
import com.zhys.fjzl.domain.BCpdrug;
import com.zhys.fjzl.enums.EbmStatus;
import com.zhys.fjzl.enums.EnableStatus;
import com.zhys.fjzl.enums.FileType;
import com.zhys.fjzl.service.BCpdrugService;

@Component
@Transactional
@FileHandler
/**
 * 知识库中成药文件处理器 
 * @author leohyluo
 */
public class EbmCpMedicalHandler extends AbstractHandler<BCpdrug> {
	
	@Resource(name = "BCpdrugService")
	private BCpdrugService cpDrugService;

	@Override
	public List<BCpdrug> read(File file) throws Exception {
		List<BCpdrug> list = new ArrayList<>();
		List<String[]> excelList = ExcelUtils.read(file);
		for(int i=0; i<excelList.size(); i++) {
			String[] arr = excelList.get(i);
			String drugCode = arr[0];
			String name = arr[1];
			String dosageForm = StringUtil.emptyToNull(arr[2]);
			String usage = arr[3];
			String chargeType = arr[4];
			String chargeGrade = arr[5];
			String atc1 = arr[6];
			String atc2 = arr[7];
			String atc3 = arr[8];
			String atc4 = arr[9];
			String grade = arr[10];
			
			BCpdrug cp = new BCpdrug();
			cp.setDrug_code(drugCode);
			cp.setCpdrugname(name);
			cp.setStandard(dosageForm);
			cp.setCusage(usage);
			cp.setCharge_type(chargeType);
			cp.setCharge_grade(chargeGrade);
			cp.setAtc1(atc1);
			cp.setAtc2(atc2);
			cp.setAtc3(atc3);
			cp.setAtc4(atc4);
			cp.setGrade(grade);
			cp.setRowNo(i+1);
			list.add(cp);
		}
		return list;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void doProcess(List<BCpdrug> list) throws Exception {
		Date now = new Date();
		for(BCpdrug drug : list) {
			String name = drug.getCpdrugname();
			String dosageForm = drug.getStandard();
			PageParam drugParam = new PageParam();
			drugParam.put("drug_code", drug.getDrug_code());
			drugParam.put("cpdrugname", name);
			drugParam.put("standard", dosageForm);
			Long count = cpDrugService.getCount(drugParam);
			if(count == 0L) {
				drug.setCreate_time(now);
				drug.setCreator(Long.valueOf(SystemConst.getLoginer().getSu_id()));
				drug.setCpdrug_status(EbmStatus.UNREVIEW.getStatus());
				drug.set_enable(EnableStatus.ENABLE.getStatus());
				cpDrugService.create(drug);
			}
		}
	}

	@Override
	public FileType getFileType() {
		return FileType.EBM_ZCY;
	}
}
