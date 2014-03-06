/**
 * 
 */
package fr.amichalon.androidappdogeweather.appwidget;

import java.util.Calendar;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

/**
 * @author alexandre.michalon
 *
 */
public class AppWidgetAlarm 
{
	private final int ALARM_ID = 0;
    
	public static final int ONE_HOUR = 60;

    private Context context;


    public AppWidgetAlarm(Context context)
    {
        this.context = context;
    }


    
    public void startAlarm()
    {
    	startAlarm(ONE_HOUR);
    }
    
    
    
    public void startAlarm(int intervalInMinutes)
    {   
    	
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, intervalInMinutes);

        Intent alarmIntent = new Intent(AppWidgetDoge.ACTION_AUTO_UPDATE);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, ALARM_ID, alarmIntent, PendingIntent.FLAG_CANCEL_CURRENT);

        int intervalInMillis = intervalInMinutes * 60 * 1000;
    	AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC, calendar.getTimeInMillis(), intervalInMillis, pendingIntent);
    }


    public void stopAlarm()
    {
        Intent alarmIntent = new Intent(AppWidgetDoge.ACTION_AUTO_UPDATE);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, ALARM_ID, alarmIntent, PendingIntent.FLAG_CANCEL_CURRENT);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);
    }

}
