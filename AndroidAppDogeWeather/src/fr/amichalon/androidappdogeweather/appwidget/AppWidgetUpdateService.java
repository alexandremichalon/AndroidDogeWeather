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
import android.app.IntentService;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.widget.RemoteViews;

/**
 * @author alexandre.michalon
 *
 */
public class AppWidgetUpdateService extends IntentService 
{
	private static final String serviceName = "AppWidgetUpdateService";
	
	public AppWidgetUpdateService() 
	{
		super(serviceName);
	}

	
	
	@Override
	protected void onHandleIntent(Intent intent) 
	{
		// get the AppWidgetManager
		Context context						= getApplicationContext();
		AppWidgetManager appWidgetManager	= AppWidgetManager.getInstance(context);
		
		// get ids of the widget instances
		ComponentName appWidget	= new ComponentName(context, AppWidgetDoge.class);
	    int[] allWidgetIds		= appWidgetManager.getAppWidgetIds(appWidget);
	    
	    
	    // get the current weather
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
		
		
		// update all instances
	    final int length = allWidgetIds.length;
    	for (int i = 0; i < length; i++)
    	{
    		int appWidgetId = allWidgetIds[i];
			RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.appwidget_doge_weather);
			
			// reset the appwidget view
			ResetView(views, dWeather);
			
			// set the intent when the user click the appwidget
	    	Intent clickImageIntent = new Intent(context, AppWidgetDoge.class);
	    	clickImageIntent.setAction(AppWidgetDoge.ACTION_AUTO_UPDATE);
	    	PendingIntent onClickIntent = PendingIntent.getBroadcast(
	    			context, 
	    			0, 
	    			clickImageIntent, 
	    			PendingIntent.FLAG_UPDATE_CURRENT);
	    	views.setOnClickPendingIntent(R.id.LayoutAppWidget, onClickIntent);
	    	
	    	appWidgetManager.updateAppWidget(appWidgetId, views);
    	}
	}

	
	private void ResetView(RemoteViews views, DogeWeather dWeather)
    {
    	String description = String.format(Locale.US,
				"%s : %s, %d°C, %d°F", 
				dWeather.getCity(),
				dWeather.getDescription(),
				dWeather.getTemperatureCelcius(),
				dWeather.getTemperatureFahrenheit());
		
		
		views.setTextColor(R.id.TextDescriptionAppWidget, getRandomColor());
		views.setTextColor(R.id.TextLFAppWidget1, getRandomColor());
		views.setTextColor(R.id.TextLFAppWidget2, getRandomColor());
		views.setTextColor(R.id.TextLFAppWidget3, getRandomColor());
		views.setTextColor(R.id.TextLFAppWidget4, getRandomColor());

		views.setTextViewText(R.id.TextDescriptionAppWidget, description);
		views.setTextViewText(R.id.TextLFAppWidget1, getRandomLexicalFieldText(dWeather));
		views.setTextViewText(R.id.TextLFAppWidget2, getRandomLexicalFieldText(dWeather));
		views.setTextViewText(R.id.TextLFAppWidget3, getRandomLexicalFieldText(dWeather));
		views.setTextViewText(R.id.TextLFAppWidget4, getRandomLexicalFieldText(dWeather));
		
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
    	String[] dogeSentences	= getResources().getStringArray(R.array.doge_sentences);
    	int dogeLength			= dogeSentences.length;
    	int rndDogeIndex		= random.nextInt(dogeLength);
    	String dogeSentence		= dogeSentences[rndDogeIndex];
    	
    	// construct the sentence
    	return String.format(Locale.US, dogeSentence, weatherWord);
    }
    
    
    private int getRandomColor()
    {
    	// array of color references in the resource files
    	TypedArray dogeColors = getResources().obtainTypedArray(R.array.doge_colors);
		
    	// get a random color reference in the array (0 <= rndIndex < length)
    	// get the color from that color reference (default magenta)
    	int length		= dogeColors.length();
		int rndIndex	= (new Random()).nextInt(length);
		int rndColorId	= dogeColors.getColor(rndIndex, Color.MAGENTA);
		
		// free the resources from the array
		dogeColors.recycle();
		
		return rndColorId;
    }
}
