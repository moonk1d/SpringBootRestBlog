package app.controllers;

import app.ExceptionHandler.exceptions.FieldIsRequiredException;
import app.ExceptionHandler.exceptions.IdException;
import app.ExceptionHandler.exceptions.ResourceNotFoundException;
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
public class RolesController extends MainController {
    @Autowired
    private RoleService roleService;

    @GetMapping(value = "/roles/{id}", produces = "application/json")
    public Role viewRole(@PathVariable("id") String id) {
        if (id != null) {
            QueryParametersValidator.validateIdQueryParameter(id);
        }

        Role role = roleService.findById(Long.valueOf(id));
        if (role == null) {
            throw new ResourceNotFoundException();
        }

        return role;
    }

    @GetMapping(value = "/roles", produces = "application/json")
    public ResponseEntity<List<Role>> listRoles(
            @RequestParam(value = "limit", required = false) String limit,
            @RequestParam(value = "offset", required = false) String offset) {

        Pageable page = pageRequestBuilder(offset, limit);

        return new ResponseEntity<>(roleService.findAll(page), HttpStatus.OK);
    }

    @PostMapping(value = "/roles")
    public ResponseEntity createPost(@Valid @RequestBody Role role) {
        checkRequiredFieldsForRole(role);

        Long newPostId = roleService.create(role).getId();

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newPostId).toUri();

        return ResponseEntity.created(location).build();
    }

    @PatchMapping(value = "/roles/{id}")
    public ResponseEntity updateRole(@PathVariable("id") String id, @RequestBody Role role) {

        if (id == null) {
            throw new IdException();
        }

        Role updatedPost = roleService.findById(Long.valueOf(id));

        if (updatedPost == null) {
            throw new ResourceNotFoundException();
        }

        if (role.getRole() != null) {
            updatedPost.setRole(role.getRole());
        }

        roleService.create(updatedPost);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();

        return ResponseEntity.created(location).build();
    }

    @DeleteMapping(value = "/roles/{id}")
    public ResponseEntity deleteRole(@PathVariable("id") String id) {

        if (id == null) {
            throw new IdException();
        }

        Role role = roleService.findById(Long.valueOf(id));

        if (role == null) {
            throw new ResourceNotFoundException();
        }

        roleService.deleteById(role.getId());

        return ResponseEntity.noContent().build();
    }

    public void checkRequiredFieldsForRole(Role role) {
        String roleName = role.getRole();

        if (roleName == null) {
            throw new FieldIsRequiredException("'role' field is required.");
        }
    }
}
