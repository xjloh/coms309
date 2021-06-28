package com.coms309.backend.mapper;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

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

import com.coms309.backend.Data.MessagePoints;
import com.coms309.backend.Data.Threads;
import com.coms309.backend.Data.Titles;
import com.coms309.backend.Controllers.ThreadController;

public class TestThreadController {
	
	@InjectMocks
	@Autowired
	static ThreadController threadController;
	
	@Mock
	static ThreadMapper threadMapper;

    @BeforeAll
    public static void initMocks() {
    	threadMapper = Mockito.mock(ThreadMapper.class);
    	
    	threadController = new ThreadController();
    	threadController.setMapper(threadMapper);

    }
    @Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void testGetThreadById() {
	    	final Threads t1 = new Threads(1,"test","hello world",null,0.0,1.0,"bob") ;
			when(threadMapper.findById(1)).thenReturn(t1);
			
			Threads t = threadMapper.findById(1);
			
			assertEquals("test", t.getTitle());
			assertEquals("hello world",t.getContent());
	}
	
	@Test
	public void testGetAllThreads() {
		final Threads t1 = new Threads(1,"test1","hello word",null,0.0,0.0,"admin");
		final Threads t2 = new Threads(2,"test2","Goodbye",null,53,10.0,"jon");
		final Threads t3 = new Threads(3,"test3",":)",null,10,-10.0,"admin");
		final List<Threads> result = new ArrayList<>();
		result.add(t1);
		result.add(t2);
		result.add(t3);
		
		when(threadMapper.getAllThreads()).thenReturn(result);
		
		List<Threads> response = threadMapper.getAllThreads();
		
		assertEquals(result.size(),response.size());
	}
	
	@Test
	public void testGetTitles() {
		final Titles r1 = new Titles("1","Title1");
		final Titles r2 = new Titles("2","Title2");
		final Titles r3 = new Titles("3","Title3");
		
		final Threads t1 = new Threads(1,"Title1","hello word",null,0.0,0.0,"admin");
		final Threads t2 = new Threads(2,"Title2","Goodbye",null,53,10.0,"jon");
		final Threads t3 = new Threads(3,"Title3",":)",null,10,-10.0,"admin");
		final List<Threads> result = new ArrayList<>();
		result.add(t1);
		result.add(t2);
		result.add(t3);
		
		when(threadMapper.getAllThreads()).thenReturn(result);
		
		ResponseEntity<List<Titles>> response = threadController.recvTitlesRequest();
		assertEquals(response.getBody().get(0).getTitle(),r1.getTitle());
		assertEquals(response.getBody().get(0).getThreadId(),r1.getThreadId());
		assertEquals(response.getBody().get(1).getTitle(),r2.getTitle());
		assertEquals(response.getBody().get(1).getThreadId(),r2.getThreadId());
		assertEquals(response.getBody().get(2).getTitle(),r3.getTitle());
		assertEquals(response.getBody().get(2).getThreadId(),r3.getThreadId());
		assertEquals(response.getBody().get(0).getTitle(),r1.getTitle());
		assertEquals(response.getBody().size(),3);
		assertEquals(response.getStatusCode(),HttpStatus.OK);	
	}
	
	@Test
	public void testAddThread() {
		final Threads t1 = new Threads(1,"thread1","hello word",null,5.0,4.0,"admin");
		
		threadMapper.addThread(t1);
	
        verify(threadMapper, times(1)).addThread(t1);	
	}

}
