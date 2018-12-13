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
import com.zhys.fjzl.domain.BodyCheck;
import com.zhys.fjzl.enums.EbmStatus;
import com.zhys.fjzl.enums.EnableStatus;
import com.zhys.fjzl.enums.FileType;
import com.zhys.fjzl.service.BodyCheckService;

@Component
@Transactional
@FileHandler
public class EbmBodyCheckHandler extends AbstractHandler<BodyCheck> {
	
	@Resource(name = "BodyCheckService")
	private BodyCheckService bodyCheckService;

	@Override
	public List<BodyCheck> read(File file) throws Exception {
		List<BodyCheck> list = new ArrayList<>();
		List<String[]> excelList = ExcelUtils.read(file);
		for(int i=0; i<excelList.size(); i++) {
			String[] arr = excelList.get(i);
			String name = arr[0];
			String grade = arr[1];
			String special = arr[2];
			String level1_price = arr[3];
			String level2_price = arr[4];
			String level3_price = arr[5];
			
			BodyCheck bc = new BodyCheck();
			bc.setName(name);
			bc.setGrade(grade);
			if(StringUtil.isNotEmpty(special))
				bc.setSpecial(Long.valueOf(special));
			if(StringUtil.isNotEmpty(level1_price))
				bc.setLevel1_price(Double.valueOf(level1_price));
			if(StringUtil.isNotEmpty(level2_price))
				bc.setLevel2_price(Double.valueOf(level2_price));
			if(StringUtil.isNotEmpty(level3_price))
				bc.setLevel3_price(Double.valueOf(level3_price));
			bc.setRowNo(i+1);
			list.add(bc);
		}
		return list;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void doProcess(List<BodyCheck> list) throws Exception {
		Date now = new Date();
		for(BodyCheck bc : list) {
			String name = bc.getName();
			PageParam param = new PageParam();
			param.put("name", name);
			Long count = bodyCheckService.getCount(param);
			if(count == 0L) {
				bc.setCreate_time(now);
				bc.setCreator(Long.valueOf(SystemConst.getLoginer().getSu_id()));
				bc.setBody_check_status(EbmStatus.UNREVIEW.getStatus());
				bc.set_enable(EnableStatus.ENABLE.getStatus());
				bodyCheckService.create(bc);
			}
		}
	}
	
	@Override
	public FileType getFileType() {
		return FileType.EBM_BODYCHECK;
	}
}
