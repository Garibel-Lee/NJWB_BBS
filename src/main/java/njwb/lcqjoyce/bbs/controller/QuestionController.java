package njwb.lcqjoyce.bbs.controller;


import njwb.lcqjoyce.bbs.dto.CommentDTO;
import njwb.lcqjoyce.bbs.dto.QuestionDTO;
import njwb.lcqjoyce.bbs.dto.UserDTO;
import njwb.lcqjoyce.bbs.entity.*;
import njwb.lcqjoyce.bbs.exception.CustomizeErrorCode;
import njwb.lcqjoyce.bbs.exception.CustomizeException;
import njwb.lcqjoyce.bbs.service.impl.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class QuestionController {

    @Autowired
    private QuestionService questionService;
    @Autowired
    private RightService rightService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private CardService cardService;
    @Autowired
    private VipService vipService;
    @Autowired
    private ReportService reportService;
    @Autowired
    private CollectService collectService;
    @Autowired
    private LikeService likeService;


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
        QuestionDTO questionDTO = questionService.selectByPrimaryKey(questionId);

        List<QuestionDTO> relatedQuestions = questionService.selectRelated(questionDTO);
        List<QuestionDTO> hotQuestions = questionService.selectHot(questionDTO);
        //  null全部评论               问题回复评论CommentTypeEnum.QUESTION
        List<CommentDTO> comments = commentService.listByTargetId(questionDTO,null);
        if(!ObjectUtils.isEmpty(comments)){
            for (CommentDTO comment : comments) {
                List<Like> objects = new ArrayList<>();
                objects=likeService.selectByCommentId(comment.getCommentId());
                comment.setLikes(objects);
            }
        }

        Map<String, Object> statusList = new HashMap<>();
        User loginUser = (User) request.getSession().getAttribute("user");
        statusList.put("topStatus", -1);         //置顶标记
        statusList.put("questionStaus", -1);   //问题状态标记
        statusList.put("setTopStatus", -1);      //设置置顶标记
        statusList.put("deleteQuestionStatus", -1); //删除标记
        statusList.put("reportStatus", -1);  //举报标记
        statusList.put("editStatus", -1);  //编辑标记
        statusList.put("collectStatus", -1);  //收藏标记
        statusList.put("replyStatus", -1);  //回复标记
        statusList.put("loveStatus", -1);
        //游客 普通用户  会员管理员   优先级最低
        //不需要任何权限
        statusList.put("topStatus", questionDTO.getQuestionTop());
        statusList.put("questionStatus", questionDTO.getQuestionStatus());

        if (ObjectUtils.isEmpty(loginUser)) {
            //未登录用户
            statusList.put("loveStatus", -1);
            model.addAttribute("userId", loginUser);
        } else {
            statusList.put("loveStatus", 0);//登录的用户可以点赞
            statusList.put("replyStatus", 0);  //回复标记
            UserDTO userDTO = new UserDTO();
            BeanUtils.copyProperties(loginUser, userDTO);
            Right rightUser = rightService.selectByUserId(loginUser.getUserId());
            Role roleUser = roleService.selectByPrimaryKey(rightUser.getRightRoleid());
            Card cardUser = cardService.selectByUseId(loginUser.getUserId());
            Vip vipUser = vipService.selectByUserId(loginUser.getUserId());

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

            //所有身份在登陆之后 可以举报帖子先查看帖子是否在report 之中
            if (ObjectUtils.isEmpty(reportService.selectByQuestionId(questionDTO.getQuestionId()))) {
                if(questionDTO.getQuestionCreator().equals(userDTO.getUserId())){
                    statusList.put("reportStatus", -1);
                }else {
                    statusList.put("reportStatus", 1);
                }
                //当前状态时未举报  设置  举报标记
            } else {
                statusList.put("reportStatus", 0);  //当前状态时已经举报 设置  已举报状态
            }
            //所有身份在登陆之后 可以自己的帖子可以编辑
            if (userDTO.getUserId().equals(questionDTO.getQuestionCreator())) {
                if (questionDTO.getQuestionStatus().equals(0)) {
                    statusList.put("editStatus", 0);  //可以编辑标记
                } else {
                    statusList.put("editStatus", 1);  //已经结帖，编辑标记
                }
            }
            //不是自己帖子可以进行收藏
            else {
               Integer collectSign=collectService.selectByQuestionIdandUserId(questionDTO.getQuestionId(),userDTO.getUserId());

               if(collectSign>0){
                   statusList.put("collectStatus", 1);  //可以编辑标记
               }else {
                   statusList.put("collectStatus", 0);  //可以不编辑标记
               }
            }

            //会员用户且是我的自己的帖子
            if (!ObjectUtils.isEmpty(userDTO.getVip()) && questionDTO.getQuestionCreator().equals(userDTO.getUserId())) {
                if (questionDTO.getQuestionTop() == 0) {
                    //设置置顶标记
                    statusList.put("setTopStatus", 0);
                } else {
                    statusList.put("setTopStatus", 1);
                }
            }

            //管理员用户 能够置顶所有帖子  删除帖子   管理员不显示举报按钮可以直接删帖
            if (!ObjectUtils.isEmpty(userDTO.getRole()) && userDTO.getRole().getRoleId().equals(3L)) {
                statusList.put("deleteQuestionStatus", 0);
                if (questionDTO.getQuestionTop() == 0) {
                    statusList.put("setTopStatus", 0);      //设置置顶标记
                } else {
                    statusList.put("setTopStatus", 1);
                }
                //reportStatus 0已举报  1为举报
                if(statusList.get("reportStatus").equals(0)){
                    statusList.put("reportStatus", 0);
                }else{
                    statusList.put("reportStatus", -1);
                }
            }

            //判断用户的权限如果是管理员 举报设置成-1，即管理员不能被举报
            Right selecRight = rightService.selectByUserId(questionDTO.getQuestionCreator());
            if(selecRight.getRightRoleid()==3){
                statusList.put("reportStatus", -1);
            }

            System.out.println(questionDTO);
            System.out.println(userDTO);
        }


        // List<QuestionDTO> relateQuestions = questionService.selectRelated(questionDTO)
        //传参枚举类型1  ，名为回复问题的评论
        //List<CommentDTO> commentDTOS = commentService.findAllByParentIdAndType(questionId, CommentTypeEnum.QUSTION);
        // System.out.println(commentDTOS);


        //*增加阅读数
        questionService.inView(questionId);
        //问题本体
        model.addAttribute("question", questionDTO);
        model.addAttribute("comments", comments);
        model.addAttribute("hotQuestions", hotQuestions);
        model.addAttribute("relatedQuestions", relatedQuestions);
        model.addAttribute("statusList",statusList);

        return "question";
    }
}
