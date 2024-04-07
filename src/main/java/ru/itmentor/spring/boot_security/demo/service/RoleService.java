package ru.itmentor.spring.boot_security.demo.service;

import ru.itmentor.spring.boot_security.demo.model.Role;

import java.util.Set;

public interface RoleService {

    Role findByName(String name);

    Set<Role> findAll();

    void save(Role role);

}
