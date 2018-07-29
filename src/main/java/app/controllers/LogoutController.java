package app.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Andrey Nazarov on 7/26/2018.
 */
@Controller
public class LogoutController {

    // logout
    @RequestMapping("/logout-successful")
    public String logout() {
        return "redirect:/";
    }
}
