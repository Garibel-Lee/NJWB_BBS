package njwb.lcqjoyce.bbs.controller;


import njwb.lcqjoyce.bbs.dto.QuestionDTO;
import njwb.lcqjoyce.bbs.entity.User;
import njwb.lcqjoyce.bbs.exception.CustomizeErrorCode;
import njwb.lcqjoyce.bbs.exception.CustomizeException;
import njwb.lcqjoyce.bbs.service.impl.CommentService;
import njwb.lcqjoyce.bbs.service.impl.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;

@Controller
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private CommentService commentService;

    @GetMapping("/question/{id}")
    public String question(@PathVariable(name = "id") String id, HttpServletRequest request,
                           Model model) {

        Long questionId;
        try {
            questionId = Long.parseLong(id);
        } catch (NumberFormatException e) {
            throw new CustomizeException(CustomizeErrorCode.INVALID_INPUT);
        }
        User loginUser = (User) request.getSession().getAttribute("user");
        //临时用用户
        if (ObjectUtils.isEmpty(loginUser)) {
            loginUser = new User();
            loginUser.setUserId(0L);
            model.addAttribute("user", loginUser);
        } else {
            model.addAttribute("user", loginUser);
        }
        QuestionDTO questionDTO = questionService.selectByPrimaryKey(questionId);
        // List<QuestionDTO> relateQuestions = questionService.selectRelated(questionDTO);

        //传参枚举类型1  ，名为回复问题的评论
        //List<CommentDTO> commentDTOS = commentService.findAllByParentIdAndType(questionId, CommentTypeEnum.QUSTION);
        // System.out.println(commentDTOS);

        //*增加阅读数
        questionService.inView(questionId);

        model.addAttribute("question", questionDTO);
        //  model.addAttribute("comments", commentDTOS);
        //    model.addAttribute("relatedQuestions", relateQuestions);
        return "question";
    }
}
