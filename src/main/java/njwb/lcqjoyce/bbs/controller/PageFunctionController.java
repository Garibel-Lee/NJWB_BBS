package njwb.lcqjoyce.bbs.controller;


import njwb.lcqjoyce.bbs.dto.QuestionDTO;
import njwb.lcqjoyce.bbs.dto.ResultDTO;
import njwb.lcqjoyce.bbs.dto.UserDTO;
import njwb.lcqjoyce.bbs.entity.*;
import njwb.lcqjoyce.bbs.service.impl.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;

@Controller
public class PageFunctionController {

    @Autowired
    private LikeService likeService;
    @Autowired
    private UserService userService;
    @Autowired
    private CardService cardService;
    @Autowired
    private QuestionService questionService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private ReportService reportService;
    @Autowired
    private NotificationService notificationService;

    @Autowired
    private CollectService collectService;

    @RequestMapping("/report")
    @ResponseBody
    public Object reportQuestion(HttpServletRequest request, Long questionId, Long questionUserId, String reportTile, String reportReason) {

        User user = (User) request.getSession().getAttribute("user");
        if (!ObjectUtils.isEmpty(reportService.selectByQuestionId(questionId))) {
            return ResultDTO.okOf(200, "已经有人举报过了");
        }
        if (StringUtils.isEmpty(reportTile) || StringUtils.isEmpty(reportTile)) {
            return ResultDTO.okOf(200, "举报失败，缺少举报信息");
        }
        Report report = new Report();
        report.setReportPostid(questionId);
        report.setReportPostuserid(questionUserId);
        report.setReportReportuserid(user.getUserId());
        report.setReportTitle(reportTile);
        report.setReportAccount(reportReason);
        report.setReportStatus(0);
        report.setReportGmtcreatetime(System.currentTimeMillis());
        if (reportService.insert(report) != 0) {
            return ResultDTO.okOf(200, "举报成功");
        } else {
            return ResultDTO.okOf(200, "举报失败，请稍后再试");

        }
    }


    @RequestMapping("/topQuestion")
    @ResponseBody
    public Object topQuestion(HttpServletRequest request, Long questionId) {
        User user = (User) request.getSession().getAttribute("user");
        QuestionDTO questionDTO = questionService.selectByPrimaryKey(questionId);
        Question updatequestion = new Question();
        updatequestion.setQuestionTop(1);
        updatequestion.setQuestionId(questionDTO.getQuestionId());
        if (questionService.updateByPrimaryKeySelective(updatequestion) == 0) {
            return ResultDTO.errorOf(500, "置顶失败");
        } else {
            return ResultDTO.errorOf(200, "置顶成功");
        }
    }

    //
    @RequestMapping("/untopQuestion")
    @ResponseBody
    public Object untopQuestion(HttpServletRequest request, Long questionId) {
        User user = (User) request.getSession().getAttribute("user");
        QuestionDTO questionDTO = questionService.selectByPrimaryKey(questionId);
        Question updatequestion = new Question();
        updatequestion.setQuestionTop(0);
        updatequestion.setQuestionId(questionDTO.getQuestionId());
        if (questionService.updateByPrimaryKeySelective(updatequestion) == 0) {
            return ResultDTO.errorOf(500, "取消置顶失败");
        } else {
            return ResultDTO.errorOf(200, "取消置顶成功");
        }
    }


    //
    @RequestMapping("/collectQuestion")
    @ResponseBody
    public Object collectQuestion(HttpServletRequest request, Long questionId) {
        User user = (User) request.getSession().getAttribute("user");
        QuestionDTO questionDTO = questionService.selectByPrimaryKey(questionId);

        Collect collect = new Collect();
        collect.setCollectPostid(questionId);
        collect.setCollectPostuserid(questionDTO.getQuestionCreator());
        collect.setCollectUserid(user.getUserId());
        collect.setCollectGmtcreatetime(System.currentTimeMillis());
        if (collectService.insert(collect) == 0) {
            return ResultDTO.errorOf(500, "收藏失败");
        } else {
            collectService.createNotify(collect, user.getUserName(), questionDTO.getQuestionTitle());
            return ResultDTO.errorOf(200, "收藏成功");
        }
    }

    //
    @RequestMapping("/unCollectQuestion")
    @ResponseBody
    public Object unCollectQuestion(HttpServletRequest request, Long questionId) {
        User user = (User) request.getSession().getAttribute("user");
        QuestionDTO questionDTO = questionService.selectByPrimaryKey(questionId);


        Collect collect = collectService.selectByQuestIdandUId(questionId, user.getUserId());
        if (ObjectUtils.isEmpty(collect)) {
            return ResultDTO.errorOf(500, "没找到 ，移除收藏失败");
        } else {
            if (collectService.deleteByPrimaryKey(collect.getCollectId()) == 0) {
                return ResultDTO.errorOf(502, "找到了， 移除收藏失败");
            } else {
                return ResultDTO.okOf(200, "找到了， 移除收藏成功");
            }

        }

    }

    //
    @RequestMapping("/deleteQustion")
    @ResponseBody
    public Object deleteQustion(HttpServletRequest request, Long questionId) {
        User user = (User) request.getSession().getAttribute("user");
        QuestionDTO questionDTO = questionService.selectByPrimaryKey(questionId);
        Dele dele = new Dele();
        dele.setDeletionGmtcreatetime(System.currentTimeMillis());
        dele.setDeletionPostuserid(questionDTO.getQuestionCreator());
        dele.setDeletionPosttitle(questionDTO.getQuestionTitle());
        dele.setDeletionPostcontent(questionDTO.getQuestionDescription());
        dele.setDeletionRemark("管理员日常查房删除违规帖");

        commentService.deleteByPrimaryKeyAndComments(questionDTO.getQuestionId());

        return ResultDTO.errorOf(200, "删除成功，管理员日常查房删除违规");

    }

    @RequestMapping("/deleteMessage")
    @ResponseBody
    public Object deleteMessage(HttpServletRequest request, Long questionId) {
        User user = (User) request.getSession().getAttribute("user");
        notificationService.deleteByNotificationReceiver(user.getUserId());
        return ResultDTO.errorOf(200, "删除成功");

    }


    //
    @RequestMapping("/userThisComment")
    @ResponseBody
    @Transactional
    public Object userThisComment(HttpServletRequest request, Long questionId, Long commentId) {
        User user = (User) request.getSession().getAttribute("user");
        QuestionDTO questionDTO = questionService.selectByPrimaryKey(questionId);

        Question updateQuestion = new Question();
        updateQuestion.setQuestionId(questionDTO.getQuestionId());
        updateQuestion.setQuestionStatus(1);
        Integer Aresult = questionService.updateByPrimaryKeySelective(updateQuestion);
        Comment comment = commentService.selectByPrimaryKey(commentId);


        //修复了飞吻值
        User updateUser = userService.selectByPrimaryKey(comment.getCommentCommentator());
        updateUser.setUserId(comment.getCommentCommentator());
        updateUser.setUserBalances(questionDTO.getQuestionExpend() + updateUser.getUserBalances());
        Integer Cresult = userService.updateByPrimaryKeySelective(updateUser);

        Comment updateComment = new Comment();
        updateComment.setCommentId(comment.getCommentId());
        updateComment.setCommentStatus(1);
        Integer Bresult = commentService.updateByPrimaryKeySelective(updateComment);

        if (Aresult != 0 && Bresult != 0 && Cresult != 0) {

            /*commentService.createNotify(comment,);*/


            return ResultDTO.errorOf(200, "采纳成功");

        } else {
            return ResultDTO.errorOf(200, "采纳失败");
        }


    }


    @RequestMapping("/bankCardCharge")
    @ResponseBody
    public Object bankCardCharge(HttpServletRequest request, String cardNumber, String password, BigDecimal comount) {
        UserDTO user = (UserDTO) request.getSession().getAttribute("userDTO");


        System.out.println((user.getCard().getCardId().toString().equals(cardNumber)));

        if (!user.getCard().getCardPwd().equals(password)) {
            return ResultDTO.errorOf(222, "密码不对");
        }

        if (user.getCard().getCardBalance().subtract(comount).compareTo(BigDecimal.ZERO) == -1) {
            return ResultDTO.errorOf(222, "银行卡钱不够了");
        }
        User updateUser = new User();
        updateUser.setUserId(user.getUserId());
        updateUser.setUserBalances(user.getUserBalances()+comount.multiply(new BigDecimal(10)).intValue());
        userService.updateByPrimaryKeySelective(updateUser);
        Card updateCard = new Card();
        updateCard.setCardId(user.getCard().getCardId());
        updateCard.setCardUserid(user.getCard().getCardUserid());
        updateCard.setCardBalance(user.getCard().getCardBalance().subtract(comount));
        cardService.updateByPrimaryKeySelective(updateCard);
        return ResultDTO.errorOf(200, "银行卡充值成功");

    }


    @RequestMapping("/lovethisComment")
    @ResponseBody
    public Object lovethisComment(HttpServletRequest request, Long commentId, Long replyId) {
        UserDTO user = (UserDTO) request.getSession().getAttribute("userDTO");
        Like like = new Like();
        if (commentId.equals(null)) {
            return ResultDTO.errorOf(200, "信号不好丢了点数据");
        }
        if (replyId.equals(null)) {
            return ResultDTO.errorOf(200, "信号不好丢了点数据");
        }
        like.setLikePostid(commentId);
        like.setLikeReplyid(replyId);
        Like selectLike = likeService.selectByPostIDandReplyId(commentId, replyId);
        if (ObjectUtils.isEmpty(selectLike)) {
            like.setLikeStatus(1);
            likeService.insert(like);
            return ResultDTO.errorOf(200, "点赞");
        } else {
            if (selectLike.getLikeStatus() == 1) {
                likeService.deleteByPrimaryKey(selectLike.getLikeId());
                return ResultDTO.errorOf(200, "取消赞");
            }
            return ResultDTO.errorOf(200, "点赞");
        }


    }

}
