package app.services.implementations;

import app.models.Role;
import app.repositories.RoleRepository;
import app.services.interfaces.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Andrey Nazarov on 7/27/2018.
 */
@Service
@Primary
public class RoleServiceJpaImpl implements RoleService {
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Role findById(Long id) {
        return this.roleRepository.findOne(id);
    }

    @Override
    public Role create(Role role) {
        return this.roleRepository.save(role);
    }

    @Override
    public Role edit(Role role) {
        return this.roleRepository.save(role);
    }

    @Override
    public void deleteById(Long id) {
        this.roleRepository.delete(id);
    }

    @Override
    public List<Role> findAll() {
        return this.roleRepository.findAll();
    }

    @Override
    public List<Role> findAll(Pageable page) {
        return this.roleRepository.findAllRoles(page);
    }
}
