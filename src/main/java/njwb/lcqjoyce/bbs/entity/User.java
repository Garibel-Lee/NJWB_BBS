package njwb.lcqjoyce.bbs.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class User implements Serializable {
    /**
     * id
     */
    private Long userId;

    /**
     * GithubID
     */
    private String userAccountid;

    /**
     * 邮箱
     */
    private String userEmail;

    /**
     * 密码
     */
    private String userPassword;

    /**
     * 姓名
     */
    private String userName;

    /**
     * 登录密钥
     */
    private String userToken;

    /**
     * 创建时间
     */
    private Long userGmtcreate;

    /**
     * 修改时间
     */
    private Long userGmtmodified;

    /**
     * 个人签名
     */
    private String userBio;

    /**
     * 头像
     */
    private String userAvatarurl;

    /**
     * 性别
     * 0男
     * 1女
     */
    private Integer userSex;

    /**
     * 用户状态
     * 0:正常
     * 1:禁用
     */
    private Integer userStatus;

    /**
     * 所在城市
     */
    private String userCity;

    /**
     * 账户余额
     */
    private Integer userBalances;

    private static final long serialVersionUID = 1L;
}