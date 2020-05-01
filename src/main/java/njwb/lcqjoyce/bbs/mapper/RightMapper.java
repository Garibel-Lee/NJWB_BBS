package njwb.lcqjoyce.bbs.mapper;

import njwb.lcqjoyce.bbs.entity.Right;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RightMapper {
    int deleteByPrimaryKey(Integer rightId);

    int insert(Right record);

    int insertSelective(Right record);

    Right selectByPrimaryKey(Integer rightId);

    int updateByPrimaryKeySelective(Right record);

    int updateByPrimaryKey(Right record);
}