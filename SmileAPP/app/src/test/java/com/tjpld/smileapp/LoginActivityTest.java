package com.tjpld.smileapp;

import com.tjpld.smileapp.login.view.LoginActivity;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by guo on 2016/3/21.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 19)
public class LoginActivityTest {
    //测试activity的创建
    @Test
    public void testLoginActivity() {
        LoginActivity loginActivity = Robolectric.setupActivity(LoginActivity.class);
        assertNotNull(loginActivity);
        assertEquals(loginActivity.getTitle(), "LoginActivity");
    }
}
