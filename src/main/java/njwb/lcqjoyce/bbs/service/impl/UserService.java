package njwb.lcqjoyce.bbs.service.impl;

import njwb.lcqjoyce.bbs.entity.User;
public interface UserService{


    int deleteByPrimaryKey(Long userId);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Long userId);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

}
