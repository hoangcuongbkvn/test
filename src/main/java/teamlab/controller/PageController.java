package teamlab.controller;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import teamlab.model.response.UserPage;
import teamlab.model.service.PageService;

@Controller
public class PageController {
    @Autowired
    private PageService pageService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index() {
        return "index";
    }

    //page?keyword=テスト
    @RequestMapping(value = "/page", method = RequestMethod.GET)
    @Transactional
    public String get(Model model, @RequestParam("keyword") String keyword) {
        long start = System.currentTimeMillis();

        List<UserPage> list = pageService.findUserViewedPage(keyword);

        long estimatedTime = System.currentTimeMillis() - start;
        List<UserPage> subList = list.size() > 15 ? list.subList(0, 15) : list;
        model.addAttribute("userpages", subList);
        model.addAttribute("searchtime", estimatedTime);
        model.addAttribute("result", list.size());

        return "page";
    }
}