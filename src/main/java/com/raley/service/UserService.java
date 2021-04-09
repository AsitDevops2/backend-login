package com.raley.service;

import java.util.List;

import com.raley.model.User;
import com.raley.model.UserDto;

public interface UserService {

   // User save(UserDto user);
    List<User> findAll();
    void delete(int id);

    User findOne(String email);

    User findById(int id);

    UserDto update(UserDto userDto);
}
