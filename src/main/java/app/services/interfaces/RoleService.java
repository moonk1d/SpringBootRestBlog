package app.services.interfaces;

import app.models.Role;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Created by Andrey Nazarov on 7/27/2018.
 */

public interface RoleService {
    Role findById(Long id);

    Role create(Role role);

    Role edit(Role role);

    void deleteById(Long id);

    List<Role> findAll();

    List<Role> findAll(Pageable page);
}
