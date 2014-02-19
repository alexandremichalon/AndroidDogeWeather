/**
 * 
 */
package fr.amichalon.androidappdogeweather.Business;

import fr.amichalon.androidappdogeweather.Model.GeoCoordinates;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;

/**
 * @author alexandre.michalon
 *
 */
public final class AndroidUtil 
{
	private static Context context;
	
	public static void setContext(Context context)
	{
		AndroidUtil.context = context;
	}

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
			// - has no requirement regarding the level of energy consumption
			Criteria criteria = new Criteria();
			criteria.setAltitudeRequired	(false);
			criteria.setBearingRequired		(false);
			criteria.setCostAllowed			(false);
			criteria.setSpeedRequired		(false);
			criteria.setHorizontalAccuracy	(Criteria.NO_REQUIREMENT);
			criteria.setPowerRequirement	(Criteria.NO_REQUIREMENT);
			
			String provider = locationManager.getBestProvider(criteria, true);
			Location location = locationManager.getLastKnownLocation(provider);
			
			if (location != null)
				return new GeoCoordinates(location.getLatitude(), location.getLongitude());
    	}
		
		return null;
	}
}
