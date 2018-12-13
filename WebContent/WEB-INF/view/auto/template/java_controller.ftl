package ${table.packageController};

import java.util.Map;
import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import com.zhys.core.annotation.ActionAnnotation;
import com.zhys.core.controller.BaseController;
import com.zhys.core.domain.PageResult;
import com.zhys.core.domain.PageParam;
import com.zhys.core.util.BeanUtil;
import com.zhys.core.util.EasyuiUtil;
import ${table.packageDomain}.${table.classDomain};
import ${table.packageService}.${table.classService};

/**
 * 版权：智慧药师 <br/>
 * 作者：dail <br/>
 * 生成日期：${now?string("yyyy-MM-dd")} <br/>
 * 描述：${table.annotation} Controller
 */
@Controller("${table.moduleName}${table.classController}")
@RequestMapping("${table.mappings}")
public class ${table.classController} extends BaseController{

    @Autowired
    @Qualifier("${table.moduleName}${table.classService}")
    private ${table.classService} ${table.classService?uncap_first};

<#--
    public ${table.classService} get${table.classService}() {
        return this.${table.classService?uncap_first};
    }

    public void set${table.classService}(${table.classService} ${table.classService?uncap_first}) {
        this.${table.classService?uncap_first} = ${table.classService?uncap_first};
    }
-->
    @ActionAnnotation(name = "${table.annotation}入口",group = "查询")
    public ModelAndView main(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return getView(request);
    }

    @ActionAnnotation(name = "${table.annotation}分页",group = "查询")
    public ModelAndView page(HttpServletRequest request, HttpServletResponse response) throws Exception {
        <#--${table.classDomain} ${table.classDomain?uncap_first} = BeanUtil.wrapPageBean(${table.classDomain}.class, request);-->
        PageParam ${table.classDomain?uncap_first} = BeanUtil.wrapPageBean(request);
        PageResult pageResult = ${table.classService?uncap_first}.pageList(${table.classDomain?uncap_first});
        return responseText(response, EasyuiUtil.toDataGridData(pageResult));
    }

    @ActionAnnotation(name = "${table.annotation}详细",group = "查询")
    public ModelAndView view(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String,Object> model = new HashMap<String,Object>();
        ${table.classDomain} ${table.classDomain?uncap_first} = ${table.classService?uncap_first}.query(request.getParameter("${table.pk}"));
        model.put("${table.classDomain?uncap_first}",${table.classDomain?uncap_first});
        return getView(request,model);
    }

    @ActionAnnotation(name = "${table.annotation}添加",group = "添加")
    public ModelAndView add(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return getView(request);
    }

    @ActionAnnotation(name = "${table.annotation}添加保存",group = "添加",log = true)
    public ModelAndView addSave(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ${table.classDomain} ${table.classDomain?uncap_first} = BeanUtil.wrapBean(${table.classDomain}.class, request.getParameter("${table.classDomain?uncap_first}"));
        String result = ${table.classService?uncap_first}.create(${table.classDomain?uncap_first});
        return responseText(response, result);
    }

    @ActionAnnotation(name = "${table.annotation}修改",group = "修改")
    public ModelAndView edit(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String,Object> model = new HashMap<String,Object>();
        ${table.classDomain} ${table.classDomain?uncap_first} = ${table.classService?uncap_first}.query(request.getParameter("${table.pk}"));
        model.put("${table.classDomain?uncap_first}",${table.classDomain?uncap_first});
        return getView(request,model);
    }

    @ActionAnnotation(name = "${table.annotation}修改保存",group = "修改",log = true)
    public ModelAndView editSave(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ${table.classDomain} ${table.classDomain?uncap_first} = BeanUtil.wrapBean(${table.classDomain}.class, request.getParameter("${table.classDomain?uncap_first}"));
        String result = ${table.classService?uncap_first}.update(${table.classDomain?uncap_first});
        return responseText(response, result);
    }

    @ActionAnnotation(name = "${table.annotation}删除",group = "删除",log = true)
    public ModelAndView delete(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String result = ${table.classService?uncap_first}.delete(request.getParameter("${table.pk}"));
        return responseText(response, result);
    }

    @ActionAnnotation(name = "${table.annotation}批量删除",group = "删除",log = true)
    public ModelAndView deleteBatch(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String[] ${table.pk}s = BeanUtil.wrapArray(String.class, request.getParameter("${table.pk}s"));
        String result = ${table.classService?uncap_first}.delete(${table.pk}s);
        return responseText(response, result);
    }
}
