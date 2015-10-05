package com.example.mukesh.test;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by MUKESH on 05-Oct-15.
 */
public class splash extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState ){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        Thread thread=new Thread() {
            public void run() {
                try{
                    sleep(1000);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    Intent intent = new Intent(splash.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        };
        thread.start();
    }
    protected void pause(){
        super.onPause();
        finish();
    }

}
