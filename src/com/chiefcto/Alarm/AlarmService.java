package com.chiefcto.Alarm;
import java.io.IOException;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
//import com.chiefcto.Alarm.AlarmAlertWakeLock;
public class AlarmService extends Service {
  private MediaPlayer player;
  private String TAG = "AlarmService";
  
  @Override
  public IBinder onBind(Intent intent) {
    return null;
  }
  @Override
  public void onCreate() {
    // TODO Auto-generated method stub
    super.onCreate();
  }
  @Override
  public void onDestroy() {
    // TODO Auto-generated method stub
    super.onDestroy();
    if (player != null) {
      player.stop();
      player.release();
    }
  }
  @Override
  public void onStart(Intent intent, int startId) {
    // TODO Auto-generated method stub
    super.onStart(intent, startId);
     if (intent != null) {
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
          if(bundle.getBoolean("music"))
            playMusic();
          else if(bundle.getBoolean("disable"))
          	disMusic();
          else
            stopMusic();
        }
      }
  }
  
  public void disMusic() {
  		AlarmAlertWakeLock.acquireCpuWakeLock(this);
	  //=========================
			try {
				Log.d("Alarm service", "  ---------disMusic  ");
//				Intent intent = new Intent(Intent.ACTION_REQUEST_SHUTDOWN);
//
//                intent.putExtra(Intent.EXTRA_KEY_CONFIRM, false);
//
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//
//                startActivity(intent);
								
				Runtime.getRuntime().exec("input keyevent KEYCODE_POWER");
//				Runtime.getRuntime().exec("ls");
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}  
	  
	  //=======================
	  
  }
  
  
  public void playMusic() {
	  //=========================
	  AlarmAlertWakeLock.acquireCpuWakeLock(this);
			try {
				Log.d("Alarm service", "  ---------------  ");
//				Intent intent = new Intent(Intent.ACTION_REQUEST_SHUTDOWN);
//
//                intent.putExtra(Intent.EXTRA_KEY_CONFIRM, false);
//
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//
//                startActivity(intent);
								
				Runtime.getRuntime().exec("input keyevent KEYCODE_POWER");
//				Runtime.getRuntime().exec("ls");
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}  
	  
	  //=======================
	  
  }
  public void stopMusic() {
    if (player != null) {
	AlarmAlertWakeLock.releaseCpuLock();
      player.stop();
      try {
        player.prepare();
      } catch (IOException ex) {
        ex.printStackTrace();
      }
    }
  }

}
