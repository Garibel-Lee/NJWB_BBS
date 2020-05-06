package njwb.lcqjoyce.bbs.controller;

import njwb.lcqjoyce.bbs.dto.NotificationDTO;
import njwb.lcqjoyce.bbs.entity.User;
import njwb.lcqjoyce.bbs.enums.NotificationTypeEnum;
import njwb.lcqjoyce.bbs.service.impl.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by codedrinker on 2019/6/14.
 */
@Controller
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @GetMapping("/notification/{id}")
    public String profile(HttpServletRequest request,
                          @PathVariable(name = "id") Long id) {

        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            return "redirect:/";
        }
        NotificationDTO notificationDTO = notificationService.read(id, user);

        if (NotificationTypeEnum.REPLY_COMMENT.getType() == notificationDTO.getNotificationType()
                || NotificationTypeEnum.REPLY_QUESTION.getType() == notificationDTO.getNotificationType()
                || NotificationTypeEnum.COLLECT_QUESTION.getType() == notificationDTO.getNotificationType()
                || NotificationTypeEnum.LOVE_QUESTION.getType() == notificationDTO.getNotificationType()) {
            return "redirect:/question/" + notificationDTO.getNotificationOuterid();
        } else {
            return "redirect:/";
        }
    }
}
