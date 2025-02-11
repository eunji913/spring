package com.example.spring.users;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/user")
public class UsersController {

    @Autowired
    private UsersService usersService;

    // 사용자 목록
    @GetMapping("/")
    public ModelAndView listGet(
        @RequestParam(defaultValue = "1") int page,
        @RequestParam(required = false) String searchType,
        @RequestParam(required = false) String searchKeyword
    ) {
        ModelAndView mav = new ModelAndView();
        Map<String, Object> result = usersService.list(page, searchType, searchKeyword);
        mav.addObject("userVoList", result.get("userVoList"));
        mav.addObject("pagination", result.get("pagination"));
        mav.setViewName("user/list");
        return mav;
    }      

    // 사용자 정보
    @GetMapping("/{userId}")
    public ModelAndView readGet(@PathVariable("userId") String userId) {
        ModelAndView mav = new ModelAndView();
        UsersVo usersVo = new UsersVo();
        usersVo.setUserId(userId);
        usersVo = usersService.read(usersVo);
        mav.addObject("usersVo", usersVo);
        mav.setViewName("user/read");
        return mav;
    }

    // 사용자 삭제
    @PostMapping("/{userId}/delete")
    public ModelAndView deletePost(UsersVo usersVo, String password, RedirectAttributes redirectAttributes) {
        ModelAndView mav = new ModelAndView();
        boolean result = usersService.delete(usersVo, password);
        if (result) {
            redirectAttributes.addFlashAttribute("successMessage", "사용자가 삭제되었습니다.");
            mav.setViewName("redirect:/user/");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "사용자 삭제에 실패하였습니다.");
            mav.setViewName("redirect:/user/read?userId=" + usersVo.getUserId());
        }
        return mav;
    }
}