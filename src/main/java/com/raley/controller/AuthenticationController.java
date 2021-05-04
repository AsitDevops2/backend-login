package com.raley.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.raley.config.JwtTokenUtil;
import com.raley.model.Response;
import com.raley.model.AuthToken;
import com.raley.model.LoginUser;
import com.raley.model.User;
import com.raley.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author abhay.thakur
 *
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/token")
@Api(value="AuthenticationController")
public class AuthenticationController {
	Logger logger = LoggerFactory.getLogger(AuthenticationController.class);

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private UserService userService;

	/**
	 * @param loginUser
	 * @return
	 */
    @ApiOperation(value="login" ,notes ="This method authenicates the user and provides auth token")
	@RequestMapping(value = "/generate-token", method = RequestMethod.POST)
	public Response<AuthToken> login(@RequestBody LoginUser loginUser) {
		logger.info("authenticate user : " + loginUser.getUsername() + " for genearate token");
		try {
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(loginUser.getUsername(), loginUser.getPassword()));
			final User user = userService.findOne(loginUser.getUsername());
			final String token = jwtTokenUtil.generateToken(user);
			return new Response<>(200, "success",new AuthToken(token, user.getId(), user.getEmail(), user.getMobile(), user.getFirstName(),
							user.getLastName(), user.getPin(), user.getCountry(), user.getState(), user.getCity(),
							user.getDept(), user.getAddr1(), user.getAddr2(),user.getRole(),user.getParent()));
		} catch (Exception e) {
			return new Response<>(400, "Username or Password is invalid.", null);

		}
	}

}
