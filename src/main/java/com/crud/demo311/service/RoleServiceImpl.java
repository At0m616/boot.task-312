package com.crud.demo311.service;

import com.crud.demo311.dao.RoleDao;
import com.crud.demo311.model.Role;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
public class RoleServiceImpl implements RoleService{

    private final RoleDao roleDao;

    public RoleServiceImpl(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    @Transactional
    @Override
    public Role findRolesById(Long id) {
        return roleDao.findById(id).orElse(null);
    }

    @Transactional
    @Override
    public Role findRoleByName(String name) {
        return roleDao.findRolesByName(name);
    }

    @Transactional
    @Override
    public List<Role> getAllRoles() {
        return roleDao.findAll();
    }

    @Transactional
    @Override
    public Set<Role> findRolesSetById(Long[] id) {
        return roleDao.findRolesSetById(id);
    }

    @Override
    public Set<Role> findRoleSetByName(String[] names) {
        return roleDao.findRoleSetByName(names);
    }
}
