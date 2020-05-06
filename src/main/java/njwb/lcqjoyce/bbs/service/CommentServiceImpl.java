package njwb.lcqjoyce.bbs.service;

import lombok.extern.slf4j.Slf4j;
import njwb.lcqjoyce.bbs.dto.CommentDTO;
import njwb.lcqjoyce.bbs.dto.CommentExsDTO;
import njwb.lcqjoyce.bbs.dto.QuestionDTO;
import njwb.lcqjoyce.bbs.entity.Comment;
import njwb.lcqjoyce.bbs.entity.Notification;
import njwb.lcqjoyce.bbs.entity.Question;
import njwb.lcqjoyce.bbs.entity.User;
import njwb.lcqjoyce.bbs.enums.CommentTypeEnum;
import njwb.lcqjoyce.bbs.enums.NotificationStatusEnum;
import njwb.lcqjoyce.bbs.enums.NotificationTypeEnum;
import njwb.lcqjoyce.bbs.exception.CustomizeErrorCode;
import njwb.lcqjoyce.bbs.exception.CustomizeException;
import njwb.lcqjoyce.bbs.mapper.CommentMapper;
import njwb.lcqjoyce.bbs.mapper.NotificationMapper;
import njwb.lcqjoyce.bbs.mapper.QuestionMapper;
import njwb.lcqjoyce.bbs.mapper.UserMapper;
import njwb.lcqjoyce.bbs.service.impl.CommentService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CommentServiceImpl implements CommentService {

    @Resource
    private CommentMapper commentMapper;
    @Resource
    private QuestionMapper questionMapper;
    @Resource
    private UserMapper userMapper;
    @Resource
    private NotificationMapper notificationMapper;

    @Override
    public int deleteByPrimaryKey(Long commentId) {
        return commentMapper.deleteByPrimaryKey(commentId);
    }

    @Override
    public void insert(Comment comment, User commentator) {
        if (comment.getCommentParentid() == null || comment.getCommentParentid() == 0) {
            throw new CustomizeException(CustomizeErrorCode.TARGET_PARAM_NOT_FOUND);
        }
        if (comment.getCommentType() == null || !CommentTypeEnum.isExist(comment.getCommentType())) {
            throw new CustomizeException(CustomizeErrorCode.TYPE_PARAM_WRONG);
        }
        if (comment.getCommentType().equals(CommentTypeEnum.COMMENT.getType())) {
            // 回复评论
            Comment dbComment = commentMapper.selectByPrimaryKey(comment.getCommentParentid());
            if (dbComment == null) {
                throw new CustomizeException(CustomizeErrorCode.COMMENT_NOT_FOUND);
            }

            // 回复问题
            Question question = questionMapper.selectByPrimaryKey(dbComment.getCommentParentid());
            if (question == null) {
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }

            commentMapper.insert(comment);

            // 增加评论数
            Comment parentComment = new Comment();
            parentComment.setCommentId(comment.getCommentParentid());

            commentMapper.addCommentCount(parentComment);

            //创建通知
             createNotify(comment, dbComment.getCommentCommentator(), commentator.getUserName(), question.getQuestionTitle(), NotificationTypeEnum.REPLY_COMMENT, question.getQuestionId());
        } else {
            // 回复问题
            Question question = questionMapper.selectByPrimaryKey(comment.getCommentParentid());
            if (question == null) {
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
            //CommentController 设计过了
            //comment.setCommentCount(0);
            commentMapper.insert(comment);
            //增加问题回复数量
            questionMapper.addCommentCount(question);

            // 创建通知
            createNotify(comment, question.getQuestionCreator(), commentator.getUserName(), question.getQuestionTitle(), NotificationTypeEnum.REPLY_QUESTION, question.getQuestionId());
        }
    }

    @Override
    public void  createNotify(Comment comment, Long receiver, String notifierName, String outerTitle, NotificationTypeEnum notificationType, Long outerId) {
        if (receiver .equals(comment.getCommentCommentator()) ) {
            return;
        }
        Notification notification = new Notification();
        notification.setNotificationGmtcreate(System.currentTimeMillis());
        notification.setNotificationType(notificationType.getType());
        notification.setNotificationOuterid(outerId);
        notification.setNotificationNotifier(comment.getCommentCommentator());
        notification.setNotificationStatus(NotificationStatusEnum.UNREAD.getStatus());
        notification.setNotificationReceiver(receiver);
        notification.setNotificationNotifiername(notifierName);
        notification.setNotificationOutertitle(outerTitle);
        notificationMapper.insert(notification);
    }



    @Override
    public int insertSelective(Comment record) {
        return commentMapper.insertSelective(record);
    }

    @Override
    public Comment selectByPrimaryKey(Long commentId) {
        return commentMapper.selectByPrimaryKey(commentId);
    }

    @Override
    public int updateByPrimaryKeySelective(Comment record) {
        return commentMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(Comment record) {
        return commentMapper.updateByPrimaryKey(record);
    }

    @Override    //所有评论查询
    public List<CommentDTO> listByTargetId(QuestionDTO questionDTO, CommentTypeEnum type) {
        Integer commentType;
        List<Comment> comments = new ArrayList<>();

        List<Long> questionId = new ArrayList<>();
        questionId.add(questionDTO.getQuestionId());
        //查出该问题所有一级回复
        List<Comment> replyQustions = commentMapper.findAllByCommentParentidAndCommentType(questionId, CommentTypeEnum.QUESTION.getType());
        //取出一级回复所有id集合 ，查询改集合每一条一级评论下的所有二级评论
        if (replyQustions.size() <= 0) {
            return null;
        }
        List<Long> commentIds = new ArrayList<>();
        //取出一级回复所有id集合 ，查询改集合每一条一级评论下的所有二级评论
        for (Comment replyQustion : replyQustions) {
            commentIds.add(replyQustion.getCommentId());
        }


        List<Comment> replyThisQuestionComments = commentMapper.findAllByCommentParentidAndCommentType(commentIds, CommentTypeEnum.COMMENT.getType());
        comments.addAll(replyQustions);
        comments.addAll(replyThisQuestionComments);

        Collections.sort(comments, (arg0, arg1) -> {
            Long count = (arg1.getCommentGmtcreate() - arg0.getCommentGmtcreate());
            if (count == 0) {
                return 0;
            }
            if (count > 0) {
                return 1;
            }
            return -1;
        });

        System.out.println("******查询问题下所有一级二级评论***时间倒序********");
        for (Comment comment : comments) {
            System.out.println(comment);
        }
        System.out.println("********查询问题下所有一级二级评论***********");


/*        //查询comment表中parentid的评论集合
        commentType = type.getType();
        List<Long> targetId = new ArrayList<>();
        targetId.add(questionDTO.getQuestionId());
        comments = commentMapper.findAllByCommentParentidAndCommentType(targetId, commentType);*/


        if (comments.size() == 0) {
            return new ArrayList<>();
        }
        // 获取去重的评论人
        Set<Long> commentators = comments.stream().map(comment -> comment.getCommentCommentator()).collect(Collectors.toSet());
        List<Long> userIds = new ArrayList();
        userIds.addAll(commentators);


        // 获取评论人并转换为 Map

        List<User> users = userMapper.findInId(userIds);
        Map<Long, User> userMap = users.stream().collect(Collectors.toMap(user -> user.getUserId(), user -> user));


        // 转换 comment 为 commentDTO   维护comment下的user实体类
        List<CommentDTO> commentDTOS = comments.stream().map(comment -> {
            CommentDTO commentDTO = new CommentDTO();
            BeanUtils.copyProperties(comment, commentDTO);
            commentDTO.setUser(userMap.get(comment.getCommentCommentator()));
            return commentDTO;
        }).collect(Collectors.toList());

        return commentDTOS;
    }

    @Override
    public List<CommentExsDTO> listmyCommentsByUserId(Long id) {
        List<Comment> commentList = commentMapper.findAllByCommentCommentatorAndCommentType(id,1);
        if(commentList.size()==0){
            return null;
        }

        List<CommentExsDTO> commentExsDTOs =new ArrayList<>();
        for (Comment comment : commentList) {
            CommentExsDTO commentExsDTO = new CommentExsDTO();
            BeanUtils.copyProperties(comment, commentExsDTO);
            commentExsDTO.setQuestion(questionMapper.selectByPrimaryKey(comment.getCommentParentid()));
            commentExsDTOs.add(commentExsDTO);
        }

        return commentExsDTOs;
    }

    @Override
    public int deleteByPrimaryKeyAndComments(Long questionId) {
        List<Long> questionIds = new ArrayList<>();
        questionIds.add(questionId);
        //查出该问题所有一级回复
        List<Comment> replyQustions = commentMapper.findAllByCommentParentidAndCommentType(questionIds, CommentTypeEnum.QUESTION.getType());
        //取出一级回复所有id集合 ，查询改集合每一条一级评论下的所有二级评论
        if (replyQustions.size() <= 0) {
            return 0;
        }
        List<Long> commentIds = new ArrayList<>();
        //取出一级回复所有id集合
        for (Comment replyQustion : replyQustions) {
            commentIds.add(replyQustion.getCommentId());
        }
        //查询改集合每一条一级评论下的所有二级评论
        List<Comment> replyThisQuestionComments = commentMapper.findAllByCommentParentidAndCommentType(commentIds, CommentTypeEnum.COMMENT.getType());
        for (Comment replyQustion : replyThisQuestionComments) {
            commentIds.add(replyQustion.getCommentId());
        }

        for (Long commentId : commentIds) {
            commentMapper.deleteByPrimaryKey(commentId);
        }
        questionMapper.deleteByPrimaryKey(questionId);
            return 0;
    }


}
