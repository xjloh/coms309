 package com.coms309.backend.Controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.coms309.backend.Data.MessagePoints;
import com.coms309.backend.Data.Threads;
import com.coms309.backend.Data.Titles;

import com.coms309.backend.mapper.ThreadMapper;
import com.coms309.backend.mapper.UserMapper;

@RestController
public class ThreadController {
	
	@Autowired
	ThreadMapper threadMapper;
	
	@Autowired
	UserMapper userMapper;

	
	/**
	 * test function
	 * @return
	 */
	public void setMapper(ThreadMapper t) {
        this.threadMapper = t;
    }


	@RequestMapping(value = "/getThread2",
			method = RequestMethod.GET
			)
	public String recvThreadRequest()
	{
		// Future, add location checking
		
		// Get thread object from database
		Threads t = threadMapper.findById(1);
		return t.getContent();
		
		/*
		 * Map<String, String> tResponse = DatabaseAPI.getInstance().getThreadById("1");
		 * 
		 * if (tResponse.get("success").equals("no")) return "db error"; else return
		 * tResponse.get("content");
		 */
		
		// TODO: Add comments field in reply
	}

	/**
	 * FUnction to get results of title objects
	 * @return ResponseEntity containing status of query and list containign results
	 */

	// Return JSON array of Titles objects
	@RequestMapping(value = "/getTitles",
			method = RequestMethod.GET)
	public ResponseEntity<List<Titles>> recvTitlesRequest()
	{
		// Get thread list array from database
		List<Threads> threadList = threadMapper.getAllThreads();
		if (threadList.isEmpty())	// Check if titles exist
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		
		// Produce response
		List<Titles> titleList = new ArrayList<Titles>();
		for (int i = 0; i < threadList.size(); i++)
		{
			Titles title = new Titles();
			title.setThreadId(String.valueOf(threadList.get(i).getTid()));
			title.setTitle(threadList.get(i).getTitle());
			titleList.add(title);
		}
		
		return new ResponseEntity<>(titleList, HttpStatus.OK);
	}
	
	/**
	 * Function to get a thread based on the threadID
	 * @param requestBody Map containing threadId
	 * @return ResponseEntity containing status of query and Map containing the title and content of the thread
	 */

	@RequestMapping(value = "/getThread",
			method = RequestMethod.POST
			)
	public ResponseEntity<Map<String, String>> recvThreadRequest(@RequestBody Map<String, String> requestBody)
	{
		// Get thread object from database
		Map<String, String> tResponse = new HashMap<String, String>();
		Threads tData = threadMapper.findById(Integer.parseInt(requestBody.get("threadId")));

		if (tData == null)	// Check if requested thread exist
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		else	
		{
			tResponse.put("title", tData.getTitle());
			tResponse.put("content", tData.getContent());
			tResponse.put("stringId",tData.getAuthor_id());
			
			return new ResponseEntity<>(tResponse, HttpStatus.OK);
		}
	}

	/**
	 * Function to add a thread into the database
	 * @param sessionKey
	 * 		user session key
	 * @param tData
	 * 		JSON containing data
	 * @return ResponseEntity regarding the success of the insertion
	 */
	@RequestMapping(value = "/addNewThread",
			method = RequestMethod.POST)
	public ResponseEntity<String> recvAddThreadRequest(@RequestHeader(value = "Authorization", required=false) String sessionKey,
			@RequestBody Map<String, String> tData)
	{
		// Return error if no or invalid session key
		String validKey = userMapper.getSessionKey(tData.get("userId")).getSessionKey();
		if (sessionKey == null || !sessionKey.equals(validKey))
			return new ResponseEntity<>("Not Authorized", HttpStatus.NETWORK_AUTHENTICATION_REQUIRED);
	
		// Generate unique threadId
		Random rGen = new Random();
		Integer uniqueId;
		do 
		{
			uniqueId = rGen.nextInt(999999);
			
		} while (threadMapper.findById(uniqueId) != null);
		
		// Add new thread to Database
		double d_latitude = Double.parseDouble(tData.get("latitude"));
		double d_longitude = Double.parseDouble(tData.get("longitude"));
		Threads newThread = new Threads(uniqueId, tData.get("title"), tData.get("content"), null, d_longitude, d_latitude, tData.get("userId"));
		threadMapper.addThread(newThread);
		
		if (threadMapper.findById(uniqueId) != null)
			return new ResponseEntity<>("Thread Submitted", HttpStatus.OK);
		else
			return new ResponseEntity<>("DATABASE ERROR", HttpStatus.BAD_GATEWAY);

	}
	/**
	 * Function to get threads within a certain radius of a point
	 * @param sessionKey
	 * 		user session key
	 * @param latitude
	 * 		user latitude
	 * @param longitude
	 * 		user longitude
	 * @return ResponseEntity regarding query status and List of ThreadMapView containing relevant data to display
	 */
	@RequestMapping(value = "/getMessagePoints",
			method = RequestMethod.POST)
	public ResponseEntity<List<MessagePoints>> getThreadsWithinRadius(@RequestHeader(value = "Authorization", required=false) String sessionKey,
			@RequestBody Map<String, String> tData)
	{	
		List<Threads> temp= threadMapper.getThreadsRadius(Double.parseDouble(tData.get("longitude")), Double.parseDouble(tData.get("latitude")), 0.3);

		List <MessagePoints> response = new ArrayList<>();
		MessagePoints t1;
		
		for (int i = 0; i < temp.size();i++) 
		{
			t1 = new MessagePoints(temp.get(i).getTid(),temp.get(i).getTitle(),temp.get(i).getLatitude(),temp.get(i).getLongitude());
			response.add(t1);
		}
		
		if (response.size() == 0)	// No message point within radius
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		else
			return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
}
