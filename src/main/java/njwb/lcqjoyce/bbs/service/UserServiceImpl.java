package njwb.lcqjoyce.bbs.service;

import njwb.lcqjoyce.bbs.entity.User;
import njwb.lcqjoyce.bbs.mapper.UserMapper;
import njwb.lcqjoyce.bbs.service.impl.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    @Override
    public int deleteByPrimaryKey(Long userId) {
        return userMapper.deleteByPrimaryKey(userId);
    }

    @Override
    public int insert(User record) {
        return userMapper.insert(record);
    }

    @Override
    public int insertSelective(User record) {
        return userMapper.insertSelective(record);
    }

    @Override
    public User selectByPrimaryKey(Long userId) {
        return userMapper.selectByPrimaryKey(userId);
    }

    @Override
    public int updateByPrimaryKeySelective(User record) {
        return userMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(User record) {
        return userMapper.updateByPrimaryKey(record);
    }

    @Override
    public boolean checkExistEmail(String email) {

        if (userMapper.selectAllByUserEmail(email).size() == 0) {
            //不存在
            return false;
        } else {
            //存在
            return true;
        }

    }

    @Override
    public List<User> selectByToken(String token) {
        return userMapper.selectAllByUserToken(token);
    }

    @Override
    public User findUserLogin(User user) {
        List<User> list = userMapper.selectAllByUserEmailAndUserPassword(user.getUserEmail(), user.getUserPassword());
        if (list.size() != 0) {
            User loginInfo = new User();
            loginInfo.setUserId(list.get(0).getUserId());
            ;
            String token = UUID.randomUUID().toString();
            loginInfo.setUserToken(token);
            list.get(0).setUserToken(token);
            //更新token;
            userMapper.updateByPrimaryKeySelective(loginInfo);
            return list.get(0);
        } else {
            return null;
        }


    }

}


