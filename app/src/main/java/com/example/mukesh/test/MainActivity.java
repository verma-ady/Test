package com.example.mukesh.test;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;

public class MainActivity extends AppCompatActivity {

    public MainActivity(){
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.refresh_button) {
            fetchweather fetch = new fetchweather();
            fetch.execute("110085");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public class fetchweather extends AsyncTask<String , Void, String[]> {

//        public fetchweather(String){
//
//        }
        private final String LOG_TAG = fetchweather.class.getSimpleName();

        protected  String[] doInBackground(String... params) {

            if (params.length == 0) {
                return null;
            }

            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            String stringJSON = null;
            String modeS = "json";
            String unitsS = "metric";
            int num = 7;
            try {
                final String Base = "http://api.openweathermap.org/data/2.5/forecast/daily?";
                final String Code = "q";
                final String mode = "mode";
                final String units = "units";
                final String cnt = "cnt";

                Uri uri = Uri.parse(Base).buildUpon()
                        .appendQueryParameter(Code, params[0]).appendQueryParameter(mode, modeS)
                        .appendQueryParameter(units, unitsS).appendQueryParameter(cnt, Integer.toString(num)).build();

                URL url = new URL(uri.toString());
                Log.v(LOG_TAG, "URL is " + uri.toString());

//                URL url = new URL("http://api.openweathermap.org/data/2.5/forecast/daily?q=94043&mode=json&units=metric&cnt=7");
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    return null;
                }

                reader = new BufferedReader(new InputStreamReader(inputStream));
                String line;

                while ((line = reader.readLine()) != null) {
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    return null;
                }

                stringJSON = buffer.toString();
                Log.v(LOG_TAG, " STring JSON " + stringJSON);
            } catch (IOException e) {
                Log.e(LOG_TAG, "Error", e);
                //return ;
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e(LOG_TAG, "ErrorClosingStream", e);
                    }
                }
            }

            try {
                return getweather(stringJSON, num );
            } catch (JSONException e) {
                Log.v(LOG_TAG, "getweather func crash");
                return null;
            }

        }//doinbackground

//        @Override
//        protected void onPostExecute( String res[] ){
//            if(res!=null){
//
//                for(int i=0 ; i<res.length ; i++ ){
//                    weekforecast.add(res[i]);
//                }
//            }
//        }


        private String getDate ( long JSONdate ){
            SimpleDateFormat shortDate = new SimpleDateFormat("EEE MMM dd");
            return shortDate.format(JSONdate);
        }

        private String formattemp( double high, double low ){
            long roundhigh = Math.round(high);
            long roundlow = Math.round(low);
            String str = roundhigh + "/" + roundlow ;
            return str;
        }


        private String[] getweather ( String JSONStr, int num ) throws JSONException {
            String resString[] = new String[num];
            JSONObject weatherJSON = new JSONObject(JSONStr);
            JSONArray weatherarray = weatherJSON.getJSONArray("list");
            Log.v(LOG_TAG, "getweather1");
            for( int i=0 ; i<weatherarray.length() ; i++ ){
                String day, desc, highlow;
//                Log.v(LOG_TAG, "getweather2");
                JSONObject dayJSON= weatherarray.getJSONObject(i);
//                Log.v(LOG_TAG, "getweather3");
                JSONObject dateJSON = dayJSON.getJSONArray("weather").getJSONObject(0);
//                Log.v(LOG_TAG, "getweather4");
                desc=dateJSON.getString("main");
//                Log.v(LOG_TAG, "getweather5");
                day = getDate(dayJSON.getLong("dt"));
//                Log.v(LOG_TAG, "getweather6"+ day);
                JSONObject tempJSON = dayJSON.getJSONObject("temp");
//                Log.v(LOG_TAG, "getweather7");
                double high = tempJSON.getDouble("max");
//                Log.v(LOG_TAG, "getweather8");
                double low = tempJSON.getDouble("min");
//                Log.v(LOG_TAG, "getweather9");
                highlow = formattemp(high,low );
//                Log.v(LOG_TAG, "getweather10");
                resString[i]= day + " - " + desc + " - " + highlow;
//                Log.v(LOG_TAG, "getweather11");
            }
            Log.v(LOG_TAG, "7 Days of Data ");
            for( int i=0; i<weatherarray.length() ; i++ ){
                Log.v(LOG_TAG, resString[i] );
            }

            return resString;
        }//getweather
    }//fetchweather
}
