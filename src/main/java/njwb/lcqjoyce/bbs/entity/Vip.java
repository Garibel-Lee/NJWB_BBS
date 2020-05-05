package njwb.lcqjoyce.bbs.entity;

import java.io.Serializable;
import lombok.Data;

@Data
public class Vip implements Serializable {
    /**
    * 会员id
    */
    private Long vipId;

    /**
    * 用户id
    */
    private Long vipUseid;

    /**
    * vip开始时间
    */
    private Long vipStarttime;

    /**
    * vip结束时间
    */
    private Long vipEndtime;

    private static final long serialVersionUID = 1L;
}