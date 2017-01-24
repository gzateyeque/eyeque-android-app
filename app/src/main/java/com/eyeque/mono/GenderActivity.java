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
import android.widget.ImageButton;
import android.view.WindowManager;
import android.view.Window;
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

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

public class GenderActivity extends AppCompatActivity {
    boolean femaleToggle = false;
    boolean maleToggle = false;
    private static final String TAG = GenderActivity.class.getSimpleName();

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

        maleToggle = false;
        femaleToggle = false;
        if (SingletonDataHolder.gender == 2) {
            femaleButton.setImageResource(R.drawable.female_selected);
            femaleToggle = true;
        }
        else
            femaleButton.setImageResource(R.drawable.female_unselected);
        femaleButton.setBackgroundDrawable(null);
        femaleButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                femaleToggle = !femaleToggle;
                if (femaleToggle) {
                    femaleButton.setImageResource(R.drawable.female_selected);
                    if (maleToggle) {
                        maleButton.setImageResource(R.drawable.male_unselected);
                        maleToggle = !maleToggle;
                    }
                } else {
                    femaleButton.setImageResource(R.drawable.female_selected);
                }
            }
        });

        if (SingletonDataHolder.gender == 1) {
            maleButton.setImageResource(R.drawable.male_selected);
            maleToggle = true;
        }
        else
            maleButton.setImageResource(R.drawable.male_unselected);
        maleButton.setBackgroundDrawable(null);
        maleButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                maleToggle = !maleToggle;
                if (maleToggle) {
                    maleButton.setImageResource(R.drawable.male_selected);
                    if (femaleToggle) {
                        femaleButton.setImageResource(R.drawable.female_unselected);
                        femaleToggle = !femaleToggle;
                    }
                } else {
                    maleButton.setImageResource(R.drawable.male_unselected);
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
                    Intent wearEyeglassIntent = new Intent(getBaseContext(), WearEyeglassActivity.class);
                    startActivity(wearEyeglassIntent);
                }
            }
        });
    }

    private void UpdateProfile() {

        String url = Constants.UrlUserProfile;
        NetConnection conn = new NetConnection();
        if (conn.isConnected(getApplicationContext())) {

            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            // showProgress(true);

            RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

            final JSONObject params = new JSONObject();
            final JSONObject tmpParams1 = new JSONObject();
            final JSONObject tmpParams2 = new JSONObject();
            final JSONArray attrArray = new JSONArray();
            final JSONObject finalParams = new JSONObject();

            try {
                // finalParams.put("token", SingletonDataHolder.token);
                params.put("email", SingletonDataHolder.email);
                params.put("firstname", SingletonDataHolder.firstName);
                params.put("lastname", SingletonDataHolder.lastName);
                params.put("website_id", 1);
                params.put("store_id", 1);
                params.put("gender", SingletonDataHolder.gender);
                params.put("dob", Integer.toString(SingletonDataHolder.birthYear) + "-01-01");
                params.put("group_id", SingletonDataHolder.groupId);
                /***
                 tmpParams1.put("attribute_code", "device_number");
                 tmpParams1.put("value", SingletonDataHolder.deviceSerialNum);
                 attrArray.put(tmpParams1);
                 tmpParams2.put("attribute_code", "wear_eyeglasses");
                 if (SingletonDataHolder.profileWearEyeglass == true)
                 tmpParams2.put("value", "yes");
                 else
                 tmpParams2.put("value", "no");
                 attrArray.put(tmpParams2);
                 params.put("custom_attributes", attrArray);
                 ***/
                finalParams.put("customer", params);
                Log.i("CUSTOMER ATTR", params.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            StringRequest postRequest = new StringRequest(Request.Method.PUT, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.i(TAG, response);
                    // Pass authentication
                    // showProgress(false);
                    /***
                     try {
                     JSONObject jsonResponse = new JSONObject(response);
                     SingletonDataHolder.userId = Integer.parseInt(jsonResponse.getString("id"));
                     } catch (JSONException e) {
                     e.printStackTrace();
                     }
                     DbStoreToken();
                     Intent topIntent = new Intent(getBaseContext(), TopActivity.class);
                     startActivity(topIntent);
                     finish();
                     ***/
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    // showProgress(false);
                    Log.d("Error.Response", error.toString());
                    Toast.makeText(GenderActivity.this, "No Server Response", Toast.LENGTH_SHORT).show();
                }
            }) {
                @Override
                public byte[] getBody() throws AuthFailureError {
                    Log.i("$$$---JSON---$$$", finalParams.toString());
                    return finalParams.toString().getBytes();
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
            Toast.makeText(GenderActivity.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
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
