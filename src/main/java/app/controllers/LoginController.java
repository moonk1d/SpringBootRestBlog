package app.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Andrey on 21.07.2018.
 */
@Controller
public class LoginController {

    // Login form
    @RequestMapping("/login")
    public String login() {
        return "/login";
    }

    // Login form error
    @RequestMapping("/login-error")
    public String loginError(Model model) {
        model.addAttribute("loginError", true);

        return "/login";
    }

    // Login form success
    @RequestMapping("/login-success")
    public String loginSuccesss() {

        return "redirect:/";
    }
}