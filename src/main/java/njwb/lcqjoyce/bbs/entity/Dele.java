package njwb.lcqjoyce.bbs.entity;

import java.io.Serializable;
import lombok.Data;

@Data
public class Dele implements Serializable {
    /**
    * 删帖id
    */
    private Long deletionId;

    /**
    * 被删帖的发帖人
    */
    private Long deletionPostuserid;

    /**
    * 被删帖标题
    */
    private String deletionPosttitle;

    /**
    * 被删帖内容
    */
    private String deletionPostcontent;

    /**
    * 删帖原因备注
    */
    private String deletionRemark;

    /**
    * 删帖时间
    */
    private Long deletionGmtcreatetime;

    private static final long serialVersionUID = 1L;
}