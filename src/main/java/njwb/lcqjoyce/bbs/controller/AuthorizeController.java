package njwb.lcqjoyce.bbs.controller;


import njwb.lcqjoyce.bbs.dto.AccessTokenDTO;
import njwb.lcqjoyce.bbs.dto.GithubUserDTO;
import njwb.lcqjoyce.bbs.entity.User;
import njwb.lcqjoyce.bbs.provider.GithubProvider;
import njwb.lcqjoyce.bbs.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.UUID;

/**
 * @author: Garibel.Lee
 * @ClassName: AuthorizeController
 * @Date:
 * @Description: TODO
 */
@Controller
@PropertySource({"classpath:application.yml"})
public class AuthorizeController {

    @Autowired
    private GithubProvider githubProvider;

    @Value("${github.client.id}")
    private String clientId;

    @Value("${github.client.secret}")
    private String clientSecret;

    @Value("${github.redirect.uri}")
    private String redirectUri;

    @Autowired
    private UserService userService;

    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state,
                           HttpServletResponse response,
                           Model model) {
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_secret(clientSecret);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri(redirectUri);
        accessTokenDTO.setState(state);
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        System.out.println("accessToken:" + accessToken);
        GithubUserDTO githubUserDTO = githubProvider.gerUser(accessToken);
        if (!ObjectUtils.isEmpty(githubUserDTO)) {
            //查询GitHub用户是否设置暴露的email信息
            if (!StringUtils.isEmpty(githubUserDTO.getEmail())) {
             //   List<User> userList = userService.selectAllByUserEmailAndAccountid(githubUserDTO.getEmail(),githubUserDTO.getId().toString());
                List<User> userList = userService.selectAllByUserEmail(githubUserDTO.getEmail());
                if (!ObjectUtils.isEmpty(userList)) {
                    User userLogin = userService.findUserLogin(userList.get(0));
                    if(userLogin.getUserStatus()==1){
                        model.addAttribute("message", "关联账号，涉嫌违规发，多次被举报现已封停");
                        return "error";
                    }
                    Cookie cookie = new Cookie("token", userLogin.getUserToken());
                    cookie.setMaxAge(60 * 60 * 24 * 30 * 6);
                    response.addCookie(cookie);
                    return "redirect:/";
                } else {
                    //为这个人注册账号

                    User userRegister = new User();
                    userRegister.setUserPassword("123456");
                    userRegister.setUserEmail(githubUserDTO.getEmail());
                    userRegister.setUserName(githubUserDTO.getName());
                    userRegister.setUserAccountid(githubUserDTO.getId().toString());
                    //token session cookies持久化登录的信息来源
                    String token = UUID.randomUUID().toString();
                    userRegister.setUserToken(token);
                    userRegister.setUserGmtcreate(System.currentTimeMillis());
                    userRegister.setUserGmtmodified(System.currentTimeMillis());
                    userRegister.setUserBio(githubUserDTO.getBio());
                    userRegister.setUserSex(0);
                    userRegister.setUserStatus(0);
                    userRegister.setUserCity("暂时无");
                    userRegister.setUserBalances(100);
                    userRegister.setUserAvatarurl(githubUserDTO.getAvatarUrl());

                    if (userService.insert(userRegister) != 0) {
                        User afterRegisterUser = userService.findUserLogin(userRegister);
                        Cookie cookie = new Cookie("token", afterRegisterUser.getUserToken());
                        cookie.setMaxAge(60 * 60 * 24 * 30 * 6);
                        response.addCookie(cookie);
                        return "redirect:/";
                    } else {
                        model.addAttribute("message", "github账户接入登录成功，注册本站时失败");
                        return "error";
                    }


                }

            } else {
                //登陆失败请重新登陆
                model.addAttribute("message", "登录有效，github账户位设置公开邮箱");
                return "error";
            }
        }else{
            model.addAttribute("message", "由于网络环境问题登陆失败，可能github账户不存在");
            return "error";
        }

    }
}
