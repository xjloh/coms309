package com.yt3.intouchapp;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.Matchers.isNotNull;
import static org.mockito.Mockito.when;

import android.content.Context;

import com.yt3.intouchapp.Adapters.Chat;
import com.yt3.intouchapp.Adapters.Messages;
import com.yt3.intouchapp.Adapters.Users;
import com.yt3.intouchapp.dialogs.EditProfilePageDialog;

import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(MockitoJUnitRunner.class)
public class MoreMokitoTesting {

    private static final Chat testChat=new Chat("jon", "hello");
    private static final Messages message=new Messages("admin", "yeet", null);

    @Mock
    Context mockContext;

    @Mock
    EditProfilePageDialog dialog;

    @Test
    public void readChatFromContext_LocalizedString() {
        // Given a mocked Context injected into the object under test...
        when(mockContext.getString(R.id.chat_list))
                .thenReturn(testChat.getName());
        ClassUnderTest myObjectUnderTest = new ClassUnderTest(mockContext);

        // ...when the string is returned from the object under test...
        String result = myObjectUnderTest.getChatString();

        // ...then the result should be the expected one.
        assertThat(result, is(testChat.getName()));
    }

    @Test
    public void readChatLatestMsg_LocalizedString() {
        // Given a mocked Context injected into the object under test...
        when(mockContext.getString(R.id.chat_list))
                .thenReturn(testChat.getLatestMsg());
        ClassUnderTest myObjectUnderTest = new ClassUnderTest(mockContext);

        // ...when the string is returned from the object under test...
        String result = myObjectUnderTest.getChatMsg();

        // ...then the result should be the expected one.
        assertThat(result, is(testChat.getLatestMsg()));
    }

    @Test
    public void readMessage(){
        when(mockContext.getString(R.id.message_list)).thenReturn(message.getMessages());
        ClassUnderTest tester=new ClassUnderTest(mockContext);

        String answer=tester.getMsgmsg();
        assertThat(answer, is(message.getMessages()));
    }

    @Test
    public void readMessageSenderName(){
        when(mockContext.getString(R.id.message_list)).thenReturn(message.getName());
        ClassUnderTest tester=new ClassUnderTest(mockContext);

        String answer=tester.getMsgmsg();
        assertThat(answer, is(message.getName()));
    }


    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    public class ClassUnderTest {

        Context mContext;

        public ClassUnderTest(Context context) {
            mContext = context;
        }

        public String getChatString() {
            return mContext.getString(R.id.chat_list);
        }

        public String getChatMsg() {
            return mContext.getString(R.id.chat_list);
        }

        public String getMsgmsg(){
            return mContext.getString(R.id.message_list);
        }
    }
}