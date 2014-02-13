/**
 * 
 */
package fr.amichalon.androidappdogeweather.Business;

import fr.amichalon.androidappdogeweather.Model.Weather;
import fr.amichalon.androidappdogeweather.Models.Enumerations.WeatherIcon;
import android.app.Activity;
import android.content.res.Resources;

/**
 * @author alexandre.michalon
 *
 */
public class DogeWeather {
	
	private DogeWeather(
			Activity activity,
			Weather weather
			)
	{
		this.activityDogeWeather = activity;
		
		if (activityDogeWeather != null)
			this.resources = activityDogeWeather.getResources();
		
		updateCurrentWeather(weather);
	}
	
	
	public DogeWeather(Activity activity)
	{
		this(activity, null);
	}
	

	private Weather weather;
	
	private WeatherIcon weatherIcon;
	
	private String[] lexicalField;
	
	private Activity activityDogeWeather;
	
	private Resources resources;
	
	
	
	public int getLatitude()
	{
		if (weather != null)
			return weather.getLatitude();
		
		else
			return 0;
	}
	
	
	
	public int getLongitude()
	{
		if (weather != null)
			return weather.getLongitude();
		
		else
			return 0;
	}
	
	
	
	public String getDescription()
	{
		if(weather != null)
			return weather.getDescription();
		
		else
			return "wow doge weather";
	}
	
	
	
	public String getCity()
	{
		if (weather != null)
			return weather.getCity();
		
		else
			return "wow city";
	}
	
	
	
	public int getTemperatureCelcius()
	{
		if (weather != null)
			return weather.getTemperatureCelcius();
		
		else
			return 0;
	}
	
	
	
	public int getTemperatureFahrenheit()
	{
		if (weather != null)
			return weather.getTemperatureFahrenheit();
		
		else
			return 0;
	}
	
	
	
	public int getBackImageId()
	{
		if(weatherIcon != null)
			return weatherIcon.getBackImageId();
		
		else 
			return 0;
	}
	
	
	
	public int getFrontImageId()
	{
		if(weatherIcon != null)
			return weatherIcon.getFrontImageId();
		
		else 
			return 0;
	}
	
	
	
	public String[] getLexicalField()
	{
		return lexicalField;
	}
	
	
	
	
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
		String[] lfAll = resources.getStringArray(
				fr.amichalon.androidappdogeweather.R.array.lexical_field_all);
		
		
		// without weatherIcon, we take the default lexical field
		if (weatherIcon == null
				|| weather == null)
		{
			String[] lfWeather 		= resources.getStringArray(
					fr.amichalon.androidappdogeweather.R.array.lexical_field_default);
			
			int allIndex			= 0;
			int weatherIndex		= 0;
			int wordIndex 			= 0;
			int lfAllLength 		= lfAll.length;
			int lfWeatherLength 	= lfWeather.length;
			int lfLength 			= lfAllLength + lfWeatherLength;
			
			
			lexicalField 			= new String[lfLength];
			
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
			String[] lfWeather = resources.getStringArray(weatherIcon.getLexicalFieldId());
			
			String[] lfTemperature = getTemperatureLexicalField();

			int allIndex			= 0;
			int weatherIndex		= 0;
			int temperatureIndex	= 0;
			int wordIndex 			= 0;
			int lfAllLength 		= lfAll.length;
			int lfWeatherLength 	= lfWeather.length;
			int lfTemperatureLength	= lfTemperature.length;
			int lfLength 			= lfAllLength + lfWeatherLength + lfTemperatureLength;
			
			
			lexicalField 			= new String[lfLength];
			
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
				lexicalField[wordIndex] = lfWeather[temperatureIndex];
				
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
		{
			return resources.getStringArray(
				fr.amichalon.androidappdogeweather.R.array.inferior_to_minus_30);
		}
		else if (-30 < temperature && temperature <= -15)
		{
			return resources.getStringArray(
				fr.amichalon.androidappdogeweather.R.array.between_minus_30_and_minus_15);
		}
		else if (-15 < temperature && temperature <= -7)
		{
			return resources.getStringArray(
				fr.amichalon.androidappdogeweather.R.array.between_minus_15_and_minus_7);
		}
		else if (-7 < temperature && temperature <= 0)
		{
			return resources.getStringArray(
				fr.amichalon.androidappdogeweather.R.array.between_minus_7_and_0);
		}
		else if (0 < temperature && temperature <= 10)
		{
			return resources.getStringArray(
				fr.amichalon.androidappdogeweather.R.array.between_0_and_10);
		}
		else if (10 < temperature && temperature <= 20)
		{
			return resources.getStringArray(
				fr.amichalon.androidappdogeweather.R.array.between_10_and_20);
		}
		else if (20 < temperature && temperature <= 30)
		{
			return resources.getStringArray(
				fr.amichalon.androidappdogeweather.R.array.between_20_and_30);
		}
		// temperature > 30
		else
		{
			return resources.getStringArray(
					fr.amichalon.androidappdogeweather.R.array.superior_to_30);
		}
	}
}
