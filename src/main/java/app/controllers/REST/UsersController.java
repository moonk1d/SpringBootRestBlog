package app.controllers.REST;

import app.ExceptionHandler.exceptions.FieldIsRequiredException;
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

import javax.validation.Valid;
import java.util.List;

/**
 * Created by Andrey Nazarov on 7/27/2018.
 */
@RestController
@RequestMapping(value = "api/users")
public class UsersController extends MainController {
    @Autowired
    private PostService postService;

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @GetMapping(value = "{id}", produces = "application/json")
    public User viewUser(@PathVariable("id") String id) {
        QueryParametersValidator.validateIdQueryParameter(id);

        return userService.findById(Long.valueOf(id));
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<List<User>> listUsers(
            @RequestParam(value = "limit", required = false) String limit,
            @RequestParam(value = "offset", required = false) String offset) {

        Pageable page = pageRequestBuilder(offset, limit);

        return new ResponseEntity<>(userService.findAll(page), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity createUser(@Valid @RequestBody User user) {
        checkRequiredFieldsForUser(user);

        Role role = roleService.findById(user.getRole().getId());
        user.setRole(role);

        return ResponseEntity
                .created(ServletUriComponentsBuilder
                        .fromCurrentRequest()
                        .path("/{id}")
                        .buildAndExpand(userService.create(user).getId())
                        .toUri())
                .build();
    }

    @PatchMapping(value = "{id}")
    public ResponseEntity updateUser(@PathVariable("id") String id, @RequestBody User user) {
        QueryParametersValidator.validateIdQueryParameter(id);

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

            updatedUser.setRole(role);
        }

        userService.create(updatedUser);

        return ResponseEntity
                .created(ServletUriComponentsBuilder
                        .fromCurrentRequest()
                        .build()
                        .toUri())
                .build();
    }

    @DeleteMapping(value = "{id}")
    public ResponseEntity deleteUser(@PathVariable("id") String id) {
        QueryParametersValidator.validateIdQueryParameter(id);

        User user = userService.findById(Long.valueOf(id));

        userService.deleteById(user.getId());

        return ResponseEntity.noContent().build();
    }

    public void checkRequiredFieldsForUser(User user) {
        Long roleId = user.getRole().getId();

        if (roleId == null) {
            throw new FieldIsRequiredException("'role id' field is required.");
        }
    }
}
