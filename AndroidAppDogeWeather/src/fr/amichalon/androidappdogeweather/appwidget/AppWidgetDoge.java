/**
 * 
 */
package fr.amichalon.androidappdogeweather.appwidget;

import java.util.Locale;
import java.util.Random;

import fr.amichalon.androidappdogeweather.R;
import fr.amichalon.androidappdogeweather.business.AndroidUtil;
import fr.amichalon.androidappdogeweather.business.DogeWeather;
import fr.amichalon.androidappdogeweather.business.WeatherUtil;
import fr.amichalon.androidappdogeweather.model.GeoCoordinates;
import fr.amichalon.androidappdogeweather.model.Weather;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.widget.RemoteViews;

/**
 * @author alexandre.michalon
 *
 */
public class AppWidgetDoge extends AppWidgetProvider 
{
	public static final String ACTION_AUTO_UPDATE = "AUTO_UPDATE";
	
	private static final int MAX_LEXICAL_FIELD_DISPLAYED = 5;
	
	private Resources resources;

    @Override
    public void onReceive(Context context, Intent intent)
    {
        super.onReceive(context, intent);

        if(intent.getAction().equals(ACTION_AUTO_UPDATE))
        {
            // DO SOMETHING
        }
    }
    
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds)
    {
    	super.onUpdate(context, appWidgetManager, appWidgetIds);
    	
    	resources = context.getResources();
    	
    	final int length = appWidgetIds.length;
    	for (int i = 0; i < length; i++)
    	{
    		int appWidgetId = appWidgetIds[i];
    		
    		RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.appwidget_doge_weather);
    		
    		ResetView(views);
    		
    		appWidgetManager.updateAppWidget(appWidgetId, views);		
    	}
    }
    
    
    
    private void ResetView(RemoteViews views)
    {
    	DogeWeather dWeather = null;
		try
		{
    		GeoCoordinates coord = AndroidUtil.getPhoneCoordinates();
			Weather weather = WeatherUtil.getCurrentWeather(coord.getLatitude(), coord.getLongitude());
			
			dWeather = new DogeWeather(weather);
		}
		catch (Throwable t)
		{
			dWeather = new DogeWeather();
		}
		
		
		String temperatures = String.format(Locale.US,
				"%d°C\t%d°F", 
				dWeather.getTemperatureCelcius(),
				dWeather.getTemperatureFahrenheit());
		
		
		String lfText = "";
		for (int i = 0; (i < MAX_LEXICAL_FIELD_DISPLAYED); i++)
		{
			lfText += getRandomLexicalFieldText(dWeather) + " ! ";
		}
		
		

		views.setTextViewText(R.id.TextDescriptionAppWidget, dWeather.getDescription());
		views.setTextViewText(R.id.TextCityAppWidget, dWeather.getCity());
		views.setTextViewText(R.id.TextTemperatureAppWidget, temperatures);
		views.setTextViewText(R.id.TextLexicalFieldAppWidget, lfText);
		
		views.setImageViewResource(R.id.ImgDogeAppWidget, dWeather.getFrontImageId());
		views.setImageViewResource(R.id.ImgBackgroundAppWidget, dWeather.getBackImageId());
    }
    
    
    
    
    private String getRandomLexicalFieldText(DogeWeather weather)
    {
    	Random random = new Random();
    	
    	// get a random word in the current weather lexical field ("cloud", "frosty", ...)
    	String[] lexicalField	= weather.getLexicalField();
    	int lfLength			= lexicalField.length;
    	int rndLfIndex			= random.nextInt(lfLength);
    	String weatherWord		= lexicalField[rndLfIndex];
    	
    	// get a random word in the doge lexical field ("such %s", "very %s", ...)
    	String[] dogeSentences	= resources.getStringArray(R.array.doge_sentences);
    	int dogeLength			= dogeSentences.length;
    	int rndDogeIndex		= random.nextInt(dogeLength);
    	String dogeSentence		= dogeSentences[rndDogeIndex];
    	
    	// construct the sentence
    	return String.format(Locale.US, dogeSentence, weatherWord);
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
