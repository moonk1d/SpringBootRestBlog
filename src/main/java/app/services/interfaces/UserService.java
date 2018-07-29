package app.services.interfaces;

import app.models.User;

import java.util.List;

/**
 * Created by Andrey Nazarov on 7/27/2018.
 */
public interface UserService {
    List<User> findAll();

    User findById(Long id);

    User create(User user);

    User edit(User user);

    void deleteById(Long id);

    User findByUsername(String username);
}
