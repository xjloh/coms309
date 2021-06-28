package com.coms309.backend.Controllers;

import java.util.ArrayList;
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

import com.coms309.backend.Data.Comments;
import com.coms309.backend.mapper.CommentsMapper;

@RestController
public class CommentsController {
	@Autowired
	CommentsMapper commentsMapper;
	
	/**
	 * Function to insert a comment into the database
	 * @param sessionKey
	 * @param data JSON containing the comment data
	 * @return
	 */
	@RequestMapping(value = "/insertComment" , method = RequestMethod.POST)
	public ResponseEntity<String> insertComment(
			@RequestHeader(value = "Authorization", required=false) String sessionKey,
			@RequestBody Map<String,String> data) 
	{
		/*
		 * do authentication
		 */
		
		commentsMapper.insertComment(data.get("comment"), Integer.parseInt(data.get("pid")), data.get("author"));
		return new ResponseEntity<>("Success",HttpStatus.I_AM_A_TEAPOT);
	}
	
	/**
	 * Function to get a comment based on the comment id
	 * @param sessionKey
	 * @param pid
	 * @return
	 */
	@RequestMapping(value = "/getComment", method = RequestMethod.POST)
	public ResponseEntity<List<Comments>> getCommentsByPid
			(@RequestHeader(value = "Authorization", required=false) String sessionKey, 
			@RequestBody Map<String, String> requestBody)
	{
		/*
		 * do authentication
		 */
		int cId = Integer.parseInt(requestBody.get("threadId"));
		List<Comments> response = commentsMapper.getCommentByPid(cId);
		
		if(response == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		else {
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
		
	}
	
	/**
	 * Function to increment the upvote count of a comment based on the comment id
	 * @param cid
	 */
	@RequestMapping(value = "/updateUpvote",
			method = RequestMethod.POST)
	public void updateUpvote(@RequestBody Map<String, String> requestBody) 
	{
		commentsMapper.updateUpvote(Integer.parseInt(requestBody.get("commentId")));
	}
	
	/**
	 * Function to decrement the upvote count of a comment based on the comment id
	 * @param cid
	 */
	@RequestMapping(value = "/updateDownvote",
			method = RequestMethod.POST)
	public void updateDownvote(@RequestBody Map<String, String> requestBody) 
	{
		commentsMapper.updateDownvote(Integer.parseInt(requestBody.get("commentId")));
	}


}
