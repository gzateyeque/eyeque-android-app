package com.eyeque.mono;

/**
 * Created by georgez on 2/9/16.
 */

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.view.LayoutInflater;
import android.view.ViewGroup.LayoutParams;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class PlayModeResultActivity extends Activity {

    private static int subjectId;
    private static int deviceId;
    private static int serverId;
    private static String odSe;
    private static String osSe;

    // Tag for log message
    private static final String TAG = PlayModeResultActivity.class.getSimpleName();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playmode_result);

        Window window = this.getWindow();
        // clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        // finally change the color
        // window.setStatusBarColor(this.getResources().getColor(R.color.colorPrimaryDark));

        subjectId = getIntent().getIntExtra("subjectId", 0);
        deviceId = getIntent().getIntExtra("deviceId", 0);
        serverId = getIntent().getIntExtra("serverId", 0);

        odSe = getIntent().getStringExtra("ODE");
        osSe = getIntent().getStringExtra("OSE");

        Button uploadButton = (Button) findViewById(R.id.uploadButton);
        final TextView testCompleteHeaderTextView = (TextView) findViewById(R.id.testCompleteHeaderTextView);
        final TextView odSpheTextView = (TextView) findViewById(R.id.odSpheTextView);
        final TextView osSpheTextView = (TextView) findViewById(R.id.osSpheTextView);
        final TextView testResultTextView = (TextView) findViewById(R.id.testResultText);

        testCompleteHeaderTextView.setText("Quick Test Completion for " + SingletonDataHolder.firstName);
        odSpheTextView.setText(odSe + " D");
        osSpheTextView.setText(osSe + " D");


        final TextView btnOpenPopup = (TextView)findViewById(R.id.testResultTextView);
        btnOpenPopup.setOnClickListener(new Button.OnClickListener(){

            @Override
            public void onClick(View arg0) {
                LayoutInflater layoutInflater
                        = (LayoutInflater)getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);
                View popupView = layoutInflater.inflate(R.layout.window_popup, null);
                final PopupWindow popupWindow = new PopupWindow(
                        popupView,
                        LayoutParams.WRAP_CONTENT,
                        LayoutParams.WRAP_CONTENT);
                popupWindow.setFocusable(true);
                popupWindow.setOutsideTouchable(true);
                /**
                Button btnDismiss = (Button)popupView.findViewById(R.id.dismiss);
                btnDismiss.setOnClickListener(new Button.OnClickListener(){

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        popupWindow.dismiss();
                    }});
                ***/
                popupWindow.showAsDropDown(btnOpenPopup, -300, 10, Gravity.CENTER);
            }});


        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toast.makeText(PlayModeResultActivity.this, "Record Discarded", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(getBaseContext(), TopActivity.class);
                startActivity(i);
                finish();
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
