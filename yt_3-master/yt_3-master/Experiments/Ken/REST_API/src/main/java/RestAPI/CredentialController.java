package RestAPI;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

public class CredentialController {
	
	// Receives POST request for user registration and generates session key and store in database
	@RequestMapping(value = "/register",
			method = RequestMethod.POST)
	public ResponseEntity<Map<String, String>> recvRegistration(@RequestBody Map<String, String> newUser)
	{		
		HashMap<String, String> registerResponse = new HashMap<String, String>();
		
		if (DatabaseAPI.getInstance().addUser(newUser.get("userId"),
				newUser.get("emailAddress"), 
				newUser.get("password")))
		{
			String newSessionKey = makeSessionKey();
			registerResponse.put("registrationStatus", "succeed");
			registerResponse.put("sessionKey", newSessionKey);
			
			// Save session key to database
			DatabaseAPI.getInstance().addSessionKeyToUser(newUser.get("userId"), newSessionKey);
		
		}
		else
		{
			registerResponse.put("registrationStatus", "failed");
		}
		
		return new ResponseEntity<>(registerResponse, HttpStatus.OK);
	}
	
	// Receives POST request for user login and return session key
	@RequestMapping(value = "/login",
			method = RequestMethod.POST)
	public ResponseEntity<Map<String, String>> recvLogin(@RequestBody Map<String, String> login)
	{
		// Check if login credentials is valid
		boolean isValid = DatabaseAPI.getInstance().isLoginValid(login.get("userId"), login.get("password"));
		
		HashMap<String, String> loginResponse = new HashMap<String, String>();
		
		if (isValid)
		{
			String clientSessionKey = DatabaseAPI.getInstance().getSessionKeyByUserId(login.get("userId"));
			loginResponse.put("loginStatus", "succeed");
			loginResponse.put("sessionKey", clientSessionKey);
		}
		else
		{
			loginResponse.put("loginStatus", "failed");
		}
		
		return new ResponseEntity<>(loginResponse, HttpStatus.OK);
	}
	
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
