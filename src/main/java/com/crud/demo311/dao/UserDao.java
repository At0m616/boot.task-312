package com.crud.demo311.dao;


import com.crud.demo311.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface UserDao extends JpaRepository<User, Long> {


//    @Query("select distinct u from User u JOIN FETCH u.roles where u.id = :id")
//    User findUserById(Long id);

    @Query("select distinct u from User u JOIN FETCH u.roles where u.username =:username")
    User findUserByUsername(String username);


    void deleteById(long id);
}
