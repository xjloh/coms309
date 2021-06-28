package com.example.demo;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.example.demo.PlaceholderAPI.*;
import com.example.demo.PlaceholderAPI.Thread;
import com.example.demo.UserData.User;
import com.example.demo.UserData.UserRepository;

import java.util.Map;
import java.util.List;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.HashMap;

// add universal mapping /api
// implement springsecurity to replace code for session key checking in each function
	// or implement filter function

// todo: cleanup register and login functions
// clear out NewUserReg etc.

@RestController
public class BaseController {
	
	@Autowired
	UserRepository userDataRepository;
	

	
	// Receives POST request for user login and checks against default login
	@RequestMapping(value = "/login",
			produces = MediaType.TEXT_PLAIN_VALUE,
			method = RequestMethod.POST)
	public String recvLogin(@RequestBody Map<String, String> login)
	{
		if (login.get("userId").equals("admin") && login.get("password").equals("password"))
			return "temporary_session_key";
		else
			return "invalid_credentials";

		// Future version : pass into validateLogin (also generates session key) and return session key
	}

	// Receives POST request for user registration and print on screen
	@RequestMapping(value = "/register",
			produces = MediaType.TEXT_PLAIN_VALUE,
			method = RequestMethod.POST)
	public String recvRegistration(@RequestBody NewUserRegistration newUser)
	{
		return "New User Registration Information\n" +
				"User Id = " + newUser.getUserId() + "\n" +
				"Password = " + newUser.getPassword() + "\n" +
				"Email = " + newUser.getEmailAddress() + "\n" +
				"User Agent = " + newUser.getUserAgent();
				
	}
	

	

	
	
	/*
	 * Response management
	 */
	


	

}
