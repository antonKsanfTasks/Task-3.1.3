package ru.itmentor.spring.boot_security.demo.dao;

import ru.itmentor.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserDao {
    User findById(Long id);
    User findByUsername(String username);
    List<User> findAll();
    void save(User user);
    void update(User user);
    void deleteById(Long id);
}
