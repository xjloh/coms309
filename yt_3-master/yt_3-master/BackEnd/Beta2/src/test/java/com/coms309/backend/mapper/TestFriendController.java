package com.coms309.backend.mapper;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.coms309.backend.Data.Friend;
import com.coms309.backend.Data.MessagePoints;
import com.coms309.backend.Data.Threads;
import com.coms309.backend.Data.Titles;
import com.coms309.backend.Data.User;
import com.coms309.backend.Controllers.FriendController;
import com.coms309.backend.Controllers.ThreadController;
import com.coms309.backend.Controllers.UserController;

public class TestFriendController {
	
	@InjectMocks
	@Autowired
	static FriendController friendController;
	
	@Mock
	static FriendMapper friendMapper;

    @BeforeAll
    public static void initMocks() {
    	friendMapper = Mockito.mock(FriendMapper.class);
    	
    	friendController = new FriendController();
    	friendController.setMapper(friendMapper);

    }
    @Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}
    
    @Test
	public void testGetFriendList() {
		final Friend f1 = new Friend("bob",false);
		final Friend f2 = new Friend("Tim",false);
		final Friend f3 = new Friend("Mary",true);
		
		final List<Friend> result = new ArrayList<>();
		result.add(f1);
		result.add(f2);
		result.add(f3);
		
		when(friendMapper.findAllFriends("sam")).thenReturn(result);
		
		Map <String,String> data = new HashMap<>();
		data.put("username", "sam");
		ResponseEntity<List<Friend>> response = friendController.findAllFriends(data);
		assertEquals(response.getBody().get(0).getName(),f1.getName());
		assertEquals(response.getBody().get(1).getName(),f2.getName());
		assertEquals(response.getBody().get(2).getName(),f3.getName());
		assertEquals(response.getBody().size(),3);
		assertEquals(response.getStatusCode(),HttpStatus.OK);	
	}
    
	@Test
	public void testAddFriend() {
		
		friendMapper.addFriend("bob","jon");

        verify(friendMapper, times(1)).addFriend("bob","jon");;	
	}
	
	public void testDeleteFriend() {
		friendMapper.addFriend("bob", "jon");
		friendMapper.deleteFriend("bob","jon");
		

        verify(friendMapper, times(1)).deleteFriend("bob","jon");;	
	}
	
}
