package com.zhys.fjzl.interceptor;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.zhys.core.domain.PageParam;
import com.zhys.core.system.SystemConst;
import com.zhys.fjzl.constant.GlobalConstant;
import com.zhys.sys.domain.Org;
import com.zhys.sys.domain.User;
import com.zhys.sys.service.OrgService;

public class ReportInterceptor extends HandlerInterceptorAdapter {

	private List<String> allowUrl;
	private OrgService orgService;
	
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		try {
			String ctxPath = request.getContextPath();
			String uri = request.getRequestURI().replace(ctxPath, "");
			if(!allowUrl.contains(uri)) {
				return true;
			}
			User user = getLoginer(request);
			//找出用户所属的医联体
			List<Org> parabioseList = getBelongParabioseList(user);
			//找出用户下的所有子机构
			String orgTree = orgService.tree(new Org(), user);
			
			request.setAttribute("parabioseList", parabioseList);
			request.setAttribute("orgTree", orgTree);
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
	}
	
	private User getLoginer(HttpServletRequest request) {
		HttpSession session = request.getSession(true);
		if (session == null) return null;
        Object obj = session.getAttribute(SystemConst.LOGIN_USER);
        return (obj != null && obj instanceof User) ? (User) obj : null;
	}
	
	//查询当前用户所属医联体
	private List<Org> getBelongParabioseList(User user) throws Exception {
		//超级管理员,找出所有医联体
		Org param = new Org();
		param.setSo_type(GlobalConstant.HOSPITAL_TYPE_PARABIOSE);
		// 找出所有医联体
		List<Org> parabioseList = orgService.list(param);
		if ("1".equals(user.getSu_admin())) {
			return parabioseList;
		} else {
			//找出该机构所属医联体
			List<Org> parentParabioseList = new ArrayList<>();
			String orgId = user.getSo_id();
			Org org = orgService.query(orgId);
			String code2 = org.getSo_code2();
			for(Org item : parabioseList) {
				if(code2.startsWith(item.getSo_code2())) {
					parentParabioseList.add(item);
					break;
				}
			}
			return parentParabioseList;
		}
	}

	public void setAllowUrl(List<String> allowUrl) {
		this.allowUrl = allowUrl;
	}

	public void setOrgService(OrgService orgService) {
		this.orgService = orgService;
	}
	
	
}
