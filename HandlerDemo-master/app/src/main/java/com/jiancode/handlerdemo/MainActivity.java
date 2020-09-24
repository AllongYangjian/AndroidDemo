package com.jiancode.handlerdemo;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

/**
 * a demo about the used of handler
 */
public class MainActivity extends AppCompatActivity {

    private Handler handler;

    private static final int MSG_1 = 1;
    private static final int MSG_2 = 2;

    private Button btnSendMsg;
    private int index = 0;

    private Handler mHander;

    private HandlerThread handlerThread = new HandlerThread("HandlerThread#");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnSendMsg = findViewById(R.id.btn_send_msg);
        btnSendMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                index++;
                startTask2(index);
            }
        });

        Button btn2 = findViewById(R.id.btn_send_msg2);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMsg();
            }
        });
        startTask1();
        handlerThread.start();
        createHandler();
        Log.e("TAG,",Thread.currentThread().getName());
    }

    private void sendMsg() {
        Message message = new Message();
        message.what = MSG_1;
        message.arg1 = 1234;
        mHander.sendMessage(message);
    }


    private void createHandler() {
        mHander = new Handler(handlerThread.getLooper()) {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case MSG_1:
                        Log.e("TAG", Thread.currentThread().getName());
                        Log.e("TAG", msg.arg1 + "");
                        break;
                    case MSG_2:

                        break;
                }
            }
        };
    }


    public void startTask3() {

    }

    /**
     * 创建Handler对象
     */
    private void startTask1() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Looper.prepare();
                handler = new Handler(Looper.myLooper()) {
                    @Override
                    public void handleMessage(Message msg) {
                        super.handleMessage(msg);
                        switch (msg.what) {
                            case MSG_1:
                                Log.e(Thread.currentThread().getName(), "receive msg " + msg.arg1);
                                break;
                            case MSG_2:
                                Log.e(Thread.currentThread().getName(), "receive msg " + msg.arg2);
                                break;
                        }
                    }
                };
                Looper.loop();
            }
        }).start();
    }

    /**
     * 使用Handler
     */
    private void startTask2(int index) {
        final int type = index % 2;
        new Thread(new Runnable() {
            @Override
            public void run() {
                Message msg = new Message();
                if (type == 0) {
                    msg.what = MSG_1;
                    msg.arg1 = 123;
                } else {
                    msg.what = MSG_2;
                    msg.arg2 = 456;
                }
                handler.sendMessage(msg);
            }
        }).start();
    }

    @Override
    protected void onStop() {
        super.onStop();
        handlerThread.quitSafely();
    }
}
