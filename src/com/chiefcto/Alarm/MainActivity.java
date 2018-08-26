package com.chiefcto.Alarm;
import java.text.Format;
import java.util.Calendar;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemProperties;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
public class MainActivity extends Activity {
  //Properties
  private Button msetButton;
  private Button msetButton1;
  private Button mcancelButton;
  private AlermReceiver uIReceiver;
  private TextView mTextView;

  public String TAG = "Alarm";
  
  private test mtest;
  
  private Calendar calendar;
  /** Called when the activity is first created. */
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main);
    calendar = Calendar.getInstance();
    mTextView = (TextView)this.findViewById(R.id.mText);
    msetButton = (Button)this.findViewById(R.id.setTimeOpen);
    msetButton1 = (Button)this.findViewById(R.id.setTimeClose);
    mcancelButton = (Button)findViewById(R.id.cancelButton);
    mtest = new test();
    
    final AlarmManager am;
    am = (AlarmManager)getSystemService(ALARM_SERVICE);
    msetButton.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        // TODO Auto-generated method stub
        calendar.setTimeInMillis(System.currentTimeMillis());
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        new TimePickerDialog(MainActivity.this, new TimePickerDialog.OnTimeSetListener() {
          @Override
          public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            calendar.setTimeInMillis(System.currentTimeMillis());
            //set(f, value) changes field f to value.
            calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
            calendar.set(Calendar.MINUTE, minute);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);
            Intent intent = new Intent(MainActivity.this, AlermReceiver.class);
            intent.putExtra("music", true);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, intent, 0);
            
            //
            Log.d("Alarm service", hourOfDay + " " + minute + "  " + calendar.getTimeInMillis());
           
//            am = (AlarmManager)getSystemService(ALARM_SERVICE);
//            am.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
            //60*1000
            //AlarmManager.INTERVAL_DAY
            am.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
            
	    String tmps = "settime "+format(hourOfDay)+":"+format(minute);
	    String alarmt = format(hourOfDay) + format(minute);
	    SystemProperties.set("persist.sys.alarm", alarmt);
//	    Log.d("Alarm service","property   " + System.getProperty("persist.sys.alarm"));
            mTextView.setText(tmps);
          }
        },hour,minute,true).show();
      }
    });
    
    msetButton1.setOnClickListener(new OnClickListener() {
        @Override
        public void onClick(View v) {
          // TODO Auto-generated method stub
          calendar.setTimeInMillis(System.currentTimeMillis());
          int hour = calendar.get(Calendar.HOUR_OF_DAY);
          int minute = calendar.get(Calendar.MINUTE);
          new TimePickerDialog(MainActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
              calendar.setTimeInMillis(System.currentTimeMillis());
              //set(f, value) changes field f to value.
              calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
              calendar.set(Calendar.MINUTE, minute);
              calendar.set(Calendar.SECOND, 0);
              calendar.set(Calendar.MILLISECOND, 0);
              Intent intent = new Intent(MainActivity.this, AlermReceiver.class);
//              intent.putExtra("music", true);
              intent.putExtra("disable", true);
              PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 1, intent, 0);
          //    AlarmManager am;
              
             // am = (AlarmManager)getSystemService(ALARM_SERVICE);
//              am.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
             //System.currentTimeMillis()
              am.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), (24*60*60*1000), pendingIntent);
              String tmps = "settime "+format(hourOfDay)+":"+format(minute);
              mTextView.setText(tmps);
            }
          },hour,minute,true).show();
        }
      });
    
    mcancelButton.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
    	  mtest.b();
//        Intent intent = new Intent(MainActivity.this, AlermReceiver.class);
//        intent.putExtra("music", true);
//        PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, intent, 0);
//        AlarmManager am;
//        //
//        am = (AlarmManager)getSystemService(ALARM_SERVICE);
//        //cancel
//        am.cancel(pendingIntent);
//        mTextView.setText("canle");
      }
    });
  }
  private String format(int x) {
    String s = ""+x;
    if(s.length() == 1)
      s = "0"+s;
    return s;
  }

}
