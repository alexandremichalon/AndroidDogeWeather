/**
 * 
 */
package fr.amichalon.androidappdogeweather.Business;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONObject;

import android.util.Log;
import fr.amichalon.androidappdogeweather.Model.GeoCoordinates;
import fr.amichalon.androidappdogeweather.Model.Weather;

/**
 * @author alexandre.michalon
 *
 */
public final class WeatherUtil 
{
	
	private final static String OWM_URL = "http://api.openweathermap.org/data/2.5/weather?lat=%f&lon=%f"; 

	private static String getCurrentOWMDatas(double latitude, double longitude)
	{
		HttpURLConnection connection	= null;
		InputStream response			= null;
		
		String request					= String.format(Locale.US, OWM_URL, latitude, longitude);
		
		try
		{
			URL requestUrl = new URL(request);
			
			connection = (HttpURLConnection) requestUrl.openConnection();
			connection.setRequestMethod("GET");             
			connection.setDoInput(true);             
			connection.setDoOutput(true);
			connection.setConnectTimeout(5000);
			connection.connect();
			
			
			response							= connection.getInputStream();
			InputStreamReader responseReader	= new InputStreamReader(response);
			BufferedReader brdr					= new BufferedReader(responseReader);
			
			StringBuilder sb	= new StringBuilder();
			String line			= null;
			while ((line = brdr.readLine()) != null)
			{
				sb.append(line);
			}
			
			response.close();
			connection.disconnect();
			
			return sb.toString();
		}
		catch(Throwable t)
		{
			Log.e("DogeWeather : WeatherUtil#getCurrentOWMDatas(double, double)", "Connection", t);
		}
		finally
		{
			try { response.close(); } catch (Throwable t) { }
			try { connection.disconnect(); } catch (Throwable t) { }
		}
		
		return null;
	}
	
	
	
	public static Weather getCurrentWeather(double latitude, double longitude)
	{
		String datas	= getCurrentOWMDatas(latitude, longitude);
		
		Weather weather	= null;
		
		try
		{
			JSONObject rootJson	= new JSONObject(datas);
			
			JSONObject coordJson	= rootJson.getJSONObject("coord");
			double latitudeJson		= coordJson.getDouble("lat");
			double longitudeJson	= coordJson.getDouble("lon");
			
			String cityJson = rootJson.getString("name");
			
			JSONArray weatherArrayJson	= rootJson.getJSONArray("weather");
			JSONObject weatherJson		= weatherArrayJson.getJSONObject(0);
			String descriptionJson		= weatherJson.getString("description");
			String iconIdJson			= weatherJson.getString("icon");
			
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
			Log.e("DogeWeather : WeatherUtil#getCurrentWeather(double, double)", "Parsing JSON", t);
		}
		
		return weather;
	}
	
	
	
	public static Weather getCurrentDefaultWeather() 
	{
		GeoCoordinates defaultCoordinates = GeoCoordinates.getDefault();
		
		return getCurrentWeather(
				defaultCoordinates.getLatitude(), 
				defaultCoordinates.getLongitude()
				);
	}
}
