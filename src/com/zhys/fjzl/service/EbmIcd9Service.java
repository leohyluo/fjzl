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

/**
 * 版权：智慧药师  <br/>
 * 作者：dailing <br/>
 * 生成日期：2016-12-16 <br/>
 * 描述：基础知识库icd9表 Service
 */
@Service("Icd9Service")
@Transactional
public class EbmIcd9Service extends BaseService {
    /**
     * 查询基础知识库icd9表对象
     * @param id 字符串型主键
     * @return EbmIcd9
     * @throws Exception 异常
     */
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public EbmIcd9 query(String id) throws Exception{
    	PageParam param = new PageParam();
    	param.put("id", id);
        return sqlDao.query("ebm_icd9.pageList", param);
    }

    /**
     * 查询基础知识库icd9表对象集合
     * @param param 查询条件
     * @return List<EbmIcd9>
     * @throws Exception 异常
     */
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<EbmIcd9> list(PageParam param) throws Exception {
    	return sqlDao.list("ebm_icd9.pageList",param);
    }
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<EbmIcd9> listQuery(EbmIcd9 ebmIcd9) throws Exception {
    	return sqlDao.list("ebm_icd9.query",ebmIcd9);
    }
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<EbmIcd9> FindEbmIcd9(EbmIcd9 ebmIcd9) throws Exception {
    	return sqlDao.list("ebm_icd9.selectSql",ebmIcd9);
    }
	
    
    /**
     * 基础知识库icd9表翻页查询
     * @param 分页条件对象
     * @return PageResult
     * @throws Exception 异常
     */
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public PageResult pageList(PageParam param) throws Exception {
    	param.setCountSql("ebm_icd9.pageCount");
    	param.setRecordSql("ebm_icd9.pageList");
        return pageService.pageQuery(param);
    }

    /**
     * 插入基础知识库icd9表记录
     * @param EbmIcd9 基础知识库icd9表对象
     * @return String
     * @throws Exception 异常
     */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public String create(EbmIcd9 EbmIcd9) throws Exception  {
        return String.valueOf(sqlDao.create("ebm_icd9.create",EbmIcd9));
    }

    /**
     * 更新基础知识库icd9表记录
     * @param EbmIcd9 基础知识库icd9表对象
     * @return String
     * @throws Exception 异常
     */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public String update(EbmIcd9 EbmIcd9) throws Exception {
        return String.valueOf(sqlDao.update("ebm_icd9.update", EbmIcd9));
    }

    /**
     * 删除基础知识库icd9表记录
     * @param EbmIcd9 基础知识库icd9表对象
     * @return String
     * @throws Exception 异常
     */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public String delete(EbmIcd9 EbmIcd9) throws Exception {
        return String.valueOf(sqlDao.delete("ebm_icd9.delete", EbmIcd9));
    }

    /**
     * 删除基础知识库icd9表记录
     * @param id 字符串型主键
     * @return String
     * @throws Exception 异常
     */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public String delete(String id) throws Exception {
        return delete(new EbmIcd9(id));
    }

    /**
     * 删除基础知识库icd9表记录
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
}