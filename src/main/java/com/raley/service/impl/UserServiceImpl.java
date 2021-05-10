package com.raley.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.shared.Application;
import com.raley.dao.UserDao;
import com.raley.model.User;
import com.raley.model.UserDto;
import com.raley.service.UserService;
import com.raley.vo.Category;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * @author saklen.mulla
 *
 */
@Service(value = "userService")
public class UserServiceImpl implements UserDetailsService, UserService {

	Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	// injecting UserDao bean for interacting with db
	@Autowired
	private UserDao userDao;

	// Injecting RestTemplate bean for calling another api
	@Autowired
	private RestTemplate restTemplate;

	// Injecting Eureka Client Bean
	@Autowired
	private EurekaClient eurekaClient;
	
	// Fetching application name from properties file
	@Value("${backend.inventory.app}")
	private String backendInventory;
	
	// Fetching category url from properties file
	@Value("${nodeApi.categoryByUser.url}")
	private String categoryInventoryEndpoint;

	// jackson library class to read/write the JSON object
	ObjectMapper mapper = new ObjectMapper();

	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		logger.debug("finding the user by email. {}",email);
		User user = userDao.findByEmail(email);
		if (user == null) {
			logger.info("User Not Exists");
			throw new UsernameNotFoundException("Invalid username or password.");
		}
		logger.info("User Exists");
		return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
				getAuthority());
	}

	private List<SimpleGrantedAuthority> getAuthority() {
		return Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN"));
	}

	public List<User> findAll() {
		logger.info("fetching list of all users");
		List<User> list = new ArrayList<>(); 					// creating arraylist of User type
		userDao.findAll().iterator().forEachRemaining(list::add); // iterating the user list fetched from db and adding
																	// into arraylist list
		return list;
	}

	@Override
	public void delete(int id) {
		logger.info("deleting user with id {}",id);
		userDao.deleteById(id); // delet's the user in db by id
		logger.info("user with id {} deleted",id);
	}

	@Override
	public User findOne(String email) {
		logger.info("findng the user : {}",email);
		return userDao.findByEmail(email); // finds the user by email id in db, returns null if not found
	}

	@Override
	public User findById(int id) {
		logger.info("finding the user : {}",id);
		Optional<User> optionalUser = userDao.findById(id); // finding the user by id in db
		return optionalUser.isPresent() ? optionalUser.get() : null; // if isPresent() returns true then return user
																		// else returns null
	}

	@Override
	public UserDto update(UserDto userDto) {
		logger.info("finding the user {} for update", userDto.getEmail());
		User user = findById(userDto.getId()); // returns user if found else returns null
		if (user != null) {
			logger.info("copying new user details");
			BeanUtils.copyProperties(userDto, user, "password"); // copying new user details from userDto to user except
																	// password field
			userDao.save(user); 						// saving updated user into db
			logger.info("updated user details saved into db");
		}
		else {
			logger.info("No user found with email id {}",userDto.getEmail());
		}
		return userDto; // returning updated user details to response
	}

	@Override
	public List<User> findByParent(int id) {
		logger.info("finding the user by parentId = {}", id);
		return userDao.findByParent(id); // returns list of users where parentId matches with parameter id
	}

	// calling node api through resttemplate
	@Override
	public List<Category> getCategoryList(int id) {
		logger.info("calling node api through resttemplate");
		
		Application application=eurekaClient.getApplication(backendInventory);	//getting application from eureka
		InstanceInfo instanceInfo=application.getInstances().get(0); //Getting instance from eureka server
		
		String url="http://"+instanceInfo.getHostName()+":"+instanceInfo.getPort()+categoryInventoryEndpoint;
		
		// fetching category list from node api & storing into responsEntity object
		ResponseEntity<String> responseEntity = restTemplate.getForEntity(url + id, String.class);

		 List<Category> categoryList=new ArrayList<>(); //creating empty list of type Category

		try {
			// mapping responsEntity(Json Object list) into categoryList(Category type Java Object)
			logger.info("mapping json object to java object list");
			categoryList = mapper.readValue(responseEntity.getBody(), new TypeReference<List<Category>>() {
			});
		} catch (Exception e) {
			logger.error("Getting Error while fetching the category list");
			e.printStackTrace();
		}
		
		User parent = findById(id); // fetching the user from db

		categoryList.forEach(category ->
			category.setParent(parent) // setting the fetched user into category object
		);

		return categoryList; // returns category list if found else returns null
	}
}
