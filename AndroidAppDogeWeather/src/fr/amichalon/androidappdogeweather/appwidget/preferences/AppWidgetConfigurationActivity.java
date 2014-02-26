package fr.amichalon.androidappdogeweather.appwidget.preferences;

import fr.amichalon.androidappdogeweather.R;

import android.app.Activity;
import android.os.Bundle;
import android.preference.PreferenceFragment;

public class AppWidgetConfigurationActivity extends Activity 
{
	
	@Override
    protected void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        
        // Display the fragment as the main content.
        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new AppWidgetPreferenceFragment())
                .commit();
        
        
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
