package njwb.lcqjoyce.bbs.service;

import njwb.lcqjoyce.bbs.entity.Card;
import njwb.lcqjoyce.bbs.entity.Right;
import njwb.lcqjoyce.bbs.entity.User;
import njwb.lcqjoyce.bbs.mapper.CardMapper;
import njwb.lcqjoyce.bbs.mapper.RightMapper;
import njwb.lcqjoyce.bbs.mapper.UserMapper;
import njwb.lcqjoyce.bbs.provider.BankNumberUtil;
import njwb.lcqjoyce.bbs.service.impl.UserService;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
    @Resource
    private CardMapper cardMapper;
    @Resource
    private UserMapper userMapper;
    @Resource
    private RightMapper rightMapper;

    @Override
    public int deleteByPrimaryKey(Long userId) {
        return userMapper.deleteByPrimaryKey(userId);
    }

    @Override
    public int insert(User record) {
        //插入用户的一些操作  一些基本配置  权限默认普通用户
        Long inserResult = userMapper.insert(record);
        if (ObjectUtils.isEmpty(inserResult)) {
            return 0;
        } else {
            System.out.println(record.getUserId());
            //用户身份  插入right表
            Right userRight = new Right();
            userRight.setRightUserid(record.getUserId());
            userRight.setRightRoleid(1L);
            rightMapper.insert(userRight);
            //用户银行卡  开卡 余额 1000
            Card userCard = new Card();
            userCard.setCardNumber(Long.valueOf(BankNumberUtil.getBrankNumber()));
            userCard.setCardUserid(record.getUserId());
            userCard.setCardPwd("123456");
            userCard.setCardBalance(new BigDecimal(1000));
            cardMapper.insert(userCard);
            return 1;
        }
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
            //刷新token更新
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

    @Override
    public List<User> selectAllByUserEmailAndAccountid(String userEmail,String accountid) {
        return userMapper.selectAllByUserEmailAndAccountId(userEmail,accountid);
    }
    @Override
    public List<User> selectAllByUserEmail(String userEmail) {
        return userMapper.selectAllByUserEmail(userEmail);
    }

}


