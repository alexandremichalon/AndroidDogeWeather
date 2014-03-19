
package fr.amichalon.androidappdogeweather.model;

/**
 * Model class for a weather retrieved by OpenWeatherMap API.
 * We select only a few of the data retrieved.
 * 
 * @author alexandre.michalon
 */
public class Weather 
{	

	/**
	 * Construct a weather object that describe the current weather.
	 * 
	 * @param city
	 * 		The name of the city.
	 * 
	 * @param description
	 * 		The description of the current weather.
	 * 
	 * @param iconId
	 * 		The ID of the icon associated with the current weather.
	 * 
	 * @param latitude
	 * 		The latitude used to retrieve the current weather.
	 * 
	 * @param longitude
	 * 		The longitude used to retrieve the current weather.
	 * 
	 * @param temperatureKelvin
	 * 		The current temperature in Kelvin.
	 */
	public Weather (
			String city,
			String description,
			String iconId,
			double latitude,
			double longitude,
			int temperatureKelvin
			)
	{
		this.city				= city;
		this.description		= description;
		this.iconId				= iconId;
		this.geoCoordinates		= new GeoCoordinates(latitude, longitude);
		this.temperatureKelvin	= temperatureKelvin;
	}
	
	
	/**
	 * Construct a weather object that describe the current weather.
	 * 
	 * @param city
	 * 		The name of the city.
	 * 
	 * @param description
	 * 		The description of the current weather.
	 * 
	 * @param iconId
	 * 		The ID of the icon associated with the current weather.
	 * 
	 * @param geoCoordinates
	 * 		The geographic coordinates used to retrieve the current weather.
	 * 
	 * @param temperatureKelvin
	 * 		The current temperature in Kelvin.
	 */
	public Weather (
			String city,
			String description,
			String iconId,
			GeoCoordinates geoCoordinates,
			int temperatureKelvin
			)
	{
		this.city				= city;
		this.description		= description;
		this.iconId				= iconId;
		this.geoCoordinates		= geoCoordinates;
		this.temperatureKelvin	= temperatureKelvin;
	}
	
	
	
	
	
	/**
	 * The name of the nearest city that gives access to its weather data,
	 * according to OWM.
	 */
	private String city;
	
	
	/**
	 * The description of the current weather, according to OWM.
	 */
	private String description;
	
	
	/**
	 * The icon ID associated with a weather in OWM API. We use it to select
	 * an icon in our resources.
	 */
	private String iconId;
	
	
	/**
	 * The geographic coordinates (latitude and longitude) sent to OWM to
	 * retrieve the current weather. If geolocalization is disabled, the default
	 * is Paris.
	 */
	private GeoCoordinates geoCoordinates;
	
	
	/**
	 * The current temperature in Kelvin, according to OWM.
	 */
	private int temperatureKelvin;
	
	
	
	
	
	/**
	 * Get the name of the nearest city that gives access to its weather data, 
	 * according to OWM.
	 * 
	 * @return A string which is retrieved by OWM.
	 * 
	 * @see Weather#city
	 */
	public String getCity()
	{
		return city;
	}
	
	
	/**
	 * Get the description of the current weather, according to OWM.
	 * 
	 * @return A string which is retrieved by OWM.
	 * 
	 * @see Weather#description
	 */
	public String getDescription()
	{
		return description;
	}
	
	
	/**
	 * Get the icon ID associated with a weather in OWM API. We use it to 
	 * select an icon in our resources.
	 * 
	 * @return A string which is retrieved by OWM.
	 * 
	 * @see Weather#iconId
	 * @see fr.amichalon.androidappdogeweather.model.enumerations.WeatherIcon
	 */
	public String getIconId()
	{
		return iconId;
	}
	
	
	/**
	 * Get the geographic coordinates (latitude and longitude) sent to OWM to 
	 * retrieve the current weather. If geolocalization is disabled, 
	 * the default is Paris.
	 * 
	 * @return A GeoCoordinates object.
	 * 
	 * @see Weather#geoCoordinates
	 * @see GeoCoordinates
	 */
	public GeoCoordinates getGeoCoordinates()
	{
		return geoCoordinates;
	}
	
	
	/**
	 * Get the latitude sent to OWM to retrieve the current weather. It is also
	 * retrieved in the response from OWM.
	 * 
	 * @return A double which is retrieved by OWM.
	 */
	public double getLatitude()
	{
		return geoCoordinates.getLatitude();
	}
	
	
	/**
	 * Get the longitude sent to OWM to retrieve the current weather. It is also
	 * retrieved in the response from OWM.
	 * 
	 * @return A double which is retrieved by OWM.
	 */
	public double getLongitude()
	{
		return geoCoordinates.getLongitude();
	}
	
	
	/**
	 * Get the current temperature in Celcius degrees according to OWM.
	 * 
	 * <p><i>
	 * The temperature in °C is calculated from the temperature in K with the
	 * formula :<br/> TempCelcius = TempKelvin - 273
	 * </i></p>
	 * 
	 * @return An integer which is calculated from OWM.
	 * 
	 * @see Weather#temperatureKelvin
	 */
	public int getTemperatureCelcius()
	{
		return temperatureKelvin - 273;
	}
	
	
	/**
	 * Get the current temperature in Fahrenheit degrees according to OWM.
	 * 
	 * <p><i>
	 * The temperature in °F is calculated from the temperature in K with the
	 * formula :<br/> TempFahrenheit = TempKelvin * 9 / 5 - 459.67
	 * </i></p>
	 * 
	 * @return An integer which is calculated from OWM.
	 * 
	 * @see Weather#temperatureKelvin
	 */
	public int getTemperatureFahrenheit()
	{
		return (int) (temperatureKelvin * 1.8 - 459.67);
	}
}
