package njwb.lcqjoyce.bbs.interceptor;

import njwb.lcqjoyce.bbs.entity.User;
import njwb.lcqjoyce.bbs.service.impl.NotificationService;
import njwb.lcqjoyce.bbs.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by LCQJOYCE on 2020.5.1.
 */
@Service
public class SessionInterceptor implements HandlerInterceptor {


    @Autowired
    private UserService userService;
    @Autowired
    private NotificationService notificationService;


    @Value("${github.redirect.uri}")
    private String redirectUri;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
       //设置 context 级别的属性
        request.getServletContext().setAttribute("redirectUri", redirectUri);
        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length != 0) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {
                    String token = cookie.getValue();

                    List<User> users =userService.selectByToken(token);;
                    if (users.size() != 0) {
                        //cookie放入session用于页面渲染
                        System.out.println("已有登录用户" + users.get(0).getUserName());
                        request.getSession().setAttribute("user", users.get(0));
                        //    Long unreadCount = notificationService.unreadCount(users.get(0).getId());
                     //   request.getSession().setAttribute("unreadCount", unreadCount);
                    }
                    break;
                }
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {

    }
}
