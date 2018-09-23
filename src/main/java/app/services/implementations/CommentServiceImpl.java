package app.services.implementations;

import app.ExceptionHandler.exceptions.ResourceNotFoundException;
import app.models.Comment;
import app.models.Post;
import app.models.User;
import app.repositories.CommentRepository;
import app.services.interfaces.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by Andrey on 24.07.2018.
 */
@Service
@Primary
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentRepository commentRepository;

    @Override
    public List<Comment> findAll() {
        return this.commentRepository.findAllComments();
    }

    @Override
    public List<Comment> findAll(Pageable pageable) {
        return this.commentRepository.findAllComments(pageable);
    }

    @Override
    public Comment findById(Long id) {
        return this.commentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException());
    }

    @Override
    public Comment create(Comment comment) {
        return this.commentRepository.save(comment);
    }

    @Override
    public void deleteById(Long id) {
        this.commentRepository.deleteById(id);
    }

    @Override
    public List<Comment> findByPost(Post post, Pageable page) {
        return this.commentRepository.findAllComments(page).stream()
                .filter(c -> Objects.equals(c.getPost(), post))
                .collect(Collectors.toList());
    }

    @Override
    public List<Comment> findByAuthor(User author, Pageable page) {
        return this.commentRepository.findAllComments(page).stream()
                .filter(c -> Objects.equals(c.getAuthor(), author))
                .collect(Collectors.toList());
    }

    @Override
    public List<Comment> findByAuthorAndPost(Post post, User author, Pageable page) {
        return this.commentRepository.findAllComments(page).stream()
                .filter(c -> Objects.equals(c.getAuthor(), author))
                .filter(c -> Objects.equals(c.getPost(), post))
                .collect(Collectors.toList());
    }
}
