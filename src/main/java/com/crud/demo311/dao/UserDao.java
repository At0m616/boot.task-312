package com.crud.demo311.dao;


import com.crud.demo311.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDao extends JpaRepository<User, Long> {


    @Query("select distinct u from User u JOIN FETCH u.roles where u.id = :id")
    User findUserById(long id);

    @Query("select distinct u from User u JOIN FETCH u.roles where u.email =:email")
    User findUserByEmail(String email);

    @Query("select distinct u from User u join fetch u.roles")
    List<User> findAllUsers();

    void deleteById(long id);
}
