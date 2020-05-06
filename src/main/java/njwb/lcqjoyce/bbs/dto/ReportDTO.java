package njwb.lcqjoyce.bbs.dto;

import lombok.Data;
import njwb.lcqjoyce.bbs.entity.Question;
import njwb.lcqjoyce.bbs.entity.User;

import java.io.Serializable;

@Data
public class ReportDTO implements Serializable {
    /**
    * 举报id
    */
    private Long reportId;

    /**
    * 举报的发帖id
    */
    private Long reportPostid;

    /**
    * 举报的发帖人id
    */
    private Long reportPostuserid;

    /**
    * 举报人id
    */
    private Long reportReportuserid;

    /**
    * 举报标题
    */
    private String reportTitle;

    /**
    * 举报理由
    */
    private String reportAccount;

    /**
    * 举报状态
     * 0：未审核
     * 1：已审核
    */
    private Integer reportStatus;

    /**
    * 举报时间
    */
    private Long reportGmtcreatetime;

    //被举报的人
    private User postUser;

    //举报人
    private User reportUser;

    private Question postQuestion;

    private static final long serialVersionUID = 1L;
}