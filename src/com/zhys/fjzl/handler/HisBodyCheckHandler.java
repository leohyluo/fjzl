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
import com.zhys.fjzl.domain.CheckMapper;
import com.zhys.fjzl.enums.EnableStatus;
import com.zhys.fjzl.enums.FileType;
import com.zhys.fjzl.enums.HisDataStatus;
import com.zhys.fjzl.service.CheckMapperService;

@Component
@Transactional
@FileHandler
public class HisBodyCheckHandler extends AbstractHandler<CheckMapper> {

	@Resource(name = "CheckMapperService")
	private CheckMapperService checkMapperService;

	@Override
	public List<CheckMapper> read(File file) throws Exception {
		List<CheckMapper> list = new ArrayList<>();
		List<String[]> excelList = ExcelUtils.read(file);
		for(int i=0; i<excelList.size(); i++) {
			String[] arr = excelList.get(i);
			String orgCheckId = arr[0];
			String orgCheckName = arr[1];
			
			CheckMapper cm = new CheckMapper();
			cm.setOrg_id(SystemConst.getLoginer().getSo_id());
			cm.setOrg_check_id(orgCheckId);
			cm.setOrg_check_name(orgCheckName);
			cm.setRowNo(i+1);
			list.add(cm);
		}
		return list;
	}

	@Override
	public void doProcess(List<CheckMapper> list) throws Exception {
		Date now = new Date();
		String importNo = StringUtil.getRandomNumber(16);
		String currentOrgId = "";
		for(CheckMapper item : list) {
			String orgId = item.getOrg_id();
			String checkName = item.getOrg_check_name();
			if(StringUtil.isEmpty(currentOrgId)) {
				currentOrgId = orgId; 
			}
			PageParam param = new PageParam();
			param.put("org_id", orgId);
			param.put("org_check_name", checkName);
			if(checkMapperService.getCount(param) == 0L) {
				item.setCreate_time(now);
				item.setImport_no(importNo);
				item.setCreator(Long.parseLong(SystemConst.getLoginer().getSu_id()));
				item.set_enable(EnableStatus.ENABLE.getStatus());
				item.setBody_check_status(HisDataStatus.UNMAPER.getStatus());
				checkMapperService.create(item);
			}
		}
		if(StringUtil.isNotEmpty(currentOrgId)) {
			new Thread(new BodyCheckAutoMatchThread(checkMapperService, currentOrgId, importNo)).start();
		}
	}
	
	@Override
	public FileType getFileType() {
		return FileType.HIS_BODYCHECK;
	}
	
}

class BodyCheckAutoMatchThread implements Runnable {

	private String orgId;
	private String importNo;
	private CheckMapperService checkMapperService;
	
	public BodyCheckAutoMatchThread(CheckMapperService checkMapperService, String orgId, String importNo) {
		this.checkMapperService = checkMapperService;
		this.orgId = orgId;
		this.importNo = importNo;
	}
	
	@Override
	public void run() {
		checkMapperService.autoMatch(orgId, importNo);
	}
	
}
