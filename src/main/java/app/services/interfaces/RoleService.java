package app.services.interfaces;

import app.models.Role;

/**
 * Created by Andrey Nazarov on 7/27/2018.
 */

public interface RoleService {
    Role findById(Long id);

    Role create(Role role);

    Role edit(Role role);

    void deleteById(Long id);
}
