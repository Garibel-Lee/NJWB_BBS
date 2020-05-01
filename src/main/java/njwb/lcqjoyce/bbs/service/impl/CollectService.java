package njwb.lcqjoyce.bbs.service.impl;

import njwb.lcqjoyce.bbs.entity.Collect;
public interface CollectService{


    int deleteByPrimaryKey(Long collectId);

    int insert(Collect record);

    int insertSelective(Collect record);

    Collect selectByPrimaryKey(Long collectId);

    int updateByPrimaryKeySelective(Collect record);

    int updateByPrimaryKey(Collect record);

}
