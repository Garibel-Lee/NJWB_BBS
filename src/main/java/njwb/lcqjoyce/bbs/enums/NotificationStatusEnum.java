package njwb.lcqjoyce.bbs.enums;

/**
 * Created by LCQJOYCE on 2020/5/1.
 */
public enum NotificationStatusEnum {
    UNREAD(0), READ(1);
    private int status;

    public int getStatus() {
        return status;
    }

    NotificationStatusEnum(int status) {
        this.status = status;
    }
}
