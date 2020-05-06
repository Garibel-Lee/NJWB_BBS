package njwb.lcqjoyce.bbs.service;

import njwb.lcqjoyce.bbs.entity.Collect;
import njwb.lcqjoyce.bbs.entity.Notification;
import njwb.lcqjoyce.bbs.enums.NotificationStatusEnum;
import njwb.lcqjoyce.bbs.enums.NotificationTypeEnum;
import njwb.lcqjoyce.bbs.mapper.CollectMapper;
import njwb.lcqjoyce.bbs.mapper.NotificationMapper;
import njwb.lcqjoyce.bbs.service.impl.CollectService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CollectServiceImpl implements CollectService {

    @Resource
    private CollectMapper collectMapper;
    @Resource
    private NotificationMapper notificationMapper;
    @Override
    public int deleteByPrimaryKey(Long collectId) {
        return collectMapper.deleteByPrimaryKey(collectId);
    }

    @Override
    public int insert(Collect record) {
        return collectMapper.insert(record);
    }

    @Override
    public int insertSelective(Collect record) {
        return collectMapper.insertSelective(record);
    }

    @Override
    public Collect selectByPrimaryKey(Long collectId) {
        return collectMapper.selectByPrimaryKey(collectId);
    }

    @Override
    public int updateByPrimaryKeySelective(Collect record) {
        return collectMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(Collect record) {
        return collectMapper.updateByPrimaryKey(record);
    }

    @Override
    public Integer selectByQuestionIdandUserId(Long questionId, Long userId) {
        return collectMapper.selectByCollectPostidAndCollectUserid(questionId, userId).size();
    }

    @Override
    public Collect selectByQuestIdandUId(Long questionId, Long userId) {
        List<Collect> collects = collectMapper.selectByCollectPostidAndCollectUserid(questionId, userId);
        if (collects.size() != 0) {
            return collects.get(0);
        } else {
            return null;
        }

    }

    @Override
    public List<Collect> listMyCollsections(Long userId) {
        List<Collect> collectUserids = collectMapper.findAllByCollectUserid(userId);
        if (collectUserids.size() == 0) {
            return null;
        } else {
            return collectUserids;

        }

    }

    @Override
    public void  createNotify(Collect comment, String loginuserName, String outerTitle) {

        Notification notification = new Notification();
        notification.setNotificationGmtcreate(System.currentTimeMillis());
        notification.setNotificationType(NotificationTypeEnum.COLLECT_QUESTION.getType());
        notification.setNotificationOuterid(comment.getCollectPostid());
        notification.setNotificationNotifier(comment.getCollectUserid());
        notification.setNotificationStatus(NotificationStatusEnum.UNREAD.getStatus());
        notification.setNotificationReceiver(comment.getCollectPostuserid());
        notification.setNotificationNotifiername(loginuserName);
        notification.setNotificationOutertitle(outerTitle);
        notificationMapper.insert(notification);
    }

}
