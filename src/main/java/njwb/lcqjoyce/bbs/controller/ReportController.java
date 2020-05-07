package njwb.lcqjoyce.bbs.controller;


import njwb.lcqjoyce.bbs.dto.QuestionDTO;
import njwb.lcqjoyce.bbs.dto.ReportDTO;
import njwb.lcqjoyce.bbs.entity.Audit;
import njwb.lcqjoyce.bbs.entity.Dele;
import njwb.lcqjoyce.bbs.entity.Report;
import njwb.lcqjoyce.bbs.entity.Violation;
import njwb.lcqjoyce.bbs.enums.NotificationTypeEnum;
import njwb.lcqjoyce.bbs.service.impl.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

@Controller
public class ReportController {


    @Autowired

    private ReportService reportService;
    @Autowired

    private ViolationService violationService;

    @Autowired

    private NotificationService notificationService;

    @Autowired
    private QuestionService questionService;
    @Autowired
    private DeleService  deleService;

    @Autowired
    private AuditService auditService;

    @Autowired
    private CommentService commentService;

    @GetMapping("/reportDetail/{id}")
    public String question(@PathVariable(name = "id") Long id,
                           Model model) {
        ReportDTO reportDTO = reportService.selectOneReportDto(id);

        model.addAttribute("reportDTO", reportDTO);
        return "reportDetail";
    }


    @GetMapping("/reportOver/{id}")
    public String reportOver(@PathVariable(name = "id") Long id,
                           Model model) {
        ReportDTO reportDTO = reportService.selectOneReportDto(id);
        Audit audit=auditService.findAuditBySome(reportDTO.getReportUser().getUserId(),reportDTO.getPostUser().getUserId(),reportDTO.getReportPostid());
        model.addAttribute("reportResult", audit.getAuditResult());
        model.addAttribute("reportDTO", reportDTO);
        return "reportOver";
    }

    //校验验证码
    @RequestMapping(value = "/reportOver", method = RequestMethod.POST)
    public String reportOver(
            @RequestParam(value = "reportId", required = false) Long  reportId,
            @RequestParam(value = "result", required = false) Integer result,
            Model model) {
        String reportSection;
        ReportDTO reportDTO = reportService.selectOneReportDto(reportId);
        if (ObjectUtils.isEmpty(reportId) || StringUtils.isEmpty(result)) {
            return "redirect:/";
        }
        Report report = reportService.selectByPrimaryKey(reportId);
            //方法名字有误 selectByUserID 被惩戒的postuseid
        Violation violation = violationService.selectByPrimaryKey(report.getReportPostuserid());
        if(result==1){
            reportSection="不惩罚被举报人";
            model.addAttribute("reportResult",reportSection);
            Report updatereport = new Report();
            updatereport.setReportId(reportId);
            updatereport.setReportStatus(1);
            reportService.updateByPrimaryKeySelective(updatereport);

        }
        if(result!=1) {


            if (result == 2) {
                reportSection = "惩罚被举报人不删除帖子";
                model.addAttribute("reportResult",reportSection);
                if (ObjectUtils.isEmpty(violation)) {
                    //第一次就创建
                    Violation updatevio = new Violation();
                    updatevio.setViolationTime(0);
                    updatevio.setViolationUserid(report.getReportPostuserid());
                    //创建记录惩罚记录
                    violationService.insert(updatevio);
                    notificationService.ViocreateNotify(reportDTO, reportSection, NotificationTypeEnum.REPORT_QUESTION);
                } else {
                    violation.setViolationTime(violation.getViolationTime() + 1);
                    violationService.updateByPrimaryKeySelective(violation);
                    notificationService.ViocreateNotify(reportDTO, reportSection, NotificationTypeEnum.REPORT_QUESTION);
                }

                //更更新举报处理表
                updateAudit(reportSection,report);

            }


            if (result == 3) {
                reportSection = "惩罚被举报人并删除该异常帖";
                model.addAttribute("reportResult",reportSection);
                //存在就加以不存在就是零
                if (ObjectUtils.isEmpty(violation)) {
                    Violation updatevio = new Violation();
                    updatevio.setViolationTime(0);
                    updatevio.setViolationUserid(report.getReportPostuserid());
                    violationService.insert(updatevio);
                    notificationService.ViocreateNotify(reportDTO, reportSection, NotificationTypeEnum.REPORT_QUESTION);
                } else {
                    violation.setViolationTime(violation.getViolationTime() + 1);
                    violationService.updateByPrimaryKeySelective(violation);
                    notificationService.ViocreateNotify(reportDTO, reportSection, NotificationTypeEnum.REPORT_QUESTION);
                }

                //删除question ID 所有的存在

                QuestionDTO questionDTO = questionService.selectByPrimaryKey(report.getReportPostid());


                Dele dele = new Dele();
                dele.setDeletionGmtcreatetime(System.currentTimeMillis());
                dele.setDeletionPostuserid(questionDTO.getQuestionCreator());
                dele.setDeletionPosttitle(questionDTO.getQuestionTitle());
                dele.setDeletionPostcontent(questionDTO.getQuestionDescription());
                dele.setDeletionRemark("管理员日常查房删除违规帖");
                deleService.insertSelective(dele);

                commentService.deleteByPrimaryKeyAndComments(questionDTO.getQuestionId());
         /*       questionService.deleteByPrimaryKey(questionDTO.getQuestionId());*/
                //更更新举报处理表
                updateAudit(reportSection,report);
            }

            Report updatereport = new Report();
            updatereport.setReportId(reportId);
            updatereport.setReportStatus(1);
            reportService.updateByPrimaryKeySelective(updatereport);


        }

        model.addAttribute("reportDTO",reportDTO);

        return "reportOver";
    }


     private void updateAudit(String section,Report report){
         //audit
         Audit audit=new Audit();
         audit.setAuditReportuserid(report.getReportReportuserid());
         audit.setAuditPostid(report.getReportPostid());
         audit.setAuditPostuserid(report.getReportPostuserid());
         audit.setAuditResult(section);
         audit.setAuditGmtcreatetime(System.currentTimeMillis());
         auditService.insert(audit);
    }
}
