package ru.itmentor.spring.boot_security.demo.Init;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import ru.itmentor.spring.boot_security.demo.model.Role;
import ru.itmentor.spring.boot_security.demo.model.User;
import ru.itmentor.spring.boot_security.demo.service.RoleService;
import ru.itmentor.spring.boot_security.demo.service.UserService;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
public class DataInitializer {

    private final UserService userService;
    private final BCryptPasswordEncoder encoder;
    private final RoleService roleService;

    @Autowired
    public DataInitializer(UserService userService, BCryptPasswordEncoder encoder, RoleService roleService) {
        this.userService = userService;
        this.encoder = encoder;
        this.roleService = roleService;
    }

    @PostConstruct
    public void init() {
        List<User> users = userService.findAll();

        if (users.isEmpty()) {
            // Add roles
            Role adminRole = roleService.findByName("ROLE_ADMIN");
            if (adminRole == null) {
                adminRole = new Role("ROLE_ADMIN");
                roleService.save(adminRole);
            }
            Role userRole = roleService.findByName("ROLE_USER");
            if (userRole == null) {
                userRole = new Role("ROLE_USER");
                roleService.save(userRole);
            }
            // Add admin
            User admin = new User("Anton", "Ksanf", 28, "aksanf@mail.ru", encoder.encode("1252"));
            admin.getRoles().add(adminRole);
            userService.save(admin);

            // Add users
            User user_one = new User("first", "lastName_one", 25, "first@example.com", encoder.encode("1111"));
            user_one.getRoles().add(userRole);
            userService.save(user_one);

            User user_two = new User("second", "lastName_two", 40, "second@example.com", encoder.encode("2222"));
            user_two.getRoles().add(userRole);
            userService.save(user_two);

            User user_three = new User("third", "lastName_three", 35, "third@example", encoder.encode("3333"));
            user_three.getRoles().add(userRole);
            userService.save(user_three);
        }
    }
}