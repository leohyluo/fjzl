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
import com.zhys.fjzl.domain.DrugMapper;
import com.zhys.fjzl.enums.DrugType;
import com.zhys.fjzl.enums.EnableStatus;
import com.zhys.fjzl.enums.FileType;
import com.zhys.fjzl.enums.HisDataStatus;
import com.zhys.fjzl.service.DrugMapperService;

@Component
@Transactional
@FileHandler
public class HisWestMedicalHandler extends AbstractHandler<DrugMapper> {
	
	@Resource(name = "DrugMapperService")
	private DrugMapperService drugMapperService;
	
	@Override
	public List<DrugMapper> read(File file) throws Exception {
		List<DrugMapper> list = new ArrayList<>();
		List<String[]> excelList = ExcelUtils.read(file);
		for(int i=0; i<excelList.size(); i++) {
			String[] arr = excelList.get(i);
			String orgDrugId = arr[0];
			String orgDrugName = arr[1];
			String orgDrugForm = StringUtil.emptyToNull(arr[2]);
			String orgUsage = arr[3];		//数据库要加此字段
			
			DrugMapper dm = new DrugMapper();
			dm.setOrg_id(SystemConst.getLoginer().getSo_id());
			dm.setOrg_drug_code(orgDrugId);
			dm.setOrg_drug_name(orgDrugName);
			dm.setDosage_form(orgDrugForm);
			dm.setDrug_usage(orgUsage);
			dm.setRowNo(i+1);
			list.add(dm);
		}
		return list;
	}

	@Override
	public void doProcess(List<DrugMapper> list) throws Exception {
		Date now = new Date();
		String importNo = StringUtil.getRandomNumber(16);
		String currentOrgId = "";
		for(DrugMapper dm : list) {
			String orgId = dm.getOrg_id();
			if(StringUtil.isEmpty(currentOrgId)) {
				currentOrgId = orgId;
			}
			String drugName = dm.getOrg_drug_name();
			String dosageForm = dm.getDosage_form();
			PageParam param = new PageParam();
			param.put("org_id", orgId);
			param.put("org_drug_name", drugName);
			param.put("dosage_form", dosageForm);
			if(drugMapperService.getCount(param) == 0L) {
				dm.setCreate_time(now);
				dm.setImport_no(importNo);
				dm.setCreator(Long.parseLong(SystemConst.getLoginer().getSu_id()));
				dm.setType(DrugType.XY.getType());
				dm.set_enable(EnableStatus.ENABLE.getStatus());
				dm.setDrug_status(HisDataStatus.UNMAPER.getStatus());
			}
			drugMapperService.create(dm);
		}
		if(StringUtil.isNotEmpty(currentOrgId)) {
			new Thread(new XyAutoMatchThread(drugMapperService, currentOrgId, importNo)).start();
		}
	}

	@Override
	public FileType getFileType() {
		return FileType.HIS_XY;
	}
}

class XyAutoMatchThread implements Runnable {

	private String orgId;
	private String importNo;
	private DrugMapperService drugMapperService;
	
	public XyAutoMatchThread(DrugMapperService drugMapperService, String orgId, String importNo) {
		this.drugMapperService = drugMapperService;
		this.orgId = orgId;
		this.importNo = importNo;
	}
	
	@Override
	public void run() {
		drugMapperService.xyAutoMatch(orgId, importNo);
	}
	
}
