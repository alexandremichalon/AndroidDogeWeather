
package fr.amichalon.androidappdogeweather.appwidget;

import java.util.Calendar;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

/**
 * Class that manage the AlarmManager that send custom update Intents
 * to the AppWidgetProvider.
 * 
 * @author alexandre.michalon
 */
public class AppWidgetAlarm 
{
	
	/**
	 * The alarm ID.
	 */
	private final int ALARM_ID = 0;
    
	
	/**
	 * The number of minutes in one hour.
	 */
	public static final int ONE_HOUR = 60;

	
	/**
	 * The context of the AppWidget.
	 */
    private Context context;


    
    
    
    /**
     * Build an alarm with an AppWidgetContext.
     * 
     * @param context
     * 		The context of the AppWidget.
     */
    public AppWidgetAlarm(Context context)
    {
        this.context = context;
    }


    
    
    
    /**
     * Start the AlarmManager that will send custom update Intents every
     * hour.
     */
    public void startAlarm()
    {
    	startAlarm(ONE_HOUR);
    }
    
    
    /**
     * Start the AlarmManager that will send custom update Intents every
     * time a certain amount of minutes is spent.
     */
    public void startAlarm(int intervalInMinutes)
    {   
    	
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, intervalInMinutes);

        Intent alarmIntent			= new Intent(AppWidgetDoge.ACTION_AUTO_UPDATE);
        PendingIntent pendingIntent	= PendingIntent.getBroadcast(context, ALARM_ID, alarmIntent, PendingIntent.FLAG_CANCEL_CURRENT);

        int intervalInMillis		= intervalInMinutes * 60 * 1000;
    	AlarmManager alarmManager	= (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC, calendar.getTimeInMillis(), intervalInMillis, pendingIntent);
    }


    /**
     * Stop the AlarmManager.
     */
    public void stopAlarm()
    {
        Intent alarmIntent			= new Intent(AppWidgetDoge.ACTION_AUTO_UPDATE);
        PendingIntent pendingIntent	= PendingIntent.getBroadcast(context, ALARM_ID, alarmIntent, PendingIntent.FLAG_CANCEL_CURRENT);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);
    }

}
