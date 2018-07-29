package app.repositories;

import app.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Andrey on 27.07.2018.
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
}
