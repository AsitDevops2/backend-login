package com.raley.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.raley.model.User;

@Repository
public interface UserDao extends CrudRepository<User, Integer> {

	//returns a user if email id matched else returns null
    User findByEmail(String email);		
    
  //returns list of users by parent id in db
    List<User> findByParent(int id);	
}
