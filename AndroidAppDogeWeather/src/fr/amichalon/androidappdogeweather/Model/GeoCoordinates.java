/**
 * 
 */
package fr.amichalon.androidappdogeweather.Model;

/**
 * @author alexandre.michalon
 *
 */
public class GeoCoordinates 
{
	private final static double PARIS_LATITUDE = 48.853409;
	
	private final static double PARIS_LONGITUDE = 2.348800;
	
	
	
	
	public GeoCoordinates(
			double latitude,
			double longitude
			)
	{
		this.latitude 	= latitude;
		this.longitude 	= longitude;
	}
	
	public GeoCoordinates()
	{
		this(PARIS_LATITUDE, PARIS_LONGITUDE);
	}
	
	
	
	
	private double latitude;
	
	private double longitude;
	
	
	
	
	public double getLatitude()
	{
		return latitude;
	}
	
	public double getLongitude()
	{
		return longitude;
	}
	
	
	
	
	public static GeoCoordinates getDefault()
	{
		return new GeoCoordinates();
	}
}
