package app.controllers.UI;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Andrey Nazarov on 9/25/2018.
 */
@Controller
public class HomeController {

    @RequestMapping(value = "/")
    public String index(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        model.addAttribute("authorities", auth.getAuthorities());
        model.addAttribute("test", new String("test"));

        return "index.html";
    }
}