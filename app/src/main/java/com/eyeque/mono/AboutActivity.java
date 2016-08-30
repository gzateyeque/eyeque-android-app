package com.eyeque.mono;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

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
                Uri uri =  Uri.parse("http://www.eyeque.com");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        TextView websiteTv = (TextView) findViewById(R.id.websiteTextStringView);
        websiteTv.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Uri uri =  Uri.parse("http://www.eyeque.com");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
    }
}
