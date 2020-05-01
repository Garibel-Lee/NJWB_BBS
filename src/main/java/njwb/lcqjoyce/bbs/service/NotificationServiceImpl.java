package njwb.lcqjoyce.bbs.service;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import njwb.lcqjoyce.bbs.mapper.NotificationMapper;
import njwb.lcqjoyce.bbs.entity.Notification;
import njwb.lcqjoyce.bbs.service.impl.NotificationService;
@Service
public class NotificationServiceImpl implements NotificationService{

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

}
