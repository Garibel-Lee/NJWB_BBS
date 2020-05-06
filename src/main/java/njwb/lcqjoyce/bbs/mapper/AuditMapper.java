package njwb.lcqjoyce.bbs.mapper;
import org.apache.ibatis.annotations.Param;

import njwb.lcqjoyce.bbs.entity.Audit;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AuditMapper {
    int deleteByPrimaryKey(Long auditId);

    int insert(Audit record);

    int insertSelective(Audit record);

    Audit selectByPrimaryKey(Long auditId);

    int updateByPrimaryKeySelective(Audit record);

    int updateByPrimaryKey(Audit record);

    Audit selectOneByAuditReportuseridAndAuditPostuseridAndAuditPostid(@Param("auditReportuserid")Long auditReportuserid,@Param("auditPostuserid")Long auditPostuserid,@Param("auditPostid")Long auditPostid);


}