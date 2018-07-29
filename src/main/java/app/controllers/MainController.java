package app.controllers;

import app.models.Book;
import app.models.User;
import app.services.interfaces.BookService;
import app.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * Created by Andrey Nazarov on 7/27/2018.
 */
@Controller
public class MainController {
    @Autowired
    private BookService bookService;

    @Autowired
    private UserService userService;

    @RequestMapping("/") public String index(Model model) {
        List<Book> bookList = bookService.findAll();
        model.addAttribute("bookList", bookList);

        return "index";
    }

    @RequestMapping("/users") public String users(Model model) {
        List<User> userList = userService.findAll();
        model.addAttribute("userList", userList);

        return "users";
    }
}
