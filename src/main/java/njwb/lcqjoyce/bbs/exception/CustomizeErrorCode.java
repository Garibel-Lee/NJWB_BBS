package njwb.lcqjoyce.bbs.exception;

/**
 * Created by LCQJOYCE on 2020/5/1.
 */
public enum CustomizeErrorCode implements ICustomizeErrorCode {

    QUESTION_NOT_FOUND(2001, "你找到问题不在了，要不要换个试试？"),
    TARGET_PARAM_NOT_FOUND(2002, "未选中任何问题或评论进行回复"),
    NO_LOGIN(2003, "当前操作需要登录，请登陆后重试"),
    SYS_ERROR(2004, "服务冒烟了，要不然你稍后再试试！！！"),
    TYPE_PARAM_WRONG(2005, "评论类型错误或不存在"),
    COMMENT_NOT_FOUND(2006, "回复的评论不存在了，要不要换个试试？"),
    CONTENT_IS_EMPTY(2007, "输入内容不能为空"),
    READ_NOTIFICATION_FAIL(2008, "兄弟你这是读别人的信息呢？"),
    NOTIFICATION_NOT_FOUND(2009, "消息莫非是不翼而飞了？"),
    FILE_UPLOAD_FAIL(2010, "图片上传失败"),
    INVALID_INPUT(2011, "非法输入"),
    NULL_CODE(2014,"验证码不存在或者过期"),
    ERROR_CODE(2013,"验证码错误"),
    INVALID_OPERATION(2012, "兄弟，是不是走错房间了？"),
    HAS_EMAIL(2015,"邮箱已存在"),
    NOTHIS_EMAIL(2016,"邮箱不存在"),
    ERROR_PWD(2017,"密码错误"),
    NOMONEY(2019,"囊中羞涩"),
    NOVIP(2019,"还不是会员"),
    REPORT_ACCOUNT(2019,"账号多次违规，现已封停"),
    RIGATER_BROKEN(2016,"注册存在问题的账号及时联系管理员"),
    GUANLIYUAN(2016,"管理员权限较大，不支持充值会员"),
    ISVIP(2016,"你要是会员才能续费哦"),
    NOT_EMAIL(2018,"邮箱格式有误"),
    ;

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    private Integer code;
    private String message;

    CustomizeErrorCode(Integer code, String message) {
        this.message = message;
        this.code = code;
    }
}
