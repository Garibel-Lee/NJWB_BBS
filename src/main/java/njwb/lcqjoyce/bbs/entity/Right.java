package njwb.lcqjoyce.bbs.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class Right implements Serializable {
    /**
    * 权限关联id
    */
    private Integer rightId;

    /**
    * 角户id
    */
    private Long rightUserid;

    /**
    * 用色id
    */
    private Long rightRoleid;

    private static final long serialVersionUID = 1L;
}