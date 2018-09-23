package app.services.interfaces;

import app.models.User;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Created by Andrey on 21.07.2018.
 */
public interface UserService {
    User findById(Long id);

    User create(User author);

    User edit(User author);

    void deleteById(Long id);

    List<User> findAll();

    List<User> findAll(Pageable page);
}
