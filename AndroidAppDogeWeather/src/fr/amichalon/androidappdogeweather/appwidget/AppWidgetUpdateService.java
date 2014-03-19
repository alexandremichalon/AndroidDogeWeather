
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
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Typeface;
import android.widget.RemoteViews;

/**
 * Service class that get the current weather by requesting the Open Weather
 * Map API and fill the AppWidget views accordingly. It is called by the 
 * AppWidgetProvider when an alarm update intent is caught.
 * 
 * @author alexandre.michalon
 */
public class AppWidgetUpdateService extends IntentService 
{
	
	/**
	 * The name of the service, used only in the constructor.
	 */
	private static final String serviceName = "AppWidgetUpdateService";
	
	
	/**
	 * The default constructor.
	 */
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
    		GeoCoordinates coord	= AndroidUtil.getPhoneCoordinates();
			Weather weather			= WeatherUtil.getCurrentWeather(
					coord.getLatitude(), 
					coord.getLongitude());
			
			if(weather != null)
				dWeather = new DogeWeather(weather);
		}
		catch (Throwable t) { }
		
		
		// update all instances
	    final int length = allWidgetIds.length;
    	for (int i = 0; i < length; i++)
    	{
    		int appWidgetId = allWidgetIds[i];
			RemoteViews views = new RemoteViews(
					context.getPackageName(), 
					R.layout.appwidget_doge_weather);
			
			
			// reset the appwidget view
			if(dWeather != null)
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
	
	
	
	
	
	/**
	 * Uses a DogeWeather to update the infos displayed on the AppWidget.
	 * 
	 * @param views
	 * 		The remote views of the widget.
	 * 
	 * @param dWeather
	 * 		The new current weather.
	 */
	private void ResetView(RemoteViews views, DogeWeather dWeather)
    {
		// format for the description :
		// "city : description, 9°C, 44°F"
    	String description = String.format(Locale.US,
				"%s : %s, %d°C, %d°F", 
				dWeather.getCity(),
				dWeather.getDescription(),
				dWeather.getTemperatureCelcius(),
				dWeather.getTemperatureFahrenheit());
		
    	// set the description textview with a random text color
    	// and the formatted description above.
		views.setTextColor(R.id.TextDescriptionAppWidget, getRandomColor());
		views.setTextViewText(R.id.TextDescriptionAppWidget, description);
		
		// generate the bitmaps of the texts around doge with 
		// - a random sentence from the lexical field of the current weather
		// - a random text color
		// - a text size set to 12dp.
		// we use imageviews instead of textviews because it's the only
		// way to use a custom font in widgets, and we LOVE comic sans.
		int textSizeInDp = 12;
		Bitmap bmp1 = buildBitmapText(getRandomLexicalFieldText(dWeather), getRandomColor(), textSizeInDp);
		Bitmap bmp2 = buildBitmapText(getRandomLexicalFieldText(dWeather), getRandomColor(), textSizeInDp);
		Bitmap bmp3 = buildBitmapText(getRandomLexicalFieldText(dWeather), getRandomColor(), textSizeInDp);
		Bitmap bmp4 = buildBitmapText(getRandomLexicalFieldText(dWeather), getRandomColor(), textSizeInDp);
		Bitmap bmp5 = buildBitmapText(getRandomLexicalFieldText(dWeather), getRandomColor(), textSizeInDp);
		
		// assign all the bitmaps to their respective imageview
		views.setImageViewBitmap(R.id.TextLFAppWidget1, bmp1);
		views.setImageViewBitmap(R.id.TextLFAppWidget2, bmp2);
		views.setImageViewBitmap(R.id.TextLFAppWidget3, bmp3);
		views.setImageViewBitmap(R.id.TextLFAppWidget4, bmp4);
		views.setImageViewBitmap(R.id.TextLFAppWidget5, bmp5);
		
		// set the front and background image of the widget
		views.setImageViewResource(R.id.ImgDogeAppWidget, dWeather.getFrontImageId());
		views.setImageViewResource(R.id.ImgBackgroundAppWidget, dWeather.getBackImageId());
    }
	
	
	/**
	 * Build a bitmap that contains a random sentence build from the lexical
	 * field of the current weather, with a random text color, and a given
	 * text size in dp.
	 * 	
	 * @param text
	 * 		The sentence.
	 * 
	 * @param textColor
	 * 		The text color.
	 * 
	 * @param textSize
	 * 		The text size.
	 * 
	 * @return
	 * 		A Bitmap objet that represent a text.
	 */
	private Bitmap buildBitmapText(String text, int textColor, int textSize)     
	{
		// such magic numbers ! very empiric ! wow !
		final double density	= getResources().getDisplayMetrics().density;
		final double textWidth	= text.length() * textSize * density;
		final int bmpWidth		= (int) (textWidth * 0.6);
		final int bmpHeight		= (int) (textSize * 5);
		final int textPadding	= (int) (bmpWidth * 0.5);
		final int textHeight	= (int) (textSize * density);
		
		// build a bitmap and draw a text with comic sans font on it
		Bitmap myBitmap		= Bitmap.createBitmap(bmpWidth, bmpHeight, Bitmap.Config.ARGB_8888);
		Canvas myCanvas		= new Canvas(myBitmap);
		Paint paint			= new Paint();
		Typeface comicsans	= Typeface.createFromAsset(this.getAssets(), "fonts/comicsans.ttf");
		
		paint.setAntiAlias(true);
		paint.setSubpixelText(true);
		paint.setTypeface(comicsans);
		paint.setStyle(Paint.Style.FILL);
		paint.setColor(textColor);
		paint.setTextSize(textHeight);
		paint.setTextAlign(Align.CENTER);
		
		myCanvas.drawText(text, textPadding, textHeight, paint);
		return myBitmap;
	}


	/**
	 * Build a random sentence from the lexical field associated with a weather.
	 * 
	 * @param weather
	 * 		The current weather.
	 * 
	 * @return
	 * 		A String with the format "such cloud" or "many rain"
	 */
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
    
    
    /**
     * Get a color at random in all the colors declared for doge in the 
     * resources.
     * 
     * @return
     * 		An integer that represent a color in Android.
     */
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
