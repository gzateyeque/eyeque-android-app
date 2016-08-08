package com.eyeque.mono;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.WindowManager;
import android.view.Window;

public class SplashActivity extends Activity {
    private long ms = 0;
    private long splashTime = 1000;
    private boolean splashActive = true;
    private boolean paused = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Window window = this.getWindow();
        // clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        // finally change the color
        // window.setStatusBarColor(this.getResources().getColor(R.color.colorPrimaryDark));

        if (SingletonDataHolder.getToken() != "") {
            Intent topIntent = new Intent(getBaseContext(), TopActivity.class);
            startActivity(topIntent);
            finish();
            return;
        }

        final ImageView splashView = (ImageView) findViewById(R.id.splashImageView);
        splashView.setImageResource(R.drawable.splash);

        Thread splashThread = new Thread() {
            public void run() {
                try {
                    while (splashActive && ms < splashTime) {
                        if (!paused)
                            ms += 100;
                        sleep(100);
                    }
                } catch(Exception e) {}
                finally {
                        SingletonDataHolder.phoneManufacturer = android.os.Build.MANUFACTURER;
                        SingletonDataHolder.phoneBrand = android.os.Build.BRAND;
                        SingletonDataHolder.phoneProduct = android.os.Build.PRODUCT;
                        SingletonDataHolder.phoneModel = android.os.Build.MODEL;
                        Log.d("************Phone Type", SingletonDataHolder.phoneBrand + " " + SingletonDataHolder.phoneModel);

                        Intent startIntent = new Intent(SplashActivity.this, LoginActivity.class);
                        startActivity(startIntent);
                        finish();
                }
            }
        };
        splashThread.start();
    }
}
