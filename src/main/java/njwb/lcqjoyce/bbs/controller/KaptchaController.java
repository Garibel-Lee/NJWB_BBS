package njwb.lcqjoyce.bbs.controller;


import com.google.code.kaptcha.Producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Controller
public class KaptchaController {
    @Resource
    private Producer producer;

    @Autowired
    @Qualifier("stringRedisTemplate")
    RedisTemplate<String, String> rt;

    @RequestMapping("/{action}/checkCode.jpg")
    public void number(HttpServletResponse response, HttpServletRequest req, @PathVariable(name = "action") String action) throws IOException {
        response.setHeader("Cache-Control", "no-store, no-cache");
        response.setContentType("image/jpeg");
        ServletOutputStream out = response.getOutputStream();
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

    }
/*
p1 project init p2 database desigin 12 tables p3 redis  two kinds of checkcode p4 static documents p5 mybatis-generator p6 github Autho2.0 p7 advice handler p8 exception handler
* */

}
