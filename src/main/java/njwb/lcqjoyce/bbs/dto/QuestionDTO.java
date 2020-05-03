package njwb.lcqjoyce.bbs.dto;

import lombok.Data;
import njwb.lcqjoyce.bbs.entity.User;

import java.io.Serializable;

@Data
public class QuestionDTO implements Serializable {
    /**
    * 发帖id
    */
    private Long questionId;

    /**
    * 标题
    */
    private String questionTitle;

    /**
    * 描述问题详情
    */
    private String questionDescription;

    /**
    * 创建时间
    */
    private Long questionGmtcreate;

    /**
    * 修改时间
    */
    private Long questionGmtmodified;

    /**
    * 创建者id
    */
    private Long questionCreator;

    /**
    * 评论数
    */
    private Integer questionCommentcount;

    /**
    * 浏览数
    */
    private Integer questionViewcount;

    /**
    * 收藏数量

    */
    private Integer questionLikecount;

    /**
    * 标签
    */
    private String questionTag;

    /**
    * 悬赏飞吻值
    */
    private Integer questionExpend;

    /**
    * 状态(
     * 0:未结贴
     * 1已结贴
     * )
    */
    private Integer questionStatus;

    /**
    * 置顶
     * 0:未置顶
     * 1:已置顶
     * ）
    */
    private Integer questionTop;

    private User user;
    private static final long serialVersionUID = 1L;
}