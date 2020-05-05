package njwb.lcqjoyce.bbs.service.impl;

import njwb.lcqjoyce.bbs.dto.CommentDTO;
import njwb.lcqjoyce.bbs.dto.QuestionDTO;
import njwb.lcqjoyce.bbs.entity.Comment;
import njwb.lcqjoyce.bbs.entity.User;
import njwb.lcqjoyce.bbs.enums.CommentTypeEnum;

import java.util.List;

public interface CommentService{


    int deleteByPrimaryKey(Long commentId);

    void insert(Comment record , User commentator) ;

    int insertSelective(Comment record);

    Comment selectByPrimaryKey(Long commentId);

    int updateByPrimaryKeySelective(Comment record);

    int updateByPrimaryKey(Comment record);

    List<CommentDTO> listByTargetId(QuestionDTO questionDTO, CommentTypeEnum type);


}
