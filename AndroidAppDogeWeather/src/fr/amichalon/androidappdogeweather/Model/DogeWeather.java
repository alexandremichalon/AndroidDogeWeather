/**
 * 
 */
package fr.amichalon.androidappdogeweather.Model;

import java.util.ArrayList;
import java.util.List;

import fr.amichalon.androidappdogeweather.Models.Enumerations.WeatherIcon;
import android.R;
import android.app.Activity;
import android.content.res.Resources;

/**
 * @author alexandre.michalon
 *
 */
class DogeWeather {
	
	private DogeWeather(
			Activity activity,
			Weather weather
			)
	{
		this.activityDogeWeather = activity;
		this.weather = weather;
		//this.weatherIcon = weatherIcon;
		
		buildLexicalField();
	}

	private Weather weather;
	
	private WeatherIcon weatherIcon;
	
	private String[] lexicalField;
	
	private Activity activityDogeWeather;
	
	private void buildLexicalField()
	{
		Resources res = activityDogeWeather.getResources();
		
		String[] lfAll = res.getStringArray(fr.amichalon.androidappdogeweather.R.array.lexical_field_all);
		
		String[] lfWeather = res.getStringArray(weatherIcon.getLexicalFieldId());
	}
	
}
