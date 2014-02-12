
package fr.amichalon.androidappdogeweather.Models.Enumerations;

import fr.amichalon.androidappdogeweather.R;

/**
 * @author alexandre.michalon
 *
 * Enumeration that associate OpenWeatherMap weather icon IDs
 * with their lexical field in the resources.
 */
public enum WeatherIcon {
	
	
	CLEAR_SKY_DAY			("01d", R.array.clear_sky_day, 			R.drawable.front_01d, R.drawable.back_01d),
	CLEAR_SKY_NIGHT			("01n", R.array.clear_sky_night, 		R.drawable.front_01n, R.drawable.back_01n),
	FEW_CLOUDS_DAY			("02d", R.array.few_clouds_day, 		R.drawable.front_02d, R.drawable.back_02d),
	FEW_CLOUDS_NIGHT		("02n", R.array.few_clouds_night, 		R.drawable.front_02n, R.drawable.back_02n),
	SCATTERED_CLOUDS_DAY	("03d", R.array.scattered_clouds_day, 	R.drawable.front_03d, R.drawable.back_03d),
	SCATTERED_CLOUDS_NIGHT	("03n", R.array.scattered_clouds_night, R.drawable.front_03n, R.drawable.back_03n),
	BROKEN_CLOUDS_DAY		("04d", R.array.broken_clouds_day, 		R.drawable.front_04d, R.drawable.back_04d),
	BROKEN_CLOUDS_NIGHT		("04n", R.array.broken_clouds_night, 	R.drawable.front_04n, R.drawable.back_04n),
	SHOWER_RAIN_DAY			("09d", R.array.shower_rain_day, 		R.drawable.front_09d, R.drawable.back_09d),
	SHOWER_RAIN_NIGHT		("09n", R.array.shower_rain_night, 		R.drawable.front_09n, R.drawable.back_09n),
	RAIN_DAY				("10d", R.array.rain_day, 				R.drawable.front_10d, R.drawable.back_10d),
	RAIN_NIGHT				("10n", R.array.rain_night, 			R.drawable.front_10n, R.drawable.back_10n),
	THUNDERSTORM_DAY		("11d", R.array.thunderstorm_day, 		R.drawable.front_11d, R.drawable.back_11d),
	THUNDERSTORM_NIGHT		("11n", R.array.thunderstorm_night, 	R.drawable.front_11n, R.drawable.back_11n),
	SNOW_DAY				("13d", R.array.snow_day, 				R.drawable.front_13d, R.drawable.back_13d),
	SNOW_NIGHT				("13n", R.array.snow_night, 			R.drawable.front_13n, R.drawable.back_13n),
	MIST_DAY				("50d", R.array.mist_day, 				R.drawable.front_50d, R.drawable.back_50d),
	MIST_NIGHT				("50n", R.array.mist_night, 			R.drawable.front_50n, R.drawable.back_50n);
		
	
	
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
	WeatherIcon(
			String iconId, 
			int lexicalFieldId, 
			int frontImageId, 
			int backImageId
			)
	{
		this.iconId 			= iconId;
		this.lexicalFieldId 	= lexicalFieldId;
		this.frontImageId 		= frontImageId;
		this.backImageId 		= backImageId;
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
	
	private int frontImageId;
	
	private int backImageId;
	
	
	
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
	
	
	public int getFrontImageId()
	{
		return frontImageId;
	}
	
	
	public int getBackImageId()
	{
		return backImageId;
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
	
	
	
	/**
	 * Get the WeatherIcon associated with an icon ID.
	 * 
	 * @param iconId
	 * 			The icon ID. 
	 * @return
	 * 			The WeatherIcon.
	 */
	public static WeatherIcon getWeatherIcon(String iconId)
	{
		for(WeatherIcon icon : WeatherIcon.values())
		{
			if(iconId.equals(icon.getIconId()))
			{
				return icon;
			}
		}
		
		throw new IllegalArgumentException(iconId + " is not a valid icon ID.");
	}
}
