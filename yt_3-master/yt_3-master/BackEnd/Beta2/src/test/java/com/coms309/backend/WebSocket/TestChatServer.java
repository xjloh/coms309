package com.coms309.backend.WebSocket;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;

import javax.websocket.Session;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import com.coms309.backend.WebSocket.ChatServer;
import com.coms309.backend.WebSocket.ChatServerUtil;
import com.coms309.backend.WebSocket.UserData;

public class TestChatServer {
	
	@Mock
    ChatServerUtil util;

	@Rule public MockitoRule rule = MockitoJUnit.rule();

	ChatServer serv = null;

	@Before
	public void setUp()
	{
		serv = new ChatServer(util);
	}
	
	@Test
	public void testOnOpenWithoutPairing() throws Exception
	{
		UserData userData = mock(UserData.class);
		
		when(util.addUser("testUser", null, 1.1f, 1.1f)).thenReturn(userData);
		when(util.getSuitablePair(any(UserData.class))).thenReturn(null);
		
		doNothing().when(util).sendDirectMessage("testUser", "Please wait while we pair you up with a random user.");
		
		when(userData.getUserId()).thenReturn("testUser");
		doNothing().when(util).addFreeUser("testUser");
		
		serv.onOpen(null, "testUser", 1.1f, 1.1f);
		
		verify(util, times(1)).addUser("testUser", null, 1.1f, 1.1f);
		verify(util, times(1)).getSuitablePair(any());
		verify(util, times(1)).sendDirectMessage("testUser", "Please wait while we pair you up with a random user.");
		verify(userData, times(1)).getUserId();
		verify(util, times(1)).addFreeUser("testUser");
	}
	
	@Test
	public void testOnOpenWithPairing() throws Exception
	{
		// userA is thisUser and userB is a suitable user in freeUserList
		UserData userA = mock(UserData.class);
		UserData userB = mock(UserData.class);
		
		when(util.addUser("userA", null, 1.1f, 1.1f)).thenReturn(userA);
		when(util.getSuitablePair(any(UserData.class))).thenReturn(userB);
		doNothing().when(util).pairUsers(userA, userB);

		serv.onOpen(null, "userA", 1.1f, 1.1f);

		verify(util, times(1)).addUser("userA", null, 1.1f, 1.1f);
		verify(util, times(1)).getSuitablePair(userA);
		verify(util, times(1)).pairUsers(userA, userB);
	}
	
	@Test
	public void testOnMessageForwarding() throws IOException
	{
		Session session = mock(Session.class);
		
		when(util.isSessionPaired(session)).thenReturn(true);
		doNothing().when(util).forwardDirectMessage(session, "Hello!");
		
		serv.onMessage(session, "Hello!");
		
		verify(util, times(1)).isSessionPaired(session);
		verify(util, times(1)).forwardDirectMessage(session, "Hello!");
	}
	
}
