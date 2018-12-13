package com.zhys.fjzl.core.pojo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.zhys.fjzl.enums.ReferralResult;

public class ItemCheckResult {

	private ReferralResult referralResult;
	private String[] args;
	private Long grade;
	
	public ItemCheckResult() {}
	
	public ItemCheckResult(ReferralResult referralResult, String[] args, Long grade) {
		this.referralResult = referralResult;
		this.args = args;
		this.grade = grade;
	}
	
	public ReferralResult getReferralResult() {
		return referralResult;
	}
	public void setReferralResult(ReferralResult referralResult) {
		this.referralResult = referralResult;
	}
	public String[] getArgs() {
		return args;
	}
	public void setArgs(String[] args) {
		this.args = args;
	}
	public Long getGrade() {
		return grade;
	}
	public void setGrade(Long grade) {
		this.grade = grade;
	}
	
	public static void main(String[] args) {
		List<ItemCheckResult> list = new ArrayList<>();
		ItemCheckResult t1 = new ItemCheckResult(ReferralResult.CHECK_GT, null, 1L);
		ItemCheckResult t2 = new ItemCheckResult(ReferralResult.CHECK_GT, null, 3L);
		ItemCheckResult t3 = new ItemCheckResult(ReferralResult.CHECK_LT, null, 2L);
		list.add(t1);
		list.add(t2);
		list.add(t3);
		Collections.sort(list, new Comparator<ItemCheckResult>() {
			@Override
			public int compare(ItemCheckResult o1, ItemCheckResult o2) {
				Long grade1 = o1.getGrade();
				Long grade2 = o2.getGrade();
				
				ReferralResult result1 = o1.getReferralResult();
				ReferralResult result2 = o2.getReferralResult();
				
				if(result1.getType()==result2.getType()){
					if(grade1 == grade2){
						return o2.getGrade().compareTo(o1.getGrade());
					}else{
						return grade2.compareTo(grade1);
					}
					
				} else {
					 return result2.getType().compareTo(result1.getType());
				}
			}
		});
		for(ItemCheckResult item : list) {
			System.out.println(item.referralResult + "=" + item.grade);
		}
	}
	
}
