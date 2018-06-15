package com.ezword.ezword;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.ezword.ezword.activities.SingleWordActivity;
import com.ezword.ezword.dictionary.Dictionary;
import com.ezword.ezword.dictionary.Word;

/**
 * Created by chita on 15/06/2018.
 */

public class NotificationService extends IntentService {
    public static final String TAG = NotificationService.class.getSimpleName();
    private static int NOTIFICATION_ID = 1;
    private static String MyChannelID = "ezWord";
    Notification notification;

    public NotificationService() {
        super(MyChannelID);
    }

    public NotificationService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Context context = this.getApplicationContext();
        Word randomWord = Dictionary.getInstance().getRandomWord(context);
        Intent nIntent = new Intent(this, SingleWordActivity.class);
        nIntent.putExtra(SingleWordActivity.SEARCH_PHRASE,randomWord.getData(Word.WORD_ENGLISH));
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        Resources res = this.getResources();
        Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        notification = new NotificationCompat.Builder(this, MyChannelID)
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setLargeIcon(BitmapFactory.decodeResource(res, R.drawable.ic_launcher_foreground))
                .setTicker("ticker value")
                .setAutoCancel(true)
                .setPriority(8)
                .setSound(soundUri)
                .setContentTitle(randomWord.getData(Word.WORD_ENGLISH))
                .setContentText(randomWord.getData(Word.WORD_DEFINITION))
                .build();
        notification.flags |= Notification.FLAG_AUTO_CANCEL | Notification.FLAG_SHOW_LIGHTS;
        notification.defaults |= Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE;
        notification.ledARGB = 0xFFFFA500;
        notification.ledOnMS = 800;
        notification.ledOffMS = 1000;
        NotificationManager notificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        if (notificationManager != null) {
            notificationManager.notify(NOTIFICATION_ID, notification);
        }
        Log.i(TAG,getString(R.string.notification_sent));

    }

}
