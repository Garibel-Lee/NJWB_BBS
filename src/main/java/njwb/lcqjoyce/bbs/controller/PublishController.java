package njwb.lcqjoyce.bbs.controller;

import njwb.lcqjoyce.bbs.cache.TagCache;
import njwb.lcqjoyce.bbs.dto.QuestionDTO;
import njwb.lcqjoyce.bbs.dto.ResultDTO;
import njwb.lcqjoyce.bbs.entity.Question;
import njwb.lcqjoyce.bbs.entity.User;
import njwb.lcqjoyce.bbs.exception.CustomizeErrorCode;
import njwb.lcqjoyce.bbs.service.impl.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
public class PublishController {

    @Autowired
    private QuestionService questionService;
    @Autowired
    @Qualifier("stringRedisTemplate")
    RedisTemplate<String, String> rt;

    @GetMapping("/publish/{id}")
    public String edit(@PathVariable(name = "id") Long id, HttpServletRequest request,
                       Model model) {
        QuestionDTO questionDTO = questionService.selectByPrimaryKey(id);
        User user = (User) request.getSession().getAttribute("user");
        /* if (user.getId().equals(questionDTO.getUser().getId())) {*/
/*            model.addAttribute("question", questionDTO);
            model.addAttribute("title", questionDTO.getTitle());
            model.addAttribute("description", questionDTO.getDescription());
            model.addAttribute("tag", questionDTO.getTag());
            model.addAttribute("id", questionDTO.getId());*/
        model.addAttribute("tags", TagCache.get());
        return "publish";

    }

    @GetMapping("/publish")
    public String publish(Model model, HttpServletRequest request) {
        model.addAttribute("tags", TagCache.get());
        User user = (User) request.getSession().getAttribute("user");
        if (ObjectUtils.isEmpty(user)) {
            return "redirect:/";
        } else {
            return "publish";
        }
    }

    //校验验证码
    @RequestMapping(value = "/publish", method = RequestMethod.POST)
    @ResponseBody
    public Object doPublish(
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "content", required = false) String content,
            @RequestParam(value = "tag", required = false) String tag,
            @RequestParam(value = "id", required = false) Long id,
            @RequestParam(value = "code", required = false) String code,
            @RequestParam(value = "experience", required = false) Integer experience,
            HttpServletRequest request, Model model) {

        User user = (User) request.getSession().getAttribute("user");
        if (ObjectUtils.isEmpty(user)) {
            return ResultDTO.errorOf(CustomizeErrorCode.NO_LOGIN);
        } else {
            ValueOperations<String, String> forValue = rt.opsForValue();
            String old_yzm = forValue.get(request.getRemoteAddr() + "_publishCheck");
            if (old_yzm == null) {
                return ResultDTO.errorOf(CustomizeErrorCode.NULL_CODE);
            } else if (old_yzm.equals(code)) {
                Question question = new Question();
                question.setQuestionTitle(title);
                question.setQuestionDescription(content);
                question.setQuestionGmtcreate(System.currentTimeMillis());
                question.setQuestionGmtmodified(System.currentTimeMillis());
                question.setQuestionCreator(user.getUserId());
                question.setQuestionCommentcount(0);
                question.setQuestionViewcount(0);
                question.setQuestionLikecount(0);
                question.setQuestionTag(tag);
                question.setQuestionExpend(experience);
                question.setQuestionStatus(0);
                question.setQuestionTop(0);
                questionService.insert(question);
                return ResultDTO.okOf();
            } else {
                return ResultDTO.errorOf(CustomizeErrorCode.ERROR_CODE);
            }
        }
    }

}
