package com.eyeque.mono;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageButton;
import android.view.WindowManager;
import android.view.Window;
import android.widget.Toast;

import java.text.DecimalFormat;

public class GenderActivity extends AppCompatActivity {
    boolean femaleToggle = false;
    boolean maleToggle = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gender);
        Window window = this.getWindow();
        // clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        // finally change the color
        // window.setStatusBarColor(this.getResources().getColor(R.color.colorPrimaryDark));

        final ImageButton femaleButton = (ImageButton) findViewById(R.id.femaleButton);
        final ImageButton maleButton = (ImageButton) findViewById(R.id.maleButton);

        femaleButton.setImageResource(R.drawable.female);
        femaleButton.setBackgroundDrawable(null);
        femaleButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                femaleToggle = !femaleToggle;
                if (femaleToggle) {
                    femaleButton.setImageResource(R.drawable.female_blue);
                    if (maleToggle) {
                        maleButton.setImageResource(R.drawable.male);
                        maleToggle = !maleToggle;
                    }
                } else {
                    femaleButton.setImageResource(R.drawable.female);
                }
            }
        });

        maleButton.setImageResource(R.drawable.male);
        maleButton.setBackgroundDrawable(null);
        maleButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                maleToggle = !maleToggle;
                if (maleToggle) {
                    maleButton.setImageResource(R.drawable.male_blue);
                    if (femaleToggle) {
                        femaleButton.setImageResource(R.drawable.female);
                        femaleToggle = !femaleToggle;
                    }
                } else {
                    maleButton.setImageResource(R.drawable.male);
                }
            }
        });

        Button nextButton = (Button) findViewById(R.id.genderNextButton);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!femaleToggle && !maleToggle)
                    Toast.makeText(GenderActivity.this, "Please select your gender", Toast.LENGTH_SHORT).show();
                else {
                    if (maleToggle)
                        SingletonDataHolder.gender = 1;
                    else
                        SingletonDataHolder.gender = 2;
                    Intent dobIntent = new Intent(getBaseContext(), DobActivity.class);
                    startActivity(dobIntent);
                }
            }
        });
    }
}
