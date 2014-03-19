
package fr.amichalon.androidappdogeweather.business;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONObject;

import android.util.Log;
import fr.amichalon.androidappdogeweather.BuildConfig;
import fr.amichalon.androidappdogeweather.model.GeoCoordinates;
import fr.amichalon.androidappdogeweather.model.Weather;

/**
 * Business class that gives methods to retrieve the current weather by
 * interrogating the Open Weather Map API. Much inspired by the 
 * <a href="http://www.survivingwithandroid.com/2013/05/build-weather-app-json-http-android.html">
 * Surviving with Android tutorial</a>.
 * 
 * @author alexandre.michalon
 */
public final class WeatherUtil 
{
	
	/**
	 * The URL used to request the current weather.
	 */
	private final static String OWM_URL = "http://api.openweathermap.org/data/2.5/weather?lat=%f&lon=%f"; 

	
	
	
	
	/**
	 * Use the Open Weather Map API to get the current weather data. The data
	 * are in a JSON form that must be parsed. An exception is thrown in case
	 * of connection error (missing, timeout...).
	 * 
	 * @param latitude
	 * 		The latitude of the place we want the current weather.
	 * 
	 * @param longitude
	 * 		The longitude of the place we want the current weather.
	 * 
	 * @return
	 * 		A JSON String containing current weather data retrieved from OWM.
	 * 
	 * @throws IOException
	 * 		Thrown in case of connection issue.
	 */
	private static String getCurrentOWMDatas(double latitude, double longitude)
			throws IOException
	{
		HttpURLConnection connection	= null;
		InputStream response			= null;
		
		String request					= String.format(Locale.US, OWM_URL, latitude, longitude);
		
		try
		{
			URL requestUrl = new URL(request);
			
			// open a connection to perform a http get
			// with a timeout of 5s on the OWM url.
			connection = (HttpURLConnection) requestUrl.openConnection();
			connection.setRequestMethod("GET");             
			connection.setDoInput(true);             
			connection.setDoOutput(true);
			connection.setConnectTimeout(5000);
			connection.connect();
			
			// get the response from OWM.
			response							= connection.getInputStream();
			InputStreamReader responseReader	= new InputStreamReader(response);
			BufferedReader brdr					= new BufferedReader(responseReader);
			
			// write the full response in a string to pass it to the parser.
			StringBuilder sb	= new StringBuilder();
			String line			= null;
			while ((line = brdr.readLine()) != null)
			{
				sb.append(line);
			}
			
			// close the connection
			response.close();
			connection.disconnect();
			
			return sb.toString();
		}
		catch(IOException ioe)
		{
			if (BuildConfig.DEBUG)
				Log.i("DogeWeather : WeatherUtil#getCurrentOWMDatas(double, double)", "Connection", ioe);
			
			throw ioe;
		}
		catch(Throwable t)
		{
			if (BuildConfig.DEBUG)
				Log.e("DogeWeather : WeatherUtil#getCurrentOWMDatas(double, double)", "Requesting OpenWeatehrMap", t);
		}
		finally
		{
			try { response.close(); } catch (Throwable t) { }
			try { connection.disconnect(); } catch (Throwable t) { }
		}
		
		return null;
	}
	
	
	/**
	 * Get the current weather data from OWM API and parse the result to
	 * return a Weather object containing the current weather. An exception is
	 * thrown if we can't build the Weather object due to connection issues.
	 * 
	 * @param latitude
	 * 		The latitude of the place we want the current weather.
	 * 
	 * @param longitude
	 * 		The longitude of the place we want the current weather.
	 * 
	 * @return
	 * 		A Weather object containing the current weather data, null in case
	 * 		of parsing error.
	 * 
	 * @throws IOException
	 * 		Thrown in case of connection issue.
	 */
	public static Weather getCurrentWeather(double latitude, double longitude) 
			throws IOException
	{
		// get the current weather data (JSON)
		String datas	= getCurrentOWMDatas(latitude, longitude);
		
		Weather weather	= null;
		
		// parsing the JSON. format is like
		// {
		// "coord":{"lon":12.4958,"lat":41.903},
		// "sys":{"country":"Italy","sunrise":1369107818,"sunset":1369160979},
		// "weather":[{
		// 		"id":802,"main":"Clouds","description":"scattered clouds",
		// 		"icon":"03d"}],
		// "base":"global stations",
		// "main":{
		// 		"temp":290.38,
		// 		"humidity":68,
		// 		"pressure":1015,
		// 		"temp_min":287.04,
		// 		"temp_max":293.71},
		// "wind":{ 
		// 		"speed":1.75,
		// 		"deg":290.002},
		// "clouds":{"all":32},
		// "dt":1369122932,
		// "id":3169070,
		// "name":"Rome",
		// "cod":200
		// }
		try
		{
			JSONObject rootJson	= new JSONObject(datas);
			
			// get latitude and longitude
			JSONObject coordJson	= rootJson.getJSONObject("coord");
			double latitudeJson		= coordJson.getDouble("lat");
			double longitudeJson	= coordJson.getDouble("lon");
			
			// get city
			String cityJson = rootJson.getString("name");
			
			// get weather description and icon ID
			JSONArray weatherArrayJson	= rootJson.getJSONArray("weather");
			JSONObject weatherJson		= weatherArrayJson.getJSONObject(0);
			String descriptionJson		= weatherJson.getString("description");
			String iconIdJson			= weatherJson.getString("icon");
			
			// get temperature in Kelvin
			JSONObject mainJson	= rootJson.getJSONObject("main");
			int temperatureJson	= (int) mainJson.getDouble("temp");
			
			weather = new Weather(
					cityJson, 
					descriptionJson, 
					iconIdJson, 
					latitudeJson, 
					longitudeJson, 
					temperatureJson
					);
		}
		catch (Throwable t)
		{
			if (BuildConfig.DEBUG)
				Log.e("DogeWeather : WeatherUtil#getCurrentWeather(double, double)", "Parsing JSON", t);
		}
		
		return weather;
	}
	
	
	/**
	 * Get the current weather in the default city, which is Paris. An 
	 * exception is thrown if we can't build the Weather object due to
	 * connection issues.
	 * 
	 * @return
	 * 		A Weather object containing the current weather data in Paris, null 
	 * 		in case of parsing error.
	 * 
	 * @throws IOException
	 * 		Thrown in case of connection issue.
	 */
	public static Weather getCurrentDefaultWeather() 
			throws IOException 
	{
		// get Paris geographic coordinates
		GeoCoordinates defaultCoordinates = GeoCoordinates.getDefault();
		
		return getCurrentWeather(
				defaultCoordinates.getLatitude(), 
				defaultCoordinates.getLongitude()
				);
	}
}
