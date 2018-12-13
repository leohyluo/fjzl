package com.zhys.fjzl.service;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.zhys.core.domain.PageResult;
import com.zhys.core.domain.PageParam;
import com.zhys.core.service.BaseService;
import com.zhys.fjzl.domain.DrugMapper;
import com.zhys.fjzl.domain.Options;
import com.zhys.fjzl.enums.EnableStatus;

/**
 * 版权：智慧药师  <br/>
 * 作者：dailing <br/>
 * 生成日期：2017-01-16 <br/>
 * 描述：转诊规则表 Service
 */
@Service("OptionsService")
@Transactional(isolation = Isolation.READ_COMMITTED, readOnly = true, rollbackFor = Throwable.class)
public class OptionsService extends BaseService {
    /**
     * 查询转诊规则表对象
     * @param id 字符串型主键
     * @return Options
     * @throws Exception 异常
     */
    public Options query(String id) throws Exception{
    	PageParam param = new PageParam();
    	param.put("id", id);
        return sqlDao.query("referral_options.pageList", param);
    }

    /**
     * 查询转诊规则表对象集合
     * @param param 查询条件
     * @return List<Options>
     * @throws Exception 异常
     */
    public List<Options> list(PageParam param) throws Exception {
    	return sqlDao.list("referral_options.pageList",param);
    }
    
    /**
     * 转诊规则表翻页查询
     * @param 分页条件对象
     * @return PageResult
     * @throws Exception 异常
     */
    public PageResult pageList(PageParam param) throws Exception {
    	param.setCountSql("referral_options.pageCount");
    	param.setRecordSql("referral_options.pageList");
        return pageService.pageQuery(param);
    }

    /**
     * 插入转诊规则表记录
     * @param options 转诊规则表对象
     * @return String
     * @throws Exception 异常
     */
    @Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = Throwable.class)
    public String create(Options options) throws Exception  {
        sqlDao.create("referral_options.create",options);
        return "1";
    }

    /**
     * 更新转诊规则表记录
     * @param options 转诊规则表对象
     * @return String
     * @throws Exception 异常
     */
    @Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = Throwable.class)
    public String update(Options options) throws Exception {
        sqlDao.update("referral_options.update", options);
        return "1";
    }

    /**
     * 删除转诊规则表记录
     * @param options 转诊规则表对象
     * @return String
     * @throws Exception 异常
     */
    @Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = Throwable.class)
    public String delete(Options options) throws Exception {
        sqlDao.delete("referral_options.delete", options);
        return "1";
    }

    /**
     * 删除转诊规则表记录
     * @param id 字符串型主键
     * @return String
     * @throws Exception 异常
     */
    @Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = Throwable.class)
    public String delete(String id) throws Exception {
        return delete(new Options(id));
    }

    /**
     * 删除转诊规则表记录
     * @param ids 字符串型主键数组
     * @return String
     * @throws Exception 异常
     */
    @Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = Throwable.class)
    public String delete(String[] ids) throws Exception {
        for(String id:ids){
            delete(id);
        }
        return "1";
    }
    /**
     * 启用药品对照表记录
     * @param ids 字符串型主键数组
     * @return String
     * @throws Exception 异常
     */
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = Throwable.class)
    public String enableOrNot(String[] ids, EnableStatus status) throws Exception {
        for(String id:ids){
        	Options options = query(id);
        	options.set_enable(status.getStatus());
        	update(options);
        }
        return "1";
    }
    public Long getCount(PageParam pageParam) {
    	return sqlDao.query("referral_options.pageCount", pageParam);
    }
}