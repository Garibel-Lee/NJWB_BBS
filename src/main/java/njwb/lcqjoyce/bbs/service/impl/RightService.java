package njwb.lcqjoyce.bbs.service.impl;

import njwb.lcqjoyce.bbs.entity.Right;
public interface RightService{


    int deleteByPrimaryKey(Integer rightId);

    int insert(Right record);

    int insertSelective(Right record);

    Right selectByPrimaryKey(Integer rightId);

    int updateByPrimaryKeySelective(Right record);

    int updateByPrimaryKey(Right record);

    Right selectByUserId(Long userId);
}
