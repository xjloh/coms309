package com.coms309.backend.WebSocket;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.websocket.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ChatServerUtil 
{
	// Store all socket session and their corresponding username.
    private static Map<String, UserData> userDataMap = new HashMap<>();
    private static Map<Session, String> sessionUsernameMap = new HashMap<>();
    private static List<String> freeUserList = new ArrayList<>();
    
    private final Logger logger = LoggerFactory.getLogger(ChatServer.class);
    
    public UserData addUser(String username, Session session, float latitude, float longitude)
    {
        logger.info("Entered into addUser");

        // Create UserData for new user
        UserData newUser = new UserData(username, session, latitude, longitude);
        userDataMap.put(username, newUser);
        sessionUsernameMap.put(session, username);
        
        return newUser;
    }
    
	public UserData getSuitablePair(UserData userData)
    {
    	// Test case
    	// Return first connected user
    	// Update: loop entire list to find closest users. Stop at the first user within x miles.
    	if (freeUserList.size() == 0)
    		return null;
    	else
    	{
    		String freeUsername = freeUserList.get(0);
    		return userDataMap.get(freeUsername);
    	}
    }
	
    public void sendDirectMessage(String username, String message) 
    {			
		try {
			// Send message to target user
			userDataMap.get(username).getSession().getBasicRemote().sendText(message);		
		
		} catch (IOException e) {
        	logger.info("Exception: " + e.getMessage().toString());
            e.printStackTrace();
        }
    }
	

	public void pairUsers(UserData userA, UserData userB)
	{
    	// Link up both users
    	userA.setConnectedUser(userB.getUserId());
    	userB.setConnectedUser(userA.getUserId());

    	sendDirectMessage(userA.getUserId(), "You are now connected with " + userB.getUserId());    	
    	sendDirectMessage(userB.getUserId(), "You are now connected with " + userA.getUserId());
    	
    	removeFromFreeList(userB.getUserId());
	}
	
	public void addFreeUser(String username)
	{
		freeUserList.add(username);
	}
	
	public boolean isSessionPaired(Session session)
	{
		// Check in freeUserList
    	String username = sessionUsernameMap.get(session);
        return !freeUserList.contains(username);
	}
	
	public void forwardDirectMessage(Session session, String message)
	{
		logger.info("forwarding message = " + message);
		
    	String username = sessionUsernameMap.get(session);
		UserData user = userDataMap.get(username);
		
		String prefix = "[" + username + "] ";
		sendDirectMessage(user.getConnectedUser(), prefix + message);	
		sendDirectMessage(user.getUserId(), prefix + message);	
	}
	
	// Free the given session's paired user
	public void freePairedUser(Session session)
	{
    	String username = sessionUsernameMap.get(session);
    	UserData user = userDataMap.get(username);
		
    	// Double-verify paired user exist
    	if (user == null || user.getConnectedUser() == null)
    		return;

		// Free paired user
		UserData pairedUser = userDataMap.get(user.getConnectedUser());
		sendDirectMessage(pairedUser.getUserId(), user.getUserId() + " has left the chat.");
		sendDirectMessage(pairedUser.getUserId(), "Please wait while we match you up with another user!");

		pairedUser.setConnectedUser(null);
		freeUserList.add(pairedUser.getUserId());
	}
	
	// Remove the given session from chat service
	public void removeUser(Session session)
	{
    	String username = sessionUsernameMap.get(session);
    	
    	// Remove user data from data structures
    	removeFromFreeList(username);
    	userDataMap.remove(username);
    	sessionUsernameMap.remove(session);
	}
	
    public void removeFromFreeList(String username)
    {
    	if (freeUserList.contains(username))
    	{
    		int rmIndex = freeUserList.indexOf(username);
    		freeUserList.remove(rmIndex);
    	}
    }
}
