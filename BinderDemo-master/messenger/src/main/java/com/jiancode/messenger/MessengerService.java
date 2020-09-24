package com.jiancode.messenger;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;

/**
 * 跨进程服务端，用来接收客户端的消息
 */
public class MessengerService extends Service {

    private static final String TAG = MessengerService.class.getSimpleName();

    /**
     * 进程间通信载体
     */
    private Messenger messenger;

    @Override
    public void onCreate() {
        super.onCreate();
        //通过handler对象创建messenger
        messenger = new Messenger(new MessageHandler());
    }

    @Override
    public IBinder onBind(Intent intent) {
        return messenger.getBinder();
    }


    private static class MessageHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case Common.MSG_CLIENT_MSG:
                    String message = msg.getData().getString("msg");
                    Log.e(TAG, "receive client msg:" + message);
                    sendMsgToClient(msg);
                    break;
            }
        }

        /**
         * 发送消息给客户端
         * @param msg
         */
        private void sendMsgToClient(Message msg) {
            Messenger client = msg.replyTo;
            Message message = Message.obtain(null,Common.MSG_SERVER_MSG);
            Bundle bundle = new Bundle();
            bundle.putString("replay","hello ,i received your msg");
            message.setData(bundle);
            try {
                client.send(message);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }
}
