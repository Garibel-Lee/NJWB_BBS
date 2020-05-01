package njwb.lcqjoyce.bbs.service.impl;

import njwb.lcqjoyce.bbs.entity.Report;
public interface ReportService{


    int deleteByPrimaryKey(Long reportId);

    int insert(Report record);

    int insertSelective(Report record);

    Report selectByPrimaryKey(Long reportId);

    int updateByPrimaryKeySelective(Report record);

    int updateByPrimaryKey(Report record);

}
