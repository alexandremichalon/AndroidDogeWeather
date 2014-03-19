
package fr.amichalon.androidappdogeweather.business;

import fr.amichalon.androidappdogeweather.model.GeoCoordinates;
import fr.amichalon.androidappdogeweather.model.Weather;
import fr.amichalon.androidappdogeweather.model.enumerations.WeatherIcon;

/**
 * Business class transforming the current weather so it is readable by
 * a doge. Very transforming !
 * 
 * @author alexandre.michalon
 */
public class DogeWeather 
{
	
	/**
	 * Construct a DogeWeather by taking a weather and making it more doge.
	 * 
	 * @param weather
	 * 		The current weather.
	 */
	public DogeWeather(Weather weather)
	{
		updateCurrentWeather(weather);
	}
	
	
	/**
	 * Construct a default DogeWeather, such sad because no weather.
	 */
	public DogeWeather()
	{
		this(null);
	}
	

	
	
	
	/**
	 * The current weather.
	 */
	private Weather weather;
	
	
	/**
	 * The weather icon associated with the current weather, used to get
	 * lexical field, background and front image in the app.
	 */
	private WeatherIcon weatherIcon;
	
	
	/**
	 * The full lexical field of the current weather, retrieved from
	 * the resources.
	 */
	private String[] lexicalField;
	
	
	
	
	
	/**
	 * Get the latitude used to retrieve the current weather, or Paris
	 * latitude if there is no current weather..
	 * 
	 * @return
	 * 		A double.
	 */
	public double getLatitude()
	{
		if (weather != null)
			return weather.getLatitude();
		
		else
			return GeoCoordinates.getDefault().getLatitude();
	}
	
	
	/**
	 * Get the longitude used to retrieve the current weather, or Paris
	 * longitude if there is no current weather.
	 * 
	 * @return
	 * 		A double.
	 */
	public double getLongitude()
	{
		if (weather != null)
			return weather.getLongitude();
		
		else
			return GeoCoordinates.getDefault().getLongitude();
	}
	
	
	/**
	 * Get the description of the current weather, or "doge weather" if there
	 * is no current weather.
	 * 
	 * @return
	 * 		A String.
	 */
	public String getDescription()
	{
		if(weather != null)
			return weather.getDescription();
		
		else
			return "doge weather";
	}
	
	
	/**
	 * Get the name of the city of the current weather, or "wow city" if there
	 * is no current weather.
	 * 
	 * @return
	 * 		A String.
	 */
	public String getCity()
	{
		if (weather != null)
			return weather.getCity();
		
		else
			return "wow city";
	}
	
	
	/**
	 * Get the current temperature in °C, or 0 if there is no current weather.
	 * 
	 * @return
	 * 		An integer.
	 */
	public int getTemperatureCelcius()
	{
		if (weather != null)
			return weather.getTemperatureCelcius();
		
		else
			return 0;
	}
	
	
	/**
	 * Get the current temperature in °F, or 0 if there is no current weather.
	 * 
	 * @return
	 */
	public int getTemperatureFahrenheit()
	{
		if (weather != null)
			return weather.getTemperatureFahrenheit();
		
		else
			return 0;
	}
	
	
	/**
	 * Get the background image ID of the current weather in the resources, 
	 * or the default background image ID if there is no current weather.
	 * 
	 * @return
	 * 		An integer that represent an ID.
	 */
	public int getBackImageId()
	{
		if(weatherIcon != null)
			return weatherIcon.getBackImageId();
		
		else 
			return WeatherIcon.DEFAULT.getBackImageId();
	}
	
	
	/**
	 * Get the front image ID of the current weather in the resources, 
	 * or the default front image ID if there is no current weather.
	 * 
	 * @return
	 */
	public int getFrontImageId()
	{
		if(weatherIcon != null)
			return weatherIcon.getFrontImageId();
		
		else 
			return WeatherIcon.DEFAULT.getFrontImageId();
	}
	
	
	/**
	 * Get the full lexical field of the current weather.
	 * 
	 * @return
	 * 		An array of String taken from the resources files.
	 * 
	 * @see DogeWeather#lexicalField
	 */
	public String[] getLexicalField()
	{
		return lexicalField;
	}
	
	
	
	
	
	/**
	 * Make a Weather the current weather. Also build the lexical field of
	 * the new current weather.
	 * 
	 * @param weather
	 * 		The weather that becomes the new current weather.
	 */
	public void updateCurrentWeather(Weather weather)
	{
		this.weather = weather;
		
		if (weather != null)
			weatherIcon = WeatherIcon.getWeatherIcon(weather.getIconId());
		
		buildLexicalField();
	}
	
	
	
	/**
	 * Browse lexical field resources from the ActivityDogeWeather resources
	 * to build the full lexical field of the current weather.
	 */
	private void buildLexicalField()
	{
		String[] lfAll = AndroidUtil.getLexicalFieldAll();
		
		
		// without weatherIcon, we take the default lexical field
		if (weatherIcon == null
				|| weather == null)
		{
			String[] lfWeather	= AndroidUtil.getLexicalFieldDefault();
			
			int allIndex		= 0;
			int weatherIndex	= 0;
			int wordIndex		= 0;
			int lfAllLength		= lfAll.length;
			int lfWeatherLength	= lfWeather.length;
			int lfLength		= lfAllLength + lfWeatherLength;
			
			
			lexicalField		= new String[lfLength];
			
			while (allIndex < lfAllLength
					&&	wordIndex < lfLength)
			{
				lexicalField[wordIndex] = lfAll[allIndex];
				
				wordIndex++;
				allIndex++;
			}
			
			
			while (weatherIndex < lfWeatherLength
					&&	wordIndex < lfLength)
			{
				lexicalField[wordIndex] = lfWeather[weatherIndex];
				
				wordIndex++;
				weatherIndex++;
			}
		}
		// the weatherIcon gives the ID of the lexical field associated with a weather
		// the weather gives the temperature, we add associated temperature lexical field
		else
		{
			String[] lfWeather		= AndroidUtil.getStringArray(weatherIcon.getLexicalFieldId());
			String[] lfTemperature	= getTemperatureLexicalField();

			int allIndex			= 0;
			int weatherIndex		= 0;
			int temperatureIndex	= 0;
			int wordIndex			= 0;
			int lfAllLength			= lfAll.length;
			int lfWeatherLength		= lfWeather.length;
			int lfTemperatureLength	= lfTemperature.length;
			int lfLength			= lfAllLength + lfWeatherLength + lfTemperatureLength;
			
			lexicalField			= new String[lfLength];
			
			while (allIndex < lfAllLength
					&&	wordIndex < lfLength)
			{
				lexicalField[wordIndex] = lfAll[allIndex];
				
				wordIndex++;
				allIndex++;
			}
			
			
			while (weatherIndex < lfWeatherLength
					&&	wordIndex < lfLength)
			{
				lexicalField[wordIndex] = lfWeather[weatherIndex];
				
				wordIndex++;
				weatherIndex++;
			}
			
			
			while (temperatureIndex < lfTemperatureLength
					&&	wordIndex < lfLength)
			{
				lexicalField[wordIndex] = lfTemperature[temperatureIndex];
				
				wordIndex++;
				temperatureIndex++;
			}
		}
	}
	
	
	
	
	/**
	 * Get the lexical field associated with a temperature interval
	 * in the resources of the ActivityDogeWeather.
	 * 			
	 * @return
	 * 			A String array that contains the lexical field of a temperature interval
	 */
	private String[] getTemperatureLexicalField()
	{
		int temperature = weather.getTemperatureCelcius();
		
		if (temperature <= -30)
			return AndroidUtil.getInferiorToMinus30();
		
		else if (-30 < temperature && temperature <= -15)
			return AndroidUtil.getBetweenMinus30AndMinus15();
		
		else if (-15 < temperature && temperature <= -7)
			return AndroidUtil.getBetweenMinus15AndMinus7();
		
		else if (-7 < temperature && temperature <= 0)
			return AndroidUtil.getBetweenMinus7And0();
		
		else if (0 < temperature && temperature <= 10)
			return AndroidUtil.getBetween0And10();
		
		else if (10 < temperature && temperature <= 20)
			return AndroidUtil.getBetween10And20();
		
		else if (20 < temperature && temperature <= 30)
			return AndroidUtil.getBetween20And30();
		
		// temperature > 30
		else
			return AndroidUtil.getSuperiorTo30();
	}
}
