
package fr.amichalon.androidappdogeweather.model;

/**
 * Model class for geographic coordinates. It only contains latitude and 
 * longitude data. Generally built with phone coordinate data.
 * 
 * @author alexandre.michalon
 */
public class GeoCoordinates 
{
	
	/**
	 * Paris latitude according to 
	 * <a href="http://openweathermap.org/help/city_list.txt">OWM cities list</a>
	 */
	private final static double PARIS_LATITUDE = 48.853409;
	
	
	/**
	 * Paris longitude according to
	 * <a href="http://openweathermap.org/help/city_list.txt">OWM cities list</a>
	 */
	private final static double PARIS_LONGITUDE = 2.348800;
	
	
	
	
	
	/**
	 * Build an object that contains geographic coordinates.
	 * 
	 * @param latitude
	 * 		The latitude.
	 * 
	 * @param longitude
	 * 		The longitude.
	 */
	public GeoCoordinates(
			double latitude,
			double longitude
			)
	{
		this.latitude 	= latitude;
		this.longitude 	= longitude;
	}
	
	
	/**
	 * Default constructor of an object that contains geographic coordinates.
	 * The default latitude and longitude are set to Paris.
	 */
	public GeoCoordinates()
	{
		this(PARIS_LATITUDE, PARIS_LONGITUDE);
	}
	
	
	
	
	
	/**
	 * The latitude.
	 */
	private double latitude;
	
	
	/**
	 * The longitude.
	 */
	private double longitude;
	
	
	
	
	
	/**
	 * Get the latitude.
	 * 
	 * @return A double.
	 * 
	 * @see GeoCoordinates#latitude
	 */
	public double getLatitude()
	{
		return latitude;
	}
	
	
	/**
	 * Get the longitude.
	 * 
	 * @return a double.
	 * 
	 * @see GeoCoordinates#longitude
	 */
	public double getLongitude()
	{
		return longitude;
	}
	
	
	
	
	
	/**
	 * Get the default geographic coordinates, i.e. Paris geographic coordinates.
	 * 
	 * @return the default GeoCoordinates.
	 * 
	 * @see GeoCoordinates#GeoCoordinates()
	 */
	public static GeoCoordinates getDefault()
	{
		return new GeoCoordinates();
	}
}
