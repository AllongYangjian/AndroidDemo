package com.yj.bubble;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Person;
import android.content.Intent;
import android.graphics.drawable.Icon;
import android.os.Bundle;

import com.yj.bubble.common.Const;
import com.yj.bubble.databinding.ActivityBubbleBinding;
import com.yj.bubble.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main);

        binding.button.setOnClickListener(view->startBubbleActivity());
    }

    private void startBubbleActivity(){
        Intent intent = new Intent(this,BubbleActivity.class);
        PendingIntent pi = PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            Notification.BubbleMetadata bubbleMetadata = new Notification.BubbleMetadata.Builder()
                    .setDesiredHeight(600)
                    .setIcon(Icon.createWithResource(this,R.drawable.ic_launcher_background))
                    .setIntent(pi)
                    .build();
            Person person = new Person.Builder()
                    .setBot(true)
                    .setName("BubbleBot")
                    .setImportant(true)
                    .build();

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                Notification.Builder builder = new Notification.Builder(this, Const.CHANNEL_ID[1])
                        .setContentIntent(pi)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setBubbleMetadata(bubbleMetadata)
                        .addPerson(person);
                Notification notification = builder.build();

                NotificationManager manager = getSystemService(NotificationManager.class);
                manager.notify(1000,notification);
            }
        }
    }
}
