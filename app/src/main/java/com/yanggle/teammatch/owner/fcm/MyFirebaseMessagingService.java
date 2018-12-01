package com.yanggle.teammatch.owner.fcm;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.yanggle.teammatch.owner.ApplicationTM;
import com.yanggle.teammatch.owner.MatchProcActivity;
import com.yanggle.teammatch.owner.R;

import java.util.Map;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = MyFirebaseMessagingService.class.getSimpleName();

    ApplicationTM mApplicationTM;

    // 메시지 수신
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.i(TAG, "onMessageReceived");

        Map<String, String> data = remoteMessage.getData();

        Log.e(TAG, data + "");

        if(openPushAlert(data)) {
            sendNotification(remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody(), data);
        }
    }

    private void sendNotification(String title, String message, Map<String, String> data) {
        Intent intent = new Intent(this, MatchProcActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_CANCEL_CURRENT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), android.R.drawable.ic_dialog_info))
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0 , notificationBuilder.build());
    }

    private boolean openPushAlert(Map<String, String> data) {
        boolean bNotification = false;

        try {
            mApplicationTM = (ApplicationTM) getApplication();

//            String team_id = mApplicationTM.getTeamId();

            String match_alert_type = data.get("match_alert_type");

            if("7".equals(match_alert_type)) {
//                String match_id = data.get("match_id");
//                String host_team_id = data.get("host_team_id");
//                String host_team_name = data.get("host_team_name");
//                String host_team_lvl = data.get("host_team_lvl");
//                String host_team_point = data.get("host_team_point");
//                String host_team_user_name = data.get("host_team_user_name");
//                String host_team_user_tel = data.get("host_team_user_tel");
//                String guest_team_id = data.get("guest_team_id");
//                String guest_team_name = data.get("guest_team_name");
//                String guest_team_lvl = data.get("guest_team_lvl");
//                String guest_team_point = data.get("guest_team_point");
//                String guest_team_user_name = data.get("guest_team_user_name");
//                String guest_team_user_tel = data.get("guest_team_user_tel");
//                String hope_match_time = data.get("hope_match_time");
//                String hope_match_ground = data.get("hope_match_ground");
//                String hope_match_ground_tel = data.get("hope_match_ground_tel");
//                String hope_match_ground_cost = data.get("hope_match_ground_cost");

//                Intent mIntent = new Intent(getApplicationContext(), DialogMatchSuccessActivity.class);
//                mIntent.putExtra("SUB_TITLE", "해당 시간으로 구장이용 신청을 하셨습니다.\n구장이용을 승인하시겠습니까?");
//                mIntent.putExtra("MATCH_ID", match_id);
//                mIntent.putExtra("HOST_TEAM_NAME", host_team_name);
//                mIntent.putExtra("HOST_TEAM_LVL", host_team_lvl);
//                mIntent.putExtra("HOST_TEAM_POINT", host_team_point);
//                mIntent.putExtra("HOST_TEAM_USER_NAME", host_team_user_name);
//                mIntent.putExtra("HOST_TEAM_USER_TEL", host_team_user_tel);
//                mIntent.putExtra("GUEST_TEAM_NAME", guest_team_name);
//                mIntent.putExtra("GUEST_TEAM_LVL", guest_team_lvl);
//                mIntent.putExtra("GUEST_TEAM_POINT", guest_team_point);
//                mIntent.putExtra("GUEST_TEAM_USER_NAME", guest_team_user_name);
//                mIntent.putExtra("GUEST_TEAM_USER_TEL", guest_team_user_tel);
//                mIntent.putExtra("HOPE_MATCH_GROUND", hope_match_ground);
//                mIntent.putExtra("MATCH_TIME", hope_match_time);
//                mIntent.putExtra("GROUND_COST", hope_match_ground_cost);
//                getApplicationContext().startActivity(mIntent);
            }
        }catch(Exception e) {
            bNotification = false;
            Log.e(TAG, "openPushAlert - " + e);
        }

        return bNotification;
    }
}
