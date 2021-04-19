package com.raley.test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.raley.controller.UserController;
import com.raley.model.User;
import com.raley.service.UserService;


public class UserControllerTest {
	

	private static final Logger logger = LoggerFactory.getLogger(UserControllerTest.class);
	private UserController userController;
	private UserService userService;
	private MockMvc mockMvc;

	public UserControllerTest() {
		userService = Mockito.mock(UserService.class);
		userController = new UserController(userService);
	}

	@Before
	public void setUp() {
		this.mockMvc = MockMvcBuilders.standaloneSetup(userController).setControllerAdvice(new Exception()).build();
	}

	@Test
	public void getUsers() throws Exception {

		String uri = "/user/list";
		List<User> users= new ArrayList<User>();
		users.add(new User(378,"Validate","User","validate.user@gmail.com","Validate@123",8900876589l,416101,"India","Maharashtra","Pune","ADM","adb","abc","normal",8));
		users.add(new User(379,"Shradha","Baldawa","shradha@gmail.com","Shradha@123",8900876589l,416101,"India","Maharashtra","Mumbai","ADM","adb","abc","normal",8));
	
		when(userService.findAll()).thenReturn(users);
		List<User> userList = userService.findAll();
		assertEquals(2, userList.size());
		verify(userService, times(1)).findAll();
		
		  mockMvc.perform(MockMvcRequestBuilders.get(uri)
		  .header("Content-Type","application/json"))
		  .andExpect(MockMvcResultMatchers.status().is(HttpStatus.OK.value()))
		  .andReturn();  		  
	}
	
	@Test
	public void getUser() throws Exception {

		String uri = "/user/getUser/1";
		
		when(userService.findById(1)).thenReturn(new User(1,"Validate","User","user@email.com","Validate@123",8900876589l,416101,"India","Maharashtra","JSP","ADM","adb","abc","normal",8));
		 	
		 User user= userService.findById(1);
		 mockMvc.perform(MockMvcRequestBuilders.get(uri)
		  .header("Content-Type","application/json"))
		  .andExpect(MockMvcResultMatchers.status().is(HttpStatus.OK.value()))
		  .andReturn();
	         
	        assertEquals("Validate", user.getFirstName());
	        assertEquals("User", user.getLastName());
	        assertEquals("user@email.com", user.getEmail());
	        
	}


}
