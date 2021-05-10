package com.raley.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.raley.model.Response;
import com.raley.model.User;
import com.raley.model.UserDto;
import com.raley.service.UserService;
import com.raley.vo.Category;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.Authorization;
/**
 * @author abhay.thakur
 *
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/user")
@Api(value="UserController")
public class UserController {
	
	Logger logger=LoggerFactory.getLogger(UserController.class);

	//Injecting UserService Bean
    @Autowired
    private UserService userService;
    
    public UserController(UserService userService) {
		super();
		this.userService = userService;
	}

    @ApiOperation(value="listUser" ,notes ="This method gets list of users", authorizations = {
  	      @Authorization(value = "Authorization")
  	    }) 
    @ApiResponses(value = { 
            @ApiResponse(code = 200, message = "Success|OK"),
            @ApiResponse(code = 401, message = "not authorized!")})
    @GetMapping("/list")
    public Response<List<User>> listUser(){
    	List<User> userList=userService.findAll();
    	if(userList.isEmpty()) {
    		logger.info("User list is empty.No users added yet");
    		return new Response<>(HttpStatus.OK.value(), "User list is empty.No users added yet",userList);
    	}
    	
    	logger.info("User list fetched successfully");
        return new Response<>(HttpStatus.OK.value(), "User list fetched successfully.",userList);
    }
    
    @ApiOperation(value="listUserByParent",notes="This method gets list of users by parent", authorizations = {
  	      @Authorization(value = "Authorization")
  	    }) 
    @ApiResponses(value = { 
            @ApiResponse(code = 200, message = "Success|OK"),
            @ApiResponse(code = 401, message = "not authorized!"), 
            @ApiResponse(code = 404, message = "user not found!!!") })
    @GetMapping("/listByParent/{id}")
    public Response<List<User>> listUserByParent(@PathVariable int id) {
    	List<User> userList=userService.findByParent(id);
    	if(userList.isEmpty()) {
    		logger.info("No Users Added By This Parent");
    		return new Response<>(HttpStatus.NOT_FOUND.value(), "No Users Added By This Parent", null);
    	}
    		
    	logger.info("User list fetched successfully.");
    	return new Response<>(HttpStatus.OK.value(), "User list fetched successfully.", userList);
    }

    @ApiOperation(value="getOne",notes="This method fetches user with id", authorizations = {
  	      @Authorization(value = "Authorization")
  	    }) 
    @ApiResponses(value = { 
            @ApiResponse(code = 200, message = "Success|OK"),
            @ApiResponse(code = 401, message = "not authorized!"), 
            @ApiResponse(code = 404, message = "user not found!!!") })
    @GetMapping("/getUser/{id}")
    public Response<User> getOne(@PathVariable int id){
    	User user=userService.findById(id);
    	if(user==null) {
    		logger.info("No user found by id {}",id);
    		return new Response<>(HttpStatus.OK.value(), "No user found by this id",user);
    	}
    	
    	logger.info("User fetched successfully by id {}",id);
        return new Response<>(HttpStatus.OK.value(), "User fetched successfully.",user);
    }

    @ApiOperation(value="updateVendor",notes="This method updates user", authorizations = {
  	      @Authorization(value = "Authorization")
  	    }) 
    @PutMapping("/updateUser/{id}")
    public Response<UserDto> updateVendor(@RequestBody UserDto userDto) {
        return new Response<>(HttpStatus.OK.value(), "User updated successfully.",userService.update(userDto));
    }

    @ApiOperation(value="deleteVendor",notes="This method deletes user with given id", authorizations = {
  	      @Authorization(value = "Authorization")
  	    }) 
    @DeleteMapping("/deleteUser/{id}")
    public Response<Void> deleteVendor( @ApiParam(
    	    name =  "id",
    	    type = "int",
    	    value = "id of the user",
    	    example = "4",
    	    required = true)@PathVariable int id) {
        userService.delete(id);
        return new Response<>(HttpStatus.OK.value(), "User deleted successfully.", null);
    }

    @ApiOperation(value="getCategory",notes="This method fetches catergory list for given id", authorizations = {
  	      @Authorization(value = "Authorization")
  	    }) 
    @ApiResponses(value = { 
            @ApiResponse(code = 200, message = "Success|OK"),
            @ApiResponse(code = 401, message = "not authorized!"), 
            @ApiResponse(code = 404, message = "category not found!!!") })
    @GetMapping("/getCategory/{id}")
    public Response<List<Category>> getCategory(@PathVariable int id) {
    	List<Category> categoryList=userService.getCategoryList(id);
    	if(categoryList.isEmpty()) {
    		logger.info("No Category Added By This User");
    		return new Response<>(HttpStatus.NOT_FOUND.value(),"No Category Added By This User",null);
    	}
    		
    	logger.info("Category List Fetched Successfully");
    	return new Response<>(HttpStatus.OK.value(),"Category List Fetched Successfully",categoryList);
    }

}