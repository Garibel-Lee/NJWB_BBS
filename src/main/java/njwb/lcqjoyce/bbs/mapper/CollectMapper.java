package njwb.lcqjoyce.bbs.mapper;
import njwb.lcqjoyce.bbs.entity.Collect;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CollectMapper {
    int deleteByPrimaryKey(Long collectId);

    int insert(Collect record);

    int insertSelective(Collect record);

    Collect selectByPrimaryKey(Long collectId);

    int updateByPrimaryKeySelective(Collect record);

    int updateByPrimaryKey(Collect record);

    List<Collect> selectByCollectPostidAndCollectUserid(@Param("collectPostid")Long collectPostid,@Param("collectUserid")Long collectUserid);

    List<Collect> findAllByCollectUserid(@Param("collectUserid")Long collectUserid);

    int deleteByCollectPostid(@Param("collectPostid")Long collectPostid);




}