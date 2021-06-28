package com.coms309.backend.Controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.coms309.backend.Data.Friend;
import com.coms309.backend.mapper.FriendMapper;
import com.coms309.backend.mapper.UserMapper;

@RestController
public class FriendController {
	@Autowired
	FriendMapper friendMapper;
	
	public void setMapper(FriendMapper t) {
        this.friendMapper = t;
    }
	
	@RequestMapping(value = "/getFriendList", method = RequestMethod.POST)
	public ResponseEntity<List<Friend>> findAllFriends(@RequestBody  Map<String, String> data){
		List<Friend> response = friendMapper.findAllFriends((String)data.get("username"));
		
		return new ResponseEntity<>(response,HttpStatus.OK);
	
	}
	
	@RequestMapping(value = "/addFriend", method = RequestMethod.POST)
	public ResponseEntity<String> addFriends(@RequestBody Map<String,String> data){
		friendMapper.addFriend((String)data.get("username"),(String)data.get("f_name"));	
		return new ResponseEntity<>("Succesfully added friend",HttpStatus.OK);
	}
	
	@RequestMapping(value = "/deleteFriend", method = RequestMethod.POST)
	public ResponseEntity<String> deleteFriend(@RequestBody Map<String,String> data){
		friendMapper.deleteFriend((String)data.get("username"),(String)data.get("f_name"));	
		return new ResponseEntity<>("Succesfully deleted friend",HttpStatus.OK);
	}
}
