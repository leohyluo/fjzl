package com.zhys.fjzl.report.builder.list;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.zhys.fjzl.report.builder.PieSimpleBuilder;
import com.zhys.fjzl.report.builder.vo.piesimple.PieSimpleVo;

/**
 * 转诊率统计报表
 * @author Administrator
 */
@Component
public class ReferralRateReport extends PieSimpleBuilder {

	@Override
	protected Map<String, Object> getReportParameters() {
		Map<String, Object> param = new HashMap<>();
		return param;
	}

	@Override
	protected Object loadData(Map<String, Object> param) {
		return null;
	}

	@Override
	protected void fillData(PieSimpleVo t, Object data) {
		// TODO Auto-generated method stub
		
	}
}
