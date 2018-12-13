package com.zhys.fjzl.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.zhys.core.annotation.ActionAnnotation;
import com.zhys.core.controller.BaseController;
import com.zhys.fjzl.enums.FileType;
import com.zhys.fjzl.handler.PersistentHandler;

@Controller("TemplateController")
@RequestMapping("/fjzl/template_*.do")
public class TemplateController extends BaseController {

	private static Map<FileType, String> templateMap;
	@Autowired
    private PersistentHandler persistentHandler;
	
	static {
		templateMap = new HashMap<>();
		templateMap.put(FileType.EBM_DISEASE, "知识库疾病模板.xls");
		templateMap.put(FileType.EBM_XY, "知识库西药模板.xls");
		templateMap.put(FileType.EBM_ZCY, "知识库中成药模板.xls");
		templateMap.put(FileType.EBM_ZYYP, "知识库中药饮片模板.xls");
		templateMap.put(FileType.EBM_BODYCHECK, "知识库检查模板.xls");
		templateMap.put(FileType.HIS_DISEASE, "机构疾病模板.xls");
		templateMap.put(FileType.HIS_XY, "机构西药模板.xls");
		templateMap.put(FileType.HIS_ZCY, "机构中成药模板.xls");
		templateMap.put(FileType.HIS_ZYYP, "机构中药饮片模板.xls");
		templateMap.put(FileType.HIS_BODYCHECK, "机构检查模板.xls");
	}
	
	@ActionAnnotation(name = "模板下载", group = "下载")
	public ModelAndView download(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		response.setContentType("text/html;charset=UTF-8");  
        request.setCharacterEncoding("UTF-8");  
        BufferedInputStream bis = null;  
        BufferedOutputStream bos = null;  
        
        String templatePath = request.getSession().getServletContext().getRealPath("WEB-INF/template");
        //String templatePath = request.getServletContext().getRealPath("WEB-INF/template");
		String type = request.getParameter("fileType");
		FileType fileType = FileType.findByType(type);
		String fileName = templateMap.get(fileType);
		String downLoadPath = templatePath + File.separator + fileName;
       
  
        long fileLength = new File(downLoadPath).length();  
  
        response.setContentType("application/vnd.ms-excel");  
        response.setHeader("Content-disposition", "attachment; filename=" + new String(fileName.getBytes("utf-8"), "ISO8859-1"));  
        response.setHeader("Content-Length", String.valueOf(fileLength));  
  
        bis = new BufferedInputStream(new FileInputStream(downLoadPath));  
        bos = new BufferedOutputStream(response.getOutputStream());  
        byte[] buff = new byte[2048];  
        int bytesRead;  
        while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {  
            bos.write(buff, 0, bytesRead);  
        }  
        bis.close();  
        bos.close(); 
		return null;
	}
	
	@ActionAnnotation(name = "保存导入数据",group = "导入",log = true)
    public ModelAndView uploadSave(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	try {
    		String filePath = request.getParameter("filePath");
    		String fileType = request.getParameter("fileType");
    		persistentHandler.handle(fileType, filePath);
    		return responseText(response, "1"); 
		} catch (Exception e) {
			e.printStackTrace();
			return responseText(response, e.getMessage());
		}
    }
}
