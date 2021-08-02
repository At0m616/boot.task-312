package com.crud.demo311.service;

import com.crud.demo311.dao.RoleDao;
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

    private final RoleDao roleDao;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserServiceImpl(UserDao userDao, RoleDao roleDao, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userDao = userDao;
        this.roleDao = roleDao;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Transactional
    @Override
    public User addUser(User user, Long[] roles) {
        User userFindDB = userDao.findUserByUsername(user.getUsername());

        if (userFindDB != null) {
            return userFindDB;
        }
        if ((roleDao.findById(1L).isPresent())
                || (roleDao.findById(2L).isPresent())) {
            roleDao.save(new Role(1L, "ROLE_ADMIN"));
            roleDao.save(new Role(2L, "ROLE_USER"));
        }
        Set<Role> role = roleDao.findRolesSetById(roles);

        user.setRoles(role);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userDao.save(user);
        return user;
    }

    @Transactional(readOnly = true)
    @Override
    public User findUserById(long id) {
        return userDao.findUserById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public User findUserByName(String name) {
        return userDao.findUserByUsername(name);
    }

    @Transactional
    @Override
    public void updateUser(User user, Long[] roles) {
        User modifyUser = userDao.findUserById(user.getId());
        modifyUser.setUsername(user.getUsername());

        Set<Role> roleSet = roleDao.findRolesSetById(roles);
        modifyUser.setRoles(roleSet);

        if (!user.getPassword().equals(modifyUser.getPassword())) {
            modifyUser.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        }
        userDao.save(modifyUser);
    }

    @Transactional
    @Override
    public void removeUser(long id) {
        userDao.deleteById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public List<User> findAllUsers() {
        return userDao.findAllUsers();
    }

}
