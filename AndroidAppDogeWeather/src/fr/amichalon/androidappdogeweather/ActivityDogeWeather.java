package fr.amichalon.androidappdogeweather;

import java.util.Random;

import fr.amichalon.androidappdogeweather.Business.DogeWeather;
import fr.amichalon.androidappdogeweather.Business.WeatherRetriever;
import fr.amichalon.androidappdogeweather.Model.Weather;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ActivityDogeWeather extends Activity {
	
	private static final String COMIC_SANS_FONT_FILE = "fonts/comicsans.ttf";
	
	private Typeface comicSans;
	
	private DogeWeather dogeWeather;

	private int latitude;
	
	private int longitude;
	
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doge_weather);
        
        initView();
        dogeWeather = new DogeWeather(this);
        fillWeatherInfos(dogeWeather);
        latitude = -1;
        longitude = -1;
        
        
        runWeatherHttpRequest = true;
        DogeWeatherTask weatherHttpRequest = new DogeWeatherTask();
        weatherHttpRequest.execute((Void) null);
    }


    
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_doge_weather, menu);
        return true;
    }
    
    
    
    
    private void fillWeatherInfos(DogeWeather weather)
    {
    	int backImageId = weather.getBackImageId();
    	rlytDogeWeather.setBackgroundResource(backImageId);
    	
    	
    	int frontImageId = weather.getFrontImageId();
    	imgvwFront.setImageResource(frontImageId);
    	
    	
    	String description = weather.getDescription();
    	txtvwDescription.setText(description);
    	
    	
    	String city = weather.getCity();
    	txtvwCity.setText(city);
    	
    	
    	String temperatureCelcius = weather.getTemperatureCelcius() + "�C";
    	txtvwTemperatureCelcius.setText(temperatureCelcius);
    	
    	
    	String temperatureFahrenheit = weather.getTemperatureFahrenheit() + "�F";
    	txtvwTemperatureFahrenheit.setText(temperatureFahrenheit);
    }
    
    
    
    
    private int getRandomColorId()
    {
    	TypedArray dogeColors = resources.obtainTypedArray(R.array.doge_colors);
		
    	int length 		= dogeColors.length();
		int rndIndex 	= (new Random()).nextInt(length);
		int rndColorId 	= dogeColors.getResourceId(rndIndex, Color.MAGENTA);
		
		dogeColors.recycle();
		
		return rndColorId;
    }
    
    
    
    private String getRandomPopupText(DogeWeather weather)
    {
    	Random random = new Random();
    	
    	String[] lexicalField 	= weather.getLexicalField();
    	int lfLength 			= lexicalField.length;
    	int rndLfIndex 			= random.nextInt(lfLength);
    	String weatherWord 		= lexicalField[rndLfIndex];
    	
    	String[] dogeSentences 	= resources.getStringArray(R.array.doge_sentences);
    	int dogeLength 			= dogeSentences.length;
    	int rndDogeIndex 		= random.nextInt(dogeLength);
    	String dogeSentence 	= dogeSentences[rndDogeIndex];
    	
    	return String.format(dogeSentence, weatherWord);
    }
    
    
    
    
    private void makeRandomPopup(DogeWeather weather)
    {
    	TextView txtvwToRemove = txtvwPopups[popupsRotationIndex];
    	
    	if(txtvwToRemove != null)
    		rlytDogeWeather.removeView(txtvwToRemove);
    	
    	TextView txtvw = new TextView(this);
    	String rndText = getRandomPopupText(weather);
    	txtvw.setText(rndText);
    	txtvw.setTypeface(comicSans);
    	txtvw.setTextColor(getRandomColorId());
    	
    	int screenWidth = rlytDogeWeather.getWidth();
    	int screenHeight = rlytDogeWeather.getHeight();
    	int txtvwWidth = txtvw.getWidth();
    	int txtvwHeight = txtvw.getHeight();
    	
    	int maxWidthDrawable = screenWidth - txtvwWidth;
    	int maxHeightDrawable = screenHeight - txtvwHeight;
    	
    	Random random  = new Random();
    	
    	txtvw.setPadding(
    			random.nextInt(maxWidthDrawable),
    			random.nextInt(maxHeightDrawable),
    			0,
    			0);
    	
    	rlytDogeWeather.addView(txtvw);
    	
    	txtvwPopups[popupsRotationIndex] = txtvw;
    	popupsRotationIndex = (popupsRotationIndex + 1) % POPUPS_MAXIMUM_ROTATION;
    }
    
    
    
    private void resetPopups()
    {
    	if(txtvwPopups != null)
    	{
    		int length = txtvwPopups.length;
    		for(int i = 0; i < length; i++)
    		{
    			TextView txtvw = txtvwPopups[i];
    			
    			if(txtvw != null)
    				rlytDogeWeather.removeView(txtvw);
    		}
    	}
    		
    	txtvwPopups 			= new TextView[POPUPS_MAXIMUM_ROTATION];
    	popupsRotationIndex 	= 0;
    }
    
    
    
    
    private boolean isNetworkAvailable() 
    {
    	Context ctx = getApplicationContext();
    	ConnectivityManager connMgr = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
    	
    	return connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnected() || 
    			connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnected();
	}

    
    
    
    
    private void initView()
    {
    	resources = getResources();
    	comicSans = Typeface.createFromAsset(getAssets(), COMIC_SANS_FONT_FILE);
    	
    	rlytDogeWeather 			= (RelativeLayout) findViewById(R.layout.activity_doge_weather);
    	imgvwFront 					= (ImageView) findViewById(R.id.ImgFrontDoge);
    	txtvwDescription 			= (TextView) findViewById(R.id.TextDescription);
    	txtvwCity 					= (TextView) findViewById(R.id.TextCity);
    	txtvwTemperatureFahrenheit 	= (TextView) findViewById(R.id.TextTemperatureFahrenheit);
    	txtvwTemperatureCelcius 	= (TextView) findViewById(R.id.TextTemperatureCelcius);
    	
    	txtvwDescription			.setTypeface(comicSans);
    	txtvwCity					.setTypeface(comicSans);
    	txtvwTemperatureCelcius		.setTypeface(comicSans);
    	txtvwTemperatureFahrenheit	.setTypeface(comicSans);
    	
    	txtvwDescription			.setTextColor(getRandomColorId());
    	txtvwCity					.setTextColor(getRandomColorId());
    	txtvwTemperatureCelcius		.setTextColor(getRandomColorId());
    	txtvwTemperatureFahrenheit	.setTextColor(getRandomColorId());
    	
    	resetPopups();
    }
    
    private Resources resources;
    
    private RelativeLayout rlytDogeWeather;
    private ImageView imgvwFront;
    private TextView txtvwDescription;
    private TextView txtvwCity;
    private TextView txtvwTemperatureFahrenheit;
    private TextView txtvwTemperatureCelcius;
    
    private TextView[] txtvwPopups;
    private int popupsRotationIndex;
    private static final int POPUPS_MAXIMUM_ROTATION = 4;
    
    
    
    private boolean runWeatherHttpRequest;
    
    private class DogeWeatherTask extends AsyncTask<Void, Weather, Void> {

    	@Override
    	protected Void doInBackground(Void... params) 
    	{
    		while(runWeatherHttpRequest)
    		{
	    		if(isNetworkAvailable())
	    		{
	    			Weather weather = (latitude < 0 || longitude < 0)
	    					? WeatherRetriever.getCurrentDefaultWeather()
							: WeatherRetriever.getCurrentWeather(latitude, longitude);
	    					
	    			publishProgress(weather);
	    		}
	    		else
	    		{
	    			publishProgress((Weather) null);
	    		}
	    		
	    		try
	    		{
	    			// sleep one minute
	    			Thread.sleep(1 * 60 * 1000);
	    		}
	    		catch(Exception exc) { }
    		}
    		
    		return (Void) null;
    	}
    	
    	
    	@Override
    	protected void onProgressUpdate(Weather... progress) {
			
    		if (progress != null
				&& progress.length > 0)
    		{
    			Weather currentWeather = progress[0];
    			
    			if(currentWeather != null)
    			{
    				if(!currentWeather.getDescription().equals(dogeWeather.getDescription()))
        			{
    	    			initView();
    	    			resetPopups();
        			}
        			
        			dogeWeather.updateCurrentWeather(currentWeather);
        			fillWeatherInfos(dogeWeather);
    			}
    		}
		}
    }
}
