package njwb.lcqjoyce.bbs.mapper;
import org.apache.ibatis.annotations.Param;

import njwb.lcqjoyce.bbs.entity.Violation;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ViolationMapper {
    int deleteByPrimaryKey(Long violationId);

    int insert(Violation record);

    int insertSelective(Violation record);

    Violation selectByPrimaryKey(Long violationId);

    int updateByPrimaryKeySelective(Violation record);

    int updateByPrimaryKey(Violation record);

    Violation selectOneByViolationUserid(@Param("violationUserid")Long violationUserid);


}