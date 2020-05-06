package njwb.lcqjoyce.bbs.enums;

/**
 * Created by LCQJOYCE on 2020/5/1.
 */
public enum NotificationTypeEnum {
    REPLY_QUESTION(1, "回复了我的问题"),
    COLLECT_QUESTION(3, "收藏了我的提问"),
    LOVE_QUESTION(4, "点赞了我的回答"),
    REPORT_QUESTION(5, "举报了我的问题"),
    VIOLIATION_QUESTION(6, "惩罚了我的提问帖"),
    REPLY_COMMENT(2, "回复了我的评论");

    private int type;
    private String name;


    public int getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    NotificationTypeEnum(int status, String name) {
        this.type = status;
        this.name = name;
    }

    public static String nameOfType(int type) {
        for (NotificationTypeEnum notificationTypeEnum : NotificationTypeEnum.values()) {
            if (notificationTypeEnum.getType() == type) {
                return notificationTypeEnum.getName();
            }
        }
        return "";
    }
}
