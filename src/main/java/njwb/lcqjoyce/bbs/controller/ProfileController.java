package njwb.lcqjoyce.bbs.controller;


import njwb.lcqjoyce.bbs.dto.PageinfoDTO;
import njwb.lcqjoyce.bbs.dto.QuestionDTO;
import njwb.lcqjoyce.bbs.entity.User;
import njwb.lcqjoyce.bbs.service.impl.NotificationService;
import njwb.lcqjoyce.bbs.service.impl.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;


@Controller
public class ProfileController {
    @Autowired
    private QuestionService questionService;
    @Autowired
    private NotificationService notificationService;

    @GetMapping("/profile/{action}")
    public String profile(HttpServletRequest request,
                          @PathVariable(name = "action") String action,
                          Model model,
                          @RequestParam(name = "page", defaultValue = "1") Integer page,
                          @RequestParam(name = "size", defaultValue = "5") Integer size) {

        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            return "redirect:/";
        }
           /* PaginationDTO paginationDTO = questionService.list(user.getId(), page, size);
            model.addAttribute("pagination", paginationDTO);
                } else if ("replies".equals(action)) {
            PaginationDTO paginationDTO = notificationService.list(user.getId(), page, size);
            model.addAttribute("section", "replies");
            model.addAttribute("pagination", paginationDTO);*/
        if ("questions".equals(action)) {
            PageinfoDTO<QuestionDTO> pageinfoDTO = questionService.listMyQuestion(user.getUserId(), page, size);
            model.addAttribute("section", "questions");
            model.addAttribute("sectionName", "我的提问");
            model.addAttribute("sectionName", "最新回复");
            model.addAttribute("pagination", pageinfoDTO);
            return "mine";
        }
        if ("questions".equals(action)) {
            PageinfoDTO<QuestionDTO> pageinfoDTO = questionService.listMyQuestion(user.getUserId(), page, size);
            model.addAttribute("section", "questions");
            model.addAttribute("sectionName", "我的提问");
            model.addAttribute("sectionName", "最新回复");
            model.addAttribute("pagination", pageinfoDTO);
            return "index";
        }


        if ("replies".equals(action)) {
            //PageinfoDTO pageinfoDTO=notificationService.list(user.getId(),page,size);
            //Long unreadCount=notificationService.selectUnreadCount(user.getId());
            // model.addAttribute("section", "replies");
            // model.addAttribute("pagination", pageinfoDTO);
            // model.addAttribute("unreadCount", unreadCount);
            //model.addAttribute("sectionName", "我的回复");
        } else if ("myIndex".equals(action)) {
            return "myIndex";
        } else if ("myCenter".equals(action)) {
            return "myCenter";
        } else if ("mySet".equals(action)) {
            return "mySet";
        } else if ("myCenter".equals(action)) {
            return "myCenter";
        } else if ("myMessage".equals(action)) {
            return "myMessage";
        } else if ("myRecharge".equals(action)) {
            return "myRecharge";
        } else if ("report".equals(action)) {
            return "report";
        }
        return "redirect:/";


    }
}
