package njwb.lcqjoyce.bbs.service;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import njwb.lcqjoyce.bbs.mapper.ReportMapper;
import njwb.lcqjoyce.bbs.entity.Report;
import njwb.lcqjoyce.bbs.service.impl.ReportService;
@Service
public class ReportServiceImpl implements ReportService{

    @Resource
    private ReportMapper reportMapper;

    @Override
    public int deleteByPrimaryKey(Long reportId) {
        return reportMapper.deleteByPrimaryKey(reportId);
    }

    @Override
    public int insert(Report record) {
        return reportMapper.insert(record);
    }

    @Override
    public int insertSelective(Report record) {
        return reportMapper.insertSelective(record);
    }

    @Override
    public Report selectByPrimaryKey(Long reportId) {
        return reportMapper.selectByPrimaryKey(reportId);
    }

    @Override
    public int updateByPrimaryKeySelective(Report record) {
        return reportMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(Report record) {
        return reportMapper.updateByPrimaryKey(record);
    }

}
