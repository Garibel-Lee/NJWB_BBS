package njwb.lcqjoyce.bbs.enums;

/**
 * Created by LCQJOYCE on 2020/5/1.
 */
public enum ReportStatusEnum {
    UNHANDLE(0), HANDEL(1);
    private int status;

    public int getStatus() {
        return status;
    }

    ReportStatusEnum(int status) {
        this.status = status;
    }
}
