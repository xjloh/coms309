package com.coms309.backend.Controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.coms309.backend.Data.Conversation;
import com.coms309.backend.Data.Inbox;
import com.coms309.backend.mapper.InboxMapper;

@RestController
public class InboxController {
	@Autowired
	InboxMapper inboxMapper;

	/**
	 * Function to return the query result formatted to fit the inbox view
	 * @param data
	 * @return ResponseEntity containing request status and list of inbox views if successfull
	 */
	
	@RequestMapping(value = "/getInboxView",
			method = RequestMethod.POST)
	public ResponseEntity<List<Inbox>> getInboxView(@RequestHeader(
			value = "Authorization", required=false) String sessionKey,
			@RequestParam String username){
		List <Inbox> i = inboxMapper.getInboxView(username);
		if(i.size() == 0) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		
		return new ResponseEntity<>(i,HttpStatus.OK);		
	}
	/**
	 * Function to insert a message into the database
	 * @param data
	 * @return RespomseEntity containing the status of the insertion
	 * 
	 */
	@RequestMapping(value = "/insertMessage",
			method = RequestMethod.POST)
	public ResponseEntity<String> insertMessage(@RequestBody Map<String,String> data){
		
		inboxMapper.InsertMessage(data.get("fromUser"),data.get("toUser"), data.get("message"));
		
		return new ResponseEntity<>("Message Submitted", HttpStatus.OK);
	}

	/**
	 * Function to return the query result formatted to fit the chat view
	 * @param data
	 * @return ResponseEntity containing status q
	 */
	
	@RequestMapping(value = "/getChat", method = RequestMethod.POST)
	public ResponseEntity<List<Conversation>>getChat(@RequestBody Map<String, String> requestBody)
	{
		List<Conversation> l = inboxMapper.getChatView(requestBody.get("sender"), requestBody.get("receiver"));
		
		return new ResponseEntity<>(l,HttpStatus.OK);
	}
	
	@RequestMapping(value = "/updateChat", method = RequestMethod.POST)
	public ResponseEntity<List<Conversation>>updateChat(@RequestBody Map<String, String> requestBody)
	{
		List<Conversation> l = inboxMapper.updateChatView(Integer.parseInt(requestBody.get("mid")), requestBody.get("sender"), requestBody.get("receiver"));
		
		return new ResponseEntity<>(l,HttpStatus.OK);
	}
	
	

}
