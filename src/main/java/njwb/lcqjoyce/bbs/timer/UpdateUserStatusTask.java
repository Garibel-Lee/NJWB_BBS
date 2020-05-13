package njwb.lcqjoyce.bbs.timer;


import njwb.lcqjoyce.bbs.service.impl.AuditService;
import njwb.lcqjoyce.bbs.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author: Garibel.Lee
 * @ClassName: UpdateUserStatusTask  
 * @Date: 2020/5/13 22:24
 * @Description: 定时处理被举报的人 设置他们的登录状态位1 用户状态  0:正常1:禁用
 */
@Component
public class UpdateUserStatusTask {

    @Autowired
    private UserService userService;
    @Autowired
    private AuditService auditService;

    /*****
     * 30秒执行一次
     * 0/30:表示从0秒开始执行，每过30秒再次执行
     */
    @Scheduled(cron = "* 0/1  * * * ?")
    public void UpdateUserStatus(){

    }

    public static void main(String[] args) {

        long milliSecond = 3178770785942L;
        Date date = new Date();
        date.setTime(milliSecond);
        System.out.println(new SimpleDateFormat().format(date));

        System.out.println(System.currentTimeMillis());
    }
}
