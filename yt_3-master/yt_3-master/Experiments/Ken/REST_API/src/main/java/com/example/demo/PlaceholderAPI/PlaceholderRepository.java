package com.example.demo.PlaceholderAPI;

import com.example.demo.PlaceholderAPI.Thread;
import com.example.demo.UserData.User;
import java.util.ArrayList;
import java.util.List;


public class PlaceholderRepository {

	/*
	 * User DB Functions
	 */
	
	// Returns null if not found
	User findUserBySessionKey(String sessionKey) { return new User(); }
	

	/*
	 * Thread DB Functions
	 */
	

	
	public static boolean addThread(Thread newThread) { return true; };
	public static Thread getThreadById(String threadId) { return new Thread(); }
	
	public static boolean addComment(Comment newComment) { return true; };
	
	public static List<ThreadPoint> getThreadPointsByCoordinates(float latitude, float longitude)
	{
		// Return all thread points within within x radius ThreadPoint struct
		// Either FrontEnd or BackEnd has to parse to group the points
		
		return new ArrayList<ThreadPoint>();
	}
	
	public static List<Thread> getThreadByCoordinates(float latitude, float longitude) { return null; }	// get nearby ones as well
	public static List<Titles> getTitlesByCoordinates(float latitude, float longitude) 
	{ 
		 List<Thread> threads = getThreadByCoordinates(latitude, longitude);
		 List<Titles> titles = new ArrayList<Titles>();
		 
		 // Add only relevant details to titles structure
		 for (int i = 0; i < threads.size(); i++)
		 {
			 Titles currentTitle = new Titles();
			 currentTitle.setTitle(threads.get(i).getTitle());
			 // currentTitle.setTimeStamp(threads.get(i).getTimeStamp());
			 titles.add(currentTitle);
		 }
		 
		 return titles;
	}
}
