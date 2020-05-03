package njwb.lcqjoyce.bbs.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class Like implements Serializable {
    /**
    * 点赞id
    */
    private Long likeId;

    /**
    * 被点赞评论id
    */
    private Long likePostid;

    /**
    * 点赞用户id
    */
    private Long likeReplyid;

    /**
    * 赞状态，
     * 0取消，
     * 1点赞
    */
    private Integer likeStatus;

    private static final long serialVersionUID = 1L;
}