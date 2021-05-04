package com.raley.controller;

import java.util.List;

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

    @Autowired
    private UserService userService;
    
    public UserController(UserService userService) {
		super();
		this.userService = userService;
	}

    @ApiOperation(value="listUser" ,notes ="This method gets list of users")
    	@ApiResponses(value = { 
            @ApiResponse(code = 200, message = "Success|OK"),
            @ApiResponse(code = 401, message = "not authorized!")})
    @GetMapping("/list")
    public Response<List<User>> listUser(){
        return new Response<>(HttpStatus.OK.value(), "User list fetched successfully.",userService.findAll());
    }
    
    @ApiOperation(value="listUserByParent",notes="This method gets list of users by parent", authorizations = {
    	      @Authorization(value = "Only authorized user can get the list. Provide access token")
    	    }) 
    @ApiResponses(value = { 
            @ApiResponse(code = 200, message = "Success|OK"),
            @ApiResponse(code = 401, message = "not authorized!"), 
            @ApiResponse(code = 404, message = "user not found!!!") })
    @GetMapping("/listByParent/{id}")
    public Response<List<User>> listUserByParent(@PathVariable int id) {
    	List<User> userList=userService.findByParent(id);
    	if(userList.isEmpty())
    		return new Response<>(HttpStatus.NOT_FOUND.value(), "No Users Added By This Parent", null);
    	
    	return new Response<List<User>>(HttpStatus.OK.value(), "User list fetched successfully.", userList);
    }

    @ApiOperation(value="getOne",notes="This method fetches user with id")
    @ApiResponses(value = { 
            @ApiResponse(code = 200, message = "Success|OK"),
            @ApiResponse(code = 401, message = "not authorized!"), 
            @ApiResponse(code = 404, message = "user not found!!!") })
    @GetMapping("/getUser/{id}")
    public Response<User> getOne(@PathVariable int id){
        return new Response<>(HttpStatus.OK.value(), "User fetched successfully.",userService.findById(id));
    }

    @ApiOperation(value="updateVendor",notes="This method updates user")
    @PutMapping("/updateUser/{id}")
    public Response<UserDto> updateVendor(@RequestBody UserDto userDto) {
        return new Response<>(HttpStatus.OK.value(), "User updated successfully.",userService.update(userDto));
    }

    @ApiOperation(value="deleteVendor",notes="This method deletes user with given id")
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

    @ApiOperation(value="getCategory",notes="This method fetches catergory list for given id")
    @ApiResponses(value = { 
            @ApiResponse(code = 200, message = "Success|OK"),
            @ApiResponse(code = 401, message = "not authorized!"), 
            @ApiResponse(code = 404, message = "category not found!!!") })
    @GetMapping("/getCategory/{id}")
    public Response<List<Category>> getCategory(@PathVariable int id) {
    	List<Category> categoryList=userService.getCategoryList(id);
    	if(categoryList.isEmpty())
    		return new Response<>(HttpStatus.NOT_FOUND.value(),"No Category Added By This User",null);
    	
    	return new Response<>(HttpStatus.OK.value(),"Category List Fetched Successfully",categoryList);
    }

}
