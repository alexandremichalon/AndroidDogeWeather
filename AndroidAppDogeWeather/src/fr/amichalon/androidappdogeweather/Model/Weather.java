/**
 * 
 */
package fr.amichalon.androidappdogeweather.Model;

/**
 * @author alexandre.michalon
 *
 */
public class Weather {
	
	private String city;
	
	private String description;
	
	private String iconId;
	
	private GeoCoordinates geoCoordinates;
	
	private int temperatureKelvin;

	public Weather (
			String city,
			String description,
			String iconId,
			double latitude,
			double longitude,
			int temperatureKelvin
			)
	{
		this.city 				= city;
		this.description 		= description;
		this.iconId 			= iconId;
		this.geoCoordinates 	= new GeoCoordinates(latitude, longitude);
		this.temperatureKelvin 	= temperatureKelvin;
	}
	
	public Weather (
			String city,
			String description,
			String iconId,
			GeoCoordinates geoCoordinates,
			int temperatureKelvin
			)
	{
		this.city 				= city;
		this.description 		= description;
		this.iconId 			= iconId;
		this.geoCoordinates 	= geoCoordinates;
		this.temperatureKelvin 	= temperatureKelvin;
	}
	
	public String getCity()
	{
		return city;
	}
	
	public String getDescription()
	{
		return description;
	}
	
	public String getIconId()
	{
		return iconId;
	}
	
	public GeoCoordinates getGeoCoordinates()
	{
		return geoCoordinates;
	}
	
	public double getLatitude()
	{
		return geoCoordinates.getLatitude();
	}
	
	public double getLongitude()
	{
		return geoCoordinates.getLongitude();
	}
	
	public int getTemperatureCelcius()
	{
		return temperatureKelvin - 273;
	}
	
	public int getTemperatureFahrenheit()
	{
		return (int)(temperatureKelvin * 1.8 - 459.67);
	}
}
