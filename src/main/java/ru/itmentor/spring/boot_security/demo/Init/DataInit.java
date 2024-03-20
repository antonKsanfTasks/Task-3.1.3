//package ru.itmentor.spring.boot_security.demo.Init;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.stereotype.Component;
//import ru.itmentor.spring.boot_security.demo.model.Role;
//import ru.itmentor.spring.boot_security.demo.model.User;
//import ru.itmentor.spring.boot_security.demo.service.RoleService;
//import ru.itmentor.spring.boot_security.demo.service.UserService;
//
//import javax.annotation.PostConstruct;
//import java.util.List;
//
//@Component
//public class DataInit {
//    private final UserService userService;
//    private final BCryptPasswordEncoder encoder;
//    private final RoleService roleService;
//
//    @Autowired
//    public DataInit(UserService userService, BCryptPasswordEncoder encoder, RoleService roleService) {
//        this.userService = userService;
//        this.encoder = encoder;
//        this.roleService = roleService;
//    }
//
//    @PostConstruct
//    public void initialization() {
//        List<User> users = userService.findAll();
//
//        Role adminRole = null;
//        if (users.isEmpty()) {
//            // Add roles
//            adminRole = roleService.findByName("ROLE_ADMIN");
//            roleService.save(adminRole);
//        }
//        Role userRole = roleService.findByName("ROLE_USER");
//        if (userRole == null) {
//            userRole = new Role("ROLE_USER");
//            roleService.save(userRole);
//        }
//        // Add admin
//        User admin = new User("Anton", "Ksanf", 28, "aksanf", "admin");
//        admin.getRoles().add(adminRole);
//        userService.save(admin);
//
//        // Add users
//        User user_one = new User("first", "lastName_one", 25, "first", "userOne");
//        user_one.getRoles().add(userRole);
//        userService.save(user_one);
//
//        User user_two = new User("second", "lastName_two", 40, "second", "userTwo");
//        user_two.getRoles().add(userRole);
//        userService.save(user_two);
//
//        User user_three = new User("third", "lastName_three", 35, "third", "userThree"  );
//        user_three.getRoles().add(userRole);
//        userService.save(user_three);
//    }
//}
