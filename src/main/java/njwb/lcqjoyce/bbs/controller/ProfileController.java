package njwb.lcqjoyce.bbs.controller;


import njwb.lcqjoyce.bbs.dto.*;
import njwb.lcqjoyce.bbs.entity.*;
import njwb.lcqjoyce.bbs.exception.CustomizeErrorCode;
import njwb.lcqjoyce.bbs.service.impl.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;


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
    private ReportService reportService;
    @Autowired
    private CollectService collectService;
    @Autowired
    private CardService cardService;
    @Autowired
    private VipService vipService;

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
                if (userService.updateByPrimaryKeySelective(updateUser) != 0) {
                    return ResultDTO.okOf(200, "修改成功");
                } else {
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
            User loginUser = (User) request.getSession().getAttribute("user");
            if (ObjectUtils.isEmpty(loginUser)) {
                return "redirect:/";
            }
            model.addAttribute("user", loginUser);
            return "myIndex";


        } else if ("myCenter".equals(action)) {
            PageinfoDTO<QuestionDTO> questionDTOS = questionService.listMyQuestion(user.getUserId(), page, 15);
            if (questionDTOS.getPages().size() == 0) {
                questionDTOS.setPageinfo(-1, -1);
                questionDTOS.setPage(0);
                questionDTOS.setShowPrevious(false);
                questionDTOS.setShowNext(false);
                questionDTOS.setShowFirstPage(false);
                questionDTOS.setShowEndPage(false);
                questionDTOS.setTotalPage(-1);
            }
            List<Collect> collects = collectService.listMyCollsections(user.getUserId());
            List<CollectDTO> collectDTOS = new ArrayList<>();
            if (!ObjectUtils.isEmpty(collects)) {
                for (Collect collect : collects) {
                    CollectDTO collectDTO = new CollectDTO();
                    BeanUtils.copyProperties(collect, collectDTO);
                    Question question = questionService.selectById(collect.getCollectPostid());
                    collectDTO.setQuestion(question);
                    collectDTOS.add(collectDTO);
                }
            }


            model.addAttribute("collects", collectDTOS);
            model.addAttribute("questionPages", questionDTOS);


            return "myCenter";

        } else if ("mySet".equals(action)) {
            UserDTO userDTO = new UserDTO();
            BeanUtils.copyProperties(user, userDTO);
            Right rightUser = rightService.selectByUserId(user.getUserId());
            Role roleUser = roleService.selectByPrimaryKey(rightUser.getRightRoleid());
            Card cardUser = cardService.selectByUseId(user.getUserId());
            Vip vipUser = vipService.selectByUserId(user.getUserId());
            if (!ObjectUtils.isEmpty(rightUser)) {
                userDTO.setRight(rightUser);
            }
            if (!ObjectUtils.isEmpty(roleUser)) {
                userDTO.setRole(roleUser);
            }
            if (!ObjectUtils.isEmpty(cardUser)) {
                userDTO.setCard(cardUser);
            }
            if (!ObjectUtils.isEmpty(vipUser)) {
                userDTO.setVip(vipUser);
            }
            model.addAttribute("userDTO", userDTO);
            return "mySet";
        } else if ("myMessage".equals(action)) {
/*
            NotificationDTO notificationDTO = new NotificationDTO();
            notificationDTO.setNotificationId();
            notificationDTO.setNotificationNotifier();
            notificationDTO.setNotificationNotifiername();
            notificationDTO.setNotificationReceiver();
            notificationDTO.setNotificationOuterid();
            notificationDTO.setNotificationType();
            notificationDTO.setNotificationTypeName();
            notificationDTO.setNotificationGmtcreate();
            notificationDTO.setNotificationStatus();
            notificationDTO.setNotificationOutertitle();
*/

            PageinfoDTO<NotificationDTO> notificationDTOPages = new PageinfoDTO<>();
            notificationDTOPages=notificationService.list(user.getUserId(), page, size);
            if (notificationDTOPages.getPages().size() == 0) {
                notificationDTOPages.setPageinfo(-1, -1);
                notificationDTOPages.setPage(0);
                notificationDTOPages.setShowPrevious(false);
                notificationDTOPages.setShowNext(false);
                notificationDTOPages.setShowFirstPage(false);
                notificationDTOPages.setShowEndPage(false);
                notificationDTOPages.setTotalPage(-1);
            }
            model.addAttribute("section", "replies");
            model.addAttribute("sectionName", "最新回复");
            model.addAttribute("notificationPages", notificationDTOPages);
            return "myMessage";
        } else if ("myRecharge".equals(action)) {
            UserDTO userDTO = new UserDTO();
            BeanUtils.copyProperties(user, userDTO);
            Right rightUser = rightService.selectByUserId(user.getUserId());
            Role roleUser = roleService.selectByPrimaryKey(rightUser.getRightRoleid());
            Card cardUser = cardService.selectByUseId(user.getUserId());
            Vip vipUser = vipService.selectByUserId(user.getUserId());
            if (!ObjectUtils.isEmpty(rightUser)) {
                userDTO.setRight(rightUser);
            }
            if (!ObjectUtils.isEmpty(roleUser)) {
                userDTO.setRole(roleUser);
            }
            if (!ObjectUtils.isEmpty(cardUser)) {
                userDTO.setCard(cardUser);
            }
            if (!ObjectUtils.isEmpty(vipUser)) {
                userDTO.setVip(vipUser);
            }
            model.addAttribute("userDTO", userDTO);


            return "myRecharge";
        } else if ("report".equals(action)) {
            List<ReportDTO> reportDTOS = reportService.selectAllreports();

            model.addAttribute("reportDTOS", reportDTOS);

            return "report";
        }
        return "redirect:/";


    }
}
