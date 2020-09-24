package com.yj.layout.view;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.RemoteViews;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.app.RemoteInput;
import androidx.databinding.DataBindingUtil;

import com.yj.layout.MyBroadcastReceiver;
import com.yj.layout.R;
import com.yj.layout.common.Constant;
import com.yj.layout.databinding.ActivityNotificationBinding;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 通知界面
 */
public class NotificationActivity extends AppCompatActivity {

    private final String TAG = "NotificationActivity";

    private ActivityNotificationBinding binding;

    private NotificationCompat.Builder builder;
    private NotificationManager manager;

    private String[] names;
    private int notificationId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_notification);
        names = getResources().getStringArray(R.array.channel_name);
        createNotificationChannel();
        initData();

        binding.btnN1.setOnClickListener(onClickListener);
        binding.btnN2.setOnClickListener(onClickListener);
        binding.btnN3.setOnClickListener(onClickListener);
        binding.btnN4.setOnClickListener(onClickListener);
        binding.btnN5.setOnClickListener(onClickListener);
        binding.btnN6.setOnClickListener(onClickListener);
        binding.btnN7.setOnClickListener(onClickListener);
        binding.btnN8.setOnClickListener(onClickListener);
        binding.btnN9.setOnClickListener(onClickListener);
        binding.btnN10.setOnClickListener(onClickListener);
        binding.btnN11.setOnClickListener(onClickListener);
    }

    public int getNotificationId() {
        return new Random().nextInt(1000) + 1;
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O) {
            NotificationManager manager = getSystemService(NotificationManager.class);
            assert manager != null;

            String[] desc = getResources().getStringArray(R.array.channel_desc);
            int importance = NotificationManager.IMPORTANCE_LOW;
            for (int x = 0; x < names.length; x++) {
                NotificationChannel channel = new NotificationChannel(names[x], names[x], importance + x);
                channel.setDescription(desc[x]);
                manager.createNotificationChannel(channel);
            }
        }
    }

    private void initData() {

    }

    private View.OnClickListener onClickListener = view -> {
        switch (view.getId()) {
            case R.id.btn_n1:
                showOrdinaryNotification(names[0]);
                break;
            case R.id.btn_n2:
                showOrdinaryNotification(names[1]);
                break;
            case R.id.btn_n3:
                showOrdinaryNotification(names[2]);
                break;
            case R.id.btn_n4:
                addAction();
                break;
            case R.id.btn_n5:
                addReplyNotification();
                break;
            case R.id.btn_n6:
                progressNotification();
                break;
            case R.id.btn_n7:
                createExpandNotification();
                break;
            case R.id.btn_n8:
                createBigTextNotification();
                break;
            case R.id.btn_n9:
                createEmailStyleNotification();
                break;
            case R.id.btn_n10:
                dialogStyleNotification();
                break;
            case R.id.btn_n11:
                createCustomNotification();
                break;
        }
    };

    /**
     * 创建自定义通知
     */
    private void createCustomNotification(){
        RemoteViews smallLayout = new RemoteViews(getPackageName(),R.layout.layout_small_notification);
        RemoteViews bigLayout = new RemoteViews(getPackageName(),R.layout.layout_big_notification);

        builder = new NotificationCompat.Builder(this,names[2]);

        Notification notification = builder.setSmallIcon(R.mipmap.ic_launcher_round)
                .setStyle(new NotificationCompat.DecoratedCustomViewStyle())
                .setCustomContentView(smallLayout)
                .setCustomBigContentView(bigLayout)
                .build();
        NotificationManagerCompat compat = NotificationManagerCompat.from(this);
        compat.notify(getNotificationId(),notification);
    }

    /**
     * 对话框样式通知
     */
    private void dialogStyleNotification() {
        int id = getNotificationId();
        NotificationManagerCompat compat = NotificationManagerCompat.from(this);

        NotificationCompat.MessagingStyle.Message message1 =
                new NotificationCompat.MessagingStyle.Message("Text1", System.currentTimeMillis(), "Sb");

        NotificationCompat.MessagingStyle.Message message2 =
                new NotificationCompat.MessagingStyle.Message("Text2", System.currentTimeMillis()+1000, "Sb2");

        builder =  new NotificationCompat.Builder(this,names[2]);

        Notification notification = builder.setContentTitle("对话框样式通知")
                .setContentText("这是一条对话框样式通知")
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setStyle(new NotificationCompat.MessagingStyle("点我")
                        .addMessage(message1)
                        .addMessage(message2))
                .build();

        compat.notify(id, notification);
    }

    /**
     * 收件箱样式通知
     */
    private void createEmailStyleNotification() {
        int id = getNotificationId();
        NotificationManagerCompat compat = NotificationManagerCompat.from(this);
        builder = new NotificationCompat.Builder(this, names[2]);

        Notification notification =
                builder.setContentTitle("收件箱样式通知")
                        .setContentText("这是一条收件箱样式的通知")
                        .setSmallIcon(R.mipmap.ic_launcher_round)
                        .setStyle(new NotificationCompat.InboxStyle()
                                .addLine("第四十六号主席令说")
                                .addLine("第四十七号主席令说"))
                        .build();

        compat.notify(id, notification);
    }

    /**
     * 创建大文本通知
     */
    private void createBigTextNotification() {
        int id = getNotificationId();
        NotificationManagerCompat compat = NotificationManagerCompat.from(this);
        builder = new NotificationCompat.Builder(this, names[2]);

        Notification notification = builder.setContentTitle("大文本通知")
                .setContentText("这是一段大文本")
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setStyle(new NotificationCompat.BigTextStyle().bigText("第四十六号主席令说，《中华人民共和国公职人员政务处分法》已由中华人民共和国第十三届全国人民代表大会常务委员会第十九次会议于2020年6月20日通过，现予公布，自2020年7月1日起施行。\\n\" +\n" +
                        "                        \"\\n\" +\n" +
                        "                        \"　　第四十七号主席令说，《中华人民共和国档案法》已由中华人民共和国第十三届全国人民代表大会常务委员会第十九次会议于2020年6月20日修订通过，现予公布，自2021年1月1日起施行。\\n\" +\n" +
                        "                        \"\\n\" +\n" +
                        "                        \"　　第四十八号主席令说，《中华人民共和国人民武装警察法》已由中华人民共和国第十三届全国人民代表大会常务委员会第十九次会议于2020年6月20日修订通过，现予公布，自2020年6月21日起施行。"))
                .build();
        compat.notify(id, notification);
    }

    /**
     * 展开式通知
     */
    private void createExpandNotification() {
        int id = getNotificationId();
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_test);
        //创建manager
        NotificationManagerCompat manager = NotificationManagerCompat.from(this);
        builder = new NotificationCompat.Builder(this, names[2]);

        //设置样式
        builder.setContentTitle("展开通知")
                .setContentText("这是一条展开通知...")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(bitmap)
                .setStyle(new NotificationCompat.BigPictureStyle().bigPicture(bitmap)
                        .bigLargeIcon(null))
                .build();
        manager.notify(id, builder.build());
    }

    /**
     * 进度通知
     */
    private void progressNotification() {
        int id = getNotificationId();
        //创建manager
        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(this);
        //创建通知
        builder = new NotificationCompat.Builder(this, names[0]);
        Notification notification = builder
                .setContentTitle("进度通知")
                .setContentText("当前进度")
                .setSmallIcon(R.mipmap.ic_launcher)
                .build();

        int progress = 100;
        AtomicInteger progress_current = new AtomicInteger();
        builder.setProgress(progress, progress_current.get(), true)
                .setOnlyAlertOnce(true);
        managerCompat.notify(id, notification);

        new Thread(() -> {
            while (progress_current.get() <= 100) {
                progress_current.addAndGet(10);
                SystemClock.sleep(500);
                builder.setContentText("当前进度:" + progress_current.get())
                        .setProgress(progress, progress_current.get(), true);
                managerCompat.notify(id, builder.build());
            }
            builder.setContentText("下载完成")
                    .setProgress(0, 0, true);
            managerCompat.notify(id, builder.build());
        }).start();
    }

    /**
     * 创建直接回复型通知
     */
    private void addReplyNotification() {
        int id = getNotificationId();
        String replayLabel = "回复";
        Intent intent = new Intent(this, MyBroadcastReceiver.class);
        intent.setAction(Constant.ACTION_2);
        intent.putExtra("id", id);
        Log.e(TAG, id + "");
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT_WATCH) {
            //创建remote input
            RemoteInput input = new RemoteInput.Builder(Constant.ACTION_REPLY_KEY)
                    .setLabel(replayLabel)
                    .build();

            PendingIntent pi = PendingIntent.getBroadcast(this, 0,
                    intent, PendingIntent.FLAG_UPDATE_CURRENT);

            builder = new NotificationCompat.Builder(this, names[0]);

            //创建action
            NotificationCompat.Action action = new NotificationCompat.Action.Builder(R.mipmap.ic_launcher,
                    "回复", pi)
                    .addRemoteInput(input)
                    .build();

            //创建notification
            Notification notification = builder.setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle("回复通知")
                    .setContentText("hello world")
                    .setAutoCancel(true)
                    .addAction(action)
                    .build();
            NotificationManagerCompat managerCompat = NotificationManagerCompat.from(this);
            managerCompat.notify(id, notification);

        }

    }

    private void showOrdinaryNotification(String channel) {
        builder = new NotificationCompat.Builder(this, channel);
        Notification notification = builder.setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle("Ordinary notification")
                .setContentText("This is a ordinary notification ...")
                .setStyle(new NotificationCompat.BigTextStyle().bigText("This is a ordinary notification ..."))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true)
                .build();
        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(this);
        managerCompat.notify(getNotificationId(), notification);
    }

    /**
     * 通知添加按钮操作
     */
    private void addAction() {

        int id = getNotificationId();

        Intent intent = new Intent(this, MyBroadcastReceiver.class);
        intent.setAction(Constant.ACTION_1);
        intent.putExtra("id", id);
        PendingIntent pi = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder = new NotificationCompat.Builder(this, names[0]);
        Notification notification = builder.setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle("按钮通知")
                .setContentText("这是一个带点击按钮的通知")
                .setStyle(new NotificationCompat.BigTextStyle().bigText("This is a ordinary notification ..."))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true)
                .setContentIntent(pi)
                .addAction(R.drawable.ic_launcher_background, "测试", pi)
                .build();
        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(this);
        managerCompat.notify(id, notification);
    }

    @Override
    public void finish() {
        super.finish();
//        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
    }
}
