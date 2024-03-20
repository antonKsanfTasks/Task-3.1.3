package ru.itmentor.spring.boot_security.demo.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.itmentor.spring.boot_security.demo.model.User;

import javax.servlet.http.HttpServletRequest;

@Controller
public class MainController {
    @GetMapping("/loginPage")
    public String showLoginPage(HttpServletRequest request, Model model) {
        if (request.getSession().getAttribute("error") != null) {
            model.addAttribute("errorMessage", "Неправильное имя пользователя или пароль.");
            request.getSession().removeAttribute("error"); // очистить ошибку, чтобы она не оставалась в сессии
        }
        return "loginPage";
    }

    @GetMapping("/admin/adminStart")
    public String adminDashboard(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String firstName = null;

        if (auth.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) auth.getPrincipal();
            firstName = ((User) userDetails).getFirstName();
        }

        model.addAttribute("firstName", firstName);
        return "admin/adminStart";
    }

    @GetMapping("/user/userStart")
    public String userDashboard(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String firstName = null;

        if(auth.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) auth.getPrincipal();
            firstName = ((User) userDetails).getFirstName();
        }

        model.addAttribute("firstName", firstName);
        return "user/userStart";
    }
}
