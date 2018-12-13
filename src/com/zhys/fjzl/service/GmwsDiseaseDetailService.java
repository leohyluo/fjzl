package com.zhys.fjzl.service;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import com.zhys.core.domain.PageResult;
import com.zhys.core.domain.PageParam;
import com.zhys.core.service.BaseService;
import com.zhys.fjzl.domain.GmwsDiseaseDetail;

/**
 * 版权：智慧药师  <br/>
 * 作者：dailing <br/>
 * 生成日期：2017-07-31 <br/>
 * 描述：智能导诊疾病检查关系表 Service
 */
@Service("GmwsDiseaseDetailService")
@Transactional(isolation = Isolation.READ_COMMITTED, readOnly = true, rollbackFor = Throwable.class)
public class GmwsDiseaseDetailService extends BaseService {
    /**
     * 查询智能导诊疾病检查关系表对象
     * @param id_ 字符串型主键
     * @return GmwsDiseaseDetail
     * @throws Exception 异常
     */
    public GmwsDiseaseDetail query(String id_) throws Exception{
    	PageParam param = new PageParam();
    	param.put("id_", id_);
        return sqlDao.query("gmws_disease_detail.pageList", param);
    }

    /**
     * 查询智能导诊疾病检查关系表对象集合
     * @param param 查询条件
     * @return List<GmwsDiseaseDetail>
     * @throws Exception 异常
     */
    public List<GmwsDiseaseDetail> list(PageParam param) throws Exception {
    	return sqlDao.list("gmws_disease_detail.pageList",param);
    }
    
    /**
     * 智能导诊疾病检查关系表翻页查询
     * @param 分页条件对象
     * @return PageResult
     * @throws Exception 异常
     */
    public PageResult pageList(PageParam param) throws Exception {
    	param.setCountSql("gmws_disease_detail.pageCount");
    	param.setRecordSql("gmws_disease_detail.pageList");
        return pageService.pageQuery(param);
    }

    /**
     * 插入智能导诊疾病检查关系表记录
     * @param GmwsDiseaseDetail 智能导诊疾病检查关系表对象
     * @return String
     * @throws Exception 异常
     */
    @Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = Throwable.class)
    public String create(GmwsDiseaseDetail GmwsDiseaseDetail) throws Exception  {
        sqlDao.create("gmws_disease_detail.create",GmwsDiseaseDetail);
        return "1";
    }

    /**
     * 更新智能导诊疾病检查关系表记录
     * @param GmwsDiseaseDetail 智能导诊疾病检查关系表对象
     * @return String
     * @throws Exception 异常
     */
    @Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = Throwable.class)
    public String update(GmwsDiseaseDetail GmwsDiseaseDetail) throws Exception {
        sqlDao.update("gmws_disease_detail.update", GmwsDiseaseDetail);
        return "1";
    }

    /**
     * 删除智能导诊疾病检查关系表记录
     * @param GmwsDiseaseDetail 智能导诊疾病检查关系表对象
     * @return String
     * @throws Exception 异常
     */
    @Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = Throwable.class)
    public String delete(GmwsDiseaseDetail GmwsDiseaseDetail) throws Exception {
        sqlDao.delete("gmws_disease_detail.delete", GmwsDiseaseDetail);
        return "1";
    }

    /**
     * 删除智能导诊疾病检查关系表记录
     * @param id_ 字符串型主键
     * @return String
     * @throws Exception 异常
     */
    @Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = Throwable.class)
    public String delete(String id_) throws Exception {
        return delete(new GmwsDiseaseDetail(id_));
    }

    /**
     * 删除智能导诊疾病检查关系表记录
     * @param id_s 字符串型主键数组
     * @return String
     * @throws Exception 异常
     */
    @Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = Throwable.class)
    public String delete(String[] id_s) throws Exception {
        for(String id_:id_s){
            delete(id_);
        }
        return "1";
    }
}