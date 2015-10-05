package com.example.mukesh.test;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;

public class weatherActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        TextView tv= (TextView)findViewById(R.id.onClick);
        Intent intent = getIntent();
        if(intent!=null && intent.hasExtra(Intent.EXTRA_TEXT) && intent.hasExtra(Intent.EXTRA_KEY_EVENT) ){
            String JSONv = intent.getStringExtra(Intent.EXTRA_TEXT);
            int pos = intent.getIntExtra(Intent.EXTRA_KEY_EVENT, 0 );

            try {
                JSONObject JSON= new JSONObject(JSONv);
                JSONArray l = JSON.getJSONArray("list");
                JSONObject day = l.getJSONObject(pos);

                long date = System.currentTimeMillis() + (86400_000*pos);
                SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy");
                String dateS = sdf.format(date);

                JSONObject temp = day.getJSONObject("temp");
                double high= temp.getDouble("max");
                double low= temp.getDouble("min");
                int hum = day.getInt("humidity");
                JSONArray wea = day.getJSONArray("weather");
                JSONObject w = wea.getJSONObject(0);
                String desc = w.getString("description");

                String print = dateS + " : " + desc + "\n" + "Maximum Temperature is : " + Double.toString(high)
                        +  "\n" + "Minimum Temperature is : " + Double.toString(low) + "\n" +
                        "Humidity in Atmosphere is : " + Integer.toString(hum);
                tv.setText(print);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_weather, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if( id==R.id.action_settings ) {
//            Intent intent= new Intent(this, SettingsActivity.class );
//            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
