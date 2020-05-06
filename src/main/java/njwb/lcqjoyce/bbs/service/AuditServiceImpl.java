package njwb.lcqjoyce.bbs.service;

import njwb.lcqjoyce.bbs.entity.Audit;
import njwb.lcqjoyce.bbs.mapper.AuditMapper;
import njwb.lcqjoyce.bbs.service.impl.AuditService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class AuditServiceImpl implements AuditService {

    @Resource
    private AuditMapper auditMapper;

    @Override
    public int deleteByPrimaryKey(Long auditId) {
        return auditMapper.deleteByPrimaryKey(auditId);
    }

    @Override
    public int insert(Audit record) {
        return auditMapper.insert(record);
    }

    @Override
    public int insertSelective(Audit record) {
        return auditMapper.insertSelective(record);
    }

    @Override
    public Audit selectByPrimaryKey(Long auditId) {
        return auditMapper.selectByPrimaryKey(auditId);
    }

    @Override
    public int updateByPrimaryKeySelective(Audit record) {
        return auditMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(Audit record) {
        return auditMapper.updateByPrimaryKey(record);
    }

    @Override
    public Audit findAuditBySome(Long reportuserid, Long postuserid, Long postid) {
        return auditMapper.selectOneByAuditReportuseridAndAuditPostuseridAndAuditPostid(reportuserid,postuserid,postid);
    }

}

