package njwb.lcqjoyce.bbs.mapper;
import njwb.lcqjoyce.bbs.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserMapper {
    int deleteByPrimaryKey(Long userId);

    long  insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Long userId);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    List<User> selectAllByUserEmailAndAccountId(@Param("userEmail")String userEmail,@Param("accountId")String accountId);

    List<User> selectAllByUserToken(@Param("userToken")String userToken);

    List<User> selectAllByUserEmailAndUserPassword(@Param("userEmail")String userEmail,@Param("userPassword")String userPassword);

    List<User> findInId(@Param("userIds") List<Long> userIds);
    List<User> selectAllByUserEmail(@Param("userEmail")String userEmail);




}