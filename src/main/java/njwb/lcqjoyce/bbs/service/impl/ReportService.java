package njwb.lcqjoyce.bbs.service.impl;

import njwb.lcqjoyce.bbs.dto.ReportDTO;
import njwb.lcqjoyce.bbs.entity.Report;

import java.util.List;

public interface ReportService{


    int deleteByPrimaryKey(Long reportId);

    int insert(Report record);

    int insertSelective(Report record);

    Report selectByPrimaryKey(Long reportId);

    int updateByPrimaryKeySelective(Report record);

    int updateByPrimaryKey(Report record);

    Report selectByQuestionId(Long reportId);

    List<ReportDTO> selectAllreports();

    ReportDTO selectOneReportDto(Long reportId);

}
