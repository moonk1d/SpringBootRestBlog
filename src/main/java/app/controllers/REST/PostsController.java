package app.controllers.REST;

import app.ExceptionHandler.exceptions.FieldIsRequiredException;
import app.models.Post;
import app.models.Posts;
import app.models.User;
import app.services.interfaces.PostService;
import app.services.interfaces.UserService;
import app.validators.QueryParametersValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by Andrey Nazarov on 7/27/2018.
 */
@RestController
@RequestMapping(value = "api/posts")
public class PostsController extends MainController {
    @Autowired
    private PostService postService;

    @Autowired
    private UserService userService;

    @GetMapping(value = "{id}", produces = "application/json")
    public Post viewPost(@PathVariable("id") String id) {
        QueryParametersValidator.validateIdQueryParameter(id);

        return postService.findById(Long.valueOf(id));
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<Posts> listPosts(
            @RequestParam(value = "limit", required = false) String limit,
            @RequestParam(value = "offset", required = false) String offset) {

        Pageable page = pageRequestBuilder(offset, limit);

        Posts posts = new Posts(postService.findAll(page));

        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    @GetMapping(value = "users/{id}", produces = "application/json")
    public ResponseEntity<List<Post>> listPostsByUser(
            @PathVariable("id") String id,
            @RequestParam(value = "limit", required = false) String limit,
            @RequestParam(value = "offset", required = false) String offset) {

        Pageable page = pageRequestBuilder(offset, limit);

        User author = userService.findById(Long.valueOf(id));

        return new ResponseEntity<>(postService.findAll(author, page), HttpStatus.OK);
    }


    @PostMapping
    public ResponseEntity createPost(@Valid @RequestBody Post post) {
        checkRequiredFieldsForPost(post);

        post.setAuthor(userService.findById(getCurrentUserId()));

        return ResponseEntity
                .created(ServletUriComponentsBuilder
                        .fromCurrentRequest()
                        .path("/{id}")
                        .buildAndExpand(postService.create(post).getId())
                        .toUri())
                .build();
    }

    @PatchMapping(value = "{id}")
    public ResponseEntity updatePost(@PathVariable("id") String id, @RequestBody Post post) {
        QueryParametersValidator.validateIdQueryParameter(id);
        Post updatedPost = postService.findById(Long.valueOf(id));

        if (post.getTitle() != null) {
            updatedPost.setTitle(post.getTitle());
        }

        if (post.getBody() != null) {
            updatedPost.setBody(post.getBody());
        }

        postService.create(updatedPost);

        return ResponseEntity
                .created(ServletUriComponentsBuilder
                        .fromCurrentRequest()
                        .build()
                        .toUri())
                .build();
    }

    @DeleteMapping(value = "{id}")
    public ResponseEntity deletePost(@PathVariable("id") String id) {
        QueryParametersValidator.validateIdQueryParameter(id);
        Post post = postService.findById(Long.valueOf(id));

        postService.deleteById(post.getId());

        return ResponseEntity.noContent().build();
    }

    public void checkRequiredFieldsForPost(Post post) {
        Long userId = post.getAuthor().getId();

        if (userId == null) {
            throw new FieldIsRequiredException("'author id' is required.");
        }
    }

    public Long getCurrentUserId() {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();

        return userService.findByUsername(name).getId();
    }

}
