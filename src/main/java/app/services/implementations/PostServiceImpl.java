package app.services.implementations;

import app.ExceptionHandler.exceptions.ResourceNotFoundException;
import app.models.Post;
import app.models.User;
import app.repositories.PostRepository;
import app.services.interfaces.PostService;
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
 * Created by Andrey Nazarov on 7/27/2018.
 */
@Service
@Primary
public class PostServiceImpl implements PostService {
    @Autowired
    private PostRepository postRepository;

    @Override
    public Post findById(Long id) {
        return this.postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException());
    }

    @Override
    public Post create(Post post) {
        return this.postRepository.save(post);
    }

    @Override
    public Post edit(Post post) {
        return this.postRepository.save(post);
    }

    @Override
    public void deleteById(Long id) {
        this.postRepository.deleteById(id);
    }

    @Override
    public List<Post> findAll() {
        return this.postRepository.findAll();
    }

    @Override
    public List<Post> findAll(Pageable page) {
        return this.postRepository.findAllPosts(page);
    }

    @Override
    public List<Post> findAll(User author, Pageable page) {
        return this.postRepository.findAllPosts(page).stream()
                .filter(p -> Objects.equals(p.getAuthor(), author))
                .collect(Collectors.toList());
    }
}