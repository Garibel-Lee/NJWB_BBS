package njwb.lcqjoyce.bbs.mapper;

import njwb.lcqjoyce.bbs.entity.Report;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ReportMapper {
    int deleteByPrimaryKey(Long reportId);

    int insert(Report record);

    int insertSelective(Report record);

    Report selectByPrimaryKey(Long reportId);

    int updateByPrimaryKeySelective(Report record);

    int updateByPrimaryKey(Report record);
}