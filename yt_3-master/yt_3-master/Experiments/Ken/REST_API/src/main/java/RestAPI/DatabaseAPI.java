package RestAPI;

import com.example.demo.PlaceholderAPI.Thread;
import com.example.demo.PlaceholderAPI.Titles;

import java.util.List;
import java.util.ArrayList;

// Singleton 
public class DatabaseAPI {

	private static DatabaseAPI single_instance = null;

	// Create Mapper objects here
	
	private DatabaseAPI() {	}
	
	public static DatabaseAPI getInstance()
	{
		if (single_instance == null)
			single_instance = new DatabaseAPI();
		
		return single_instance;
	}
	
	/*
	 * Credential API
	 */
	
	public boolean addUser(String userId, String emailAddress, String password)
	{
		// Add a new user in the database
		// Currently only supports userId, emailAddress, and password
		// Session_key will be added later using addSessionKeyToUser, therefore it will initially be empty
		return true;
	}
	
	// Return true if login credentials is valid
	public boolean isLoginValid(String userId, String password)
	{
		// Checks whether the userId and password matches
		return true;
	}
	
	public boolean addSessionKeyToUser(String userId, String sessionKey)
	{
		// Adds a sessionKey to a user by userId
		return true;
	}
	
	public String getSessionKeyByUserId(String sessionKey)
	{
		// Returns userId by sessionKey
		return "session_key";
	}
	
	public boolean validateSessionKey(String userId, String sessionKey)
	{
		// Checks whether the userId in the database contains the sessionKey
		return true;
	}

	
	/*
	 * Content API
	 */
	
	public boolean addThread_safe(String title, String content, String userId)
	{
		// Similar to verifyThreadAdd(Thread t)
		// _safe postfix indicates that it verifies whether the thread has been added
		return true;
	}
	
	public Thread getThreadById(String threadId)
	{
		return new Thread();
	}
	
	public List<Thread> getAllThreads()
	{
		// return threadMapper.findAll();
		return new ArrayList<Thread>();
	}
	
	public List<Titles> getAllTitles()
	{
		// Currently returns all titles without geo-restriction
		List<Thread> threadList = getAllThreads();
		
		List<Titles> titleList = new ArrayList<Titles>();
		
		for (int i = 0; i < threadList.size(); i++)
		{
			Titles newTitle = new Titles();
			newTitle.setTitle(threadList.get(i).getTitle());
			newTitle.setTid(threadList.get(i).getTid());
			titleList.add(newTitle);
		}
		
		return titleList;
	}
}
