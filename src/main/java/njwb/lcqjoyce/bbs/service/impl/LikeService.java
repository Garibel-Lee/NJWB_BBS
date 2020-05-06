package njwb.lcqjoyce.bbs.service.impl;

import njwb.lcqjoyce.bbs.entity.Like;

import java.util.List;

public interface LikeService{


    int deleteByPrimaryKey(Long likeId);

    int insert(Like record);

    int insertSelective(Like record);

    Like selectByPrimaryKey(Long likeId);

    int updateByPrimaryKeySelective(Like record);

    int updateByPrimaryKey(Like record);

    List<Like> selectByCommentId(Long commentId);

    Like selectByPostIDandReplyId(Long postId,Long replyId);

     int updateByByPostIDandReplyId(Like record);
}
