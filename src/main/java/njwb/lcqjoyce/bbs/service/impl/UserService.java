package njwb.lcqjoyce.bbs.service.impl;

import njwb.lcqjoyce.bbs.entity.User;

import java.util.List;

public interface UserService {


    int deleteByPrimaryKey(Long userId);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Long userId);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    boolean checkExistEmail(String email);

    List<User> selectByToken(String token);

    User findUserLogin(User user);

    List<User> selectAllByUserEmailAndAccountid(String userEmail,String accountid);
    List<User> selectAllByUserEmail(String userEmail);

}


