package com.wzh.notifictiondemo.notification;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

/**
 * 开发人员: Wzh.
 * 开发日期: 2018/4/25.
 * 开发描述: 通知栏接口设计
 */

public interface IWNotification {
    int NO_IMPORTANCE = -1;

    /**
     * 通知被关闭
     */
    int IMPORTANCE_NONE = Build.VERSION.SDK_INT >= Build.VERSION_CODES.O
            ? NotificationManager.IMPORTANCE_NONE : NO_IMPORTANCE;

    /**
     * 通知级别低
     */
    int IMPORTANCE_MIN = Build.VERSION.SDK_INT >= Build.VERSION_CODES.O
            ? NotificationManager.IMPORTANCE_MIN : NO_IMPORTANCE;
    int IMPORTANCE_LOW = Build.VERSION.SDK_INT >= Build.VERSION_CODES.O
            ? NotificationManager.IMPORTANCE_LOW : NO_IMPORTANCE;

    /**
     * 默认通知级别
     */
    int IMPORTANCE_DEFAULT = Build.VERSION.SDK_INT >= Build.VERSION_CODES.O
            ? NotificationManager.IMPORTANCE_DEFAULT : NO_IMPORTANCE;

    /**
     * 通知级别最高
     */
    int IMPORTANCE_HIGH = Build.VERSION.SDK_INT >= Build.VERSION_CODES.O
            ? NotificationManager.IMPORTANCE_HIGH : NO_IMPORTANCE;
    int IMPORTANCE_MAX = Build.VERSION.SDK_INT >= Build.VERSION_CODES.O
            ? NotificationManager.IMPORTANCE_MAX : NO_IMPORTANCE;

    /**
     * 创建通知渠道
     *
     * @param channelId   渠道id
     * @param channelName 渠道名称
     * @param importance  通知的重要程度
     * @return 通知渠道
     */
    NotificationChannel createNotificationChannel(String channelId, String channelName, int importance);

    /**
     * 加入通知渠道
     *
     * @param notificationChannel 通知渠道
     */
    void addNotificationChannel(NotificationChannel notificationChannel);

    /**
     * 显示通知
     *
     * @param notificationId 通知id
     * @param builder        通知栏的构建
     * @param channelId      渠道id
     */
    void showNotification(int notificationId, NotificationCompat.Builder builder, String channelId);

    /**
     * 取消通知
     *
     * @param notificationId 通知id
     */
    void cancelNotification(int notificationId);
}
