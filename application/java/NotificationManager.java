package com.example.grad;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationChannelGroup;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;

import androidx.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class NotificationManager {

    //메인에서 돌아가는 thread 에 의해 알림이 필요한 경우에 실행
    //채널을 만들어서 알림을 준다. (버젼이 올라가면서 채널을 만들어야만 알림 줄 수 있도록 변경됨)

    private static final String GROUP_SY_KIM ="syKim";

    public static void createChannel(Context context) {

        NotificationChannelGroup group1 = new NotificationChannelGroup(GROUP_SY_KIM, GROUP_SY_KIM);
        getManager(context).createNotificationChannelGroup(group1);

        NotificationChannel channelMessage = new NotificationChannel(Channel.MESSAGE,
                context.getString(R.string.notification_channel_message_title), android.app.NotificationManager.IMPORTANCE_DEFAULT);
        channelMessage.setDescription(context.getString(R.string.notification_channel_message_description));
        channelMessage.setGroup(GROUP_SY_KIM);
        channelMessage.setLightColor(Color.GREEN);
        channelMessage.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
        getManager(context).createNotificationChannel(channelMessage);
    }

    private static android.app.NotificationManager getManager(Context context) {
        return (android.app.NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
    }

    public static void sendNotification(Context context, int id, @Channel String channel, String title, String body) {
        //클릭시 pushActivity를 열어주기 위해 intent 생성
        Intent notificationIntent = new Intent(context,pushActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        if(id==2) {     //화재발생에 대한 알림
            Bitmap bitmap= BitmapFactory.decodeResource(context.getResources(),R.drawable.firestate);
            @SuppressLint("ResourceAsColor") Notification.Builder builder = new Notification.Builder(context, channel)
                    .setContentTitle(title)
                    .setContentText(body)
                    .setSmallIcon(R.drawable.firestate)
                    .setLargeIcon(bitmap)
                    .setAutoCancel(true)
                    .setContentIntent(contentIntent);
            getManager(context).notify(id, builder.build());
        }else {     //움직임 감지에 대한 알림 -> 여기서는 어떤 알림인지와는 상관없이 넘어온 값으로만 처리한다.(모드에 대한 처리는 main에서 해준다)
            Bitmap bitmap= BitmapFactory.decodeResource(context.getResources(),R.drawable.movement);
            @SuppressLint("ResourceAsColor") Notification.Builder builder = new Notification.Builder(context, channel)
                    .setContentTitle(title)
                    .setContentText(body)
                    .setSmallIcon(R.drawable.movement)
                    .setLargeIcon(bitmap)
                    .setAutoCancel(true)
                    .setContentIntent(contentIntent);
            getManager(context).notify(id, builder.build());
        }
    }

    @Retention(RetentionPolicy.SOURCE)
    @StringDef({
            Channel.MESSAGE
    })
    public @interface Channel {
        String MESSAGE = "message";
    }
}
