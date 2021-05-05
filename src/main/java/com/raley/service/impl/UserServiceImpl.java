package com.raley.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.raley.controller.AuthenticationController;
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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;


@Service(value = "userService")
public class UserServiceImpl implements UserDetailsService, UserService {
	
	Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
	
	@Autowired
	private UserDao userDao;
	
	//Injecting RestTemplate bean for calling another api 
    @Autowired
    private RestTemplate restTemplate;
    
    //Fetching the NodeApi url from properties file
    @Value("${nodeApi.categoryByUser.url}")
    private String url;

    //jackson library class to read/write the JSON object
    ObjectMapper mapper=new ObjectMapper();

	@Autowired
	private BCryptPasswordEncoder bcryptEncoder;

	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		logger.debug("finding the user by email. "+email);
		User user = userDao.findByEmail(email);
		if(user == null){
			logger.info("User Not Exists");
			throw new UsernameNotFoundException("Invalid username or password.");
		}
		logger.info("User Exists");
		return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), getAuthority());
	}

	private List<SimpleGrantedAuthority> getAuthority() {
		return Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN"));
	}

	public List<User> findAll() {
		List<User> list = new ArrayList<>();
		userDao.findAll().iterator().forEachRemaining(list::add);
		return list;
	}

	@Override
	public void delete(int id) {
		userDao.deleteById(id);
	}

	@Override
	public User findOne(String email) {
		return userDao.findByEmail(email);
	}

	@Override
	public User findById(int id) {
		Optional<User> optionalUser = userDao.findById(id);
		return optionalUser.isPresent() ? optionalUser.get() : null;
	}

    @Override
    public UserDto update(UserDto userDto) {
        User user = findById(userDto.getId());
        if(user != null) {
            BeanUtils.copyProperties(userDto, user, "password");
            userDao.save(user);
        }
        return userDto;
    }

	@Override
	public List<User> findByParent(int id) {
		return userDao.findByParent(id);
	}

	@Override
	public List<Category> getCategoryList(int id) {
		ResponseEntity<String> responseEntity=restTemplate.getForEntity(url+id, String.class);
		
		List<Category> categoryList=null;
		
		try {
			categoryList=mapper.readValue(responseEntity.getBody(), new TypeReference<List<Category>>(){});
		} 
		catch (Exception e) {
			logger.error("Getting Error while fetching the category list");
			e.printStackTrace();
		}
		
		categoryList.forEach(category -> {
			User parent=findById(Integer.parseInt(category.getUserId()));
			category.setParent(parent);
		});
		
		return categoryList;
	}
}
