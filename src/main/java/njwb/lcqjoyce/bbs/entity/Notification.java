package njwb.lcqjoyce.bbs.entity;

import java.io.Serializable;
import lombok.Data;

@Data
public class Notification implements Serializable {
    /**
    * 消息id
    */
    private Long notificationId;

    /**
    * 消息发起人
    */
    private Long notificationNotifier;

    /**
    * 消息发起人姓名
    */
    private String notificationNotifiername;

    /**
    * 消息接收人
    */
    private Long notificationReceiver;

    /**
    * 跳转id
    */
    private Long notificationOuterid;

    /**
    * 消息类型
0被回复问题
1被回复评论
2被收藏
3被删帖
4举报审核惩罚结果接收 
5被惩罚审核结果接收
6被点赞
    */
    private Integer notificationType;

    /**
    * 消息创建时间
    */
    private Long notificationGmtcreate;

    /**
    * 消息状态0未读1已阅读
    */
    private Integer notificationStatus;

    /**
    * 消息标题
    */
    private String notificationOutertitle;

    private static final long serialVersionUID = 1L;
}