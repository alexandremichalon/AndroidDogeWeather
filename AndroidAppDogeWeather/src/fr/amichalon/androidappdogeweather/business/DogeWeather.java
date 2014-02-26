/**
 * 
 */
package fr.amichalon.androidappdogeweather.business;

import fr.amichalon.androidappdogeweather.model.GeoCoordinates;
import fr.amichalon.androidappdogeweather.model.Weather;
import fr.amichalon.androidappdogeweather.model.enumerations.WeatherIcon;

/**
 * @author alexandre.michalon
 *
 */
public class DogeWeather 
{
	
	public DogeWeather(Weather weather)
	{
		updateCurrentWeather(weather);
	}
	
	
	public DogeWeather()
	{
		this(null);
	}
	

	private Weather weather;
	
	private WeatherIcon weatherIcon;
	
	private String[] lexicalField;
	
	
	
	public double getLatitude()
	{
		if (weather != null)
			return weather.getLatitude();
		
		else
			return GeoCoordinates.getDefault().getLatitude();
	}
	
	
	
	public double getLongitude()
	{
		if (weather != null)
			return weather.getLongitude();
		
		else
			return GeoCoordinates.getDefault().getLongitude();
	}
	
	
	
	public String getDescription()
	{
		if(weather != null)
			return weather.getDescription();
		
		else
			return "doge weather";
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
			return WeatherIcon.DEFAULT.getBackImageId();
	}
	
	
	
	public int getFrontImageId()
	{
		if(weatherIcon != null)
			return weatherIcon.getFrontImageId();
		
		else 
			return WeatherIcon.DEFAULT.getFrontImageId();
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
