package com.raley.controller;

import com.raley.model.ApiResponse;
import com.raley.model.User;
import com.raley.model.UserDto;
import com.raley.service.UserService;
import com.raley.vo.Category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
/**
 * @author abhay.thakur
 *
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    
    public UserController(UserService userService) {
		super();
		this.userService = userService;
	}

    @GetMapping("/list")
    public ApiResponse<List<User>> listUser(){
        return new ApiResponse<>(HttpStatus.OK.value(), "User list fetched successfully.",userService.findAll());
    }
    
    @GetMapping("/listByParent/{id}")
    public ApiResponse<List<User>> listUserByParent(@PathVariable int id) {
    	List<User> userList=userService.findByParent(id);
    	if(userList.isEmpty())
    		return new ApiResponse<>(HttpStatus.NOT_FOUND.value(), "No Users Added By This Parent", null);
    	
    	return new ApiResponse<List<User>>(HttpStatus.OK.value(), "User list fetched successfully.", userList);
    }

    @GetMapping("/getUser/{id}")
    public ApiResponse<User> getOne(@PathVariable int id){
        return new ApiResponse<>(HttpStatus.OK.value(), "User fetched successfully.",userService.findById(id));
    }

    @PutMapping("/updateUser/{id}")
    public ApiResponse<UserDto> updateVendor(@RequestBody UserDto userDto) {
        return new ApiResponse<>(HttpStatus.OK.value(), "User updated successfully.",userService.update(userDto));
    }

    @DeleteMapping("/deleteUser/{id}")
    public ApiResponse<Void> deleteVendor(@PathVariable int id) {
        userService.delete(id);
        return new ApiResponse<>(HttpStatus.OK.value(), "User deleted successfully.", null);
    }

    @GetMapping("/getCategory/{id}")
    public ApiResponse<List<Category>> getCategory(@PathVariable int id) {
    	List<Category> categoryList=userService.getCategoryList(id);
    	if(categoryList.isEmpty())
    		return new ApiResponse<>(HttpStatus.NOT_FOUND.value(),"No Category Added By This User",null);
    	
    	return new ApiResponse<>(HttpStatus.OK.value(),"Category List Fetched Successfully",categoryList);
    }

}
