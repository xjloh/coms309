package com.coms309.backend.Controllers;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.coms309.backend.Data.User;
import com.coms309.backend.mapper.ThreadMapper;
import com.coms309.backend.mapper.UserMapper;

@RestController
public class UserController {
	
	@Autowired
	UserMapper userMapper;
	
	public void setMapper(UserMapper t) {
        this.userMapper = t;
    }

	/**
	 * Function to add a new user into the database
	 * @param u
	 * 		JSON containing information about the user
	 * @return ResponseEntity containing the status of the insertion
	 */

	@RequestMapping(value = "/register",
			method = RequestMethod.POST)
	public ResponseEntity<Map<String, String>> recvRegistration(@RequestBody Map<String, String> u)
	{		
		Random rGen = new Random();
		int uId = rGen.nextInt(9999);	// TODO : validate unique id like in addThread
		
		User newUser = new User(uId, u.get("userId"), u.get("password"), u.get("email"), 0, 0, "default","","","");
		userMapper.insertUser(newUser);
		
		// Check if user is added
		HashMap<String, String> registerResponse = new HashMap<String, String>();

		String newSessionKey = makeSessionKey();
		userMapper.addSessionKey(u.get("userId"), newSessionKey);
		
		registerResponse.put("registrationStatus", "succeed");
		registerResponse.put("userId", u.get("userId"));

			// registerResponse.put("registrationStatus", "failed");
		registerResponse.put("sessionKey", newSessionKey);

		return new ResponseEntity<>(registerResponse, HttpStatus.OK);
	}

	/**
	 * Function to validate user credentials and authenticate login
	 * @param login
	 * 		JSON containing user password and name
	 * @return ResponseEntity regarding the login status
	 */
	//TODO update user location after login successful

	@RequestMapping(value = "/login",
			method = RequestMethod.POST)
	public ResponseEntity<Map<String, String>> recvLogin(@RequestBody Map<String, String> login)
	{
		String userId = login.get("userId");
		String password = login.get("password");
				
		// Checks whether the userId and password matches
		HashMap<String, String> loginResponse = new HashMap<String, String>();

		User u = userMapper.getUserByUsername(userId);
		if (password.equals(u.getPassword()))
		{
			loginResponse.put("loginStatus", "succeed");
			loginResponse.put("userId", userId);
			String clientSessionKey = userMapper.getSessionKey(userId).getSessionKey();
			loginResponse.put("sessionKey", clientSessionKey);
			userMapper.updateUserOnline(userId, 1);
		}
		else
		{
			loginResponse.put("loginStatus", "failed");
		}
		
		return new ResponseEntity<>(loginResponse, HttpStatus.OK);
	}

	/**
	 * Function to update the user location
	 * @param user
	 * 		JSON containing user information
	 */

	@RequestMapping(value = "/updateLocation",
			method = RequestMethod.PUT)
	public void updateUserLocation(Map<String , String> user) {
		userMapper.updateUserLocation(Double.parseDouble(user.get("longitude")),Double.parseDouble(user.get("latitude")), user.get("username"));
	}
	
	@RequestMapping(value = "/getUserInfo",
			method = RequestMethod.POST)
	public ResponseEntity<User> getUserInfo(@RequestHeader(value = "Authorization", required=false) String sessionKey,
			@RequestBody Map<String, String> data){
		User u = userMapper.getUserByUsername(data.get("username"));
		if(u == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		else {
			return new ResponseEntity<>(u, HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/updateUserProfile",
			method = RequestMethod.POST)
	public ResponseEntity<String> updateUserInfo(@RequestHeader(value = "Authorization", required=false) String sessionKey,
				@RequestBody Map<String, String> data){
			String username, email, bio;
			int user_id;
			username = data.get("username");
			email = data.get("email");
			bio = data.get("bio");
			user_id = Integer.parseInt(data.get("uid"));
			userMapper.updateUserProfile(username, email, bio, user_id);
			return new ResponseEntity<>("Update successful", HttpStatus.OK);
	}
	
	
	//==========UTILITY FUNCTIONS===============================

	/**
	 * Function to generate a session Key
	 * @return a session Key
	 */

	public String makeSessionKey()
	{
		// make private static final as instance variable for thread safe
		// https://stackoverflow.com/questions/13992972/how-to-create-a-authentication-token-using-java
		SecureRandom sr = new SecureRandom();
		Base64.Encoder b64 = Base64.getUrlEncoder();
		
		byte[] sessionKey = new byte[24];
		sr.nextBytes(sessionKey);
		return b64.encodeToString(sessionKey);
	}
	
}
