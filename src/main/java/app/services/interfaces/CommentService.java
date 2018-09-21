package app.services.interfaces;

import app.models.Comment;
import app.models.Post;
import app.models.User;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by Andrey on 21.07.2018.
 */
public interface CommentService {
    List<Comment> findAll();

    List<Comment> findAll(Pageable page);

    List<Comment> findByPost(Post post, Pageable page);

    List<Comment> findByAuthor(User author, Pageable page);

    List<Comment> findByAuthorAndPost(Post post, User author, Pageable page);

    Comment findById(Long id);

    Comment create(Comment comment);

    void deleteById(Long id);
}
