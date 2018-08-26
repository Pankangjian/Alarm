package com.chiefcto.Alarm;
import java.io.IOException;
import java.util.Calendar;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.os.PowerManager.WakeLock;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
public class AlermReceiver extends BroadcastReceiver {
  private MediaPlayer mMediaPlayer;
  Context context;
  @Override
  public void onReceive(Context context, Intent intent) {
    // TODO Auto-generated method stub
    Calendar calendar = Calendar.getInstance();
    calendar.setTimeInMillis(System.currentTimeMillis());
    int minute = calendar.get(Calendar.MINUTE);
    CharSequence text = String.valueOf(minute);
    Toast.makeText(context, text, Toast.LENGTH_LONG).show();

   final WakeLock wl = AlarmAlertWakeLock.createPartialWakeLock(context);       
    wl.acquire();
    this.context = context;
    Bundle bundle = intent.getExtras();
    Intent serviceIntent = new Intent("chief_musicService");
    serviceIntent.putExtras(bundle);
    if(bundle != null) {
      Log.i("CTO", String.valueOf(bundle.getBoolean("music")));
      if(bundle.getBoolean("music")) {
        context.startService(serviceIntent);
      }
      else if (bundle.getBoolean("disable"))
        context.startService(serviceIntent);
      else
    	  context.stopService(serviceIntent);
    }

  }

  private void StopAlarmRing() {
    mMediaPlayer.stop();
  }
}
