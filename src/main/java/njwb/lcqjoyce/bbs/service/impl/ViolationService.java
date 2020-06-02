package njwb.lcqjoyce.bbs.service.impl;

import njwb.lcqjoyce.bbs.entity.Violation;

import java.util.List;

public interface ViolationService {


    int deleteByPrimaryKey(Long violationId);

    int insert(Violation record);

    int insertSelective(Violation record);

    Violation selectByPrimaryKey(Long violationId);

    int updateByPrimaryKeySelective(Violation record);

    int updateByPrimaryKey(Violation record);


    List<Violation>  selectAllWithAudit();

    Violation selectOneByViolationUserid(Long violationUserid);



}
