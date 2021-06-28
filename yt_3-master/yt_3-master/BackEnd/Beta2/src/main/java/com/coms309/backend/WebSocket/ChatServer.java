package com.coms309.backend.WebSocket;

import java.io.IOException;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@ServerEndpoint(value = "/websocket/{username}/{latitude}/{longitude}", configurator = CustomConfigurator.class)
@Component
public class ChatServer {
	
	private final ChatServerUtil util;
	
	@Autowired
	public ChatServer(ChatServerUtil util)
	{
		this.util = util;
	}
    
    private final Logger logger = LoggerFactory.getLogger(ChatServer.class);
    
    @OnOpen
    public void onOpen(Session session, 
    		@PathParam("username") String username,
    		@PathParam("latitude") float latitude,
    		@PathParam("longitude") float longitude) throws IOException 
    {
        logger.info("Entered into Open");
        
        // Create UserData for new user
        UserData newUser = util.addUser(username, session, latitude, longitude);
        
        // Find for user in freeUserList
        UserData pairedUser = util.getSuitablePair(newUser);
        
        if (pairedUser == null)
        {
        	util.sendDirectMessage(username, "Please wait while we pair you up with a random user.");
        	util.addFreeUser(newUser.getUserId());
        }
        else
        {
        	// Link up both users
        	util.pairUsers(newUser, pairedUser);
        }
    }
 
    @OnMessage
    public void onMessage(Session session, String message) throws IOException 
    {
        // Handle new messages
    	logger.info("Entered into Message: Got Message:"+message);
    	    	
    	// Forward message if user is paired
    	if (util.isSessionPaired(session))
    	{
    		util.forwardDirectMessage(session, message);
    	}
    }
 
    @OnClose
    public void onClose(Session session) throws IOException
    {
    	logger.info("Entered into Close");
    	
    	if (util.isSessionPaired(session))
    		util.freePairedUser(session);
    	
    	util.removeUser(session);
    }
 
    @OnError
    public void onError(Session session, Throwable throwable) 
    {
        // Do error handling here
    	logger.info("Entered into Error");
    }
}
