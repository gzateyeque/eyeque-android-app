package com.eyeque.mono;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.WindowManager;
import android.view.Window;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import android.database.Cursor;
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

public class SplashActivity extends Activity {
    private long ms = 0;
    private long splashTime = 1000;
    private boolean splashActive = true;
    private boolean paused = false;
    private static SQLiteDatabase myDb = null;
    private static String dbToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Window window = this.getWindow();
        // clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        // finally change the color
        // window.setStatusBarColor(this.getResources().getColor(R.color.colorPrimaryDark));

        SingletonDataHolder.phoneManufacturer = android.os.Build.MANUFACTURER;
        SingletonDataHolder.phoneBrand = android.os.Build.BRAND;
        SingletonDataHolder.phoneProduct = android.os.Build.PRODUCT;
        SingletonDataHolder.phoneModel = android.os.Build.MODEL;
        Log.d("**** Phone Type ****", SingletonDataHolder.phoneBrand + " " + SingletonDataHolder.phoneModel);

        if (SingletonDataHolder.token != "") {
            Intent topIntent = new Intent(getBaseContext(), TopActivity.class);
            startActivity(topIntent);
            finish();
            return;
        }

        // Check local persistent mono.db database
        try {
            DatabaseHelper dbHelper = new DatabaseHelper(getApplicationContext());
            myDb = dbHelper.getReadableDatabase();
            Log.d("TAG", "open database successfully");

            String[] projection = {
                    Constants.USER_ENTITY_ID_COLUMN,
                    Constants.USER_ENTITY_TOKEN_COLUMN
            };
            // How you want the results sorted in the resulting Cursor
            String sortOrder =
                    Constants.USER_ENTITY_ID_COLUMN + " DESC";

            Cursor cursor = myDb.query(
                    Constants.USER_ENTITY_TABLE,    // The table to query
                    projection,                     // The columns to return
                    null,                           // The columns for the WHERE clause
                    null,                           // The values for the WHERE clause
                    null,                           // don't group the rows
                    null,                           // don't filter by row groups
                    sortOrder                       // The sort order
            );
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                dbToken = cursor.getString(
                        cursor.getColumnIndexOrThrow(Constants.USER_ENTITY_TOKEN_COLUMN));

                if (dbToken != null && dbToken != "") {
                    SingletonDataHolder.token = dbToken;
                    Log.d("### SP DB Token###", dbToken);
                    // Intent topIntent = new Intent(getBaseContext(), TopActivity.class);
                    // startActivity(topIntent);
                    // finish();
                    // return;
                }
            }
        } catch (IOException e) {
            Log.d("TAG", "open database failed");
        }

        final ImageView splashView = (ImageView) findViewById(R.id.splashImageView);
        splashView.setImageResource(R.drawable.eyeque_logo);

        Thread splashThread = new Thread() {
            public void run() {
                try {
                    while (splashActive && ms < splashTime) {
                        if (!paused)
                            ms += 100;
                        sleep(100);
                    }
                } catch(Exception e) {}
                finally {
                    if (dbToken != null)
                        ValidateToken();
                    else {
                        Intent startIntent = new Intent(SplashActivity.this, LoginActivity.class);
                        startActivity(startIntent);
                        finish();
                    }
                        // Intent startIntent = new Intent(SplashActivity.this, LoginActivity.class);
                        // startActivity(startIntent);
                        // finish();
                }
            }
        };
        splashThread.start();
    }

    private void ValidateToken() {

        NetConnection conn = new NetConnection();
        if (conn.isConnected(getApplicationContext())) {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            // showProgress(true);

            RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
            String url = Constants.UrlUserProfile;
            final JSONObject params = new JSONObject();

            StringRequest postRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.i("*** UserProfile ***", response);

                    //Parse the JOSN response
                    try {
                        JSONObject jsonResponse = new JSONObject(response);
                        SingletonDataHolder.userId = Integer.parseInt(jsonResponse.getString("id"));
                        SingletonDataHolder.email = jsonResponse.getString("email");
                        SingletonDataHolder.firstName = jsonResponse.getString("firstname");
                        SingletonDataHolder.lastName = jsonResponse.getString("lastname");
                        if (jsonResponse.has("gender"))
                            SingletonDataHolder.gender = Integer.parseInt(jsonResponse.getString("gender"));
                        if (jsonResponse.has("dob")) {
                            Log.i("*** Birth Year ***", jsonResponse.getString("dob").substring(1, 5));
                            SingletonDataHolder.birthYear = Integer.valueOf(jsonResponse.getString("dob").substring(0, 4));
                        }
                        if (jsonResponse.has("custom_attributes")) {
                            JSONArray jsonCustArrArray = jsonResponse.getJSONArray("custom_attributes");

                            for (int i = 0; i < jsonCustArrArray.length(); i++) {
                                JSONObject objectInArray = jsonCustArrArray.getJSONObject(i);
                                String attrName = objectInArray.getString("attribute_code");
                                String attrValue = objectInArray.getString("value");

                                if (attrName.matches("device_number")) {
                                    if (attrValue.matches("") || (attrValue.matches("null") || attrValue == null)) {
                                        Log.i("********4*********", "true");
                                        // Intent nameIntent = new Intent(getBaseContext(), NameActivity.class);
                                        // startActivity(nameIntent);
                                        Intent startIntent = new Intent(SplashActivity.this, LoginActivity.class);
                                        startActivity(startIntent);
                                    } else {
                                        Log.i("********5*********", "false");
                                        Intent topIntent = new Intent(getBaseContext(), TopActivity.class);
                                        startActivity(topIntent);
                                    }
                                    finish();
                                    break;
                                }
                            }
                        } else {
                            Log.i("********4*********", "true");
                            // Intent nameIntent = new Intent(getBaseContext(), NameActivity.class);
                            // startActivity(nameIntent);
                            Intent startIntent = new Intent(SplashActivity.this, LoginActivity.class);
                            startActivity(startIntent);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    // AccessToken tok;
                    // tok = AccessToken.getCurrentAccessToken();
                    // Log.d(TAG, tok.getUserId());
                    // Pass authentication

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("Error.Response", error.toString());
                    Intent startIntent = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(startIntent);
                    finish();
                }
            }) {

                /***
                 @Override
                 public byte[] getBody() throws AuthFailureError {
                 Log.i("$$$---JSON---$$$", params.toString());
                 return params.toString().getBytes();
                 }
                 ***/

                @Override
                public String getBodyContentType() {
                    return "application/json";
                }

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> headers = new HashMap<>();
                    String authStr = "Bearer " + SingletonDataHolder.token;
                    headers.put("Content-Type", "application/json;charset=UTF-8");
                    headers.put("Authorization", authStr);
                    return headers;
                }
            };
            RetryPolicy policy = new DefaultRetryPolicy(Constants.NETCONN_TIMEOUT_VALUE, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
            postRequest.setRetryPolicy(policy);
            queue.add(postRequest);
        } else {
            Intent startIntent = new Intent(SplashActivity.this, LoginActivity.class);
            startActivity(startIntent);
            finish();
        }
    }
}
