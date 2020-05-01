package njwb.lcqjoyce.bbs.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


/**
 * Created by LCQJOYCE on 2020.
 */
@Controller
public class IndexController {


    @GetMapping("/")
    public String index() {
/*        PaginationDTO pagination = questionService.list(search, page, size);
        model.addAttribute("pagination", pagination);
        model.addAttribute("search", search);*/
        return "postIndex";
    }
}
