/**
 * 
 */
package fr.amichalon.androidappdogeweather.Business;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONObject;

import fr.amichalon.androidappdogeweather.Model.Weather;

/**
 * @author alexandre.michalon
 *
 */
class WeatherRetriever {
	
	private static String OWM_URL = "http://api.openweathermap.org/data/2.5/weather?lat=%d&lon=%d"; 
	
	private static int PARIS_LATITUDE = 48;
	
	private static int PARIS_LONGITUDE = 2;

	static String getCurrentOWMDatas(int latitude, int longitude)
	{
		HttpURLConnection connection 	= null;
		InputStream response			= null;
		
		String request 					= String.format(OWM_URL, latitude, longitude);
		
		try
		{
			URL requestUrl = new URL(request);
			
			connection = (HttpURLConnection) requestUrl.openConnection();
			connection.setRequestMethod("GET");             
			connection.setDoInput(true);             
			connection.setDoOutput(true);             
			connection.connect();
			
			
			response 							= connection.getInputStream();
			InputStreamReader responseReader	= new InputStreamReader(response);
			BufferedReader brdr					= new BufferedReader(responseReader);
			
			StringBuilder sb 	= new StringBuilder();
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
			t.printStackTrace();
		}
		finally
		{
			try { response.close(); } catch (Throwable t) { }
			try { connection.disconnect(); } catch (Throwable t) { }
		}
		
		return null;
	}
	
	
	
	static Weather getCurrentWeather(int latitude, int longitude)
	{
		String datas 		= getCurrentOWMDatas(latitude, longitude);
		
		Weather weather 	= null;
		
		try
		{
			JSONObject rootJson = new JSONObject(datas);
			
			JSONObject coordJson 	= rootJson.getJSONObject("coord");
			int latitudeJson 		= coordJson.getInt("lat");
			int longitudeJson 		= coordJson.getInt("lon");
			
			String cityJson = rootJson.getString("name");
			
			JSONArray weatherArrayJson 	= rootJson.getJSONArray("weather");
			JSONObject weatherJson 		= weatherArrayJson.getJSONObject(0);
			String descriptionJson		= weatherJson.getString("description");
			String iconIdJson			= weatherJson.getString("icon");
			
			JSONObject mainJson 	= rootJson.getJSONObject("main");
			int temperatureJson 	= (int) mainJson.getDouble("temp");
			
			weather = new Weather(
					cityJson, 
					descriptionJson, 
					iconIdJson, 
					latitudeJson, 
					longitudeJson, 
					temperatureJson
					);
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		return weather;
	}
	
	
	
	static Weather getCurrentDefaultWeather() 
	{
		return getCurrentWeather(PARIS_LATITUDE, PARIS_LONGITUDE);
	}
}
