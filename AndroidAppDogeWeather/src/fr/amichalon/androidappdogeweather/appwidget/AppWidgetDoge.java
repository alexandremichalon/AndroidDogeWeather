/**
 * 
 */
package fr.amichalon.androidappdogeweather.appwidget;

import fr.amichalon.androidappdogeweather.business.AndroidUtil;
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

	
	
    @Override
    public void onReceive(Context context, Intent intent)
    {
        super.onReceive(context, intent);

        if(ACTION_AUTO_UPDATE.equals(intent.getAction()))
        {
        	// intent that start the views update
        	Intent updateViewsIntent = new Intent(context, AppWidgetUpdateService.class);
        	context.startService(updateViewsIntent);
        }
    }

    
    
    
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds)
    {
    	super.onUpdate(context, appWidgetManager, appWidgetIds);
    	
    	// intent that start the views update
    	// only here for initial placement
    	Intent updateViewsIntent = new Intent(context, AppWidgetUpdateService.class);
    	context.startService(updateViewsIntent);
    }
    
    
    
    

    @Override
    public void onEnabled(Context context)
    {    
    	// set the context to access the resources in the widget
    	AndroidUtil.setContext(context);
    	
        // start alarm
        AppWidgetAlarm appWidgetAlarm = new AppWidgetAlarm(context.getApplicationContext());
        appWidgetAlarm.startAlarm();
    }

    
    
    
    @Override
    public void onDisabled(Context context)
    {
        // stop alarm
        AppWidgetAlarm appWidgetAlarm = new AppWidgetAlarm(context.getApplicationContext());
        appWidgetAlarm.stopAlarm();
    }

}
