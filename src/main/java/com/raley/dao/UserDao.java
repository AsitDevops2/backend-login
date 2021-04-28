package com.raley.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.raley.model.User;

@Repository
public interface UserDao extends CrudRepository<User, Integer> {

    User findByEmail(String email);
    
    List<User> findByParent(int id);
}
