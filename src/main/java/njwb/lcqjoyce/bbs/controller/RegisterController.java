package njwb.lcqjoyce.bbs.controller;


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

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Controller
public class RegisterController {

    @Autowired
    private UserService userService;

    @Autowired
    @Qualifier("stringRedisTemplate")
    RedisTemplate<String, String> rt;

    @GetMapping("/register")
    public String goReg() {
        return "register";
    }


    //校验验证码
    @RequestMapping("/register")
    @ResponseBody
    public Object insertUserInfo(HttpServletResponse response, HttpServletRequest req, String email, String username, String password, String code, String sex) {
        ValueOperations<String, String> forValue = rt.opsForValue();
        String old_yzm = forValue.get(req.getRemoteAddr() + "_registerCheck");
        if (old_yzm == null) {
            return ResultDTO.errorOf(CustomizeErrorCode.NULL_CODE);
        } else if (old_yzm.equals(code)) {
            Integer SEX = Integer.valueOf(sex);
            User user = new User();
            user.setUserPassword(password);
            user.setUserEmail(email);
            user.setUserName(username);
            String token = UUID.randomUUID().toString();
            user.setUserToken(token);
            user.setUserGmtcreate(System.currentTimeMillis());
            user.setUserGmtmodified(System.currentTimeMillis());
            user.setUserBio("暂时无");

            if (SEX == 1) {
                user.setUserAvatarurl("/images/1.jpg");
            } else if (SEX == 0) {
                user.setUserAvatarurl("/images/0.jpg");
            } else {
                user.setUserAvatarurl("/images/-1.jpg");
            }
            user.setUserSex(Integer.valueOf(sex));
            user.setUserStatus(0);
            user.setUserCity("暂时无");
            user.setUserBalances(100);
            userService.insert(user);
            response.addCookie(new Cookie("token", token));
            System.out.println(user);
            if (userService.insert(user) != 0) {
                return ResultDTO.okOf();
            } else {
                return ResultDTO.errorOf(CustomizeErrorCode.INVALID_OPERATION);
            }
        } else {
            return ResultDTO.errorOf(CustomizeErrorCode.ERROR_CODE);
        }


    }


}
