package njwb.lcqjoyce.bbs.service;

import njwb.lcqjoyce.bbs.dto.ReportDTO;
import njwb.lcqjoyce.bbs.entity.Question;
import njwb.lcqjoyce.bbs.entity.Report;
import njwb.lcqjoyce.bbs.entity.User;
import njwb.lcqjoyce.bbs.mapper.QuestionMapper;
import njwb.lcqjoyce.bbs.mapper.ReportMapper;
import njwb.lcqjoyce.bbs.mapper.UserMapper;
import njwb.lcqjoyce.bbs.service.impl.ReportService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReportServiceImpl implements ReportService{

    @Resource
    private ReportMapper reportMapper;
    @Resource
    private QuestionMapper questionMapper;
    @Resource
    private UserMapper userMapper;
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
    public Report selectByQuestionId(Long reportId) {
        List<Report> reports = reportMapper.selectByReportPostid(reportId);
        if(reports.size()>0){
            return reports.get(0);
        }
        return null;
    }

    @Override
    public List<ReportDTO> selectAllreports() {
        List<Report> reports = reportMapper.selectAll();
        if(reports.size()==0){
            return null;
        }else {
            List<ReportDTO> reportDTOS=new ArrayList<>();
            for (Report report : reports) {
                //举报的发帖人id
                User postUser= userMapper.selectByPrimaryKey(report.getReportPostid());
                User reportUser=userMapper.selectByPrimaryKey(report.getReportReportuserid());
                Question question=questionMapper.selectByPrimaryKey(report.getReportPostid());
                ReportDTO reportDTO=new ReportDTO();
                BeanUtils.copyProperties(report, reportDTO);
                reportDTO.setPostUser(postUser);
                reportDTO.setPostQuestion(question);
                reportDTO.setReportUser(reportUser);
                reportDTOS.add(reportDTO);
            }
            return reportDTOS;
        }
    }


    @Override
    public ReportDTO selectOneReportDto(Long reportId) {
        Report report = reportMapper.selectByPrimaryKey(reportId);
        if(ObjectUtils.isEmpty(report)){
            return null;
        }else {

                User postUser= userMapper.selectByPrimaryKey(report.getReportPostuserid());
                User reportUser=userMapper.selectByPrimaryKey(report.getReportReportuserid());
                Question question=questionMapper.selectByPrimaryKey(report.getReportPostid());
                ReportDTO reportDTO=new ReportDTO();
                BeanUtils.copyProperties(report, reportDTO);
                reportDTO.setPostUser(postUser);
                reportDTO.setPostQuestion(question);
                reportDTO.setReportUser(reportUser);

            return reportDTO;
        }
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
