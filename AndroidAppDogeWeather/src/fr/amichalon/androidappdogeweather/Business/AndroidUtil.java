/**
 * 
 */
package fr.amichalon.androidappdogeweather.Business;

import fr.amichalon.androidappdogeweather.R;
import fr.amichalon.androidappdogeweather.Model.GeoCoordinates;
import android.content.Context;
import android.content.res.Resources;
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
	
	private static Resources resources;
	
	
	
	public static void setContext(Context context)
	{
		AndroidUtil.context = context;
		AndroidUtil.resources = context.getResources();
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
			
			if (provider != null)
			{
				Location location = locationManager.getLastKnownLocation(provider);
				
				if (location != null)
					return new GeoCoordinates(location.getLatitude(), location.getLongitude());
			}
    	}
		
		return null;
	}
	
	
	
	public static String[] getStringArray(int arrayId)
	{
		return resources.getStringArray(arrayId);
	}
	
	
	public static String[] getLexicalFieldAll()
	{
		return getStringArray(R.array.lexical_field_all);
	}
	
	
	public static String[] getLexicalFieldDefault()
	{
		return getStringArray(R.array.lexical_field_default);
	}
	
	
	public static String[] getInferiorToMinus30()
	{
		return getStringArray(R.array.inferior_to_minus_30);
	}
	
	
	public static String[] getBetweenMinus30AndMinus15()
	{
		return getStringArray(R.array.between_minus_30_and_minus_15);
	}
	
	
	public static String[] getBetweenMinus15AndMinus7()
	{
		return getStringArray(R.array.between_minus_15_and_minus_7);
	}
	
	
	public static String[] getBetweenMinus7And0()
	{
		return getStringArray(R.array.between_minus_7_and_0);
	}
	
	
	public static String[] getBetween0And10()
	{
		return getStringArray(R.array.between_0_and_10);
	}
	
	
	public static String[] getBetween10And20()
	{
		return getStringArray(R.array.between_10_and_20);
	}
	
	
	public static String[] getBetween20And30()
	{
		return getStringArray(R.array.between_20_and_30);
	}
	
	
	public static String[] getSuperiorTo30()
	{
		return getStringArray(R.array.superior_to_30);
	}
}
