package com.crud.demo311.service;


import com.crud.demo311.model.User;

import java.util.List;


public interface UserService {
    void saveUser(User user, Long[] roles);

    void updateUser(User user, Long[] roles);

    User findUserById(long id);

    User findUserByEmail(String email);

    void removeUser(long id);

    List<User> findAllUsers();



}
