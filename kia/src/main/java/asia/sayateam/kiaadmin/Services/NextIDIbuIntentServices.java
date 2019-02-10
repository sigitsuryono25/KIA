package asia.sayateam.kiaadmin.Services;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import java.util.Calendar;

/**
 * Created by Sigit Suryono on 002, 02-Sep-17.
 */

public class NextIDIbuIntentServices extends Service{
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Calendar c = Calendar.getInstance();
        Intent i = new Intent(getBaseContext(), IDBroadcast.class);
        PendingIntent p = PendingIntent.getBroadcast(this, 0, i, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        manager.setRepeating(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), 5000, p);
        return START_STICKY;
    }
}
