package njwb.lcqjoyce.bbs.dto;

import lombok.Data;
import njwb.lcqjoyce.bbs.entity.Question;

import java.io.Serializable;

@Data
public class CollectDTO implements Serializable {
    /**
    * 收藏id
    */
    private Long collectId;

    /**
    * 收藏帖子的id
    */
    private Long collectPostid;

    /**
    * 收藏帖子的发帖人id
    */
    private Long collectPostuserid;

    /**
    * 收藏用户id
    */
    private Long collectUserid;

    /**
    * 收藏时间
    */
    private Long collectGmtcreatetime;

    private Question question;


    private static final long serialVersionUID = 1L;
}