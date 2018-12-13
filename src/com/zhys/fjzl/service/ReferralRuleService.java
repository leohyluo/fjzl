package com.zhys.fjzl.service;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.zhys.core.domain.PageResult;
import com.zhys.core.domain.PageParam;
import com.zhys.core.service.BaseService;
import com.zhys.fjzl.domain.EbmIcd9;
import com.zhys.fjzl.domain.ReferralRule;

/**
 * 版权：智慧药师  <br/>
 * 作者：dailing <br/>
 * 生成日期：2017-02-06 <br/>
 * 描述：规则表 Service
 */
@Service("ReferralRuleService")
@Transactional(isolation = Isolation.READ_COMMITTED, readOnly = true, rollbackFor = Throwable.class)
public class ReferralRuleService extends BaseService {
    /**
     * 查询规则表对象
     * @param id 字符串型主键
     * @return ReferralRule
     * @throws Exception 异常
     */
    public ReferralRule query(String id) throws Exception{
    	PageParam param = new PageParam();
    	param.put("id", id);
        return sqlDao.query("ebm_referral_rule.pageList", param);
    }

    /**
     * 查询规则表对象集合
     * @param param 查询条件
     * @return List<ReferralRule>
     * @throws Exception 异常
     */
    public List<ReferralRule> list(PageParam param) throws Exception {
    	return sqlDao.list("ebm_referral_rule.pageList",param);
    }
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<ReferralRule> listQuery(ReferralRule referralRule) throws Exception {
    	return sqlDao.list("ebm_referral_rule.query",referralRule);
    }
    
    /**
     * 规则表翻页查询
     * @param 分页条件对象
     * @return PageResult
     * @throws Exception 异常
     */
    public PageResult pageList(PageParam param) throws Exception {
    	param.setCountSql("ebm_referral_rule.pageCount");
    	param.setRecordSql("ebm_referral_rule.pageList");
        return pageService.pageQuery(param);
    }

    /**
     * 插入规则表记录
     * @param referralRule 规则表对象
     * @return String
     * @throws Exception 异常
     */
    @Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = Throwable.class)
    public String create(ReferralRule referralRule) throws Exception  {
        sqlDao.create("ebm_referral_rule.create",referralRule);
        return "1";
    }

    /**
     * 更新规则表记录
     * @param referralRule 规则表对象
     * @return String
     * @throws Exception 异常
     */
    @Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = Throwable.class)
    public String update(ReferralRule referralRule) throws Exception {
        sqlDao.update("ebm_referral_rule.update", referralRule);
        return "1";
    }

    /**
     * 删除规则表记录
     * @param referralRule 规则表对象
     * @return String
     * @throws Exception 异常
     */
    @Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = Throwable.class)
    public String delete(ReferralRule referralRule) throws Exception {
        sqlDao.delete("ebm_referral_rule.delete", referralRule);
        return "1";
    }

    /**
     * 删除规则表记录
     * @param id 字符串型主键
     * @return String
     * @throws Exception 异常
     */
    @Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = Throwable.class)
    public String delete(String id) throws Exception {
        return delete(new ReferralRule(id));
    }

    /**
     * 删除规则表记录
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
}