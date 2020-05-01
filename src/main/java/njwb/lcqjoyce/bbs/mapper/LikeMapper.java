package njwb.lcqjoyce.bbs.mapper;

import njwb.lcqjoyce.bbs.entity.Like;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LikeMapper {
    int deleteByPrimaryKey(Long likeId);

    int insert(Like record);

    int insertSelective(Like record);

    Like selectByPrimaryKey(Long likeId);

    int updateByPrimaryKeySelective(Like record);

    int updateByPrimaryKey(Like record);
}