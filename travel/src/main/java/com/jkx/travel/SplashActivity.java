package com.jkx.travel;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.jkx.travel.R;

public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
              runOnUiThread(new Runnable() {
                  @Override
                  public void run() {
                      Intent intent = new Intent(SplashActivity.this,
                              LoginActivity.class);
                      startActivity(intent);
                  }
              });
            }
        }.start();
    }



}
