package njwb.lcqjoyce.bbs.mapper;

import njwb.lcqjoyce.bbs.entity.Like;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface LikeMapper {
    int deleteByPrimaryKey(Long likeId);

    int insert(Like record);

    int insertSelective(Like record);

    Like selectByPrimaryKey(Long likeId);

    int updateByPrimaryKeySelective(Like record);

    int updateByByPostIDandReplyId(Like record);

    int updateByPrimaryKey(Like record);

    List<Like> selectAllByLikePostid(@Param("likePostid") Long likePostid);

    int deleteByLikePostid(@Param("likePostid")Long likePostid);



    List<Like> selectAllByLikePostidAndLikeReplyid(@Param("likePostid") Long likePostid, @Param("likeReplyid") Long likeReplyid);


}