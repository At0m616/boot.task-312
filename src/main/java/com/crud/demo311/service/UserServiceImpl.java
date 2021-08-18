package com.crud.demo311.service;

import com.crud.demo311.dao.UserDao;
import com.crud.demo311.model.Role;
import com.crud.demo311.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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

        if ((roleService.findById(1L) == null)
                || (roleService.findById(2L) == null)) {
            roleService.save(new Role(1L, "ROLE_ADMIN"));
            roleService.save(new Role(2L, "ROLE_USER"));
        }
        Set<Role> roleSet = roleService.findRolesSetById(roles);

        if (userDao.findUserByUsername(user.getUsername()) == null) {
            user.setRoles(roleSet);
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            userDao.save(user);
        } else {
            try {
                throw new Exception("User exists");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    @Transactional
    @Override
    public void updateUser(User user, Long[] roles) {
        if (user.getId() == null) {
            try {
                throw new Exception("User not exist");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        Set<Role> roleSet = roleService.findRolesSetById(roles);
        user.setRoles(roleSet);

        var userFindDB = findUserById(user.getId());

        userFindDB.setFirstname(user.getFirstname());
        userFindDB.setLastname(user.getLastname());
        userFindDB.setAge(user.getAge());
        userFindDB.setUsername(user.getUsername());
        if (!user.getPassword().equals(userFindDB.getPassword())) {
            userFindDB.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        }
        userDao.save(userFindDB);

    }

    @Transactional(readOnly = true)
    @Override
    public User findUserById(long id) {
        return userDao.findById(id).orElse(null);
    }

    @Transactional(readOnly = true)
    @Override
    public User findUserByEmail(String name) {
        var u = userDao.findUserByUsername(name);
        if (u == null) {
            throw new UsernameNotFoundException("User not exist");
        }
        return u;
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
