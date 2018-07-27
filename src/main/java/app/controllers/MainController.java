package app.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Andrey Nazarov on 7/27/2018.
 */
@Controller
public class MainController {
    @RequestMapping("/") public String index() {
        return "index";
    }
}
