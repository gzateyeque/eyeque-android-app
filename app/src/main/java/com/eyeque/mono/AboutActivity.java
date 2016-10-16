package com.eyeque.mono;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Button;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        TextView rateAppTv = (TextView) findViewById(R.id.rateAppTextStringView);
        TextView verStringTv = (TextView) findViewById(R.id.verStringTextView);
        verStringTv.setText("Version " + Constants.BuildNumber);

        rateAppTv.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Uri uri =  Uri.parse(Constants.UrlRateApp);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        /***
        TextView websiteTv = (TextView) findViewById(R.id.websiteTextStringView);
        websiteTv.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Uri uri =  Uri.parse("http://www.eyeque.com");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
         ****/

        TextView termsOfServiceTv = (TextView) findViewById(R.id.termsOfServiceButton);
        termsOfServiceTv.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Uri uri = Uri.parse(Constants.UrlTermsOfService);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        TextView privatePolicyTv = (TextView) findViewById(R.id.privatePolicyButton);
        privatePolicyTv.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Uri uri = Uri.parse(Constants.UrlPrivacyPolicy);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        switch (keyCode) {
            case KeyEvent.KEYCODE_VOLUME_DOWN:
                return true;
            case KeyEvent.KEYCODE_VOLUME_UP:
                return true;
            default:
                return super.onKeyDown(keyCode, event);
        }
    }
}
