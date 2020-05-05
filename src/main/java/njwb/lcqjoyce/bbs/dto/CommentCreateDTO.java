package njwb.lcqjoyce.bbs.dto;

import lombok.Data;

/**
 * Created by LCQJOYCE on 2020/5/1.
 */
@Data
public class CommentCreateDTO {
    private Long parentId;
    private String content;
    private Integer type;
}
