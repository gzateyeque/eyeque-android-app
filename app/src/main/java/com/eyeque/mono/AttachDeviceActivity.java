package com.eyeque.mono;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;


/**
 * AttachDevice Activity
 */
public class AttachDeviceActivity extends AppCompatActivity {
    // TODO: Rename parameter arguments, choose names that match
    // private PatternView patternView;
    private static int deviceId;
    private PatternView patternView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_attach_device);

        deviceId = 3;
        patternView = (PatternView) findViewById(R.id.drawView);
        patternView.setDeviceId((int) deviceId);
        patternView.setdrawDeviceOnly(true);
        patternView.start();

        Button attachDeviceContinueButton = (Button) findViewById(R.id.attachDeviceContinueButton);
        attachDeviceContinueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext(), TestConditionActivity.class);
                startActivity(i);
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
