package RestAPI;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.PlaceholderAPI.Thread;
import com.example.demo.PlaceholderAPI.Titles;

public class ContentController {

	// Prints "Hello" on homepage
	@RequestMapping(value = "/",
			produces = MediaType.TEXT_PLAIN_VALUE)
	public String showHello()
	{
		return "Hello";
	}
	
	// Return JSON array of Titles objects
	@RequestMapping(value = "/getTitles",
			method = RequestMethod.GET)
	public ResponseEntity<List<Titles>> recvTitlesRequest(@RequestHeader(value = "Authorization", required=false) String sessionKey,
			@RequestParam String userId, @RequestParam float latitude, @RequestParam float longitude)
	{
		// Validate client's session key
		if (!DatabaseAPI.getInstance().validateSessionKey(userId, sessionKey))
			return new ResponseEntity<>(HttpStatus.NETWORK_AUTHENTICATION_REQUIRED);
	
		// Get Title array from database
		List<Titles> titles = DatabaseAPI.getInstance().getAllTitles();
		if (titles.isEmpty())	// Check if titles exist
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		
		// Produce response
		return new ResponseEntity<>(titles, HttpStatus.OK);
	}
	
	// Returns JSON Object with "title" and "content" fields
	@RequestMapping(value = "/getThread",
			method = RequestMethod.GET)
	public ResponseEntity<Map<String, String>> recvThreadRequest(@RequestHeader(value = "Authorization", required=false) String sessionKey,
			@RequestParam String userId, @RequestParam String threadId)
	{
		// Future, add location checking
		
		// Validate client's session key
		if (!DatabaseAPI.getInstance().validateSessionKey(userId, sessionKey))
			return new ResponseEntity<>(HttpStatus.NETWORK_AUTHENTICATION_REQUIRED);
		
		// Get thread object from database
		Thread result = DatabaseAPI.getInstance().getThreadById(threadId);
		if (result == null)	// Check if requested thread exist
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		
		// Produce response
		HashMap<String, String> response = new HashMap<>();
		response.put("title", result.getTitle());
		response.put("content", result.getContent());
				
		return new ResponseEntity<>(response, HttpStatus.OK);
		
		// TODO: Add comments field in reply
	}
	
	// Add a new thread into database
	@RequestMapping(value = "/addNewThread",
			method = RequestMethod.POST)
	public ResponseEntity<String> recvAddThreadRequest(@RequestHeader(value = "Authorization", required=false) String sessionKey,
			@RequestBody Map<String, String> tData)
	{
		// Validate client's session key
		if (!DatabaseAPI.getInstance().validateSessionKey(tData.get("userId"), sessionKey))
			return new ResponseEntity<>("INVALID SESSION KEY", HttpStatus.NETWORK_AUTHENTICATION_REQUIRED);
		
		// Add thread to database
		if (DatabaseAPI.getInstance().addThread_safe(tData.get("title"), tData.get("content"), tData.get("userId")))
			return new ResponseEntity<>("Thread Submitted", HttpStatus.OK);
		else
			// Fail to add new thread to database
			return new ResponseEntity<>("DATABASE ERROR", HttpStatus.BAD_GATEWAY);
		
		// Client should display success message and refresh page by requesting for the thread content again
	}
	

}
