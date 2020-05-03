package njwb.lcqjoyce.bbs.controller;


import njwb.lcqjoyce.bbs.dto.PageinfoDTO;
import njwb.lcqjoyce.bbs.dto.QuestionDTO;
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


    //主页控制
    @GetMapping("/")
    public String index(@RequestParam(defaultValue = "1") int page,
                        @RequestParam(defaultValue = "10") int size,
                        Model model) {
/*        PaginationDTO pagination = questionService.list(search, page, size);
        model.addAttribute("pagination", pagination);
        model.addAttribute("search", search);*/
        /*问题主题*/
        PageinfoDTO<QuestionDTO> pagination = questionService.getAll(page, size);
        questionService.getAll(page, size);
        model.addAttribute("pagination", pagination);
        return "index";
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
    public void upload(MultipartFile file) {
        String fileName = FileUpload.saveFile(file, "/upload/img");
        System.out.println(fileName);
    }

}
