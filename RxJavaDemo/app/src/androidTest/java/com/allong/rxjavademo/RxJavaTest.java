package com.allong.rxjavademo;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class RxJavaTest {

    @Test
    public void testSubscribe(){

        RxJavaUtils rxJavaUtils = new RxJavaUtils();
        rxJavaUtils.subscribe();
    }

    @Test
    public void testCustomSubscribe(){
        CustomSubscribeMode subscribeMode = new CustomSubscribeMode();
        subscribeMode.test();
    }
}
