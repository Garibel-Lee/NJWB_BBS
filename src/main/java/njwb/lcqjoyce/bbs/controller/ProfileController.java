package njwb.lcqjoyce.bbs.controller;


import njwb.lcqjoyce.bbs.dto.PageinfoDTO;
import njwb.lcqjoyce.bbs.dto.QuestionDTO;
import njwb.lcqjoyce.bbs.dto.ResultDTO;
import njwb.lcqjoyce.bbs.dto.UserDTO;
import njwb.lcqjoyce.bbs.entity.Card;
import njwb.lcqjoyce.bbs.entity.Right;
import njwb.lcqjoyce.bbs.entity.Role;
import njwb.lcqjoyce.bbs.entity.User;
import njwb.lcqjoyce.bbs.exception.CustomizeErrorCode;
import njwb.lcqjoyce.bbs.service.impl.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


@Controller
public class ProfileController {
    @Autowired
    private UserService userService;
    @Autowired
    private QuestionService questionService;
    @Autowired
    private NotificationService notificationService;
    @Autowired
    private RightService rightService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private CardService cardService;

    @PostMapping("/profile/{action}")
    @ResponseBody
    public Object profile(HttpServletRequest request, @PathVariable(name = "action")
            String action,
                          String password,
                          String oldPassword,
                          String city,
                          String bio,
                          String nickname) {
        User user = (User) request.getSession().getAttribute("user");
        if (ObjectUtils.isEmpty(user)) {
            return "redirect:/";
        } else {
            if ("resetPassword".equals(action)) {
                if (oldPassword.equals(user.getUserPassword())) {
                    User updateUser = new User();
                    updateUser.setUserId(user.getUserId());
                    updateUser.setUserPassword(password);
                    userService.updateByPrimaryKeySelective(updateUser);
                    return ResultDTO.okOf(200, "修改成功");
                } else {
                    return ResultDTO.errorOf(500, "原密码不正确");
                }
            }

            if ("reSet".equals(action)) {

                User updateUser = new User();
                updateUser.setUserId(user.getUserId());
                updateUser.setUserBio(bio);
                updateUser.setUserCity(city);
                updateUser.setUserName(nickname);
               if( userService.updateByPrimaryKeySelective(updateUser)!=0){
                   return ResultDTO.okOf(200, "修改成功");
               }else {
                   return ResultDTO.okOf(200, "修改失败，请联系管理员");
               }

            }

            return ResultDTO.errorOf(CustomizeErrorCode.INVALID_OPERATION);
        }


    }


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
            PageinfoDTO<QuestionDTO> questionDTOS = questionService.listMyQuestion(user.getUserId(), page, size);
            if (questionDTOS.getPages().size() == 0) {
                questionDTOS.setPageinfo(-1, -1);
                questionDTOS.setPage(0);
                questionDTOS.setShowPrevious(false);
                questionDTOS.setShowNext(false);
                questionDTOS.setShowFirstPage(false);
                questionDTOS.setShowEndPage(false);
                questionDTOS.setTotalPage(-1);
            }
            model.addAttribute("section", "questions");
            model.addAttribute("pagination", questionDTOS);
            return "mine";
        }
      /*  if ("questions".equals(action)) {
            List<QuestionDTO>  questionDTOS = questionService.listMyQuestion(user.getUserId());
            model.addAttribute("section", "questions");
            model.addAttribute("sectionName", "我的提问");
            model.addAttribute("sectionName", "最新回复");
            model.addAttribute("pagination", questionDTOS);
            return "index";
        }*/


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
            UserDTO userDTO = new UserDTO();
            BeanUtils.copyProperties(user, userDTO);
            Right rightUser = rightService.selectByUserId(user.getUserId());
            Role roleUser = roleService.selectByPrimaryKey(rightUser.getRightRoleid());
            Card cardUser = cardService.selectByUseId(user.getUserId());

            userDTO.setRight(rightUser);
            userDTO.setRole(roleUser);
            userDTO.setCard(cardUser);

            model.addAttribute("userDTO", userDTO);
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
