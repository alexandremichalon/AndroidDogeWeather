
package fr.amichalon.androidappdogeweather.Models.Enumerations;

import fr.amichalon.androidappdogeweather.R;

/**
 * @author alexandre.michalon
 *
 * Enumeration that associate OpenWeatherMap weather icon IDs
 * with their lexical field in the resources.
 */
public enum WeatherIcon {
	
	
	CLEAR_SKY_DAY			("01d", R.array.clear_sky_day),
	CLEAR_SKY_NIGHT			("01n", R.array.clear_sky_night),
	FEW_CLOUDS_DAY			("02d", R.array.few_clouds_day),
	FEW_CLOUDS_NIGHT		("02n", R.array.few_clouds_night),
	SCATTERED_CLOUDS_DAY	("03d", R.array.scattered_clouds_day),
	SCATTERED_CLOUDS_NIGHT	("03n", R.array.scattered_clouds_night),
	BROKEN_CLOUDS_DAY		("04d", R.array.broken_clouds_day),
	BROKEN_CLOUDS_NIGHT		("04n", R.array.broken_clouds_night),
	SHOWER_RAIN_DAY			("09d", R.array.shower_rain_day),
	SHOWER_RAIN_NIGHT		("09n", R.array.shower_rain_night),
	RAIN_DAY				("10d", R.array.rain_day),
	RAIN_NIGHT				("10n", R.array.rain_night),
	THUNDERSTORM_DAY		("11d", R.array.thunderstorm_day),
	THUNDERSTORM_NIGHT		("11n", R.array.thunderstorm_night),
	SNOW_DAY				("13d", R.array.snow_day),
	SNOW_NIGHT				("13n", R.array.snow_night),
	MIST_DAY				("50d", R.array.mist_day),
	MIST_NIGHT				("50n", R.array.mist_night);
		
	
	
	/**
	 * Default constructor
	 * 
	 * @param iconId
	 * 			The OpenWeatherMap Id of the WeatherIcon.
	 * 
	 * @param lexicalFieldId
	 * 			The ID of the lexical field associated with a weather
	 * 			in the resource files.
	 */
	WeatherIcon(String iconId, int lexicalFieldId)
	{
		this.iconId 			= iconId;
		this.lexicalFieldId 	= lexicalFieldId;
	}
	
	
	
	/**
	 * The OpenWeatherMap Id of the WeatherIcon.
	 * It is used to reference the icon of a weather.
	 */
	private String iconId;
	
	/**
	 * The ID of the lexical field associated with a weather
	 * in the resource files.
	 */
	private int lexicalFieldId;
	
	
	
	/**
	 * <p>
	 * 	Get the OpenWeatherMap Id of the WeatherIcon.
	 * 	It is used to reference the icon of a weather.
	 * </p>
	 * 
	 * <p>Ex : "01d" stands for the clear sky weather icon ID</p>
	 * 
	 * @return a String that is the OpenWeatherMap Id of the WeatherIcon
	 *
	 * @see WeatherIcon#iconId
	 */
	public String getIconId()
	{
		return iconId;
	}
	
	
	
	/**
	 * <p>
	 * 	Get the ID of the Android resource associated with
	 * 	the weather. The resource is a String Array containing the
	 * 	lexical field associated with a weather.
	 * </p>
	 * 
	 * @return an integer that is the ID of the Android resource
	 * 
	 * @see WeatherIcon#lexicalFieldId
	 */
	public int getLexicalFieldId()
	{
		return lexicalFieldId;
	}
	
	
	
	/**
	 * <p>
	 * 	Get the OpenWeatherMap Id of the WeatherIcon.
	 * 	It is used to reference the icon of a weather.
	 * </p>
	 * 
	 * <p>Ex : "01d" stands for the clear sky weather icon ID</p>
	 * 
	 * @return a String that is the OpenWeatherMap Id of the WeatherIcon
	 * 
	 * @see WeatherIcon#iconId
	 */
	public String toString()
	{
		return iconId;
	}
	
}
