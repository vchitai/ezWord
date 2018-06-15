package com.ezword.ezword;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

/**
 * Created by chita on 15/06/2018.
 */

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "Alarm worked.", Toast.LENGTH_LONG).show();
        Intent service = new Intent(context, NotificationService.class);
        service.setData((Uri.parse("custom://"+System.currentTimeMillis())));
        context.startService(service);

    }
}
