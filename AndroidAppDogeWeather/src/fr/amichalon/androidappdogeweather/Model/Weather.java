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
	
	private float latitude;
	
	private float longitude;
	
	private int temperatureKelvin;

	public Weather (
			String city,
			String description,
			String iconId,
			float latitude,
			float longitude,
			int temperatureKelvin
			)
	{
		this.city = city;
		this.description = description;
		this.iconId = iconId;
		this.latitude = latitude;
		this.longitude = longitude;
		this.temperatureKelvin = temperatureKelvin;
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
	
	public float getLatitude()
	{
		return latitude;
	}
	
	public float getLongitude()
	{
		return longitude;
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
