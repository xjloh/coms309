package com.yt3.intouchapp;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.Matchers.isNotNull;
import static org.mockito.Mockito.when;

import android.content.Context;

import com.yt3.intouchapp.Adapters.Users;

import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(MockitoJUnitRunner.class)
public class ExampleUnitTest {

    private static final String FAKE_ID = "BLAH99";
    private static final String FAKE_EMAIL="default@gmail.com";
    private static final Users testUser=new Users("Xin Jun", "hihi");

    @Mock
    Context mockContext;

    @Test
    public void readUSERIDFromContext_LocalizedString() {
        // Given a mocked Context injected into the object under test...
        when(mockContext.getString(R.id.loginpage_username))
                .thenReturn(FAKE_ID);
        ClassUnderTest myObjectUnderTest = new ClassUnderTest(mockContext);

        // ...when the string is returned from the object under test...
        String result = myObjectUnderTest.getUSERIDString();

        // ...then the result should be the expected one.
        assertThat(result, is(FAKE_ID));
    }

    @Test
    public void readEMAILFromContext_LocalizedString() {
        // Given a mocked Context injected into the object under test...
        when(mockContext.getString(R.id.registerpage_email))
                .thenReturn(FAKE_EMAIL);
        ClassUnderTest myObjectUnderTest = new ClassUnderTest(mockContext);

        // ...when the string is returned from the object under test...
        String result = myObjectUnderTest.getEMAILString();

        // ...then the result should be the expected one.
        assertThat(result, is(FAKE_EMAIL));
    }

    @Test
    public void readFRIENDSFromContext_LocalizedString() {
        // Given a mocked Context injected into the object under test...
        when(mockContext.getString(R.id.users_list))
                .thenReturn(testUser.getName());
        ClassUnderTest myObjectUnderTest = new ClassUnderTest(mockContext);

        // ...when the string is returned from the object under test...
        String result = myObjectUnderTest.getFNAMEString();

        // ...then the result should be the expected one.
        assertThat(result, is(testUser.getName()));
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

        public String getUSERIDString() {
            return mContext.getString(R.id.loginpage_username);
        }

        public String getEMAILString() {
            return mContext.getString(R.id.registerpage_email);
        }

        public String getFNAMEString() {
            return mContext.getString(R.id.users_list);
        }


    }
}

