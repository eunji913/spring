package com.example.spring.auth;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.spring.users.UsersService;
import com.example.spring.users.UsersVo;

@Controller
@RequestMapping("/auth")
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);  // SLF4J Logger

    @Autowired
    private UsersService usersService;

    @Autowired
    private AuthService authService;

    // 회원가입 페이지 (GET /register)
    @GetMapping("/register")
    public String registerPage() {
        return "auth/register";  // 회원가입 페이지로 이동
    }

    // 회원가입 처리 (POST 요청)
    @PostMapping("/register")
    public ModelAndView register(@ModelAttribute UsersVo usersVo, RedirectAttributes redirectAttributes) {
        ModelAndView mav = new ModelAndView();
        try {
            boolean created = usersService.create(usersVo);

            if (created) {
                redirectAttributes.addFlashAttribute("successMessage", "회원가입이 완료되었습니다.");
                mav.setViewName("redirect:/auth/login");
            } else {
                redirectAttributes.addFlashAttribute("errorMessage", "회원가입에 실패했습니다.");
                mav.setViewName("redirect:/auth/register");
            }
        } catch (Exception e) {
            logger.error("회원가입 처리 중 오류가 발생했습니다: ", e);  // 예외 로깅
            redirectAttributes.addFlashAttribute("errorMessage", "회원가입에 실패했습니다.");
            mav.setViewName("redirect:/auth/register");
        }
        return mav;
    }

    // 로그인 페이지 요청 (GET 요청)
    @GetMapping("/login")
    public ModelAndView loginPage() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("auth/login"); // 로그인 페이지 뷰 이름
        return mav;
    }

    // 로그인 처리 (POST 요청)
    @PostMapping("/login")
    public ModelAndView login(UsersVo usersVo, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        ModelAndView mav = new ModelAndView();
    
        try {
            // null 체크 추가
            if (usersVo == null || usersVo.getUserId() == null || usersVo.getPassword() == null) {
                redirectAttributes.addFlashAttribute("errorMessage", "아이디 또는 비밀번호가 입력되지 않았습니다.");
                mav.setViewName("redirect:/auth/login");
                return mav;
            }
    
            usersVo = authService.login(usersVo);
    
            if (usersVo != null) {
                HttpSession session = request.getSession(true);
                session.setAttribute("user", usersVo);
                session.setAttribute("isLoggedIn", true);
    
                logger.info("User {} logged in successfully.", usersVo.getUserId());  // 로그 기록
    
                mav.setViewName("redirect:/auth/profile");
                return mav;
            }
    
            redirectAttributes.addFlashAttribute("errorMessage", "아이디 또는 비밀번호가 일치하지 않습니다.");
            mav.setViewName("redirect:/auth/login");
    
        } catch (Exception e) {
            logger.error("Login error occurred for user {}: ", usersVo != null ? usersVo.getUserId() : "Unknown", e);  // 오류 로깅
            HttpSession session = request.getSession(false);
            if (session != null) session.invalidate();
            redirectAttributes.addFlashAttribute("errorMessage", "로그인 처리 중 오류가 발생했습니다.");
            mav.setViewName("redirect:/auth/login");
        }
    
        return mav;
    }

    // 로그아웃 (GET 요청)
    @GetMapping("/logout")
    public ModelAndView logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return new ModelAndView("redirect:/auth/login");
    }

    // 프로필 페이지 (GET 요청)
    @GetMapping("/profile")
    public ModelAndView profile() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("auth/profile");
        return mav;
    }


    // 프로필 업데이트 페이지를 GET 방식으로 처리
    @GetMapping("/update-profile")
    public ModelAndView updateProfilePage(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("user") != null) {
            UsersVo currentUser = (UsersVo) session.getAttribute("user");
            mav.addObject("user", currentUser);  // 현재 사용자 정보를 모델에 추가
        }
        mav.setViewName("auth/update_profile");
        return mav;
    }


    // 프로필 수정 (POST 요청)
    @PostMapping("/update-profile")
    public ModelAndView updateProfile(UsersVo usersVo, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        ModelAndView mav = new ModelAndView();

        try {
            boolean updated = usersService.update(usersVo);
            if (updated) {

                HttpSession session = request.getSession(false);
                if (session != null) {
                    UsersVo updatedUser = usersService.read(usersVo); // DB에서 최신 정보 가져오기
                    session.setAttribute("user", updatedUser); // 세션 업데이트
                }
                redirectAttributes.addFlashAttribute("successMessage", "프로필이 수정되었습니다.");
                mav.setViewName("redirect:/auth/profile");
            } else {
                redirectAttributes.addFlashAttribute("errorMessage", "프로필 수정에 실패했습니다.");
                mav.setViewName("redirect:/auth/update-profile");
            }
        } catch (Exception e) {
            logger.error("Profile update error occurred for user {}: ", usersVo.getUserId(), e);  // 오류 로깅
            redirectAttributes.addFlashAttribute("errorMessage", "프로필 수정 중 오류가 발생했습니다.");
            mav.setViewName("redirect:/auth/update-profile");
        }

        return mav;
    }

    // 비밀번호 업데이트 페이지를 GET 방식으로 처리
    @GetMapping("/update-password")
    public ModelAndView updatePasswordPage() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("auth/update_password");
        return mav;
    }

    // 비밀번호 수정 (POST 요청)
    @PostMapping("/update-password")
    public ModelAndView updatePassword(@RequestParam("password") String password,
                                       @RequestParam("password1") String password1,
                                       @RequestParam("password2") String password2,
                                       HttpServletRequest request, RedirectAttributes redirectAttributes) {
        ModelAndView mav = new ModelAndView();
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        try {
            HttpSession session = request.getSession(false);
            if (session == null || session.getAttribute("user") == null) {
                redirectAttributes.addFlashAttribute("errorMessage", "로그인이 필요합니다.");
                mav.setViewName("redirect:/auth/login");
                return mav;
            }
    
            UsersVo loggedInUser = (UsersVo) session.getAttribute("user");
            UsersVo latestUserData = usersService.read(loggedInUser); // DB에서 최신 데이터 가져오기
    
            if (password == null || password.trim().isEmpty()) {
                redirectAttributes.addFlashAttribute("errorMessage", "현재 비밀번호를 입력해주세요.");
                mav.setViewName("redirect:/auth/update-password");
                return mav;
            }

            // 현재 비밀번호 확인
            if (!passwordEncoder.matches(password, latestUserData.getPassword())) {
                redirectAttributes.addFlashAttribute("errorMessage", "현재 비밀번호가 일치하지 않습니다.");
                mav.setViewName("redirect:/auth/update-password");
                return mav;
            }

            // 새 비밀번호 길이 검증
            if (password1.length() < 8 || password1.length() > 20) {
                redirectAttributes.addFlashAttribute("errorMessage", "새 비밀번호는 8자 이상, 20자 이하로 입력하세요.");
                mav.setViewName("redirect:/auth/update-password");
                return mav;
            }

            if (!password1.equals(password2)) {
                redirectAttributes.addFlashAttribute("errorMessage", "새 비밀번호가 일치하지 않습니다.");
                mav.setViewName("redirect:/auth/update-password");
                return mav;
            }


            latestUserData.setPassword(passwordEncoder.encode(password1));
            boolean updated = usersService.updatePassword(latestUserData);

            if (updated) {
                // 최신 정보 다시 가져와서 세션에 반영
                UsersVo updatedUser = usersService.read(latestUserData);
                session.setAttribute("user", updatedUser);
    
                System.out.println("새 비밀번호 (DB 저장값): " + updatedUser.getPassword());
    
                redirectAttributes.addFlashAttribute("successMessage", "비밀번호가 성공적으로 변경되었습니다.");
                mav.setViewName("redirect:/auth/profile");
            } else {
                redirectAttributes.addFlashAttribute("errorMessage", "비밀번호 수정에 실패했습니다.");
                mav.setViewName("redirect:/auth/update-password");
            }
            
        } catch (Exception e) {
            logger.error("Password update error occurred: ", e);  // 오류 로깅
            redirectAttributes.addFlashAttribute("errorMessage", "비밀번호 수정 중 오류가 발생했습니다.");
            mav.setViewName("redirect:/auth/update-password");
        }

        return mav;
    }

    // 아이디 찾기
    @GetMapping("/find-user-id")
    public ModelAndView findUserId() {
        return new ModelAndView("auth/find_user_id");
    }

    // 아이디 찾기
    @PostMapping("/find-user-id")
    public ModelAndView findUserId(UsersVo usersVo, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        ModelAndView mav = new ModelAndView();

        UsersVo user = usersService.read(usersVo);        

        if (user != null) {
            redirectAttributes.addFlashAttribute("successMessage", "아이디는 " + user.getUserId() + "입니다.");
            mav.setViewName("redirect:/auth/find-user-id");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "아이디를 찾을 수 없습니다.");
            mav.setViewName("redirect:/auth/find-user-id");
        }

        return mav;
    }

    // 비밀번호 초기화
    @GetMapping("/reset-password")
    public ModelAndView resetPassword() {
        return new ModelAndView("auth/reset_password");
    }
    
    // 비밀번호 초기화
    @PostMapping("/reset-password")
    public ModelAndView resetPassword(UsersVo usersVo, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        ModelAndView mav = new ModelAndView();

        UsersVo user = usersService.read(usersVo);        

        if (user != null) {
            String rndPassword = authService.resetPassword(usersVo);
            redirectAttributes.addFlashAttribute("successMessage", "초기화된 비밀번호는 " + rndPassword + "입니다.");
            mav.setViewName("redirect:/auth/reset-password");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "아이디를 찾을 수 없습니다.");
            mav.setViewName("redirect:/auth/reset-password");
        }

        return mav;
    }

    
}
