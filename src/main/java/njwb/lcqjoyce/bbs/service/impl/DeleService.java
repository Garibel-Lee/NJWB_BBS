package njwb.lcqjoyce.bbs.service.impl;

import njwb.lcqjoyce.bbs.entity.Dele;
public interface DeleService{


    int deleteByPrimaryKey(Long deletionId);

    int insert(Dele record);

    int insertSelective(Dele record);

    Dele selectByPrimaryKey(Long deletionId);

    int updateByPrimaryKeySelective(Dele record);

    int updateByPrimaryKey(Dele record);

}
