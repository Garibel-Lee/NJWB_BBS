package njwb.lcqjoyce.bbs.controller;


import com.google.code.kaptcha.Producer;
import njwb.lcqjoyce.bbs.dto.ResultDTO;
import njwb.lcqjoyce.bbs.exception.CustomizeErrorCode;
import njwb.lcqjoyce.bbs.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
public class KaptchaController {

    @Autowired
    private UserService userService;

    @Resource
    private Producer producer;

    @Autowired
    @Qualifier("stringRedisTemplate")
    RedisTemplate<String, String> rt;

    @RequestMapping("/{action}/checkCode.jpg")
    public void number(HttpServletResponse response, HttpServletRequest req, @PathVariable(name = "action") String action) {
        response.setHeader("Cache-Control", "no-store, no-cache");
        response.setContentType("image/jpeg");
        ServletOutputStream out = null;
        try {
            out = response.getOutputStream();

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
            ValueOperations forValue = rt.opsForValue();
            if ("login".equals(action)) {
                forValue.set(ip + "_loginCheck", result);
                rt.expire(ip + "_loginCheck", 60 * 1000, TimeUnit.MILLISECONDS);
                ImageIO.write(image, "jpg", out);
            }
            if ("register".equals(action)) {
                forValue.set(ip + "_registerCheck", result);
                rt.expire(ip + "_registerCheck", 60 * 1000, TimeUnit.MILLISECONDS);
                ImageIO.write(image, "jpg", out);
            }
            if ("publish".equals(action)) {
                forValue.set(ip + "_publishCheck", result);
                rt.expire(ip + "_publishCheck", 60 * 1000, TimeUnit.MILLISECONDS);
                ImageIO.write(image, "jpg", out);
            }
        } catch (IOException e) {
            return;
        }
    }

    /*    //泛型运行
        @ResponseBody
        @RequestMapping(value = "/comment/{id}", method = RequestMethod.GET)


        public ResultDTO<List<CommentDTO>> comments(@PathVariable(name="id") Long id ){
            List<CommentDTO> commentDTOs= commentService.findAllByParentIdAndType(id, CommentTypeEnum.COMMENT);
            System.out.println(commentDTOs);
            return ResultDTO.okof(commentDTOs);
        }
        */
    @GetMapping("/loginout")
    public String loginout(
            HttpServletRequest request, HttpServletResponse response) {
        request.getSession().removeAttribute("user");
        Cookie cookie = new Cookie("token", null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return "redirect:/";
    }

    @RequestMapping("/checkEmail")
    @ResponseBody
    public ResultDTO checkEmail(HttpServletRequest req, String email) {
        String RULE_EMAIL = "^\\w+((-\\w+)|(\\.\\w+))*\\@[A-Za-z0-9]+((\\.|-)[A-Za-z0-9]+)*\\.[A-Za-z0-9]+$";
        //正则表达式的模式 编译正则表达式
        Pattern p = Pattern.compile(RULE_EMAIL);
        //正则表达式的匹配器
        Matcher m = p.matcher(email);
        if (m.matches()) {
            if (userService.checkExistEmail(email)) {

                return ResultDTO.okOf();
            }
            return ResultDTO.errorOf(CustomizeErrorCode.NOTHIS_EMAIL);
        } else {
            return ResultDTO.errorOf(CustomizeErrorCode.NOT_EMAIL);
        }
    }

}
