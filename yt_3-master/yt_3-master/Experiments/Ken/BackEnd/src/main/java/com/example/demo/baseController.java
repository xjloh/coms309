package com.example.demo;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.MediaType;

import java.util.Map;

@RestController
public class baseController {

	// Prints "Hello" on homepage
	@RequestMapping(value = "/",
			produces = MediaType.TEXT_PLAIN_VALUE)
	public String showHello()
	{
		return "Hello";
	}
	
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
}
