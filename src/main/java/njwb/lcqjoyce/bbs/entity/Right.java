package njwb.lcqjoyce.bbs.entity;

import java.io.Serializable;
import lombok.Data;

@Data
public class Right implements Serializable {
    /**
    * 权限关联id
    */
    private Integer rightId;

    /**
    * 角色id
    */
    private Long rightUserid;

    /**
    * 用户id
    */
    private Long rightRoleid;

    private static final long serialVersionUID = 1L;
}