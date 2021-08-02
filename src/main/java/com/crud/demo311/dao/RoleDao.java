package com.crud.demo311.dao;


import com.crud.demo311.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface RoleDao  {

    void addRoleAdmin();

    void addRoleUser();

    Role findRoleById(Long id);

    Set<Role> findRolesSetById(Long[] id);

    Role findRoleByName(String name);

    Set<Role> findRoleSetByName(String[] names);

    List<Role> getAllRoles();
}
