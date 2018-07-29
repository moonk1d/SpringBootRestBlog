package app;

import app.models.Author;
import app.models.Book;
import app.models.Role;
import app.models.User;
import app.services.interfaces.AuthorService;
import app.services.interfaces.BookService;
import app.services.interfaces.RoleService;
import app.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * Created by Andrey on 25.07.2018.
 */
@Component
public class DataInitializer implements ApplicationRunner {
    @Autowired
    private PasswordEncoder passwordEncoder;

    private UserService userService;
    private RoleService roleService;
    private BookService bookService;
    private AuthorService authorService;

    @Autowired
    public DataInitializer(UserService userService, RoleService roleService, BookService bookService, AuthorService authorService) {
        this.userService = userService;
        this.roleService = roleService;
        this.bookService = bookService;
        this.authorService = authorService;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
            Role roleUser = new Role();
            roleUser.setId(1L);
            roleUser.setRole("USER_ROLE");
            Role roleAdmin = new Role();
            roleAdmin.setId(2L);
            roleAdmin.setRole("ADMIN_ROLE");

            roleService.create(roleUser);
            roleService.create(roleAdmin);

            System.out.println(roleService.findById(1L));
            System.out.println(roleService.findById(2L));

            User user = new User();
            user.setId(1L);
            user.setUsername("user");
            user.setPassword(passwordEncoder.encode("123"));
            user.setLastName("John");
            user.setRole(roleUser);

            User admin = new User();
            admin.setId(2L);
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("123"));
            admin.setPassword("Bob");
            admin.setLastName("Broker");
            admin.setRole(roleAdmin);

            userService.create(user);
            userService.create(admin);

            System.out.println(userService.findById(1L));
            System.out.println(userService.findById(2L));

            Author author1 = new Author();
            author1.setId(1L);
            author1.setName("John");
            author1.setLastName("Graham");

            Author author2 = new Author();
            author2.setId(2L);
            author2.setName("Jack");
            author2.setLastName("London");

            authorService.create(author1);
            authorService.create(author2);

            System.out.println(authorService.findById(1L));
            System.out.println(authorService.findById(2L));

            Book book1 = new Book();
            book1.setId(1L);
            book1.setName("The Firm");
            book1.setAuthor(author1);

            Book book2 = new Book();
            book2.setId(2L);
            book2.setName("David Coperfield");
            book2.setAuthor(author2);

            Book book3 = new Book();
            book3.setId(3L);
            book3.setName("White fang");
            book3.setAuthor(author2);

            bookService.create(book1);
            bookService.create(book2);
            bookService.create(book3);

            System.out.println(bookService.findById(1L));
            System.out.println(bookService.findById(2L));
            System.out.println(bookService.findById(3L));
    }
}