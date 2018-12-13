package com.zhys.fjzl.service;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import com.zhys.core.domain.PageResult;
import com.zhys.core.domain.PageParam;
import com.zhys.core.service.BaseService;
import com.zhys.fjzl.domain.GmwsDiseaseDrug;

/**
 * 版权：智慧药师  <br/>
 * 作者：dailing <br/>
 * 生成日期：2017-07-31 <br/>
 * 描述：智能导诊疾病药品关系表 Service
 */
@Service("GmwsDiseaseDrugService")
@Transactional(isolation = Isolation.READ_COMMITTED, readOnly = true, rollbackFor = Throwable.class)
public class GmwsDiseaseDrugService extends BaseService {
    /**
     * 查询智能导诊疾病药品关系表对象
     * @param gmws_disease_id 字符串型主键
     * @return GmwsDiseaseDrug
     * @throws Exception 异常
     */
    public GmwsDiseaseDrug query(String gmws_disease_id) throws Exception{
    	PageParam param = new PageParam();
    	param.put("gmws_disease_id", gmws_disease_id);
        return sqlDao.query("gmws_disease_drug.pageList", param);
    }

    /**
     * 查询智能导诊疾病药品关系表对象集合
     * @param param 查询条件
     * @return List<GmwsDiseaseDrug>
     * @throws Exception 异常
     */
    public List<GmwsDiseaseDrug> list(PageParam param) throws Exception {
    	return sqlDao.list("gmws_disease_drug.pageList",param);
    }
    
    /**
     * 智能导诊疾病药品关系表翻页查询
     * @param 分页条件对象
     * @return PageResult
     * @throws Exception 异常
     */
    public PageResult pageList(PageParam param) throws Exception {
    	param.setCountSql("gmws_disease_drug.pageCount");
    	param.setRecordSql("gmws_disease_drug.pageList");
        return pageService.pageQuery(param);
    }

    /**
     * 插入智能导诊疾病药品关系表记录
     * @param GmwsDiseaseDrug 智能导诊疾病药品关系表对象
     * @return String
     * @throws Exception 异常
     */
    @Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = Throwable.class)
    public String create(GmwsDiseaseDrug GmwsDiseaseDrug) throws Exception  {
        sqlDao.create("gmws_disease_drug.create",GmwsDiseaseDrug);
        return "1";
    }

    /**
     * 更新智能导诊疾病药品关系表记录
     * @param GmwsDiseaseDrug 智能导诊疾病药品关系表对象
     * @return String
     * @throws Exception 异常
     */
    @Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = Throwable.class)
    public String update(GmwsDiseaseDrug GmwsDiseaseDrug) throws Exception {
        sqlDao.update("gmws_disease_drug.update", GmwsDiseaseDrug);
        return "1";
    }

    /**
     * 删除智能导诊疾病药品关系表记录
     * @param GmwsDiseaseDrug 智能导诊疾病药品关系表对象
     * @return String
     * @throws Exception 异常
     */
    @Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = Throwable.class)
    public String delete(GmwsDiseaseDrug GmwsDiseaseDrug) throws Exception {
        sqlDao.delete("gmws_disease_drug.delete", GmwsDiseaseDrug);
        return "1";
    }

    /**
     * 删除智能导诊疾病药品关系表记录
     * @param gmws_disease_id 字符串型主键
     * @return String
     * @throws Exception 异常
     */
    @Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = Throwable.class)
    public String delete(String gmws_disease_id) throws Exception {
        return delete(new GmwsDiseaseDrug(gmws_disease_id));
    }

    /**
     * 删除智能导诊疾病药品关系表记录
     * @param gmws_disease_ids 字符串型主键数组
     * @return String
     * @throws Exception 异常
     */
    @Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = Throwable.class)
    public String delete(String[] gmws_disease_ids) throws Exception {
        for(String gmws_disease_id:gmws_disease_ids){
            delete(gmws_disease_id);
        }
        return "1";
    }
}