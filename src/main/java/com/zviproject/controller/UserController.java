package com.zviproject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.zviproject.component.entity.ReturnedId;
import com.zviproject.component.entity.User;
import com.zviproject.service.UserService;

@Configuration
@RestController
@RequestMapping("/chat")
public class UserController {

	@Autowired
	UserService messageService;

	/**
	 * Register new user in chat<br>
	 * Return information for user.
	 * 
	 * @param user
	 * @return String
	 */
	@RequestMapping(value = "/register", consumes = "application/json", method = RequestMethod.POST)
	public ReturnedId registerUser(@RequestBody User user) {

		return messageService.registerUser(user);

	}

	/**
	 * Update token for user
	 * 
	 * @param token
	 */
	@RequestMapping(value = "/update/{idUser}", method = RequestMethod.PUT)
	public ReturnedId updateToken(@RequestHeader(value = "access_token") String access_token,
			@PathVariable("idUser") int id) {
		return messageService.updateToken(access_token, id);
	}
}
