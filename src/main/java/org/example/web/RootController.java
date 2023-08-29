package org.example.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class RootController {
    @GetMapping("/")
    public String root() {
        return "index";
    }

    @PostMapping("/restaurants")
    public String setUser(HttpServletRequest request) {
        int userId = Integer.parseInt(request.getParameter("userId"));
        SecurityUtil.setAuthUserId(userId);
        if (userId == 100000) {
            return "redirect:user/restaurants";
        } else {
            return "redirect:admin/restaurants";
        }
    }
}
