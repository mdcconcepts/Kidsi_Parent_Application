package org.mdcconcepts.kidsi.chat;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;

import org.mdcconcepts.kidsi.R;


public class GCMNotificationIntentService extends IntentService {
    public static final int NOTIFICATION_ID = 1;
    private NotificationManager mNotificationManager;
    NotificationCompat.Builder builder;
    
    //SharedPreferences prefs =getSharedPreferences("MyData", Context.MODE_PRIVATE);
    public GCMNotificationIntentService() {
        super("GcmIntentService");
      
    }

    public static final String TAG = "GCMNotificationIntentService";

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d(TAG, "onHandleIntent "+intent.getDataString());
        Bundle extras = intent.getExtras();
        GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);

        String messageType = gcm.getMessageType(intent);

        if (extras != null) {
            if (!extras.isEmpty()) {
                if (GoogleCloudMessaging.MESSAGE_TYPE_SEND_ERROR
                        .equals(messageType)) {
                  //  sendNotification("Send error: " + extras.toString());
                } else if (GoogleCloudMessaging.MESSAGE_TYPE_DELETED
                        .equals(messageType)) {
                  //  sendNotification("Deleted messages on server: "
                  //          + extras.toString());
                } else if (GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE
                        .equals(messageType)) {
                	
                    if("USERLIST".equals(extras.get("SM"))){
                        Log.d(TAG, "onHandleIntent - USERLIST ");
                        //update the userlist view
                        Intent userListIntent = new Intent("org.mdcconcepts.kidsi.chat.UserListActivity");
                        String userList = extras.get("USERLIST").toString();
                        userListIntent.putExtra("USERLIST",userList);
                        sendBroadcast(userListIntent);
                    } else if("CHAT".equals(extras.get("SM"))){
                    	Log.d(TAG, "Preparing to send notification from user...: "+extras.get("FROMUSER").toString());
                    	Log.d(TAG, "Preparing to send notification touser...: "+extras.get("TOUSER").toString());
                    	Log.d(TAG, "Preparing to send notification message...: "+extras.get("CHATMESSAGE").toString());
                    	String fromuser=extras.get("FROMUSER").toString();
                    	UserData obj=new UserData(1,fromuser,extras.get("TOUSER").toString(),extras.get("CHATMESSAGE").toString(),extras.get("SM").toString());
                    	DBAdapter.init(GlobalContext.getContext());
                    	DBAdapter.addUserData(obj);
                   	
                    	 
                    	
                    	sendNotification(extras.get("CHATMESSAGE").toString(),fromuser);
                        Log.d(TAG, "onHandleIntent - CHAT ");
                        Intent chatIntent = new Intent("org.mdcconcepts.kidsi.chat.ChatMessage");
                        chatIntent.putExtra("CHATMESSAGE",extras.get("CHATMESSAGE").toString());
                        chatIntent.putExtra("TYPE",extras.get("SM").toString());
                        sendBroadcast(chatIntent);
                    }
                    else if("MEDIA".equals(extras.get("SM"))){
                    	Log.d(TAG, "Preparing to send notification from user...: "+extras.get("FROMUSER").toString());
                    	Log.d(TAG, "Preparing to send notification touser...: "+extras.get("TOUSER").toString());
                    	Log.d(TAG, "Preparing to send notification message...: "+extras.get("CHATMESSAGE").toString());
                    	String fromuser=extras.get("FROMUSER").toString();
                    	UserData obj=new UserData(1,fromuser,extras.get("TOUSER").toString(),extras.get("CHATMESSAGE").toString(),extras.get("SM").toString());
                    	DBAdapter.init(GlobalContext.getContext());
                    	DBAdapter.addUserData(obj);
                   	
                    	 
                    	
                    	sendNotification(extras.get("CHATMESSAGE").toString(),fromuser);
                        Log.d(TAG, "onHandleIntent - CHAT ");
                        Intent chatIntent = new Intent("org.mdcconcepts.kidsi.chat.ChatMessage");
                        chatIntent.putExtra("CHATMESSAGE",extras.get("CHATMESSAGE").toString());
                        chatIntent.putExtra("TYPE",extras.get("SM").toString());
                        sendBroadcast(chatIntent);
                    }
                    Log.i(TAG, "SERVER_MESSAGE: " + extras.toString());

                }
            }
        }
        GcmBroadcastReceiver.completeWakefulIntent(intent);
    }

    private void sendNotification(String msg,String touser) {
    	// Log.i(TAG, "SERVER_MESSAGE: " + extras.toString());
        Log.d(TAG, "Preparing to send notification...: " + msg);
        
       
        mNotificationManager = (NotificationManager) this
                .getSystemService(Context.NOTIFICATION_SERVICE);
        Intent newIntent=new Intent(this,UserListActivity.class);

        newIntent.putExtra("TOUSER", touser);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                newIntent, 0);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
                this).setSmallIcon(R.drawable.gcm_cloud)
                .setContentTitle("GCM XMPP Message")
                .setStyle(new NotificationCompat.BigTextStyle().bigText(msg))
                .setContentText(msg);

        mBuilder.setContentIntent(contentIntent);
        mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
        Log.d(TAG, "Notification sent successfully.");
    }
}
