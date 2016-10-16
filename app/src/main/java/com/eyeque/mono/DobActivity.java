package com.eyeque.mono;

import java.util.Calendar;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.text.Html;
import android.view.WindowManager;
import android.view.Window;
import android.util.Log;
import android.widget.Toast;

public class DobActivity extends AppCompatActivity {
    private WebView webview;
    private EditText dobEt;
    // Tag for log message
    private static final String TAG = DobActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dob);
        Window window = this.getWindow();
        // clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        // finally change the color
        // window.setStatusBarColor(this.getResources().getColor(R.color.colorPrimaryDark));

        dobEt = (EditText) findViewById(R.id.dobEditText);
        dobEt.setHint(Html.fromHtml("<small>" + "Your Birth Year (YYYY)" + "</small>" ));

        Button nextButton = (Button) findViewById(R.id.dobNextButton);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String dob = dobEt.getText().toString();
                try {
                    int num = Integer.parseInt(dob);
                    int currentYear = Calendar.getInstance().get(Calendar.YEAR);
                    if (num >= (currentYear - 100) && num <= (currentYear - 10)) {
                        SingletonDataHolder.birthYear = num;
                        Intent serialIntent = new Intent(getBaseContext(), SerialActivity.class);
                        startActivity(serialIntent);
                    } else
                        Toast.makeText(DobActivity.this, "The birth year is out of range", Toast.LENGTH_SHORT).show();
                } catch (NumberFormatException e) {
                    Toast.makeText(DobActivity.this, "The birth year is out of range", Toast.LENGTH_SHORT).show();
                }
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
