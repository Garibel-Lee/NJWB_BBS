package njwb.lcqjoyce.bbs.mapper;
import njwb.lcqjoyce.bbs.entity.Report;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ReportMapper {
    int deleteByPrimaryKey(Long reportId);

    int insert(Report record);

    int insertSelective(Report record);

    Report selectByPrimaryKey(Long reportId);

    int updateByPrimaryKeySelective(Report record);

    int updateByPrimaryKey(Report record);

    List<Report> selectByReportPostid(@Param("reportPostid")Long reportPostid);

    List<Report> selectAllByReportStatus(@Param("reportStatus")Integer reportStatus);

    List<Report> selectAll();





}