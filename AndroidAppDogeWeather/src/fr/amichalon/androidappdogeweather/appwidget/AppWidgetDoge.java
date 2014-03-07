
package fr.amichalon.androidappdogeweather.appwidget;

import fr.amichalon.androidappdogeweather.business.AndroidUtil;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;

/**
 * The AppWidgetProvider that contains the Doge Weather Widget intelligence.
 * 
 * @author alexandre.michalon
 */
public class AppWidgetDoge extends AppWidgetProvider 
{
	
	/**
	 * The designation of the Intent sent by the alarm every hour. It is
	 * allowed in the Manifest file.
	 */
	public static final String ACTION_AUTO_UPDATE = "fr.amichalon.androidappdogeweather.AUTO_UPDATE";
	
	
	
	
	
    @Override
    public void onReceive(Context context, Intent intent)
    {
        super.onReceive(context, intent);

        // every time an intent is received, we filter
        // we only react to our declared intent, i.e. alarm intent.
        if (ACTION_AUTO_UPDATE.equals(intent.getAction()))
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
    	// when the first widget is added on the phone,
    	// set the Context and start the alarm
    	
    	// set the context to access the resources in the widget
    	AndroidUtil.setContext(context);
    	
        // start alarm
        AppWidgetAlarm appWidgetAlarm = new AppWidgetAlarm(context.getApplicationContext());
        appWidgetAlarm.startAlarm();
    }

    
    
    
    @Override
    public void onDisabled(Context context)
    {
        // when the last widget is removed on the phone, 
    	// stop alarm
        AppWidgetAlarm appWidgetAlarm = new AppWidgetAlarm(context.getApplicationContext());
        appWidgetAlarm.stopAlarm();
    }

}
