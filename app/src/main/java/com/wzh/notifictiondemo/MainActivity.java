package com.wzh.notifictiondemo;

import android.app.NotificationChannel;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.wzh.notifictiondemo.notification.IWNotification;
import com.wzh.notifictiondemo.notification.WNotification;

public class MainActivity extends AppCompatActivity {

    private IWNotification mIwNotification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mIwNotification = new WNotification(this);
    }

    public void notifiction(View view){
        NotificationChannel channel = mIwNotification.createNotificationChannel("test", "渠道", WNotification.IMPORTANCE_HIGH);
        mIwNotification.addNotificationChannel(channel);
        NotificationChannel channel2 = mIwNotification.createNotificationChannel("test2", "渠道2", WNotification.IMPORTANCE_HIGH);
        mIwNotification.addNotificationChannel(channel2);
        NotificationCompat.Builder builder=new NotificationCompat.Builder(this,"test");
        builder.setContentTitle("通知栏");
        builder.setContentText("WuMoneyException");
        builder.setWhen(System.currentTimeMillis());
        builder.setSmallIcon(R.mipmap.ic_launcher_round);
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher));
        builder.setStyle(new NotificationCompat.BigTextStyle());
        mIwNotification.showNotification(1,builder,"test");
    }
}
