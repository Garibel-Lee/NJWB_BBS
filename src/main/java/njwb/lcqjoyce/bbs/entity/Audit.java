package njwb.lcqjoyce.bbs.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class Audit implements Serializable {
    /**
    * 举报id
    */
    private Long auditId;

    /**
    * 举报人id
    */
    private Long auditReportuserid;

    /**
    * 被举报帖子的id
    */
    private Long auditPostid;

    /**
    * 被举报发帖用户的id
    */
    private Long auditPostuserid;

    /**
    * 处理结果
    */
    private String auditResult;

    /**
    * 处理举报时间
    */
    private Long auditGmtcreatetime;

    private static final long serialVersionUID = 1L;
}