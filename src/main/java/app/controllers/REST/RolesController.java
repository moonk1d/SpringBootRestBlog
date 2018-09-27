package app.controllers.REST;

import app.models.Role;
import app.services.interfaces.RoleService;
import app.validators.QueryParametersValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

/**
 * Created by Andrey Nazarov on 7/27/2018.
 */
@RestController
@RequestMapping(value = "api/roles")
public class RolesController extends MainController {
    @Autowired
    private RoleService roleService;

    @GetMapping(value = "{id}", produces = "application/json")
    public Role viewRole(@PathVariable(value = "id") String id) {
        QueryParametersValidator.validateIdQueryParameter(id);

        return roleService.findById(Long.valueOf(id));
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<List<Role>> listRoles(
            @RequestParam(value = "limit", required = false) String limit,
            @RequestParam(value = "offset", required = false) String offset) {

        Pageable page = pageRequestBuilder(offset, limit);

        return new ResponseEntity<>(roleService.findAll(page), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity createRole(@Valid @RequestBody Role role) {
        Long newPostId = roleService.create(role).getId();

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newPostId).toUri();

        return ResponseEntity.created(location).build();
    }

    @PatchMapping(value = "{id}")
    public ResponseEntity updateRole(@PathVariable(value = "id") String id, @Valid @RequestBody Role role) {
        QueryParametersValidator.validateIdQueryParameter(id);

        Role updatedRole = roleService.findById(Long.valueOf(id));
        updatedRole.setRole(role.getRole());

        roleService.create(updatedRole);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();

        return ResponseEntity.created(location).build();
    }

    @DeleteMapping(value = "{id}")
    public ResponseEntity deleteRole(@PathVariable(value = "id") String id) {
        QueryParametersValidator.validateIdQueryParameter(id);

        Role role = roleService.findById(Long.valueOf(id));

        roleService.deleteById(role.getId());

        return ResponseEntity.noContent().build();
    }
}
