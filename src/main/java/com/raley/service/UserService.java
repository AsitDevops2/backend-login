package com.raley.service;

import java.util.List;

import com.raley.model.User;
import com.raley.model.UserDto;
import com.raley.vo.Category;

public interface UserService {

   // User save(UserDto user);
    List<User> findAll();
    
    void delete(int id);

    User findOne(String email);

    User findById(int id);
    
    List<User> findByParent(int id);
    
    List<Category> getCategoryList(int id);

    UserDto update(UserDto userDto);
}
