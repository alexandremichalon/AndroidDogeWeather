/**
 * 
 */
package fr.amichalon.androidappdogeweather.appwidget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;

/**
 * @author alexandre.michalon
 *
 */
public class AppWidgetDoge extends AppWidgetProvider 
{
	public static final String ACTION_AUTO_UPDATE = "fr.amichalon.androidappdogeweather.AUTO_UPDATE";

	
	/*
    @Override
    public void onReceive(Context context, Intent intent)
    {
        super.onReceive(context, intent);

        if(intent.getAction().equals(ACTION_AUTO_UPDATE))
        {
            // DO SOMETHING
        }
    }
    */
    
    
    
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds)
    {
    	super.onUpdate(context, appWidgetManager, appWidgetIds);
    	
    	// intent that start the views update
    	Intent updateViewsIntent = new Intent(context, AppWidgetUpdateService.class);
    	context.startService(updateViewsIntent);
    }
    
    
    
    

    @Override
    public void onEnabled(Context context)
    {
    	super.onEnabled(context);
    	
        // start alarm
        AppWidgetAlarm appWidgetAlarm = new AppWidgetAlarm(context.getApplicationContext());
        appWidgetAlarm.startAlarm();
    }

    
    
    
    @Override
    public void onDisabled(Context context)
    {
    	super.onDisabled(context);
    	
        // stop alarm
        AppWidgetAlarm appWidgetAlarm = new AppWidgetAlarm(context.getApplicationContext());
        appWidgetAlarm.stopAlarm();
    }

}
