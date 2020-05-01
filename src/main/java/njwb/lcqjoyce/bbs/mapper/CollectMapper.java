package njwb.lcqjoyce.bbs.mapper;

import njwb.lcqjoyce.bbs.entity.Collect;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CollectMapper {
    int deleteByPrimaryKey(Long collectId);

    int insert(Collect record);

    int insertSelective(Collect record);

    Collect selectByPrimaryKey(Long collectId);

    int updateByPrimaryKeySelective(Collect record);

    int updateByPrimaryKey(Collect record);
}