package com.eyeque.mono;
/**
 * Created by georgez on 2/1/16.
 */

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.view.Menu;
import android.graphics.Color;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.media.MediaPlayer;
import android.net.Uri;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;
import java.text.DecimalFormat;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class MainActivity extends Activity {
    private PatternView patternView;
    private Pattern pattern;

    private static int minVal;
    private static int maxVal;
    private int prevStopValue = maxVal;
    private static int longPressStep = 3;
    private boolean closerOrFurther = true;
    private boolean toggleEye = true;

    // Long press event
    private boolean inLongPressMode = false;
    private final long REPEAT_DELAY = 50;
    private Handler repeatUpdateHandler = new Handler();

    // Inter Activity Parameters
    private static int subjectId;
    private static int deviceId;
    private static int serverId;
    private static long seconds;
    // private static int brightColor = Color.rgb(96, 96, 96);
    // private static int darkColor = Color.rgb(21, 21, 21);
    private static int brightColor = Color.rgb(255, 255, 255);
    private static int darkColor = Color.rgb(96, 96, 96);
    private static Button contButton;
    private static Button exitButton;

    // Tag for log message
    private static final String TAG = MainActivity.class.getSimpleName();

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        Log.i("MyActivity", "onCreate");
        setContentView(R.layout.activity_main);
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

        switch (deviceId) {
            case 2:
                minVal = Constants.MINVAL_DEVICE_3;
                maxVal = Constants.MAXVAL_DEVICE_3;
                longPressStep = 5;
                break;
            case 3:
                // minVal = Constants.MINVAL_DEVICE_5;
                // maxVal = Constants.MAXVAL_DEVICE_5;
                minVal = SingletonDataHolder.minDistance;
                maxVal = SingletonDataHolder.maxDistance - SingletonDataHolder.minDistance;
                longPressStep = 5;
                break;
            case 4:
                minVal = Constants.MINVAL_DEVICE_6;
                maxVal = Constants.MAXVAL_DEVICE_6;
                longPressStep = 5;
                break;
            default:
                minVal = Constants.MINVAL_DEVICE_1;
                maxVal = Constants.MAXVAL_DEVICE_1;
                longPressStep = 5;
                break;
        }

        class RepetitiveUpdater implements Runnable {
            @Override
            public void run() {
                if (inLongPressMode) {
                    if (closerOrFurther)
                        closerLongPressedAction();
                    else
                        furtherLongPressedAction();
                    repeatUpdateHandler.postDelayed(new RepetitiveUpdater(), REPEAT_DELAY);
                }
            }
        }

        /**
         * Widgets requires modification during interaction
         */
        final TextView inTestTv = (TextView) findViewById(R.id.testHeaderTextView);
        final TextView tv = (TextView) findViewById(R.id.powerText);
        final TextView dtv = (TextView) findViewById(R.id.distText);
        final TextView atv = (TextView) findViewById(R.id.angleText);

        // final PatternView patternView = (PatternView) findViewById(R.id.drawView);
        // final Pattern pattern = patternView.getPatternInstance();
        patternView = (PatternView) findViewById(R.id.drawView);
        pattern = patternView.getPatternInstance();
        patternView.setDeviceId((int) deviceId);
        patternView.start();
        // if (deviceId != 4) {
        AccormAnimation animation = new AccormAnimation(patternView);
        seconds = System.currentTimeMillis();
        animation.setDuration(seconds);
        animation.setseconds(seconds);
        animation.setRepeatCount(Animation.INFINITE);
        patternView.startAnimation(animation);
        // }

        final TextView eyeLeftText = (TextView) findViewById(R.id.eyeLeftText);
        final TextView eyeRightText = (TextView) findViewById(R.id.eyeRightText);
        final ImageView eyeImageView = (ImageView) findViewById(R.id.eyeImage);

        final SeekBar alignSeekBar = (SeekBar) findViewById(R.id.alignSeekBar);
        alignSeekBar.setMax(maxVal);
        prevStopValue = maxVal;
        alignSeekBar.setProgress(maxVal);
        alignSeekBar.setVisibility(View.INVISIBLE);

        dtv.setText("Distance: " + String.valueOf(pattern.getDistance()));
        atv.setText("Angle: " + String.valueOf(patternView.getAngle()) + (char) 0x00B0);
        DecimalFormat precision = new DecimalFormat("#.##");
        Double i2 = Double.valueOf(precision.format(pattern.getPowerValue()));
        tv.setText("Power: " + String.valueOf(i2));
        dtv.setVisibility(View.INVISIBLE);
        atv.setVisibility(View.INVISIBLE);
        tv.setVisibility(View.INVISIBLE);
        final MediaPlayer mp = new MediaPlayer();

        exitButton = (Button) findViewById(R.id.exitButton);
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialog2 = new AlertDialog.Builder(
                        MainActivity.this);

                // Setting Dialog Title
                alertDialog2.setTitle("Confirm Exit...");

                // Setting Dialog Message
                alertDialog2.setMessage("Are you sure you want exit this test?");

                // Setting Icon to Dialog
                // alertDialog2.setIcon(R.drawable.delete);

                // Setting Positive "Yes" Btn
                alertDialog2.setPositiveButton("YES",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // Write your code here to execute after dialog
                                // Toast.makeText(getApplicationContext(),
                                // "You clicked on YES", Toast.LENGTH_SHORT)
                                // .show();
                                if (mp.isPlaying()) {
                                    mp.stop();
                                }
                                finish();
                            }
                        });

                // Setting Negative "NO" Btn
                alertDialog2.setNegativeButton("NO",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // Write your code here to execute after dialog
                                // Toast.makeText(getApplicationContext(),
                                // "You clicked on NO", Toast.LENGTH_SHORT)
                                // .show();
                                dialog.cancel();
                            }
                        });

                // Showing Alert Dialog
                alertDialog2.show();
            }
        });

        if (pattern.getWhichEye()) {
            eyeRightText.setTextColor(brightColor);
            eyeLeftText.setTextColor(darkColor);
            eyeImageView.setImageResource(R.drawable.eye_right1);
        }  else {
            eyeRightText.setTextColor(darkColor);
            eyeLeftText.setTextColor(brightColor);
            eyeImageView.setImageResource(R.drawable.eye_left1);
        }
        inTestTv.setText("Test 1/9");


        if (mp.isPlaying()) {
            mp.stop();
        }
        try {
            mp.reset();
            mp.setDataSource(getApplicationContext(),
                    Uri.parse("android.resource://com.eyeque.mono/" + R.raw.start_test));
            mp.prepare();
            mp.start();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // catch (InterruptedException e) {
            // e.printStackTrace();
        // }

        /****
        try {
            Thread.sleep(13000);
            mp.reset();
            mp.setDataSource(getApplicationContext(),
                    Uri.parse("android.resource://com.eyeque.mono/" + R.raw.frp_1));
            mp.prepare();
            mp.start();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            Thread.sleep(10000);
            mp.reset();
            mp.setDataSource(getApplicationContext(),
                    Uri.parse("android.resource://com.eyeque.mono/" + R.raw.fri_2));
            mp.prepare();
            mp.start();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
         ****/

        /**
         * Add callback handler to change the pattern in pattern view
         */


        contButton = (Button) findViewById(R.id.contButton);
        contButton.setOnClickListener(new View.OnClickListener() {
            String str;
            @Override
            public void onClick(View v) {
                Log.i(TAG, "Next Button clicked.");

                int patternIndex = patternView.getPatternInstance().getPattenIndex();
                if (pattern.isAllPatternComplete() && patternIndex == 0) {
                    calcResult();
                    return;
                }

                if (prevStopValue == maxVal)
                    return;

                if (mp.isPlaying()) {
                    mp.stop();
                }
                // int patternIndex = patternView.getPatternInstance().getPattenIndex();
                try {
                    mp.reset();
                    // int patternIndex = patternView.getPatternInstance().getPattenIndex();

                    switch (patternIndex) {
                        case 0:
                            if (deviceId == 4)
                                mp.setDataSource(getApplicationContext(),
                                        // Uri.parse("android.resource://com.eyeque.mono/" + R.raw.mm1));
                                        Uri.parse("android.resource://com.eyeque.mono/" + R.raw.mm1));
                            else
                                if (pattern.getWhichEye())
                                    mp.setDataSource(getApplicationContext(),
                                        Uri.parse("android.resource://com.eyeque.mono/" + R.raw.frp_2));
                                else
                                    mp.setDataSource(getApplicationContext(),
                                            Uri.parse("android.resource://com.eyeque.mono/" + R.raw.flp_2));
                            break;

                        case 1:
                            if (deviceId == 4)
                                mp.setDataSource(getApplicationContext(),
                                        Uri.parse("android.resource://com.eyeque.mono/" + R.raw.mm2));
                            else
                            if (pattern.getWhichEye())
                                mp.setDataSource(getApplicationContext(),
                                        Uri.parse("android.resource://com.eyeque.mono/" + R.raw.frp_3));
                            else
                                mp.setDataSource(getApplicationContext(),
                                        Uri.parse("android.resource://com.eyeque.mono/" + R.raw.flp_3));
                            break;
                        case 2:
                            if (deviceId == 4)
                                mp.setDataSource(getApplicationContext(),
                                        Uri.parse("android.resource://com.eyeque.mono/" + R.raw.mm3));
                            else
                            if (pattern.getWhichEye())
                                mp.setDataSource(getApplicationContext(),
                                        Uri.parse("android.resource://com.eyeque.mono/" + R.raw.frp_4));
                            else
                                mp.setDataSource(getApplicationContext(),
                                        Uri.parse("android.resource://com.eyeque.mono/" + R.raw.flp_4));

                            break;
                        case 3:
                            if (deviceId == 4)
                                mp.setDataSource(getApplicationContext(),
                                        Uri.parse("android.resource://com.eyeque.mono/" + R.raw.mm4));
                            else
                            if (pattern.getWhichEye())
                                mp.setDataSource(getApplicationContext(),
                                        Uri.parse("android.resource://com.eyeque.mono/" + R.raw.frp_5));
                            else
                                mp.setDataSource(getApplicationContext(),
                                        Uri.parse("android.resource://com.eyeque.mono/" + R.raw.flp_5));
                            break;
                        case 4:
                            if (deviceId == 4)
                                mp.setDataSource(getApplicationContext(),
                                        Uri.parse("android.resource://com.eyeque.mono/" + R.raw.mm5));
                            else
                            if (pattern.getWhichEye())
                                mp.setDataSource(getApplicationContext(),
                                        Uri.parse("android.resource://com.eyeque.mono/" + R.raw.frp_6));
                            else
                                mp.setDataSource(getApplicationContext(),
                                        Uri.parse("android.resource://com.eyeque.mono/" + R.raw.flp_6));
                            break;
                        case 5:
                            if (deviceId == 4)
                                mp.setDataSource(getApplicationContext(),
                                        Uri.parse("android.resource://com.eyeque.mono/" + R.raw.mm6));
                            else
                            if (pattern.getWhichEye())
                                mp.setDataSource(getApplicationContext(),
                                        Uri.parse("android.resource://com.eyeque.mono/" + R.raw.frp_7));
                            else
                                mp.setDataSource(getApplicationContext(),
                                        Uri.parse("android.resource://com.eyeque.mono/" + R.raw.flp_7));
                            break;
                        case 6:
                            if (deviceId == 4)
                                mp.setDataSource(getApplicationContext(),
                                        Uri.parse("android.resource://com.eyeque.mono/" + R.raw.mm7));
                            else
                            if (pattern.getWhichEye())
                                mp.setDataSource(getApplicationContext(),
                                        Uri.parse("android.resource://com.eyeque.mono/" + R.raw.frp_8));
                            else
                                mp.setDataSource(getApplicationContext(),
                                        Uri.parse("android.resource://com.eyeque.mono/" + R.raw.flp_8));
                            break;
                        case 7:
                            if (deviceId == 4)
                                mp.setDataSource(getApplicationContext(),
                                        Uri.parse("android.resource://com.eyeque.mono/" + R.raw.mm8));
                            else
                            if (pattern.getWhichEye())
                                mp.setDataSource(getApplicationContext(),
                                        Uri.parse("android.resource://com.eyeque.mono/" + R.raw.frp_9));
                            else
                                mp.setDataSource(getApplicationContext(),
                                        Uri.parse("android.resource://com.eyeque.mono/" + R.raw.flp_9));
                            break;
                        case 8:
                            if (deviceId == 4)
                                mp.setDataSource(getApplicationContext(),
                                        Uri.parse("android.resource://com.eyeque.mono/" + R.raw.mm9));
                            if (pattern.getWhichEye())
                                mp.setDataSource(getApplicationContext(),
                                        Uri.parse("android.resource://com.eyeque.mono/" + R.raw.fr_last));
                            else
                                mp.setDataSource(getApplicationContext(),
                                        Uri.parse("android.resource://com.eyeque.mono/" + R.raw.fl_last));
                            break;
                        default:
                            if (deviceId == 4)
                                mp.setDataSource(getApplicationContext(),
                                        Uri.parse("android.resource://com.eyeque.mono/" + R.raw.mm1));
                            else
                                mp.setDataSource(getApplicationContext(),
                                        Uri.parse("android.resource://com.eyeque.mono/" + R.raw.m0));
                            break;
                    }

                    mp.prepare();
                    mp.start();

                    if (patternIndex == 2 && deviceId < 2) {
                        try {
                            Thread.sleep(3000);                 //1000 milliseconds is one second.
                        } catch (InterruptedException ex) {
                            Thread.currentThread().interrupt();
                        }
                        if (mp.isPlaying()) {
                            mp.stop();
                        }
                        try {
                            mp.reset();
                            mp.setDataSource(getApplicationContext(),
                                    Uri.parse("android.resource://com.eyeque.mono/" + R.raw.turn90));
                            mp.prepare();
                            mp.start();
                        } catch (IllegalStateException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    if ((deviceId < 2 && patternIndex == 5)
                            || (deviceId == 2 && patternIndex == 8)
                            || (deviceId == 3 && patternIndex == 8)
                            || (deviceId == 4 && patternIndex == 8)) {
                        try {
                            Thread.sleep(2000);                 //1000 milliseconds is one second.
                        } catch (InterruptedException ex) {
                            Thread.currentThread().interrupt();
                        }
                        if (mp.isPlaying()) {
                            mp.stop();
                        }
                        try {
                            mp.reset();
                            if (!pattern.getWhichEye() && pattern.isAllPatternComplete()) {

                                // Announce Test Complete audio
                                // mp.setDataSource(getApplicationContext(),
                                        // Uri.parse("android.resource://com.eyeque.mono/" + R.raw.mm81));
                            } else if (pattern.getWhichEye())
                                mp.setDataSource(getApplicationContext(),
                                        Uri.parse("android.resource://com.eyeque.mono/" + R.raw.flp_1));
                            else
                                mp.setDataSource(getApplicationContext(),
                                        Uri.parse("android.resource://com.eyeque.mono/" + R.raw.frp_1));
                            mp.prepare();
                            mp.start();
                        } catch (IllegalStateException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                } catch (IllegalStateException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                patternView.nextPattern();
                dtv.setText("Distance: " + String.valueOf(pattern.getDistance()));
                atv.setText("Angle: " + String.valueOf(pattern.getAngle()) + (char) 0x00B0);
                DecimalFormat precision = new DecimalFormat("#.##");
                Double i2 = Double.valueOf(precision.format(pattern.getPowerValue()));
                tv.setText("Power: " + String.valueOf(i2));

                Log.d(TAG, String.valueOf(pattern.getDistance()));
                prevStopValue = maxVal;
                // currStopValue = maxVal;
                alignSeekBar.setProgress(maxVal);

                if (pattern.isAllPatternComplete()
                        && ((deviceId < 2 && patternIndex == 5)
                        || (deviceId == 2 && patternIndex == 8)
                        || (deviceId == 3 && patternIndex == 8)
                        || (deviceId == 4 && patternIndex == 8)))
                    calcResult();
                else {
                    if (pattern.getWhichEye()) {
                        eyeRightText.setTextColor(brightColor);
                        eyeLeftText.setTextColor(darkColor);
                        eyeImageView.setImageResource(R.drawable.eye_right1);
                    }  else {
                        eyeRightText.setTextColor(darkColor);
                        eyeLeftText.setTextColor(brightColor);
                        eyeImageView.setImageResource(R.drawable.eye_left1);
                    }
                    if (patternIndex == 8)
                        str = "Test " + Integer.toString((patternIndex + 2) % 9) + "/9";
                    else
                        str = "Test " + Integer.toString(patternIndex + 2) + "/9";
                    inTestTv.setText(str);
                }
            }
        });

        final Button closerButton = (Button) findViewById(R.id.closerButton);
        closerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "Closer Button clicked.");
                // int pressedColor = Color.rgb(255,255,255);
                // closerButton.setTextColor(pressedColor);
                int lineSpace = pattern.getDistance();
                if (lineSpace >= minVal + 1) {
                    patternView.closerDraw(1);
                    dtv.setText("Distance: " + String.valueOf(pattern.getDistance()));
                    DecimalFormat precision = new DecimalFormat("#.##");
                    Double i2 = Double.valueOf(precision.format(pattern.getPowerValue()));
                    tv.setText("Power: " + String.valueOf(i2));
                    prevStopValue = lineSpace - minVal;
                    alignSeekBar.setProgress(lineSpace - minVal);
                }
            }
        });

        closerButton.setOnLongClickListener(new View.OnLongClickListener() {

            @Override
            public boolean onLongClick(View v) {
                inLongPressMode = true;
                closerOrFurther = true;
                repeatUpdateHandler.post(new RepetitiveUpdater());
                return true;
            }
        });

        closerButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP && inLongPressMode) {
                    inLongPressMode = false;
                }
                if (event.getAction() == MotionEvent.ACTION_DOWN ||
                        event.getAction() == MotionEvent.ACTION_CANCEL ) {
                    int releasedColor = Color.rgb(255,255,255);
                    closerButton.setTextColor(releasedColor);
                }
                else if (event.getAction() == MotionEvent.ACTION_UP) {
                    int pressedColor = Color.rgb(200,200,200);
                    closerButton.setTextColor(pressedColor);
                }
                return false;
            }
        });


        final Button furtherButton = (Button) findViewById(R.id.furtherButton);
        furtherButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("MyActivity", "Further Button clicked.");
                int lineSpace = pattern.getDistance();
                if (lineSpace <= minVal + maxVal - 1) {
                    patternView.furtherDraw(1);
                    dtv.setText("Distance: " + String.valueOf(pattern.getDistance()));
                    DecimalFormat precision = new DecimalFormat("#.##");
                    Double i2 = Double.valueOf(precision.format(pattern.getPowerValue()));
                    tv.setText("Power: " + String.valueOf(i2));
                    prevStopValue = lineSpace - minVal;
                    alignSeekBar.setProgress(lineSpace - minVal);
                }

            }
        });


        furtherButton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                inLongPressMode = true;
                closerOrFurther = false;
                repeatUpdateHandler.post(new RepetitiveUpdater());
                return true;
            }
        });

        furtherButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP && inLongPressMode) {
                    inLongPressMode = false;
                }
                if (event.getAction() == MotionEvent.ACTION_DOWN ||
                        event.getAction() == MotionEvent.ACTION_CANCEL ) {
                    int releasedColor = Color.rgb(255,255,255);
                    furtherButton.setTextColor(releasedColor);
                }
                else if (event.getAction() == MotionEvent.ACTION_UP) {
                    int pressedColor = Color.rgb(200,200,200);
                    furtherButton.setTextColor(pressedColor);
                }
                return false;
            }
        });

        // alignSeekBar.setOnSeekBarChangeListener(this);
        alignSeekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // TODO Auto-generated method stub;
                if (progress < prevStopValue)
                    patternView.closerDraw(prevStopValue - progress);
                else
                    patternView.furtherDraw(progress - prevStopValue);
                dtv.setText("Distance: " + String.valueOf(pattern.getDistance()));
                DecimalFormat precision = new DecimalFormat("#.##");
                Double i2 = Double.valueOf(precision.format(pattern.getPowerValue()));
                tv.setText("Power: " + String.valueOf(i2));
                prevStopValue = progress;
            }
        });
    }

    public void calcResult() {

        NetConnection conn = new NetConnection();
        if (conn.isConnected(getApplicationContext())) {
            Toast.makeText(MainActivity.this,
                    "Processing measurement data", Toast.LENGTH_LONG).show();
            contButton.setEnabled(false);
            exitButton.setEnabled(false);
            RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
            final String url = Constants.UrlUploadTest;

            final JSONObject params = new JSONObject();
            final JSONObject finalParam = new JSONObject();
            try {
                params.put("binoOp", "No binocular");
                params.put("testType", "Full Refraction");
                if (deviceId == 2)
                    params.put("deviceName", "Device 3");
                else if (deviceId == 3)
                    params.put("deviceName", "Device5");
                else
                    params.put("deviceName", "Device 1");
                params.put("phoneType", SingletonDataHolder.phoneType);
                params.put("accomPattern", "AP5G");
                params.put("screenProtect", SingletonDataHolder.screenProtect);
                params.put("wearGlasses", SingletonDataHolder.wearGlasses);
                params.put("subjectID", SingletonDataHolder.userId);
                params.put("lineLength", SingletonDataHolder.lineLength);
                params.put("lineWidth", SingletonDataHolder.lineWidth);

                // if (serverId > 0) {
                JSONArray mDataArr = new JSONArray();
                for (int i = 0; i < 9; i++) {
                    JSONObject mDataObj = new JSONObject();
                    mDataObj.put("subjectID", SingletonDataHolder.userId);
                    mDataObj.put("angle", pattern.getPatternCalcAngleList()[i]);
                    mDataObj.put("distance", pattern.getRightDistValueList()[i]);
                    mDataObj.put("rightEye", 1);
                    mDataObj.put("power", 0);
                    mDataObj.put("duration", pattern.rightDurationList[i]);
                    mDataArr.put(mDataObj);
                }
                for (int i = 0; i < 9; i++) {
                    JSONObject mDataObj = new JSONObject();
                    mDataObj.put("subjectID", SingletonDataHolder.userId);
                    mDataObj.put("angle", pattern.getPatternCalcAngleList()[i]);
                    mDataObj.put("distance", pattern.getLeftDistValueList()[i]);
                    mDataObj.put("rightEye", 0);
                    mDataObj.put("power", 0);
                    mDataObj.put("duration", pattern.leftDurationList[i]);
                    mDataArr.put(mDataObj);
                }
                params.put("measures", mDataArr);
                finalParam.put("testdata", params);
                // }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            StringRequest postRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    Log.i(TAG, response);

                    Intent resultIntent = new Intent(getBaseContext(), ResultActivity.class);
                    try {
                        final JSONObject result = new JSONObject(response);
                        resultIntent.putExtra("TestId", result.getInt("test_condition_id"));
                        resultIntent.putExtra("Score", result.getInt("score"));
                        if (Double.parseDouble(result.getString("sphe_od")) > 0)
                            resultIntent.putExtra("ODE", String.format("+%.2f", Double.parseDouble(result.getString("sphe_od"))));
                        else
                            resultIntent.putExtra("ODE", String.format("%.2f", Double.parseDouble(result.getString("sphe_od"))));
                        if (Double.parseDouble(result.getString("sphe_os")) > 0)
                            resultIntent.putExtra("OSE", String.format("+%.2f", Double.parseDouble(result.getString("sphe_os"))));
                        else
                            resultIntent.putExtra("OSE", String.format("%.2f", Double.parseDouble(result.getString("sphe_os"))));

                        /**** For video shooting
                        resultIntent.putExtra("ODE", "-2.25");
                        resultIntent.putExtra("OSE", "-2.75");
                         ****/
                        startActivity(resultIntent);
                        finish();
                    } catch (JSONException e) {
                        Toast.makeText(MainActivity.this,
                                "Operation failed, please try it again", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    contButton.setEnabled(true);
                    exitButton.setEnabled(true);
                    Toast.makeText(MainActivity.this,
                            "Can't connect to the server", Toast.LENGTH_SHORT).show();
                    Log.d("Error.Response", error.toString());
                }
            }) {
                @Override
                public byte[] getBody() throws AuthFailureError {
                    Log.i("$$$--UPLOAD JSON---$$$", finalParam.toString());
                    return finalParam.toString().getBytes();
                }

                @Override
                public String getBodyContentType() {
                    return "application/json";
                }

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> headers = new HashMap<>();
                    String authString = "Bearer " + SingletonDataHolder.token;
                    headers.put("Content-Type", "application/json;charset=UTF-8");
                    headers.put("Authorization", authString);
                    Log.i("$$$---HEADER---$$$", headers.toString());
                    return headers;
                }
            };
            RetryPolicy policy = new DefaultRetryPolicy(Constants.NETCONN_TIMEOUT_VALUE, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
            postRequest.setRetryPolicy(policy);
            queue.add(postRequest);
        } else
            Toast.makeText(MainActivity.this,
                    "Please connect to the Internet to continue", Toast.LENGTH_SHORT).show();

        /***
        double[] results = pattern.calculateResults();
        Intent resultIntent = new Intent(getBaseContext(), ResultActivity.class);
        resultIntent.putExtra("subjectId", subjectId);
        resultIntent.putExtra("deviceId", deviceId);
        resultIntent.putExtra("serverId", serverId);
        resultIntent.putExtra("ODS", results[0]);
        resultIntent.putExtra("ODC", results[1]);
        resultIntent.putExtra("ODA", results[2]);
        resultIntent.putExtra("ODE", results[3]);
        resultIntent.putExtra("ODR", results[4]);
        resultIntent.putExtra("OSS", results[5]);
        resultIntent.putExtra("OSC", results[6]);
        resultIntent.putExtra("OSA", results[7]);
        resultIntent.putExtra("OSE", results[8]);
        resultIntent.putExtra("OSR", results[9]);

        double[] patternCalcAngleList = pattern.getPatternCalcAngleList();
        double[] leftPowerList = pattern.getLeftPowerValueList();
        double[] rightPowerList = pattern.getRightPowerValueList();
        int[] leftDistList = pattern.getLeftDistValueList();
        int[] rightDistList = pattern.getRightDistValueList();

        resultIntent.putExtra("Angle-1", patternCalcAngleList[0]);
        resultIntent.putExtra("Angle-2", patternCalcAngleList[1]);
        resultIntent.putExtra("Angle-3", patternCalcAngleList[2]);
        resultIntent.putExtra("Angle-4", patternCalcAngleList[3]);
        resultIntent.putExtra("Angle-5", patternCalcAngleList[4]);
        resultIntent.putExtra("Angle-6", patternCalcAngleList[5]);

        if (deviceId >= 2) {
            resultIntent.putExtra("Angle-7", patternCalcAngleList[6]);
            resultIntent.putExtra("Angle-8", patternCalcAngleList[7]);
            resultIntent.putExtra("Angle-9", patternCalcAngleList[8]);
        }

        resultIntent.putExtra("L-Dist-1", leftDistList[0]);
        resultIntent.putExtra("L-Dist-2", leftDistList[1]);
        resultIntent.putExtra("L-Dist-3", leftDistList[2]);
        resultIntent.putExtra("L-Dist-4", leftDistList[3]);
        resultIntent.putExtra("L-Dist-5", leftDistList[4]);
        resultIntent.putExtra("L-Dist-6", leftDistList[5]);

        if (deviceId >= 2) {
            resultIntent.putExtra("L-Dist-7", leftDistList[6]);
            resultIntent.putExtra("L-Dist-8", leftDistList[7]);
            resultIntent.putExtra("L-Dist-9", leftDistList[8]);
        }

        resultIntent.putExtra("R-Dist-1", rightDistList[0]);
        resultIntent.putExtra("R-Dist-2", rightDistList[1]);
        resultIntent.putExtra("R-Dist-3", rightDistList[2]);
        resultIntent.putExtra("R-Dist-4", rightDistList[3]);
        resultIntent.putExtra("R-Dist-5", rightDistList[4]);
        resultIntent.putExtra("R-Dist-6", rightDistList[5]);

        if (deviceId >= 2) {
            resultIntent.putExtra("R-Dist-7", rightDistList[6]);
            resultIntent.putExtra("R-Dist-8", rightDistList[7]);
            resultIntent.putExtra("R-Dist-9", rightDistList[8]);
        }

        Log.i("MA-OD Spherical:  ", Double.toString(results[0]));
        Log.i("MA-OD Cylindrical:  ", Double.toString(results[1]));
        Log.i("MA-OD Axis:  ", Double.toString(results[2]));
        Log.i("MA-OD SE:  ", Double.toString(results[3]));
        Log.i("MA-OS Spherical:  ", Double.toString(results[5]));
        Log.i("MA-OS Cylindrical:  ", Double.toString(results[6]));
        Log.i("MA-OS Axis:  ", Double.toString(results[7]));
        Log.i("MA-OS SE:  ", Double.toString(results[8]));

        startActivity(resultIntent);
        finish();
        ***/

    }

    public void closerLongPressedAction() {
        // final PatternView patternView = (PatternView) findViewById(R.id.drawView);
        final TextView tv = (TextView) findViewById(R.id.powerText);
        final Pattern pattern = patternView.getPatternInstance();
        final TextView dtv = (TextView) findViewById(R.id.distText);
        final SeekBar alignSeekBar = (SeekBar) findViewById(R.id.alignSeekBar);
        if (pattern.getDistance() >=minVal+6) {
            patternView.closerDraw(longPressStep);
            dtv.setText("Distance: " + String.valueOf(pattern.getDistance()));
            DecimalFormat precision = new DecimalFormat("#.##");
            Double i2 = Double.valueOf(precision.format(pattern.getPowerValue()));
            tv.setText("Power: " + String.valueOf(i2));
            prevStopValue = pattern.getDistance() - minVal;
            alignSeekBar.setProgress(pattern.getDistance() - minVal);
        }
    }

    public void furtherLongPressedAction() {
        // final PatternView patternView = (PatternView) findViewById(R.id.drawView);
        final TextView tv = (TextView) findViewById(R.id.powerText);
        final Pattern pattern = patternView.getPatternInstance();
        final TextView dtv = (TextView) findViewById(R.id.distText);
        final SeekBar alignSeekBar = (SeekBar) findViewById(R.id.alignSeekBar);
        if (pattern.getDistance() <=minVal+maxVal-2) {
            patternView.furtherDraw(longPressStep);
            dtv.setText("Distance: " + String.valueOf(pattern.getDistance()));
            DecimalFormat precision = new DecimalFormat("#.##");
            Double i2 = Double.valueOf(precision.format(pattern.getPowerValue()));
            tv.setText("Power: " + String.valueOf(i2));
            prevStopValue = pattern.getDistance() - minVal;
            alignSeekBar.setProgress(pattern.getDistance() - minVal);
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // final PatternView patternView = (PatternView) findViewById(R.id.drawView);
        final TextView tv = (TextView) findViewById(R.id.powerText);
        // pattern = patternView.getPatternInstance();
        final TextView dtv = (TextView) findViewById(R.id.distText);
        final SeekBar alignSeekBar = (SeekBar) findViewById(R.id.alignSeekBar);

        /***
         switch (keyCode) {
         case KeyEvent.KEYCODE_VOLUME_DOWN:
         Log.d(TAG, "Volume Down Button is pressed");
         if (pattern.getDistance() >=minVal+2) {
         patternView.closerDraw(longPressStep);
         dtv.setText("Distance: " + String.valueOf(pattern.getDistance()));
         DecimalFormat precision = new DecimalFormat("#.##");
         Double i2 = Double.valueOf(precision.format(pattern.getPowerValue()));
         tv.setText("Power: " + String.valueOf(i2));
         prevStopValue = pattern.getDistance() - minVal;
         alignSeekBar.setProgress(pattern.getDistance() - minVal);
         }
         break;
         case KeyEvent.KEYCODE_VOLUME_UP:
         Log.d(TAG, "Volume Up Button is pressed");
         if (pattern.getDistance() <=minVal+maxVal-2) {
         patternView.furtherDraw(longPressStep);
         dtv.setText("Distance: " + String.valueOf(pattern.getDistance()));
         DecimalFormat precision = new DecimalFormat("#.##");
         Double i2 = Double.valueOf(precision.format(pattern.getPowerValue()));
         tv.setText("Power: " + String.valueOf(i2));
         prevStopValue = pattern.getDistance() - minVal;
         alignSeekBar.setProgress(pattern.getDistance() - minVal);
         }
         break;
         default:
         break;
         }
         ***/
        return true;
    }


    @Override
    public void onBackPressed() {}


    /*****************************************
     * DEFAULT Stub function with Menubar
     * ==================================
     *
     @Override
     protected void onCreate(Bundle savedInstanceState) {
     super.onCreate(savedInstanceState);
     setContentView(R.layout.activity_main);
     Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
     setSupportActionBar(toolbar);

     FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
     fab.setOnClickListener(new View.OnClickListener() {
     @Override
     public void onClick(View view) {
     Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
     .setAction("Action", null).show();
     }
     });
     }

     @Override
     public boolean onCreateOptionsMenu(Menu menu) {
     // Inflate the menu; this adds items to the action bar if it is present.
     getMenuInflater().inflate(R.menu.menu_main, menu);
     return true;
     }

     @Override
     public boolean onOptionsItemSelected(MenuItem item) {
     // Handle action bar item clicks here. The action bar will
     // automatically handle clicks on the Home/Up button, so long
     // as you specify a parent activity in AndroidManifest.xml.
     int id = item.getItemId();

     //noinspection SimplifiableIfStatement
     if (id == R.id.action_settings) {
     return true;
     }

     return super.onOptionsItemSelected(item);
     }
     ********/
}
