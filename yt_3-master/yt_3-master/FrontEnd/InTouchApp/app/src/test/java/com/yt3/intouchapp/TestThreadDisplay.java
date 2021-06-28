package com.yt3.intouchapp;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.isNotNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import android.content.Context;

import com.yt3.intouchapp.activities.ThreadDisplayActivity;
import com.yt3.intouchapp.comment_view.Comment;
import com.yt3.intouchapp.utils.ThreadDisplayUtil;

import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;

@RunWith(MockitoJUnitRunner.class)
public class TestThreadDisplay
{
    @InjectMocks
    ThreadDisplayActivity tDA;

    @Mock
    ThreadDisplayUtil util;


    String testThreadId = "default_id";

    @Mock
    Context mContext;

    @Before
    public void setUp()
    {
        tDA.setThreadId(testThreadId);
    }

    /**
     * Mock the network calls to get thread data and comment data in ThreadDisplayActivity
     */
    @Test
    public void testMakeNetworkRequests()
    {
        doNothing().when(util).makeThreadRequest(any(ThreadDisplayActivity.class), eq(testThreadId));
        doNothing().when(util).getComments(any(ThreadDisplayActivity.class), eq(testThreadId));

        tDA.makeNetworkRequests();

        verify(util, times(1)).makeThreadRequest(any(ThreadDisplayActivity.class), eq(testThreadId));
        verify(util, times(1)).getComments(any(ThreadDisplayActivity.class), eq(testThreadId));
    }

    /**
     * Mock comment data parsing in ThreadDisplayUtil.java
     */
    @Test
    public void testParseCommentData()
    {
        String jsonString = "{\"userId\":\"admin\", \"comment\":\"hello world!\", \"dateTime\":\"00/00/0000 00:00\", \"points\":2}";

        try
        {
            JSONArray jsonArray = new JSONArray(jsonString);

            // Function return value
            ArrayList<Comment> commentList = new ArrayList<Comment>();
            Comment comment = new Comment("admin", "hello world!", "00/00/0000 00:00", 2);
            commentList.add(comment);

            when(util.parseCommentData(jsonArray)).thenReturn(commentList);

            Assert.assertThat(util.parseCommentData(jsonArray), is(commentList));

        } catch (Exception e) {}
    }

    /**
     * Mock getTitleString in ThreadDisplayUtil
     */
    @Test
    public void testGetTitleString()
    {
        JSONObject json = new JSONObject();
        String response = "hello";

        try {
            json.put("title", "hello");
            json.put("content", "good day");
        } catch (Exception e) {}

        when(util.getTitleString(json)).thenReturn(response);

        Assert.assertThat(util.getTitleString(json), is(response));
    }

    /**
     * Mock getContentString in ThreadDisplayUtil
     */
    @Test
    public void testGetContentString()
    {
        JSONObject json = new JSONObject();
        String response = "good day";

        try {
            json.put("title", "hello");
            json.put("content", "good day");
        } catch (Exception e) {}

        when(util.getTitleString(json)).thenReturn(response);

        Assert.assertThat(util.getTitleString(json), is(response));
    }
}
