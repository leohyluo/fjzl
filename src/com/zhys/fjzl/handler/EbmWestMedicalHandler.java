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
import com.zhys.fjzl.domain.WesternMedicine;
import com.zhys.fjzl.enums.EbmStatus;
import com.zhys.fjzl.enums.EnableStatus;
import com.zhys.fjzl.enums.FileType;
import com.zhys.fjzl.service.WesternMedicineService;

@Component
@Transactional
@FileHandler
public class EbmWestMedicalHandler extends AbstractHandler<WesternMedicine> {
	
	@Resource(name = "WesternMedicineService")
	private WesternMedicineService westernMedicineService;

	@Override
	public List<WesternMedicine> read(File file) throws Exception {
		List<WesternMedicine> list = new ArrayList<>();
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
			
			WesternMedicine wm = new WesternMedicine();
			wm.setDrug_code(drugCode);
			wm.setDrug_name(name);
			wm.setDosage_form(dosageForm);
			wm.set_usage(usage);
			wm.setCharge_type(chargeType);
			wm.setCharge_grade(chargeGrade);
			wm.setAtc1(atc1);
			wm.setAtc2(atc2);
			wm.setAtc3(atc3);
			wm.setAtc4(atc4);
			wm.setGrade(grade);
			wm.setRowNo(i+1);
			list.add(wm);
		}
		return list;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void doProcess(List<WesternMedicine> list) throws Exception {
		Date now = new Date();
		for(WesternMedicine drug : list) {
			String name = drug.getDrug_name();
			String dosageForm = drug.getDosage_form();
			PageParam drugParam = new PageParam();
			drugParam.put("drug_code", drug.getDrug_code());
			drugParam.put("drug_name", name);
			drugParam.put("dosage_form", dosageForm);
			Long count = westernMedicineService.getCount(drugParam);
			if(count == 0L) {
				drug.setCreate_time(now);
				drug.setCreator(Long.valueOf(SystemConst.getLoginer().getSu_id()));
				drug.setWestern_status(EbmStatus.UNREVIEW.getStatus());
				drug.set_enable(EnableStatus.ENABLE.getStatus());
				westernMedicineService.create(drug);
			}
		}
	}

	@Override
	public FileType getFileType() {
		return FileType.EBM_XY;
	}
}
