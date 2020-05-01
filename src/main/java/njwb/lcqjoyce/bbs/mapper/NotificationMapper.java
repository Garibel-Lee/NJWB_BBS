package njwb.lcqjoyce.bbs.mapper;

import njwb.lcqjoyce.bbs.entity.Notification;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface NotificationMapper {
    int deleteByPrimaryKey(Long notificationId);

    int insert(Notification record);

    int insertSelective(Notification record);

    Notification selectByPrimaryKey(Long notificationId);

    int updateByPrimaryKeySelective(Notification record);

    int updateByPrimaryKey(Notification record);
}