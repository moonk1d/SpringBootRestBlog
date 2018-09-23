package app.controllers;

import app.ExceptionHandler.exceptions.FieldIsRequiredException;
import app.ExceptionHandler.exceptions.IdException;
import app.ExceptionHandler.exceptions.ResourceNotFoundException;
import app.models.Post;
import app.models.User;
import app.services.interfaces.PostService;
import app.services.interfaces.UserService;
import app.validators.QueryParametersValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Created by Andrey Nazarov on 7/27/2018.
 */
@RestController
public class PostsController extends MainController {
    @Autowired
    private PostService postService;

    @Autowired
    private UserService userService;

    @GetMapping(value = "/posts/{id}", produces = "application/json")
    public Post viewPost(@PathVariable("id") String id) {
        QueryParametersValidator.validateIdQueryParameter(id);

        return postService.findById(Long.valueOf(id));
    }

    @GetMapping(value = "/posts", produces = "application/json")
    public ResponseEntity<List<Post>> listPosts(
            @RequestParam(value = "userId", required = false) String userId,
            @RequestParam(value = "limit", required = false) String limit,
            @RequestParam(value = "offset", required = false) String offset) {

        Pageable page = pageRequestBuilder(offset, limit);

        if (userId != null) {
            QueryParametersValidator.validateIdQueryParameter(userId);
        }

        User author;

        if (userId == null) {
            return new ResponseEntity<>(postService.findAll(page), HttpStatus.OK);
        } else {
            author = userService.findById(Long.valueOf(userId));

            return new ResponseEntity<>(postService.findAll(author, page), HttpStatus.OK);
        }
    }

    @PostMapping(value = "/posts")
    public ResponseEntity createPost(@Valid @RequestBody Post post) {
        checkRequiredFieldsForPost(post);

        post.setAuthor(userService.findById(post.getAuthor().getId()));

        return ResponseEntity
                .created(ServletUriComponentsBuilder
                        .fromCurrentRequest()
                        .path("/{id}")
                        .buildAndExpand(postService.create(post).getId())
                        .toUri())
                .build();
    }

    @PatchMapping(value = "/posts/{id}")
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

    @DeleteMapping(value = "/posts/{id}")
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

}
