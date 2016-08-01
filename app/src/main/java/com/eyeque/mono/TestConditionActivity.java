package com.eyeque.mono;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import static com.eyeque.mono.R.id.glassOnNoCheckbox;
import static com.eyeque.mono.R.id.glassOnYesCheckbox;
import static com.eyeque.mono.R.id.screenProtectorOnNoCheckbox;
import static com.eyeque.mono.R.id.screenProtectorOnYesCheckbox;

public class TestConditionActivity extends AppCompatActivity {

    private PatternView patternView;
    private static int deviceId;
    private boolean glassOnYesChecked = false;
    private boolean glassOnNoChecked = false;
    private boolean screenProtectorOnYesChecked = false;
    private boolean screenProtectorOnNoChecked = false;
    private CheckBox glassOnYesCheckBox;
    private CheckBox glassOnNoCheckBox;
    private CheckBox screenProtectorOnYesCheckBox;
    private CheckBox screenProtectorOnNoCheckBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_condition);

        Window window = this.getWindow();
        // clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        // finally change the color
        // window.setStatusBarColor(this.getResources().getColor(R.color.colorPrimaryDark));

        patternView = (PatternView) findViewById(R.id.drawView);
        // Draw the device mounting line. Hard code for now.
        // Need to dynamically populate when supporting multiple devices
        deviceId = 3;
        patternView.setDeviceId((int) deviceId);
        patternView.setdrawDeviceOnly(true);
        patternView.start();

        Button testConditionContinueButton = (Button) findViewById(R.id.testConditionContinueButton);
        testConditionContinueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!glassOnYesChecked && !glassOnNoChecked
                        || !screenProtectorOnYesChecked && !screenProtectorOnNoChecked)
                    Toast.makeText(TestConditionActivity.this, "Please confirm the test condition", Toast.LENGTH_SHORT).show();
                else {
                    Intent i = new Intent(getBaseContext(), MainActivity.class);
                    i.putExtra("subjectId", 21);
                    i.putExtra("deviceId", 3);
                    i.putExtra("serverId", 1);
                    startActivity(i);
                }
            }
        });

        glassOnYesCheckBox = (CheckBox) findViewById(glassOnYesCheckbox);
        glassOnNoCheckBox = (CheckBox) findViewById(glassOnNoCheckbox);
        screenProtectorOnYesCheckBox = (CheckBox) findViewById(screenProtectorOnYesCheckbox);
        screenProtectorOnNoCheckBox = (CheckBox) findViewById(screenProtectorOnNoCheckbox);
     }

    public void glassOnYesClicked(View v) {
        //code to check if this checkbox is checked!
        CheckBox checkBox = (CheckBox) v;
        if (checkBox.isChecked()) {
            glassOnYesChecked = true;
            glassOnNoChecked = false;
            glassOnNoCheckBox.setChecked(false);
            glassOnNoCheckBox.setSelected(false);
        }
        else
            glassOnYesChecked = false;
    }

    public void glassOnNoClicked(View v) {
        //code to check if this checkbox is checked!
        CheckBox checkBox = (CheckBox) v;
        if (checkBox.isChecked()) {
            glassOnYesChecked = false;
            glassOnNoChecked = true;
            glassOnYesCheckBox.setChecked(false);
            glassOnYesCheckBox.setSelected(false);
        }
        else
            glassOnNoChecked = false;
    }

    public void screenProtectorOnYesClicked(View v) {
        //code to check if this checkbox is checked!
        CheckBox checkBox = (CheckBox) v;
        if (checkBox.isChecked()) {
            screenProtectorOnYesChecked = true;
            screenProtectorOnNoChecked = false;
            screenProtectorOnNoCheckBox.setChecked(false);
            screenProtectorOnNoCheckBox.setSelected(false);
        }
        else
            screenProtectorOnYesChecked = false;
    }

    public void screenProtectorOnNoClicked(View v) {
        //code to check if this checkbox is checked!
        CheckBox checkBox = (CheckBox) v;
        if (checkBox.isChecked()) {
            screenProtectorOnYesChecked = false;
            screenProtectorOnNoChecked = true;
            screenProtectorOnYesCheckBox.setChecked(false);
            screenProtectorOnYesCheckBox.setSelected(false);
        }
        else
            screenProtectorOnNoChecked = false;

    }
}
