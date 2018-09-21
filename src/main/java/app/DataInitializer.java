package app;

import app.models.Comment;
import app.models.Post;
import app.models.Role;
import app.models.User;
import app.services.interfaces.CommentService;
import app.services.interfaces.PostService;
import app.services.interfaces.RoleService;
import app.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * Created by Andrey on 25.07.2018.
 */
@Component
public class DataInitializer implements ApplicationRunner {
    private UserService userService;
    private PostService postService;
    private RoleService roleService;
    private CommentService commentService;

    @Autowired
    public DataInitializer(UserService userService, PostService postService, RoleService roleService, CommentService commentService) {
        this.userService = userService;
        this.postService = postService;
        this.roleService = roleService;
        this.commentService = commentService;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Role roleUser = new Role("ROLE_USER");
        Role roleAdmin = new Role("ROLE_ADMIN");

        roleService.create(roleUser);
        roleService.create(roleAdmin);

        User user = new User();
        user.setName("user");
        user.setPassword("user");
        user.setRole(roleUser);

        User admin = new User();
        admin.setName("admin");
        admin.setPassword("admin");
        admin.setRole(roleAdmin);

        userService.create(user);
        userService.create(admin);

        Post postUser = new Post();
        postUser.setTitle("Spring Cloud1");
        postUser.setBody("On behalf of the community, I am pleased to announce that SR1 (Service Release) of the Spring Cloud Finchley Release Train is available today. The release can be found in Maven Central. You can check out the Finchley release notes for more information. \nhttps://codepen.io/Dentz/pen/wMYRXY");
        postUser.setAuthor(user);

        Thread.sleep(1);

        Post postUser1 = new Post();
        postUser1.setTitle("Spring Cloud2");
        postUser1.setBody("On behalf of the community, I am pleased to announce that SR1 (Service Release) of the Spring Cloud Finchley Release Train is available today. The release can be found in Maven Central. You can check out the Finchley release notes for more information. \nhttps://codepen.io/Dentz/pen/wMYRXY");
        postUser1.setAuthor(user);

        Thread.sleep(1);

        Post postAdmin1 = new Post();
        postAdmin1.setTitle("Spring Vault5");
        postAdmin1.setBody("On behalf of the community, I’m pleased to announce the availability of the first Spring Vault 2.1 milestone. This release ships with 47 tickets fixed and the highlights of this first milestone include:");
        postAdmin1.setAuthor(admin);

        Thread.sleep(1);

        Post postAdmin2 = new Post();
        postAdmin2.setTitle("Spring Vault6");
        postAdmin2.setBody("On behalf of the community, I’m pleased to announce the availability of the first Spring Vault 2.1 milestone. This release ships with 47 tickets fixed and the highlights of this first milestone include:");
        postAdmin2.setAuthor(admin);

        postService.create(postUser);
        postService.create(postUser1);
        postService.create(postAdmin1);
        postService.create(postAdmin2);

        Comment comment1 = new Comment("comment1", user, postUser);
        Thread.sleep(1);
        Comment comment2 = new Comment("comment2", admin, postAdmin1);
        Thread.sleep(1);
        Comment comment3 = new Comment("comment3", admin, postAdmin1);
        Thread.sleep(1);
        Comment comment4 = new Comment("comment4", admin, postAdmin1);
        Thread.sleep(1);
        Comment comment5 = new Comment("comment5", admin, postAdmin1);
        Thread.sleep(1);
        Comment comment6 = new Comment("comment6", admin, postAdmin1);
        Thread.sleep(1);
        Comment comment7 = new Comment("comment7", admin, postAdmin1);
        Thread.sleep(1);
        Comment comment8 = new Comment("comment8", admin, postAdmin1);

        commentService.create(comment1);
        commentService.create(comment2);
        commentService.create(comment3);
        commentService.create(comment4);
        commentService.create(comment5);
        commentService.create(comment6);
        commentService.create(comment7);
        commentService.create(comment8);
    }
}