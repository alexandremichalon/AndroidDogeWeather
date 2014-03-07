
package fr.amichalon.androidappdogeweather.business;

import fr.amichalon.androidappdogeweather.R;
import fr.amichalon.androidappdogeweather.model.GeoCoordinates;
import android.content.Context;
import android.content.res.Resources;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;

/**
 * Business class that provides methods to access Android functionalities and
 * some resources.
 * 
 * @author alexandre.michalon
 */
public final class AndroidUtil 
{
	
	/**
	 * The context of the Android application or widget.
	 */
	private static Context context;
	
	
	/**
	 * The resources of the Android application.
	 */
	private static Resources resources;
	
	
	
	
	
	
	/**
	 * Set the Context of the static class so that it becomes the Context given
	 * in argument. This method must be called when the Activity or
	 * AppWidgetProvider start.
	 * 
	 * @param context
	 * 		The context of the Android application or widget.
	 */
	public static void setContext(Context context)
	{
		AndroidUtil.context = context;
		AndroidUtil.resources = context.getResources();
	}
	
	
	
	
	
	/**
	 * Uses Android connectivity service to give information about the 
	 * availability of the network on the phone.
	 * 
	 * @return
	 * 		true if the network is available.
	 */
	public static boolean isNetworkAvailable() 
    {
    	if(context != null)
    	{
	    	ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
	    	
	    	return connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnected()
	    			|| connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnected();
    	}
    		
    	return false;
	}
	
	
	/**
	 * Uses Android location service to give the current geographic coordinates 
	 * of the phone. Returns null in case of error.
	 * 
	 * @return
	 * 		A GeoCoordinates object.
	 * 
	 * @see GeoCoordinates
	 */
	public static GeoCoordinates getPhoneCoordinates()
	{
		if(context != null)
    	{
			LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
			
			// Looking for a location provider that :
			// - isn't required to provide the altitude
			// - isn't required to provide the orientation (N, S, W, E)
			// - isn't allowed to bill any service
			// - isn't required to provide the speed
			// - has no accuracy requirement regarding the latitude or the longitude
			// - prefers low energy consumption
			Criteria criteria = new Criteria();
			criteria.setAltitudeRequired	(false);
			criteria.setBearingRequired		(false);
			criteria.setCostAllowed			(false);
			criteria.setSpeedRequired		(false);
			criteria.setHorizontalAccuracy	(Criteria.NO_REQUIREMENT);
			criteria.setPowerRequirement	(Criteria.POWER_LOW);
			
			String provider = locationManager.getBestProvider(criteria, true);
			
			if (provider != null)
			{
				Location location = locationManager.getLastKnownLocation(provider);
				
				if (location != null)
					return new GeoCoordinates(location.getLatitude(), location.getLongitude());
			}
    	}
		
		return null;
	}
	
	
	
	
	
	/**
	 * Get a string array by its ID in the resources.
	 * 
	 * @param arrayId
	 * 		The ID of the string array in the resources.
	 * 
	 * @return
	 * 		An array of String taken from the resources files.
	 */
	public static String[] getStringArray(int arrayId)
	{
		return resources.getStringArray(arrayId);
	}
	
	
	/**
	 * Get the lexical field that all weathers share.
	 * 
	 * @return
	 * 		An array of String taken from the resources files.
	 */
	public static String[] getLexicalFieldAll()
	{
		return getStringArray(R.array.lexical_field_all);
	}
	
	
	/**
	 * Get the default lexical field, the one used when there is no current 
	 * weather.
	 * 
	 * @return
	 * 		An array of String taken from the resources files.
	 */
	public static String[] getLexicalFieldDefault()
	{
		return getStringArray(R.array.lexical_field_default);
	}
	
	
	/**
	 * Get the lexical field associated with a temperature <= -30°C.
	 * 
	 * @return
	 * 		An array of String taken from the resources files.
	 */
	public static String[] getInferiorToMinus30()
	{
		return getStringArray(R.array.inferior_to_minus_30);
	}
	
	
	/**
	 * Get the lexical field associated with a -30°C < temperature <= -15°C.
	 * 
	 * @return
	 * 		An array of String taken from the resources files.
	 */
	public static String[] getBetweenMinus30AndMinus15()
	{
		return getStringArray(R.array.between_minus_30_and_minus_15);
	}
	
	
	/**
	 * Get the lexical field associated with a -15°C < temperature <= -7°C.
	 * 
	 * @return
	 * 		An array of String taken from the resources files.
	 */
	public static String[] getBetweenMinus15AndMinus7()
	{
		return getStringArray(R.array.between_minus_15_and_minus_7);
	}
	
	
	/**
	 * Get the lexical field associated with a -7°C < temperature <= 0°C.
	 * 
	 * @return
	 * 		An array of String taken from the resources files.
	 */
	public static String[] getBetweenMinus7And0()
	{
		return getStringArray(R.array.between_minus_7_and_0);
	}
	
	
	/**
	 * Get the lexical field associated with a 0°C < temperature <= 10°C.
	 * 
	 * @return
	 * 		An array of String taken from the resources files.
	 */
	public static String[] getBetween0And10()
	{
		return getStringArray(R.array.between_0_and_10);
	}
	
	
	/**
	 * Get the lexical field associated with a 10°C < temperature <= 20°C.
	 * 
	 * @return
	 * 		An array of String taken from the resources files.
	 */
	public static String[] getBetween10And20()
	{
		return getStringArray(R.array.between_10_and_20);
	}
	
	
	/**
	 * Get the lexical field associated with a 20°C < temperature <= 30°C.
	 * 
	 * @return
	 * 		An array of String taken from the resources files.
	 */
	public static String[] getBetween20And30()
	{
		return getStringArray(R.array.between_20_and_30);
	}
	
	
	/**
	 * Get the lexical field associated with a temperature > 30°C.
	 * 
	 * @return
	 * 		An array of String taken from the resources files.
	 */
	public static String[] getSuperiorTo30()
	{
		return getStringArray(R.array.superior_to_30);
	}
}
