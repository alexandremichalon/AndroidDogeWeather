package fr.amichalon.androidappdogeweather.appwidget.preferences;

import fr.amichalon.androidappdogeweather.R;
import fr.amichalon.androidappdogeweather.appwidget.AppWidgetAlarm;
import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;

public class AppWidgetConfigurationActivity extends Activity 
{
	
	private int mAppWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        
        setResult(RESULT_CANCELED);
        
        PreferenceManager.setDefaultValues(this, R.xml.appwidget_preference, false);
        
        // Display the fragment as the main content.
        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new AppWidgetPreferenceFragment())
                .commit();
        
        // Find the widget id from the intent.
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        
        if (extras != null) 
        {
             mAppWidgetId = extras.getInt(
                     AppWidgetManager.EXTRA_APPWIDGET_ID, 
                     AppWidgetManager.INVALID_APPWIDGET_ID);
        }
 
        // If they gave us an intent without the widget id, just bail.
        if (mAppWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
            finish();
        }
    }
	
	
	@Override
	public void onBackPressed()
	{		
		// get the refresh interval set by user
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        int intervalInMinutes = preferences.getInt("appWidgetRefreshInterval", AppWidgetAlarm.ONE_HOUR);
        
        // restart alarm
        AppWidgetAlarm appWidgetAlarm = new AppWidgetAlarm(getApplicationContext());
        appWidgetAlarm.startAlarm(intervalInMinutes);
        
        // send OK and terminate the configuration activity
        Intent resultValue = new Intent();
        resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId);
        setResult(RESULT_OK, resultValue);
        finish();
	}
	
	
	
	public static class AppWidgetPreferenceFragment extends PreferenceFragment 
	{

		@Override
	    public void onCreate(Bundle savedInstanceState) 
		{
	    	super.onCreate(savedInstanceState);

	        // Load the preferences from an XML resource
	        addPreferencesFromResource(R.xml.appwidget_preference);
	    }
	}
}
