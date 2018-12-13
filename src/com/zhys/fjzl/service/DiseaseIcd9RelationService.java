package com.zhys.fjzl.service;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zhys.core.domain.PageParam;
import com.zhys.core.domain.PageResult;
import com.zhys.core.service.BaseService;
import com.zhys.fjzl.domain.DiseaseIcd9Relation;

/**
 * 版权：智慧药师  <br/>
 * 作者：dailing <br/>
 * 生成日期：2016-12-16 <br/>
 * 描述：基础知识库疾病与icd9关系表 Service
 */
@Service("DiseaseIcd9Service")
@Transactional
public class DiseaseIcd9RelationService extends BaseService {
    /**
     * 查询基础知识库疾病与icd9关系表对象
     * @param id 字符串型主键
     * @return DiseaseIcd9Relation
     * @throws Exception 异常
     */
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public DiseaseIcd9Relation query(String id) throws Exception{
    	PageParam param = new PageParam();
    	param.put("id", id);
        return sqlDao.query("ebm_disease_icd9.pageList", param);
    }

    /**
     * 查询基础知识库疾病与icd9关系表对象集合
     * @param param 查询条件
     * @return List<DiseaseIcd9Relation>
     * @throws Exception 异常
     */
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<DiseaseIcd9Relation> list(PageParam param) throws Exception {
    	return sqlDao.list("ebm_disease_icd9.pageList",param);
    }
    
    /**
     * 基础知识库疾病与icd9关系表翻页查询
     * @param 分页条件对象
     * @return PageResult
     * @throws Exception 异常
     */
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public PageResult pageList(PageParam param) throws Exception {
    	param.setCountSql("ebm_disease_icd9.pageCount");
    	param.setRecordSql("ebm_disease_icd9.pageList");
        return pageService.pageQuery(param);
    }

    /**
     * 插入基础知识库疾病与icd9关系表记录
     * @param DiseaseIcd9Relation 基础知识库疾病与icd9关系表对象
     * @return String
     * @throws Exception 异常
     */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public String create(DiseaseIcd9Relation DiseaseIcd9Relation) throws Exception  {
        return String.valueOf(sqlDao.create("ebm_disease_icd9.create",DiseaseIcd9Relation));
    }

    /**
     * 更新基础知识库疾病与icd9关系表记录
     * @param DiseaseIcd9Relation 基础知识库疾病与icd9关系表对象
     * @return String
     * @throws Exception 异常
     */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public String update(DiseaseIcd9Relation DiseaseIcd9Relation) throws Exception {
        return String.valueOf(sqlDao.update("ebm_disease_icd9.update", DiseaseIcd9Relation));
    }

    /**
     * 删除基础知识库疾病与icd9关系表记录
     * @param DiseaseIcd9Relation 基础知识库疾病与icd9关系表对象
     * @return String
     * @throws Exception 异常
     */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public String delete(DiseaseIcd9Relation DiseaseIcd9Relation) throws Exception {
        return String.valueOf(sqlDao.delete("ebm_disease_icd9.delete", DiseaseIcd9Relation));
    }
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public String deleteByDiseaseId(String diseaseId) {
		return String.valueOf(sqlDao.delete("ebm_disease_icd9.deleteByDiseaseId", diseaseId));
	}

    /**
     * 删除基础知识库疾病与icd9关系表记录
     * @param id 字符串型主键
     * @return String
     * @throws Exception 异常
     */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public String delete(String id) throws Exception {
        return delete(new DiseaseIcd9Relation(id));
    }

    /**
     * 删除基础知识库疾病与icd9关系表记录
     * @param ids 字符串型主键数组
     * @return String
     * @throws Exception 异常
     */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public String delete(String[] ids) throws Exception {
        for(String id:ids){
            delete(id);
        }
        return "1";
    }
	   /**
     * 删除基础知识库疾病与icd9关系表记录
     * @param ids 字符串型主键数组
     * @return String
     * @throws Exception 异常
     */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public String deleteByDiseaseId(String[] ids) throws Exception {
        for(String id:ids){
        	deleteByDiseaseId(id);
        }
        return "1";
    }
}