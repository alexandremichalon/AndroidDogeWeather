package fr.amichalon.androidappdogeweather;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class ActivityDogeWeather extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doge_weather);
        
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_doge_weather, menu);
        return true;
    }
    
}
