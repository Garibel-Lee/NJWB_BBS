package njwb.lcqjoyce.bbs.service.impl;

import njwb.lcqjoyce.bbs.dto.NotificationDTO;
import njwb.lcqjoyce.bbs.dto.PageinfoDTO;
import njwb.lcqjoyce.bbs.dto.ReportDTO;
import njwb.lcqjoyce.bbs.entity.Notification;
import njwb.lcqjoyce.bbs.entity.User;
import njwb.lcqjoyce.bbs.enums.NotificationTypeEnum;

public interface NotificationService {


    int deleteByPrimaryKey(Long notificationId);

    int insert(Notification record);

    int insertSelective(Notification record);

    Notification selectByPrimaryKey(Long notificationId);

    int updateByPrimaryKeySelective(Notification record);

    int updateByPrimaryKey(Notification record);

    NotificationDTO read(Long id, User user);

    PageinfoDTO list(Long userId, Integer page, Integer size);

    Long unreadCount(Long userId);

    void  ViocreateNotify(ReportDTO reportDTO, String outerTitle, NotificationTypeEnum notificationType);
}
