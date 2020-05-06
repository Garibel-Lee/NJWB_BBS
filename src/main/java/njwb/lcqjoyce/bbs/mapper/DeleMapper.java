package njwb.lcqjoyce.bbs.mapper;

import njwb.lcqjoyce.bbs.entity.Dele;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DeleMapper {
    int deleteByPrimaryKey(Long deletionId);

    int insert(Dele record);

    int insertSelective(Dele record);

    Dele selectByPrimaryKey(Long deletionId);

    int updateByPrimaryKeySelective(Dele record);

    int updateByPrimaryKey(Dele record);
}