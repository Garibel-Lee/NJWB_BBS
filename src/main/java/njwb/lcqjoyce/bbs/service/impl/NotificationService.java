package njwb.lcqjoyce.bbs.service.impl;

import njwb.lcqjoyce.bbs.entity.Notification;
public interface NotificationService{


    int deleteByPrimaryKey(Long notificationId);

    int insert(Notification record);

    int insertSelective(Notification record);

    Notification selectByPrimaryKey(Long notificationId);

    int updateByPrimaryKeySelective(Notification record);

    int updateByPrimaryKey(Notification record);

}
