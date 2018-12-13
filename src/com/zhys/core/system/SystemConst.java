package com.zhys.core.system;

import com.zhys.sys.domain.User;

/**
 * 
 * 版权：智慧药师 <br/>
 * 作者：dailing <br/>
 * 生成日期：2013-10-24 <br/>
 * 描述：系统常量
 */
public class SystemConst {
	
    public final static String LOGIN_USER="loginUser";
    public final static String LOGIN_ACTION="loginAction";
    public final static String LOGIN_MENU="menu";
    public final static String RANDOM_CODE="randomCode";

    /**
     * EXPORT_MAX_ROWS : <列表导出最大记录数>
     */
    public final static int EXPORT_MAX_ROWS = Integer.parseInt(SystemParameter.get("exportMaxRows"));
    private static User LOGINER;
    
    public static void setLoginer(User user) {
    	LOGINER = user;
    }
    
    public static User getLoginer() {
    	return LOGINER;
    }
}
