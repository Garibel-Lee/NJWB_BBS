package njwb.lcqjoyce.bbs.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class Violation implements Serializable {
    /**
    * 违规id
    */
    private Long violationId;

    /**
    * 违规用户id
    */
    private Long violationUserid;

    /**
    * 违规次数
    */
    private Integer violationTime;

    private static final long serialVersionUID = 1L;
}