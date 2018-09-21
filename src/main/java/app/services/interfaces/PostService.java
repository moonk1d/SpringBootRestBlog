package app.services.interfaces;

import app.models.Post;
import app.models.User;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by Andrey Nazarov on 7/27/2018.
 */
public interface PostService {
    Post findById(Long id);

    Post create(Post post);

    Post edit(Post post);

    void deleteById(Long id);

    List<Post> findAll();

    List<Post> findAll(Pageable page);

    List<Post> findAll(User author, Pageable page);
}
