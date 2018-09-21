package app.controllers;

import app.ExceptionHandler.exceptions.FieldIsRequiredException;
import app.ExceptionHandler.exceptions.IdException;
import app.ExceptionHandler.exceptions.ResourceNotFoundException;
import app.models.Role;
import app.models.User;
import app.services.interfaces.PostService;
import app.services.interfaces.RoleService;
import app.services.interfaces.UserService;
import app.validators.QueryParametersValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Date;
import java.util.List;

/**
 * Created by Andrey Nazarov on 7/27/2018.
 */
@RestController
public class UsersController extends MainController {
    @Autowired
    private PostService postService;

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @GetMapping(value = "/users/{id}", produces = "application/json")
    public User viewUser(@PathVariable("id") String id) {
        if (id != null) {
            QueryParametersValidator.validateIdQueryParameter(id);
        }

        User user = userService.findById(Long.valueOf(id));
        if (user == null) {
            throw new ResourceNotFoundException();
        }

        return user;
    }

    @GetMapping(value = "/users", produces = "application/json")
    public ResponseEntity<List<User>> listUsers(
            @RequestParam(value = "limit", required = false) String limit,
            @RequestParam(value = "offset", required = false) String offset) {

        Pageable page = pageRequestBuilder(offset, limit);

        return new ResponseEntity<>(userService.findAll(page), HttpStatus.OK);
    }

    @PostMapping(value = "/users")
    public ResponseEntity createUser(@RequestBody User user) {
        checkRequiredFieldsForUser(user);

        Role role = roleService.findById(user.getRole().getId());

        if (role == null) {
            throw new ResourceNotFoundException();
        }

        user.setRole(role);
        Long newUserId = userService.create(user).getId();

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newUserId).toUri();

        return ResponseEntity.created(location).build();
    }

    @PatchMapping(value = "/users/{id}")
    public ResponseEntity updateUser(@PathVariable("id") String id, @RequestBody User user) {

        if (id == null) {
            throw new IdException();
        }

        User updatedUser = userService.findById(Long.valueOf(id));

        if (updatedUser == null) {
            throw new ResourceNotFoundException();
        }

        if (user.getName() != null) {
            updatedUser.setName(user.getName());
        }

        if (user.getPassword() != null) {
            updatedUser.setPassword(user.getPassword());
        }

        if (user.getRole().getId() != null) {
            Role role = roleService.findById(user.getRole().getId());

            if(role == null) {
                throw new ResourceNotFoundException();
            }

            updatedUser.setRole(role);
        }

        userService.create(updatedUser);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();

        return ResponseEntity.created(location).build();
    }

    @DeleteMapping(value = "/users/{id}")
    public ResponseEntity deleteUser(@PathVariable("id") String id) {

        if (id == null) {
            throw new IdException();
        }

        User us = userService.findById(Long.valueOf(id));

        if (us == null) {
            throw new ResourceNotFoundException();
        }

        userService.deleteById(us.getId());

        return ResponseEntity.noContent().build();
    }

    public void checkRequiredFieldsForUser(User user) {
        Long roleId = user.getRole().getId();
        String name = user.getName();
        String password = user.getPassword();
        Date date = user.getCreationDate();

        if (date == null) {
            throw new FieldIsRequiredException("'creationDate' field is required.");
        }

        if (name == null) {
            throw new FieldIsRequiredException("'name' field is required.");
        }

        if (password == null) {
            throw new FieldIsRequiredException("'password' field is required.");
        }

        if (roleId == null) {
            throw new FieldIsRequiredException("'role id' field is required.");
        }
    }
}
