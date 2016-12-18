package com.eyeque.mono;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.view.WindowManager;
import android.view.Window;
import android.widget.Toast;
import static com.eyeque.mono.R.id.wearEyeglassNoCheckbox;
import static com.eyeque.mono.R.id.wearEyeglassYesCheckbox;

public class WearEyeglassActivity extends AppCompatActivity {
    private CheckBox wearEyeglassYesCheckBox;
    private CheckBox waerEyeglassNoCheckBox;
    private boolean wearEyeglassYesChecked = false;
    private boolean wearEyeglassNoChecked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wear_eyeglass);
        Window window = this.getWindow();
        // clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        // finally change the color
        // window.setStatusBarColor(this.getResources().getColor(R.color.colorPrimaryDark));

        wearEyeglassYesCheckBox = (CheckBox) findViewById(wearEyeglassYesCheckbox);
        waerEyeglassNoCheckBox = (CheckBox) findViewById(wearEyeglassNoCheckbox);

        Button nextButton = (Button) findViewById(R.id.wearEyeglassNextButton);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!wearEyeglassYesChecked && !wearEyeglassNoChecked)
                    Toast.makeText(WearEyeglassActivity.this, "Please provide the answer", Toast.LENGTH_SHORT).show();
                else {
                    if (wearEyeglassYesChecked)
                        SingletonDataHolder.profileWearEyeglass = true;
                    else
                        SingletonDataHolder.profileWearEyeglass = false;
                    Intent dobIntent = new Intent(getBaseContext(), DobActivity.class);
                    startActivity(dobIntent);
                }
            }
        });
    }

    public void wearEyeglassYesClicked(View v) {
        //code to check if this checkbox is checked!
        CheckBox checkBox = (CheckBox) v;
        if (checkBox.isChecked()) {
            wearEyeglassYesChecked = true;
            wearEyeglassNoChecked = false;
            waerEyeglassNoCheckBox.setChecked(false);
            waerEyeglassNoCheckBox.setSelected(false);
        } else
            wearEyeglassYesChecked = false;
    }

    public void wearEyeglassNoClicked(View v) {
        //code to check if this checkbox is checked!
        CheckBox checkBox = (CheckBox) v;
        if (checkBox.isChecked()) {
            wearEyeglassYesChecked = false;
            wearEyeglassNoChecked = true;
            wearEyeglassYesCheckBox.setChecked(false);
            wearEyeglassYesCheckBox.setSelected(false);
        } else
            wearEyeglassNoChecked = false;
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
