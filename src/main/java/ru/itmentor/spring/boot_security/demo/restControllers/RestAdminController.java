package ru.itmentor.spring.boot_security.demo.restControllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import ru.itmentor.spring.boot_security.demo.model.Role;
import ru.itmentor.spring.boot_security.demo.model.User;
import ru.itmentor.spring.boot_security.demo.service.RoleService;
import ru.itmentor.spring.boot_security.demo.service.UserService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("api/admin")
public class RestAdminController {
    private final UserService userService;
    private final BCryptPasswordEncoder passwordEncoder;
    private final RoleService roleService;

    @Autowired
    public RestAdminController(UserService userService, BCryptPasswordEncoder passwordEncoder, RoleService roleService) {

        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> listUsers() {
        List<User> users = userService.findAll();

        Map<String, Object> response = new HashMap<>();

        response.put("users", users);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        user.setPassword(userService.encodePassword(user.getPassword()));
        if (user.getRoles() == null) {
            user.getRoles().add(roleService.findByName("ROLE_USER"));
        }
        userService.save(user);
        User createdUser = userService.findById(user.getId());
        return createdUser != null
                ? new ResponseEntity<>(createdUser, HttpStatus.CREATED)
                : new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") Long id) {
        User currentUser = userService.findById(id);

        if (currentUser == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(currentUser, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable("id") Long id, @RequestBody User userUpdates) {
        User currentUser = userService.findById(id);

        if (currentUser == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        //Change username
        if (userUpdates.getUsername() != null) {
            currentUser.setUsername(userUpdates.getUsername());
        }
        //Change password
        if (userUpdates.getPassword() != null) {
            currentUser.setPassword((passwordEncoder.encode(userUpdates.getPassword())));
        }
        //Change firstName
        if (userUpdates.getFirstName() != null) {
            currentUser.setFirstName(userUpdates.getFirstName());
        }

        userService.update(currentUser);

        User createdUser = userService.findByUsername(userUpdates.getUsername());
        return createdUser != null
                ? new ResponseEntity<>(createdUser, HttpStatus.CREATED)
                : new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") Long id) {
        User currentUser = userService.findById(id);

        if (currentUser == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        userService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
