package app.controllers;

import app.ExceptionHandler.exceptions.FieldIsRequiredException;
import app.ExceptionHandler.exceptions.IdException;
import app.ExceptionHandler.exceptions.ResourceNotFoundException;
import app.models.Comment;
import app.models.Post;
import app.models.User;
import app.services.interfaces.CommentService;
import app.services.interfaces.PostService;
import app.services.interfaces.UserService;
import app.validators.QueryParametersValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Date;
import java.util.List;

/**
 * Created by Andrey Nazarov on 7/27/2018.
 */
@RestController
public class CommentsController extends MainController {
    @Autowired
    private CommentService commentService;

    @Autowired
    private PostService postService;

    @Autowired
    private UserService userService;

    @GetMapping(value = "/comments/{id}", produces = "application/json")
    public Comment viewComment(@PathVariable("id") String id) {
        if (id != null) {
            QueryParametersValidator.validateIdQueryParameter(id);
        }

        Comment comment = commentService.findById(Long.valueOf(id));
        if (comment == null) {
            throw new ResourceNotFoundException();
        }

        return comment;
    }

    @GetMapping(value = "/comments", produces = "application/json")
    public ResponseEntity<List<Comment>> listComments(
            @RequestParam(value = "postId", required = false) String postId,
            @RequestParam(value = "userId", required = false) String userId,
            @RequestParam(value = "limit", required = false) String limit,
            @RequestParam(value = "offset", required = false) String offset) {

        Pageable page = pageRequestBuilder(offset, limit);

        if (postId != null) {
            QueryParametersValidator.validateIdQueryParameter(postId);
        }

        if (userId != null) {
            QueryParametersValidator.validateIdQueryParameter(userId);
        }

        Post post;
        User author;

        if (postId == null && userId == null) {
            return new ResponseEntity<>(commentService.findAll(page), HttpStatus.OK);
        } else if (postId != null && userId == null) {
            post = postService.findById(Long.valueOf(postId));

            return new ResponseEntity<>(commentService.findByPost(post, page), HttpStatus.OK);
        } else if (userId != null && postId == null) {
            author = userService.findById(Long.valueOf(userId));

            return new ResponseEntity<>(commentService.findByAuthor(author, page), HttpStatus.OK);
        } else {
            post = postService.findById(Long.valueOf(postId));
            author = userService.findById(Long.valueOf(userId));

            return new ResponseEntity<>(commentService.findByAuthorAndPost(post, author, page), HttpStatus.OK);
        }
    }

    @PostMapping(value = "/comments")
    public ResponseEntity createComment(@RequestBody Comment comment) {
        checkRequiredFieldsForComment(comment);

        Post post = postService.findById(comment.getPost().getId());
        User author = userService.findById(comment.getAuthor().getId());

        if (post == null) {
            throw new ResourceNotFoundException();
        }

        if (author == null) {
            throw new ResourceNotFoundException();
        }

        comment.setPost(post);
        comment.setAuthor(author);
        Long newCommentId = commentService.create(comment).getId();

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newCommentId).toUri();

        return ResponseEntity.created(location).build();
    }

    @PatchMapping(value = "/comments/{id}")
    public ResponseEntity updateComment(@PathVariable("id") String id, @RequestBody Comment comment) {

        if (id == null) {
            throw new IdException();
        }

        Comment updatedComment = commentService.findById(Long.valueOf(id));

        if (updatedComment == null) {
            throw new ResourceNotFoundException();
        }

        if (comment.getContent() != null) {
            updatedComment.setContent(comment.getContent());
        }

        commentService.create(updatedComment);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();

        return ResponseEntity.created(location).build();
    }

    @DeleteMapping(value = "/comments/{id}")
    public ResponseEntity deleteComment(@PathVariable("id") String id) {

        if (id == null) {
            throw new IdException();
        }

        Comment comment = commentService.findById(Long.valueOf(id));

        if (comment == null) {
            throw new ResourceNotFoundException();
        }

        commentService.deleteById(comment.getId());

        return ResponseEntity.noContent().build();
    }

    public void checkRequiredFieldsForComment(Comment comment) {
        Long postId = comment.getPost().getId();
        Long userId = comment.getAuthor().getId();
        String content = comment.getContent();
        Date date = comment.getCreationDate();

        if (date == null) {
            throw new FieldIsRequiredException("'creationDate' field is required.");
        }

        if (content == null) {
            throw new FieldIsRequiredException("'content' field is required.");
        }

        if (postId == null) {
            throw new FieldIsRequiredException("'post id' is required.");
        }

        if (userId == null) {
            throw new FieldIsRequiredException("'author id' is required.");
        }
    }
}