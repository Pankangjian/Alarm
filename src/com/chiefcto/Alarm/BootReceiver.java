package com.chiefcto.Alarm;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.os.SystemProperties;
import android.os.Handler;
import android.app.PendingIntent;
import android.app.AlarmManager;
import android.os.PowerManager.WakeLock;
import java.text.Format;
import java.util.Calendar;


public class BootReceiver extends BroadcastReceiver {  
	private static final String TAG = "BootReceiver";
	private static int hour = 0;
	private static int minute = 0;
	private static String timealarm = "";
    @Override  
    public void onReceive(Context context, Intent intent) { 
	Log.d("Bootclock", " ===========alarm clock=========== "); 
        if(intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {     // boot  
        	Log.d(TAG,"BootReceiver complete");

			 long delay = 2000;
			new Handler().postDelayed(new Runnable(){  
			     public void run() {  
            			Log.d("Alarm service","property   ////" + System.getProperty("persist.sys.alarm"));
			     //execute the task  
			     }  
			  }, delay ); 
            Log.d("Alarm service","property   +++++" + SystemProperties.get("persist.sys.timezone", ""));
            Log.d("Alarm service","property   +++++" + SystemProperties.get("persist.sys.alarm", ""));
	    final WakeLock wl = AlarmAlertWakeLock.createPartialWakeLock(context);        
	   wl.acquire();
	    timealarm = SystemProperties.get("persist.sys.alarm", "");
	    hour = Integer.parseInt(timealarm.substring(0, 2));
	    minute = Integer.parseInt(timealarm.substring(2, 4));
/*
            Intent intent2 = new Intent(context, MainActivity.class);  
//          intent2.setAction("android.intent.action.MAIN");  
//          intent2.addCategory("android.intent.category.LAUNCHER");  
            intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);  
            context.startActivity(intent2);  */

 	    Intent intent1 = new Intent(context, AlermReceiver.class);
            intent1.putExtra("music", true);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent1, 0);
	    AlarmManager am = (AlarmManager) context
                .getSystemService(Context.ALARM_SERVICE);
            //
      	    Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, hour);
            calendar.set(Calendar.MINUTE, minute);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);
            Log.d("Alarm service", hour + " " + minute + "  " + calendar.getTimeInMillis());
            am.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent)
;
        }  
    }
}  
