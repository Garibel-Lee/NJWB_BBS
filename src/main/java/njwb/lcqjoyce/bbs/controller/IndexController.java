package njwb.lcqjoyce.bbs.controller;
import njwb.lcqjoyce.bbs.dto.PageinfoDTO;
import njwb.lcqjoyce.bbs.dto.QuestionDTO;
import njwb.lcqjoyce.bbs.dto.ResultDTO;
import njwb.lcqjoyce.bbs.entity.User;
import njwb.lcqjoyce.bbs.provider.FileUpload;
import njwb.lcqjoyce.bbs.service.impl.QuestionService;
import njwb.lcqjoyce.bbs.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;


/**
 * Created by LCQJOYCE on 2020.5.1.
 */
@Controller
public class IndexController {

    @Autowired
    private UserService userService;


    @Resource
    private QuestionService questionService;



    /*
    PageInfo这个类里面的属性:
            pageNum当前页
            pageSize每页的数量
            size当前页的数量
            orderBy排序
            startRow当前页面第一个元素在数据库中的行号
            endRow当前页面最后一个元素在数据库中的行号
            total总记录数(在这里也就是查询到的用户总数)
            pages总页数 (这个页数也很好算，每页5条，总共有11条，需要3页才可以显示完)
            list结果集
            prePage前一页
            nextPage下一页
            isFirstPage是否为第一页
            isLastPage是否为最后一页
            hasPreviousPage是否有前一页
            hasNextPage是否有下一页
            navigatePages导航页码数
            navigatepageNums所有导航页号
            navigateFirstPage导航第一页
            navigateLastPage导航最后一页
            firstPage第一页
            lastPage最后一页

    * */

    //主页控制
    @GetMapping("/")
    public String index(@RequestParam(defaultValue = "1") int page,
                        @RequestParam(defaultValue = "10") int size,
                        Model model) {
        PageinfoDTO<QuestionDTO> pagination = questionService.getAll("index",page, size);
/*        PaginationDTO pagination = questionService.list(search, page, size);
        model.addAttribute("pagination", pagination);
        model.addAttribute("search", search);*/
        /*问题主题*/
        if (pagination.getPages().size()==0){
            pagination.setPageinfo(-1,-1);
            pagination.setPage(0);
            pagination.setShowPrevious(false);
            pagination.setShowNext(false);
            pagination.setShowFirstPage(false);
            pagination.setShowEndPage(false);
            pagination.setTotalPage(-1);
        }
        model.addAttribute("pagination", pagination);
        return "index";
    }


    //主页控制
    @GetMapping("/top")
    public String top(@RequestParam(defaultValue = "1") int page,
                      @RequestParam(defaultValue = "10") int size,
                      Model model) {
/*        PaginationDTO pagination = questionService.list(search, page, size);
        model.addAttribute("pagination", pagination);
        model.addAttribute("search", search);*/
        /*问题主题*/
        PageinfoDTO<QuestionDTO> pagination = questionService.getAll("top",page, size);
        if (pagination.getPages().size()==0){
            pagination.setPageinfo(-1,-1);
            pagination.setPage(0);
            pagination.setShowPrevious(false);
            pagination.setShowNext(false);
            pagination.setShowFirstPage(false);
            pagination.setShowEndPage(false);
            pagination.setTotalPage(-1);
        }
        model.addAttribute("pagination", pagination);

        return "top";
    }

    //主页控制
    @GetMapping("/solved")
    public String solved(@RequestParam(defaultValue = "1") int page,
                         @RequestParam(defaultValue = "10") int size,
                         Model model) {
/*        PaginationDTO pagination = questionService.list(search, page, size);
        model.addAttribute("pagination", pagination);
        model.addAttribute("search", search);*/
        /*问题主题*/
        PageinfoDTO<QuestionDTO> pagination = questionService.getAll("solved",page, size);
        if (pagination.getPages().size()==0){
            pagination.setPageinfo(-1,-1);
            pagination.setPage(0);
            pagination.setShowPrevious(false);
            pagination.setShowNext(false);
            pagination.setShowFirstPage(false);
            pagination.setShowEndPage(false);
            pagination.setTotalPage(-1);
       }
        model.addAttribute("pagination", pagination);
        return "solved";
    }

    //主页控制
    @GetMapping("/unsolve")
    public String unsolve(@RequestParam(defaultValue = "1") int page,
                          @RequestParam(defaultValue = "10") int size,
                          Model model) {
/*        PaginationDTO pagination = questionService.list(search, page, size);
        model.addAttribute("pagination", pagination);
        model.addAttribute("search", search);*/
        /*问题主题*/
        PageinfoDTO<QuestionDTO> pagination = questionService.getAll("unsolve",page, size);
       /* if(pagination.getData())*/
        model.addAttribute("pagination", pagination);
        return "unsolve";
    }

    //登录之后可以查看其他用户的主页
    @GetMapping("/user/{id}")
    public String edit(@PathVariable(name = "id") Long id, HttpServletRequest request,
                       Model model) {
        User loginUser = (User) request.getSession().getAttribute("user");
        if (ObjectUtils.isEmpty(loginUser)) {
            return "redirect:/";
        }
        User anthor = userService.selectByPrimaryKey(id);
        if (ObjectUtils.isEmpty(anthor)) {
            return "redirect:/";
        }

        //查看自己去myIndex渲染
        if (loginUser.getUserId().equals(anthor.getUserId())) {
            return "myIndex";
        } else {
            model.addAttribute("user", anthor);
            return "userIndex";
        }
    }

    //保存文件
    @RequestMapping("/saveAvator")
    @ResponseBody
    public Object upload(MultipartFile file, HttpServletRequest request) {
        String fileName = FileUpload.saveFile(file, "/upload/img");
        String newAvatorUrl = "/img/" + fileName;
        User user = (User) request.getSession().getAttribute("user");
        user.setUserAvatarurl(newAvatorUrl);
        User updateUser = new User();
        updateUser.setUserId(user.getUserId());
        updateUser.setUserAvatarurl(newAvatorUrl);
        user.setUserAvatarurl(newAvatorUrl);
        userService.updateByPrimaryKeySelective(updateUser);
        request.getSession().setAttribute("user", user);
        return ResultDTO.okOf(200, "/img/" + fileName);
    }


}
