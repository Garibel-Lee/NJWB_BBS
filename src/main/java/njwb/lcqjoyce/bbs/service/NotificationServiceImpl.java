package njwb.lcqjoyce.bbs.service;

import njwb.lcqjoyce.bbs.dto.NotificationDTO;
import njwb.lcqjoyce.bbs.dto.PageinfoDTO;
import njwb.lcqjoyce.bbs.dto.ReportDTO;
import njwb.lcqjoyce.bbs.entity.Notification;
import njwb.lcqjoyce.bbs.entity.User;
import njwb.lcqjoyce.bbs.enums.NotificationStatusEnum;
import njwb.lcqjoyce.bbs.enums.NotificationTypeEnum;
import njwb.lcqjoyce.bbs.exception.CustomizeErrorCode;
import njwb.lcqjoyce.bbs.exception.CustomizeException;
import njwb.lcqjoyce.bbs.mapper.NotificationMapper;
import njwb.lcqjoyce.bbs.service.impl.NotificationService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class NotificationServiceImpl implements NotificationService {

    @Resource
    private NotificationMapper notificationMapper;

    @Override
    public int deleteByPrimaryKey(Long notificationId) {
        return notificationMapper.deleteByPrimaryKey(notificationId);
    }

    @Override
    public int insert(Notification record) {
        return notificationMapper.insert(record);
    }

    @Override
    public int insertSelective(Notification record) {
        return notificationMapper.insertSelective(record);
    }

    @Override
    public Notification selectByPrimaryKey(Long notificationId) {
        return notificationMapper.selectByPrimaryKey(notificationId);
    }

    @Override
    public int updateByPrimaryKeySelective(Notification record) {
        return notificationMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(Notification record) {
        return notificationMapper.updateByPrimaryKey(record);
    }

    @Override
    public PageinfoDTO list(Long userId, Integer page, Integer size) {

        PageinfoDTO<NotificationDTO> paginationDTO = new PageinfoDTO<>();

        Integer totalPage;


        Integer totalCount = 0;

        try {
            totalCount =notificationMapper.findAllByNotificationReceiver(userId).size();
        } catch (NullPointerException e) {
            totalCount = 0;
        }

        if (totalCount.equals(0)) {
            return paginationDTO;
        }
        if (totalCount % size == 0) {
            totalPage = totalCount / size;
        } else {
            totalPage = totalCount / size + 1;
        }

        if (page < 1) {
            page = 1;
        }
        if (page > totalPage) {
            page = totalPage;
        }

        paginationDTO.setPageinfo(totalPage, page);

        //size*(page-1)
        Integer offset = size * (page - 1);


        List<Notification> notifications = notificationMapper.selectAllByNotificationReceiver(userId, offset, size);

        if (notifications.size() == 0) {
            return paginationDTO;
        }

        List<NotificationDTO> notificationDTOS = new ArrayList<>();

        for (Notification notification : notifications) {
            NotificationDTO notificationDTO = new NotificationDTO();
            BeanUtils.copyProperties(notification, notificationDTO);
            notificationDTO.setNotificationTypeName(NotificationTypeEnum.nameOfType(notification.getNotificationType()));
            notificationDTOS.add(notificationDTO);
        }
        paginationDTO.setData(notificationDTOS);
        return paginationDTO;
    }


    @Override
    public NotificationDTO read(Long id, User user) {
        Notification notification = notificationMapper.selectByPrimaryKey(id);
        if (notification == null) {
            throw new CustomizeException(CustomizeErrorCode.NOTIFICATION_NOT_FOUND);
        }
        if (!Objects.equals(notification.getNotificationReceiver(), user.getUserId())) {
            throw new CustomizeException(CustomizeErrorCode.READ_NOTIFICATION_FAIL);
        }
        notification.setNotificationStatus(NotificationStatusEnum.READ.getStatus());
        notificationMapper.updateByPrimaryKeySelective(notification);
        NotificationDTO notificationDTO = new NotificationDTO();
        BeanUtils.copyProperties(notification, notificationDTO);
        notificationDTO.setNotificationTypeName(NotificationTypeEnum.nameOfType(notification.getNotificationType()));
        return notificationDTO;
    }

    @Override
    public Long unreadCount(Long userId) {

        Long unReadCount = notificationMapper.findAllByNotificationReceiverAndNotificationStatus(userId, NotificationStatusEnum.UNREAD.getStatus());

        return unReadCount;
    }

    @Override
    public void ViocreateNotify(ReportDTO reportDTO, String vioSection, NotificationTypeEnum notificationType) {

        Notification notification = new Notification();
        notification.setNotificationGmtcreate(System.currentTimeMillis());
        notification.setNotificationType(notificationType.getType());
        notification.setNotificationOuterid(-1L);
        notification.setNotificationNotifier(reportDTO.getReportReportuserid());
        notification.setNotificationStatus(NotificationStatusEnum.UNREAD.getStatus());
        notification.setNotificationReceiver(reportDTO.getReportPostuserid());
        notification.setNotificationNotifiername(reportDTO.getReportUser().getUserName());
        notification.setNotificationOutertitle(vioSection);
        notificationMapper.insert(notification);

    }

    @Override
    public void deleteByNotificationReceiver(Long userId) {
        notificationMapper.deleteByNotificationReceiver(userId);
    }


}
