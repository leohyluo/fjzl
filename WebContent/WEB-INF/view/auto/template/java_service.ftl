package ${table.packageService};
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import com.zhys.core.domain.PageResult;
import com.zhys.core.domain.PageParam;
import com.zhys.core.service.BaseService;
import ${table.packageDomain}.${table.classDomain};

/**
 * 版权：智慧药师  <br/>
 * 作者：dailing <br/>
 * 生成日期：${now?string("yyyy-MM-dd")} <br/>
 * 描述：${table.annotation} Service
 */
@Service("${table.moduleName}${table.classService}")
@Transactional(isolation = Isolation.READ_COMMITTED, readOnly = true, rollbackFor = Throwable.class)
public class ${table.classService} extends BaseService {
    <#--
    @Autowired
    public void setSqlDao(@Qualifier("sqlDao") SqlDao sqlDao) {
        this.sqlDao = sqlDao;
    }

    @Autowired
    public void setPageService(@Qualifier("dmPageService") AbstractPageService pageService) {
        this.pageService = pageService;
    }
    -->
    /**
     * 查询${table.annotation}对象
     * @param ${table.pk} 字符串型主键
     * @return ${table.classDomain}
     * @throws Exception 异常
     */
    public ${table.classDomain} query(String ${table.pk}) throws Exception{
    	PageParam param = new PageParam();
    	param.put("${table.pk}", ${table.pk});
        return sqlDao.query("${table.name}.pageList", param);
        <#--return query(new ${table.classDomain}(${table.pk}));-->
    }
    <#--
    /**
     * 查询${table.annotation}对象
     *
     * @param ${table.classDomain?uncap_first} ${table.annotation}对象
     * @return ${table.classDomain}
     * @throws Exception 异常
     */
    public ${table.classDomain} query(${table.classDomain} ${table.classDomain?uncap_first}) throws Exception{
        return sqlDao.query("${table.name}.query",${table.classDomain?uncap_first});
    }

    /**
     * 查询${table.annotation}对象集合
     *
     * @param ${table.classDomain?uncap_first} ${table.annotation}对象
     * @return List
     * @throws Exception 异常
     */
    public List<${table.classDomain}> list(${table.classDomain} ${table.classDomain?uncap_first}) throws Exception {
        return sqlDao.list("${table.name}.query",${table.classDomain?uncap_first});
    }

    /**
     * 查询${table.annotation}对象集合
     *
     * @return List
     * @throws Exception 异常
     */
    public List<${table.classDomain}> list() throws Exception {
        return list(new ${table.classDomain}());
    }-->

    /**
     * 查询${table.annotation}对象集合
     * @param param 查询条件
     * @return List<${table.classDomain}>
     * @throws Exception 异常
     */
    public List<${table.classDomain}> list(PageParam param) throws Exception {
    	return sqlDao.list("${table.name}.pageList",param);
    }
    
    /**
     * ${table.annotation}翻页查询
     * @param 分页条件对象
     * @return PageResult
     * @throws Exception 异常
     */
    public PageResult pageList(PageParam param) throws Exception {
    	param.setCountSql("${table.name}.pageCount");
    	param.setRecordSql("${table.name}.pageList");
        return pageService.pageQuery(param);
    }

    /**
     * 插入${table.annotation}记录
     * @param ${table.classDomain?uncap_first} ${table.annotation}对象
     * @return String
     * @throws Exception 异常
     */
    @Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = Throwable.class)
    public String create(${table.classDomain} ${table.classDomain?uncap_first}) throws Exception  {
        sqlDao.create("${table.name}.create",${table.classDomain?uncap_first});
        return "1";
    }

    /**
     * 更新${table.annotation}记录
     * @param ${table.classDomain?uncap_first} ${table.annotation}对象
     * @return String
     * @throws Exception 异常
     */
    @Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = Throwable.class)
    public String update(${table.classDomain} ${table.classDomain?uncap_first}) throws Exception {
        sqlDao.update("${table.name}.update", ${table.classDomain?uncap_first});
        return "1";
    }

    /**
     * 删除${table.annotation}记录
     * @param ${table.classDomain?uncap_first} ${table.annotation}对象
     * @return String
     * @throws Exception 异常
     */
    @Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = Throwable.class)
    public String delete(${table.classDomain} ${table.classDomain?uncap_first}) throws Exception {
        sqlDao.delete("${table.name}.delete", ${table.classDomain?uncap_first});
        return "1";
    }

    <#--
    /**
     * 批量删除${table.annotation}记录
     *
     * @param paramList  ${table.annotation}对象集合
     * @return String
     * @throws Exception 异常
     */
    @Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = Throwable.class)
    public String delete(List<${table.classDomain}> paramList) throws Exception {
        for(${table.classDomain} param:paramList){
            delete(param);
        }
        return "1";
    }
    -->
    /**
     * 删除${table.annotation}记录
     * @param ${table.pk} 字符串型主键
     * @return String
     * @throws Exception 异常
     */
    @Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = Throwable.class)
    public String delete(String ${table.pk}) throws Exception {
        return delete(new ${table.classDomain}(${table.pk}));
    }

    /**
     * 删除${table.annotation}记录
     * @param ${table.pk}s 字符串型主键数组
     * @return String
     * @throws Exception 异常
     */
    @Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = Throwable.class)
    public String delete(String[] ${table.pk}s) throws Exception {
        for(String ${table.pk}:${table.pk}s){
            delete(${table.pk});
        }
        return "1";
    }
}