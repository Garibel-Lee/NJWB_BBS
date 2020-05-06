package njwb.lcqjoyce.bbs.service.impl;

import njwb.lcqjoyce.bbs.entity.Audit;
import org.springframework.stereotype.Service;

@Service
public interface AuditService{


    int deleteByPrimaryKey(Long auditId);

    int insert(Audit record);

    int insertSelective(Audit record);

    Audit selectByPrimaryKey(Long auditId);

    int updateByPrimaryKeySelective(Audit record);

    int updateByPrimaryKey(Audit record);
    //selectOneByAuditReportuseridAndAuditPostuseridAndAuditPostid
    Audit findAuditBySome(Long reportuserid,Long postuserid,Long auditpostid);

}
