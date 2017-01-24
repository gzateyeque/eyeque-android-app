package com.eyeque.mono;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

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
        if (!SingletonDataHolder.birthYear.equals(""))
            dobEt.setText(Integer.toString(SingletonDataHolder.birthYear));
        else
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
                        UpdateProfile();
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
                    Toast.makeText(DobActivity.this, "No Server Response", Toast.LENGTH_SHORT).show();
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
            Toast.makeText(DobActivity.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
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
