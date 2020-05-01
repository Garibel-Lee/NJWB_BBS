package njwb.lcqjoyce.bbs.entity;

import java.io.Serializable;
import lombok.Data;

@Data
public class Role implements Serializable {
    /**
    * 角色id
    */
    private Long roleId;

    /**
    * 角色名称
    */
    private String roleName;

    private static final long serialVersionUID = 1L;
}