package njwb.lcqjoyce.bbs.dto;


import lombok.Data;
import njwb.lcqjoyce.bbs.entity.Question;

/**
 * Created by LCQJOYCE on 2020/5/2.
 */
@Data
public class CommentExsDTO {
    /**
     * 一二级评论id
     */
    private Long commentId;

    /**
     * 来自问题id 或者评论id
     */
    private Long commentParentid;

    /**
     * 评论类型1回复问题2回复评论
     */
    private Integer commentType;

    /**
     * 评论人id
     */
    private Long commentCommentator;

    /**
     * 创建评论时间

     */
    private Long commentGmtcreate;

    /**
     * 修改评论时间
     */
    private Long commentGmtcodified;

    /**
     * 点赞数量
     */
    private Long commentLikecount;

    /**
     * 内容
     */
    private String commentContent;

    /**
     * 评论数量为二级评论设计
     */
    private Integer commentCommentcount;

    /**
     * 评论状态（
     *
     * 0:普通回帖
     * 1:最佳答案
     * ）
     */
    private Integer commentStatus;

    private Question question;
}
