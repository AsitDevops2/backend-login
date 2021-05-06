package com.raley.service;

import java.util.List;

import com.raley.model.User;
import com.raley.model.UserDto;
import com.raley.vo.Category;

public interface UserService {

	// returns list of all users
	List<User> findAll();

	// delete user in db according to id
	void delete(int id);

	// returns a user by email id
	User findOne(String email);

	// returns a user by id 
	User findById(int id);

	// returns list of users parent id in db
	List<User> findByParent(int id);

	// returns list of categories where id matches with userId column in node api
	List<Category> getCategoryList(int id);

	// update the user data in db
	UserDto update(UserDto userDto);
}
