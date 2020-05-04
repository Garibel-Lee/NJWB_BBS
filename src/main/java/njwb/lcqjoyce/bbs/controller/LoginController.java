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
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


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
    public String goReg(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        if (ObjectUtils.isEmpty(user)) {
            return "login";
        } else {
            return "redirect:/";
        }

    }

    //校验登录
    @RequestMapping(value = "/checkExistPassword", method = RequestMethod.POST)
    @ResponseBody
    public Object checkExistPassword(HttpServletRequest request, String oldPassword) {
        User user = (User) request.getSession().getAttribute("user");
        if (ObjectUtils.isEmpty(user)) {
            return ResultDTO.errorOf(CustomizeErrorCode.NO_LOGIN);
        } else {
            if (user.getUserPassword().equals(oldPassword)) {
                return ResultDTO.okOf(200, "原密码正确");
            } else {
                return ResultDTO.okOf(200, "原密码不正确");
            }
        }
    }


    //校验登录
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public Object dologin(HttpServletRequest req, HttpServletResponse response, String email, String password, String code) {
        ValueOperations<String, String> forValue = rt.opsForValue();
        String old_yzm = forValue.get(req.getRemoteAddr() + "_loginCheck");
        if (old_yzm == null) {
            return ResultDTO.errorOf(CustomizeErrorCode.NULL_CODE);
        } else if (old_yzm.equals(code)) {
            User user = new User();
            user.setUserEmail(email);
            user.setUserPassword(password);
            User userLogin = userService.findUserLogin(user);
            if (!ObjectUtils.isEmpty(userLogin)) {
                Cookie cookie = new Cookie("token", userLogin.getUserToken());
                cookie.setMaxAge(60 * 60 * 24 * 30 * 6);
                response.addCookie(cookie);
                return ResultDTO.okOf();
            } else {
                return ResultDTO.errorOf(CustomizeErrorCode.ERROR_PWD);
            }

        } else {
            return ResultDTO.errorOf(CustomizeErrorCode.ERROR_CODE);
        }
    }


}