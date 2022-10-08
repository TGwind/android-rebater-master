package com.rebater.cash.well.fun.activity;

import androidx.annotation.NonNull;

import com.rebater.cash.well.fun.bean.BusSendEvent;
import com.rebater.cash.well.fun.util.LogInfo;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.greenrobot.eventbus.EventBus;

public class PushService extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(@NonNull RemoteMessage message) {
        super.onMessageReceived(message);
        try {
            if (message.getNotification() != null) {
                String info = message.getNotification().getBody();
                String text = info + message.getNotification().getChannelId() + "--" + message.getNotification().getIcon() + "--" + message.getNotification().getTitle() + "--" + message.getNotification().getColor() + "--" + message.getNotification().getClickAction() + "--" + message.getNotification().getBodyLocalizationKey() + "--" + message.getNotification().getIcon() + "--" + message.getNotification().getSound() + "--" + message.getNotification().getImageUrl();
                LogInfo.e("body: " + text);
                sendNotification(info);
                BusSendEvent appEvent = new BusSendEvent(0x16, message.getNotification());
                EventBus.getDefault().postSticky(appEvent);
//                Toast.makeText(this,"--"+info,Toast.LENGTH_SHORT).show();
            }


        }catch (Exception e){
            LogInfo.e(e.toString());
        }
    }
    private void sendNotification(String messageBody) {

//        Intent intent = new Intent(this, GotoActivity.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
//                PendingIntent.FLAG_ONE_SHOT);
//
//        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
//                .setSmallIcon(R.mipmap.ic_stat_ic_notification)
//                .setContentTitle("FCM Message")
//                .setContentText(messageBody)
//                .setAutoCancel(true)
//                .setSound(defaultSoundUri)
//                .setContentIntent(pendingIntent);
//
//        NotificationManager notificationManager =
//                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//
//        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }

    @Override
    public void onNewToken(@NonNull String token) {
        super.onNewToken(token);
//        LogInfo.e(token);
    }
}