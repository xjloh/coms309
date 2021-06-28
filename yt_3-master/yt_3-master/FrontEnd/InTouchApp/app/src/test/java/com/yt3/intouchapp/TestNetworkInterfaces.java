package com.yt3.intouchapp;

import android.content.Context;

import com.yt3.intouchapp.activities.MessageActivity;
import com.yt3.intouchapp.network_interface.MessageRequest;

import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class TestNetworkInterfaces
{
    @Mock
    Context contextMock;

    @Test
    public void testMakeUpdateChatRequestBody()
    {
        MessageRequest tMessageRequest = new MessageRequest(contextMock);
        String mid_sample = "1";
        String sender_sample = "sender";
        String receiver_sample = "receiver";

        JSONObject expectedResponse = new JSONObject();
        try
        {
            expectedResponse.put("mid", mid_sample);
            expectedResponse.put("sender", sender_sample);
            expectedResponse.put("receiver", receiver_sample);
        }
        catch (Exception e) {}

        JSONObject response = tMessageRequest.makeUpdateChatRequestBody(mid_sample, sender_sample, receiver_sample);
        Assert.assertThat(response.toString(), is(expectedResponse.toString()));
    }

    @Mock
    MessageActivity tMessageActivity;

    @Test
    public void testGetChat()
    {
        String sender = "sender";
        String receiver = "receiver";
        tMessageActivity.getChat(sender, receiver);
        verify(tMessageActivity, times(1)).getChat(eq(sender), eq(receiver));
    }

    @Test
    public void testUpdateChat()
    {
        int mid = 1;
        String sender = "sender";
        String receiver = "receiver";
        tMessageActivity.updateChat(mid, sender, receiver);
        verify(tMessageActivity, times(1)).updateChat(eq(mid), eq(sender), eq(receiver));
    }

    @Test
    public void testSendMessage()
    {
        String message = "message";
        String friendId = "friendId";
        tMessageActivity.sendMessageToServer(message);
        verify(tMessageActivity, times(1)).sendMessageToServer(message);
    }
}
