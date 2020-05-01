package njwb.lcqjoyce.bbs.entity;

import java.io.Serializable;
import lombok.Data;

@Data
public class Question implements Serializable {
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
    * 状态(0:未结贴1已结贴)
    */
    private Integer questionStatus;

    /**
    * 置顶0:未置顶1:已置顶）
    */
    private Integer questionTop;

    private static final long serialVersionUID = 1L;
}