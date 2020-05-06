package njwb.lcqjoyce.bbs.service.impl;

import njwb.lcqjoyce.bbs.dto.CommentDTO;
import njwb.lcqjoyce.bbs.dto.CommentExsDTO;
import njwb.lcqjoyce.bbs.dto.QuestionDTO;
import njwb.lcqjoyce.bbs.entity.Comment;
import njwb.lcqjoyce.bbs.entity.User;
import njwb.lcqjoyce.bbs.enums.CommentTypeEnum;
import njwb.lcqjoyce.bbs.enums.NotificationTypeEnum;

import java.util.List;

public interface CommentService{


    int deleteByPrimaryKey(Long commentId);

    void insert(Comment record , User commentator) ;

    int insertSelective(Comment record);

    Comment selectByPrimaryKey(Long commentId);

    int updateByPrimaryKeySelective(Comment record);

    int updateByPrimaryKey(Comment record);

    List<CommentDTO> listByTargetId(QuestionDTO questionDTO, CommentTypeEnum type);

    List<CommentExsDTO> listmyCommentsByUserId(Long id);

    int deleteByPrimaryKeyAndComments(Long questionId);

     void  createNotify(Comment comment, Long receiver, String notifierName, String outerTitle, NotificationTypeEnum notificationType, Long outerId);
}
