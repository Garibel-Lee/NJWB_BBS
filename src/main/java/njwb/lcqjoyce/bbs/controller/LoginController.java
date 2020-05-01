package njwb.lcqjoyce.bbs.controller;


import com.google.code.kaptcha.Producer;
import njwb.lcqjoyce.bbs.dto.ResultDTO;
import njwb.lcqjoyce.bbs.entity.User;
import njwb.lcqjoyce.bbs.exception.CustomizeErrorCode;
import njwb.lcqjoyce.bbs.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p></p >
 *
 * @author hqk
 * @version 1.0: SysLoginController.java v0.1 2019/6/25 上午10:44 hqk Exp$
 */

@Controller
public class LoginController {
    @Autowired
    private UserService userService;

    @Autowired
    @Qualifier("stringRedisTemplate")
    RedisTemplate<String, String> rt;

    @Resource
    private Producer producer;


    @GetMapping("/login")
    public String goReg() {
        return "login";
    }


    /*@RequestMapping("login/checkCode.jpg")
    public void number(HttpServletResponse response, HttpServletRequest req) throws IOException {
        response.setHeader("Cache-Control", "no-store, no-cache");
        response.setContentType("image/jpeg");

        //生成文字验证码
        String text = producer.createText();

        //个位数字相加
        String s1 = text.substring(0, 1);
        String s2 = text.substring(1, 2);
        int count = Integer.valueOf(s1).intValue() + Integer.valueOf(s2).intValue();

        //生成图片验证码
        BufferedImage image = producer.createImage(s1 + "+" + s2 + "=?");

        //生产验证码 保持到redis数据库中，有效时间为一分钟
        //保存 redis key 自己设置
        String ip = req.getRemoteAddr();
        String result = String.valueOf(count);
        System.out.println(result);
        ValueOperations forValue = rt.opsForValue();
        forValue.set(ip + "_check", result);
        rt.expire(ip + "_check", 60 * 1000, TimeUnit.MILLISECONDS);

        ServletOutputStream out = response.getOutputStream();
        ImageIO.write(image, "jpg", out);
    }*/


    //校验验证码
    @RequestMapping("login.do")
    @ResponseBody
    public Object dologin(HttpServletRequest req, String email, String password, String code) {
        ValueOperations<String, String> forValue = rt.opsForValue();
        String old_yzm = forValue.get(req.getRemoteAddr() + "_check");
        if (old_yzm == null) {
            return ResultDTO.errorOf(CustomizeErrorCode.NULL_CODE);
        } else if (old_yzm.equals(code)) {
            User user = new User();
            // user.setEmail(email);
            // user.setPassword(password);
            // System.out.println(user);
     /*       if (userService.findUserLogin(user) != 0) {*/
                if (1==1) {
                    return ResultDTO.okOf();
                } else {
                    return ResultDTO.errorOf(CustomizeErrorCode.ERROR_PWD);
                }

            } else {
                return ResultDTO.errorOf(CustomizeErrorCode.ERROR_CODE);
            }
        }

        @RequestMapping("/login/checkEmail")
        @ResponseBody
        public Object checkEmail (HttpServletRequest req, String email){
            String RULE_EMAIL = "^\\w+((-\\w+)|(\\.\\w+))*\\@[A-Za-z0-9]+((\\.|-)[A-Za-z0-9]+)*\\.[A-Za-z0-9]+$";
            //正则表达式的模式 编译正则表达式
            Pattern p = Pattern.compile(RULE_EMAIL);
            //正则表达式的匹配器
            Matcher m = p.matcher(email);
            if (m.matches()) {
/*                if (userService.checkEmail(email) == 1) {    */
                    if (1== 1) {

                    return ResultDTO.errorOf(200, "正确的email");
                }
                return ResultDTO.errorOf(CustomizeErrorCode.NOTHIS_EMAIL);
            } else {
                return ResultDTO.errorOf(CustomizeErrorCode.NOT_EMAIL);
            }
        }


    }