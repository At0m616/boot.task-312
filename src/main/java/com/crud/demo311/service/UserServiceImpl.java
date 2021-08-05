package com.crud.demo311.service;

import com.crud.demo311.dao.UserDao;
import com.crud.demo311.model.Role;
import com.crud.demo311.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    private final UserDao userDao;

    private final RoleService roleService;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserServiceImpl(UserDao userDao, RoleService roleService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userDao = userDao;
        this.roleService = roleService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Transactional
    @Override
    public void saveUser(User user, Long[] roles) {
        var userFindDB = userDao.findUserByUsername(user.getUsername());
        Set<Role> roleSet = roleService.findRolesSetById(roles);

        if ((roleService.findById(1L) == null)
                || (roleService.findById(2L) == null)) {
            roleService.save(new Role(1L, "ROLE_ADMIN"));
            roleService.save(new Role(2L, "ROLE_USER"));
        }

        if (userFindDB != null) {
            userFindDB.setFirstname(user.getFirstname());
            userFindDB.setLastname(user.getLastname());
            userFindDB.setAge(user.getAge());
            userFindDB.setUsername(user.getUsername());
            user.setRoles(roleSet);
            if (!user.getPassword().equals(userFindDB.getPassword())) {
                userFindDB.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            }
            userDao.save(userFindDB);
        }

        user.setRoles(roleSet);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userDao.save(user);
    }

    @Transactional(readOnly = true)
    @Override
    public User findUserById(long id) {
        return userDao.findUserById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public User findUserByEmail(String name) {
        return userDao.findUserByUsername(name);
    }


    @Transactional
    @Override
    public void removeUser(long id) {
        userDao.deleteById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public List<User> findAllUsers() {
        return userDao.findAll();
    }

}
