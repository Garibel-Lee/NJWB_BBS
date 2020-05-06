package njwb.lcqjoyce.bbs.mapper;

import njwb.lcqjoyce.bbs.entity.Notification;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface NotificationMapper {
    int deleteByPrimaryKey(Long notificationId);

    int insert(Notification record);

    int insertSelective(Notification record);

    Notification selectByPrimaryKey(Long notificationId);

    int updateByPrimaryKeySelective(Notification record);

    int updateByPrimaryKey(Notification record);

    Long findAllByNotificationReceiverAndNotificationStatus(@Param("notificationReceiver") Long notificationReceiver, @Param("notificationStatus") Integer notificationStatus);

    List<Notification> findAllByNotificationReceiver(@Param("notificationReceiver") Long notificationReceiver);

    List<Notification> selectAllByNotificationReceiver(@Param("notificationReceiver") Long notificationReceiver, @Param("offset") Integer offset, @Param("size") Integer size);


}