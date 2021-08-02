package com.crud.demo311.dao;


import com.crud.demo311.model.User;

import java.util.List;

public interface UserDao {
    void addUser(User user);

    User getUserById(long id);

    User getUserByName(String name);

    List<User> getAllUsers();

    void updateUser(User user);

    void removeUser(long id);
}
