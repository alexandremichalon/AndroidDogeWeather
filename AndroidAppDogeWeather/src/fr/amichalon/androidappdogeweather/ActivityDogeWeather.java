package fr.amichalon.androidappdogeweather;

import java.io.IOException;
import java.util.Locale;
import java.util.Random;

import fr.amichalon.androidappdogeweather.Business.AndroidUtil;
import fr.amichalon.androidappdogeweather.Business.DogeWeather;
import fr.amichalon.androidappdogeweather.Business.WeatherUtil;
import fr.amichalon.androidappdogeweather.Model.GeoCoordinates;
import fr.amichalon.androidappdogeweather.Model.Weather;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.DialogFragment;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ActivityDogeWeather extends Activity 
{
	
	private static final String COMIC_SANS_FONT_FILE = "fonts/comicsans.ttf";
	
	private Typeface comicSans;
	
	private DogeWeather dogeWeather;

	private GeoCoordinates geoCoordinates;
	
	private Object drawLock = new Object();
	
	
    @Override
    protected void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doge_weather);
        
        // initialize AndroidUtil with the context of the application
        AndroidUtil.setContext(getApplicationContext());
        
        // initialize the use my location button
        locationState = ButtonLocationState.DEFAULT;
        
        // initialize the view
        synchronized (drawLock) 
        {
	    	initView();
			
	    	dogeWeather = new DogeWeather();
	        fillWeatherInfos(dogeWeather);
        }
        
        // get the current latitude and longitude 
        // via the phone geolocalization, if possible
        geoCoordinates = AndroidUtil.getPhoneCoordinates();

        // get the coordinates whenever the user click the
        // use my location button
        btnUseLocation.setOnClickListener(getUserLocation);
        
        // start the job that get the weather info
        // from OpenWeatherMap (once)
        DogeWeatherTask weatherHttpRequest = new DogeWeatherTask();
        weatherHttpRequest.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        
        // start the job that generate popups randomly
        // on the screen every second
        runPopupGenerator = true;
        LexicalFieldPopupGeneratorTask lfPopupGenerator = new LexicalFieldPopupGeneratorTask();
        lfPopupGenerator.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }


         
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) 
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_doge_weather, menu);
        return true;
    }
    
    
        
    @Override
    public boolean onOptionsItemSelected (MenuItem item)
    {
      if (item.getItemId() == R.id.action_settings)
      {
    	  DialogFragment dialog = new CreditsDialogFragment();
    	  dialog.show(getFragmentManager(), "credits");
      }
      
      return super.onOptionsItemSelected(item);
    }
    
    
    
    
    private void fillWeatherInfos(DogeWeather weather)
    {
    	// background image
    	int backImageId = weather.getBackImageId();
    	if(backImageId > 0)
    		rlytDogeWeather.setBackgroundResource(backImageId);
    	
    	
    	// front image (a doge image)
    	int frontImageId = weather.getFrontImageId();
    	if(frontImageId > 0)
    		imgvwFront.setImageResource(frontImageId);
    	
    	
    	// description of the current weather according OpenWeatherMap
    	String description = "wow " + weather.getDescription();
    	txtvwDescription.setText(description);
    	
    	
    	// city found by geolocalization (default Paris)
    	String city = weather.getCity();
    	txtvwCity.setText(city);
    	
    	
    	int temperatureC			= weather.getTemperatureCelcius();
    	int temperatureF			= weather.getTemperatureFahrenheit();
    	boolean temperatureKnown	= (temperatureC != temperatureF);
    			
    	// current temperature in celcius
    	String temperatureCelcius = (temperatureKnown)
    			? temperatureC + "°C"
				: "";
    	txtvwTemperatureCelcius.setText(temperatureCelcius);
    	
    	
    	// current temperature in fahrenheit
    	String temperatureFahrenheit = (temperatureKnown)
    			? temperatureF + "°F"
				: "";
    	txtvwTemperatureFahrenheit.setText(temperatureFahrenheit);
    }
    
    
    
    
    private int getRandomColor()
    {
    	// array of color references in the resource files
    	TypedArray dogeColors = resources.obtainTypedArray(R.array.doge_colors);
		
    	// get a random color reference in the array (0 <= rndIndex < length)
    	// get the color from that color reference (default magenta)
    	int length		= dogeColors.length();
		int rndIndex	= (new Random()).nextInt(length);
		int rndColorId	= dogeColors.getColor(rndIndex, Color.MAGENTA);
		
		// free the resources from the array
		dogeColors.recycle();
		
		return rndColorId;
    }
    
    
    
    private String getRandomPopupText(DogeWeather weather)
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
    
    
    
    
    private void makeRandomPopup(DogeWeather weather)
    {
    	int screenWidth		= rlytDogeWeather.getWidth();
    	int screenHeight	= rlytDogeWeather.getHeight();
    	
    	// check if the layout is drawn
    	if(screenWidth > 0 && screenHeight > 0)
    	{
	    	// get the oldest popup
			TextView txtvwToRemove = txtvwPopups[popupsRotationIndex];
	    	
			// if exist, remove it
	    	if(txtvwToRemove != null)
	    		rlytDogeWeather.removeView(txtvwToRemove);
	    	
	    	// new popup :
	    	// - random sentence
	    	// - comic sans
	    	// - random color
	    	// - text size according to screen size (in resources)
	    	// - random left padding (x position)
	    	// - random top padding (y position)
	    	TextView txtvw = new TextView(this);
	    	String rndText = getRandomPopupText(weather);
	    	txtvw.setText(rndText);
	    	txtvw.setTypeface(comicSans);
	    	txtvw.setTextColor(getRandomColor());
	    	
	    	float text_size = resources.getDimension(R.dimen.text_size);
	    	txtvw.setTextSize(TypedValue.COMPLEX_UNIT_PX, text_size);
	    	
	    	
	    	// old version, but could not calculate W and H in time on TextView
	    	// int txtvwWidth = txtvw.getWidth();
	    	// int txtvwHeight = txtvw.getHeight();
	    	int txtvwWidth		= screenWidth / 10;
	    	int txtvwHeight		= screenHeight / 10;
	    	int screenPaddingL	= rlytDogeWeather.getPaddingLeft();
	    	int screenPaddingR	= rlytDogeWeather.getPaddingRight();
	    	int screenPaddingT	= rlytDogeWeather.getPaddingTop();
	    	int screenPaddingB	= rlytDogeWeather.getPaddingBottom();
	    	
	    	int maxWidthDrawable	= screenWidth - screenPaddingR - txtvwWidth;
	    	int maxHeightDrawable	= screenHeight - screenPaddingB - txtvwHeight;
	    	
	    	Random random = new Random();
	    	
	    	// place the textview considering the screen padding
	    	// for simplification, textview width = screen width / 10
	    	// and textview height = screen height / 10
	    	txtvw.setPadding(
	    			random.nextInt(maxWidthDrawable - screenPaddingL) + screenPaddingL,
	    			random.nextInt(maxHeightDrawable - screenPaddingT) + screenPaddingT,
	    			0,
	    			0);
	    	
	    	// make the textview appear on screen
	    	rlytDogeWeather.addView(txtvw);
	    	
	    	// make the new textview the newest, and
	    	// the next one the oldest
	    	txtvwPopups[popupsRotationIndex] = txtvw;
	    	popupsRotationIndex = (popupsRotationIndex + 1) % POPUPS_MAXIMUM_ROTATION;
    	}
    }
    
    
    
    private void resetPopups()
    {
    	// if popups exist on screen, we need to remove them
    	if(txtvwPopups != null)
    	{
    		int length = txtvwPopups.length;
    		
    		// remove each popup from the layout
    		for(int i = 0; i < length; i++)
    		{
    			TextView txtvw = txtvwPopups[i];
    			
    			if(txtvw != null)
    				rlytDogeWeather.removeView(txtvw);
    		}
    	}
    		
    	// make a new textview array and reset the index
    	txtvwPopups 			= new TextView[POPUPS_MAXIMUM_ROTATION];
    	popupsRotationIndex 	= 0;
    }
    
    
    
    
    private void initView()
    {
    	// initialize resources and font
    	resources = getResources();
    	comicSans = Typeface.createFromAsset(getAssets(), COMIC_SANS_FONT_FILE);
    	
    	// get the app main layout and static textviews
    	// initialize textviews with comic sans font and random text colors
    	rlytDogeWeather				= (RelativeLayout) findViewById(R.id.LayoutDoge);
    	imgvwFront					= (ImageView) findViewById(R.id.ImgFrontDoge);
    	btnUseLocation				= (Button) findViewById(R.id.ButtonUseLocation);
    	txtvwDescription			= (TextView) findViewById(R.id.TextDescription);
    	txtvwCity					= (TextView) findViewById(R.id.TextCity);
    	txtvwTemperatureFahrenheit	= (TextView) findViewById(R.id.TextTemperatureFahrenheit);
    	txtvwTemperatureCelcius		= (TextView) findViewById(R.id.TextTemperatureCelcius);
    	
    	btnUseLocation				.setTypeface(comicSans);
    	txtvwDescription			.setTypeface(comicSans);
    	txtvwCity					.setTypeface(comicSans);
    	txtvwTemperatureCelcius		.setTypeface(comicSans);
    	txtvwTemperatureFahrenheit	.setTypeface(comicSans);
    	
    	btnUseLocation				.setTextColor(getRandomColor());
    	txtvwDescription			.setTextColor(getRandomColor());
    	txtvwCity					.setTextColor(getRandomColor());
    	int temperatureColor = getRandomColor();
    	txtvwTemperatureCelcius		.setTextColor(temperatureColor);
    	txtvwTemperatureFahrenheit	.setTextColor(temperatureColor);
    	
    	// initialize the popup array
    	resetPopups();
    }
    
    private Resources resources;
    
    private RelativeLayout rlytDogeWeather;
    private ImageView imgvwFront;
    private Button btnUseLocation;
    private TextView txtvwDescription;
    private TextView txtvwCity;
    private TextView txtvwTemperatureFahrenheit;
    private TextView txtvwTemperatureCelcius;
    
    private TextView[] txtvwPopups;
    private int popupsRotationIndex;
    private static final int POPUPS_MAXIMUM_ROTATION = 4;
    
    
    private View.OnClickListener getUserLocation = new View.OnClickListener()
    {
		@Override
		public void onClick(View v) 
		{
			synchronized (drawLock)
			{
				btnUseLocation.setText(R.string.waiting_location);
			}
			
			locationState = ButtonLocationState.WAITING;
			
			geoCoordinates = AndroidUtil.getPhoneCoordinates();
			
			// start the job that get the weather info
	        // from OpenWeatherMap (once)
	        DogeWeatherTask weatherHttpRequest = new DogeWeatherTask();
	        weatherHttpRequest.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
		}
	};
    
    
    
    private class DogeWeatherTask extends AsyncTask<Void, Void, Weather> 
    {
    	@Override
    	protected Weather doInBackground(Void... params) 
    	{
    		Weather weather = null;
    		
    		if (AndroidUtil.isNetworkAvailable())
    		{
    			try
    			{
	    			weather = (geoCoordinates == null)
	    					? WeatherUtil.getCurrentDefaultWeather()
							: WeatherUtil.getCurrentWeather(geoCoordinates.getLatitude(), geoCoordinates.getLongitude());
					
					switchLocationState(ButtonLocationState.LOCATED);
    			}
    			catch(IOException ioe)
    			{
    				switchLocationState(ButtonLocationState.NOT_LOCATED);
    			}
    		}
    		else
    		{
    			switchLocationState(ButtonLocationState.NOT_CONNECTED);
    		}

    		return weather;
    	}
    	
    	
    	@Override
    	protected void onPostExecute(Weather weather)
    	{
    		synchronized(drawLock) 
			{
	    		if (weather != null)
	    		{
					initView();
	    			
	    			dogeWeather.updateCurrentWeather(weather);
	    			fillWeatherInfos(dogeWeather);
				}
    		
				switch (locationState)
				{
					case LOCATED:
						btnUseLocation.setText(R.string.located);
						break;
						
					case NOT_CONNECTED:
						btnUseLocation.setText(R.string.not_connected);
						break;
						
					case NOT_LOCATED:
	    				btnUseLocation.setText(R.string.not_located);
						break;
						
					default:
						btnUseLocation.setText(R.string.use_my_location);
						break;
				}
    		}
    	}
    }
    
    
    
    
    private boolean runPopupGenerator;
    
    private class LexicalFieldPopupGeneratorTask extends AsyncTask<Void, Void, Void>
    {

		@Override
		protected Void doInBackground(Void... params) 
		{
			while(runPopupGenerator)
			{
				publishProgress();
				
				// sleep 2.5s
				try { Thread.sleep(2500); }
	    		catch(Exception exc) { }
			}
			
			return (Void) null;
		}
    
		
		
		@Override
    	protected void onProgressUpdate(Void... progress) 
    	{
			synchronized (drawLock)
			{
				makeRandomPopup(dogeWeather);
			}
		}
    }
    
    
    
    
    private enum ButtonLocationState
    {
    	DEFAULT,
    	WAITING,
    	NOT_CONNECTED,
    	NOT_LOCATED,
    	LOCATED
    }
    
    private ButtonLocationState locationState;
    
    private void switchLocationState(ButtonLocationState locationState)
    {
    	if (locationState == ButtonLocationState.WAITING
			|| this.locationState != ButtonLocationState.DEFAULT)
    	{
    		this.locationState = locationState;
    	}
    }
}
