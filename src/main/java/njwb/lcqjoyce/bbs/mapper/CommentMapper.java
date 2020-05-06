package njwb.lcqjoyce.bbs.mapper;
import njwb.lcqjoyce.bbs.entity.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CommentMapper {
    int deleteByPrimaryKey(Long commentId);

    int insert(Comment record);

    int insertSelective(Comment record);

    Comment selectByPrimaryKey(Long commentId);

    int updateByPrimaryKeySelective(Comment record);

    int updateByPrimaryKey(Comment record);

    int addCommentCount(Comment parentComment);

    List<Comment> findAllByCommentParentidAndCommentType(@Param("commentParentids") List<Long> commentParentids,@Param("commentType")Integer commentType);

    List<Comment> findAllByCommentCommentatorAndCommentType(@Param("commentCommentator")Long commentCommentator,@Param("commentType")Integer commentType);

    List<Comment> deleteInIds(@Param("commentIds") List<Long> commentIds);


}