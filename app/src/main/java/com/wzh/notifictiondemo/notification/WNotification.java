package com.wzh.notifictiondemo.notification;

import android.app.AppOpsManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * 开发人员: Wzh.
 * 开发日期: 2018/4/25.
 * 开发描述: 通知栏实现
 */

public class WNotification implements IWNotification {

    /**
     * 通知栏管理
     */
    private NotificationManager mNotificationManager;

    private Context mContext;

    public WNotification(Context context) {
        mContext = context;
        mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
    }

    @Override
    public NotificationChannel createNotificationChannel(String channelId, String channelName, int importance) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(channelId, channelName, importance);
            notificationChannel.setShowBadge(false);
            if (importance == IMPORTANCE_HIGH || importance == IMPORTANCE_MAX) {
                notificationChannel.setLightColor(Color.RED);
                notificationChannel.enableLights(true);
            }
            return notificationChannel;
        }
        return null;
    }

    @Override
    public void addNotificationChannel(NotificationChannel notificationChannel) {
        if (null == notificationChannel) return;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            mNotificationManager.createNotificationChannel(notificationChannel);
    }

    @Override
    public void showNotification(int notificationId, NotificationCompat.Builder builder, String channelId) {
        notificationDenied(channelId);
        android.app.Notification notification = builder.build();
        mNotificationManager.notify(notificationId, notification);
    }

    @Override
    public void cancelNotification(int notificationId) {
        mNotificationManager.cancel(notificationId);
    }

    /**
     * 通知栏被关闭
     *
     * @param channelId 渠道id
     */
    private void notificationDenied(String channelId) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (TextUtils.isEmpty(channelId)) return;
            NotificationChannel channel = mNotificationManager.getNotificationChannel(channelId);
            if (null == channel) return;
            if (channel.getImportance() == IMPORTANCE_NONE) {//小米8.0，这里有问题(关闭通知，不会改变这个值)
                Intent intent = new Intent(Settings.ACTION_CHANNEL_NOTIFICATION_SETTINGS);
                intent.putExtra(Settings.EXTRA_APP_PACKAGE, mContext.getPackageName());
                intent.putExtra(Settings.EXTRA_CHANNEL_ID, channelId);
                mContext.startActivity(intent);
            }
        } else {
            if (!isNotificationEnabled(mContext)) {
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                intent.setData(Uri.fromParts("package", mContext.getPackageName(), null));
                mContext.startActivity(intent);
            }
        }
    }

    /**
     * Android 19以上，8.0以下的通知栏是否显示
     */
    private boolean isNotificationEnabled(Context context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) return true;
        String CHECK_OP_NO_THROW = "checkOpNoThrow";
        String OP_POST_NOTIFICATION = "OP_POST_NOTIFICATION";

        AppOpsManager mAppOps = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
        ApplicationInfo appInfo = context.getApplicationInfo();
        String pkg = context.getApplicationContext().getPackageName();
        int uid = appInfo.uid;

        Class appOpsClass = null;
         /* Context.APP_OPS_MANAGER */
        try {
            appOpsClass = Class.forName(AppOpsManager.class.getName());
            Method checkOpNoThrowMethod = appOpsClass.getMethod(CHECK_OP_NO_THROW, Integer.TYPE, Integer.TYPE,
                    String.class);
            Field opPostNotificationValue = appOpsClass.getDeclaredField(OP_POST_NOTIFICATION);
            int value = (Integer) opPostNotificationValue.get(Integer.class);
            return ((Integer) checkOpNoThrowMethod.invoke(mAppOps, value, uid, pkg) == AppOpsManager.MODE_ALLOWED);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
