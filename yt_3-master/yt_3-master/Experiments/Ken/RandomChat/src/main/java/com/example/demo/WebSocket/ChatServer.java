package com.example.demo.WebSocket;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


@ServerEndpoint("/websocket/{username}")
@Component
public class ChatServer {
	
	// Store all socket session and their corresponding username.
    private static Map<String, UserData> userDataMap = new HashMap<>();
    private static Map<Session, String> sessionUsernameMap = new HashMap<>();
    private static List<String> freeUserList = new ArrayList<>();
    
    private final Logger logger = LoggerFactory.getLogger(ChatServer.class);
    
    @OnOpen
    public void onOpen(Session session, 
    		@PathParam("username") String username,
    		@PathParam("latitude") float latitude,
    		@PathParam("longitude") float longitude) throws IOException 
    {
        logger.info("Entered into Open");
        
        // Create UserData for new user
        UserData newUser = new UserData(username, session, latitude, longitude);
        userDataMap.put(username, newUser);
        freeUserList.add(username);
        
        // Find for user in freeUserList
        UserData pairedUser = getSuitablePair(newUser);
        if (pairedUser == null)
        {
        	sendDirectMessage(username, "Please wait while we pair you up with a random user.");
        }
        else
        {
        	// Link up both users
        	newUser.setConnectedUser(pairedUser.getUserId());
        	sendDirectMessage(username, "You are now connected with " + newUser.getConnectedUser());
        	
        	pairedUser.setConnectedUser(newUser.getUserId());
        	sendDirectMessage(pairedUser.getUserId(), "You are now connected with " + pairedUser.getConnectedUser());
        	removeFromFreeList(pairedUser.getUserId());
        }
    }
 
    @OnMessage
    public void onMessage(Session session, String message) throws IOException 
    {
        // Handle new messages
    	logger.info("Entered into Message: Got Message:"+message);
    	String username = sessionUsernameMap.get(session);
    	
    	// Run if user is already paired
    	if (!freeUserList.contains(username))
    	{
    		UserData user = userDataMap.get(username);
    		sendDirectMessage(user.getConnectedUser(), message);	
    		sendDirectMessage(user.getUserId(), message);	
    	}
    }
 
    @OnClose
    public void onClose(Session session) throws IOException
    {
    	logger.info("Entered into Close");
    	
    	String username = sessionUsernameMap.get(session);    	
    	UserData user = userDataMap.get(username);
    	
    	if (user.getConnectedUser() != null)
    	{
    		// Handle paired user
    		UserData pairedUser = userDataMap.get(user.getConnectedUser());
    		sendDirectMessage(pairedUser.getUserId(), user.getUserId() + " has left the chat.");
    		pairedUser.setConnectedUser(null);
    		freeUserList.add(pairedUser.getUserId());
    	}
    	else
    	{
    		removeFromFreeList(username);
    	}
    	
    	// Clean up disconnecting user
    	userDataMap.remove(username);
    }
 
    @OnError
    public void onError(Session session, Throwable throwable) 
    {
        // Do error handling here
    	logger.info("Entered into Error");
    }
    
	private void sendDirectMessage(String username, String message) 
    {			
		try {
			// Send message to target user
			userDataMap.get(username).getSession().getBasicRemote().sendText(message);		
		
		} catch (IOException e) {
        	logger.info("Exception: " + e.getMessage().toString());
            e.printStackTrace();
        }
    }
    
    private static UserData getSuitablePair(UserData userData)
    {
    	// Test case
    	// Return first connected user
    	// Update: loop entire list to find closest users. Stop at the first user within x miles.
    	if (freeUserList.size() < 2)
    		return null;
    	else
    	{
    		String freeUser = freeUserList.get(0);
    		return userDataMap.get(freeUser);
    	}
    }
    
    private static void removeFromFreeList(String username)
    {
		int rmIndex = freeUserList.indexOf(username);
		freeUserList.remove(rmIndex);
    }
}
