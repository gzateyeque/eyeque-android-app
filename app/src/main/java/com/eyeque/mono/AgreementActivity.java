package com.eyeque.mono;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;
import android.text.style.ClickableSpan;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.graphics.Color;
import android.text.Html;
import android.net.Uri;
import android.widget.Toast;
import android.view.WindowManager;
import android.view.Window;
import android.widget.CheckBox;
import android.util.Log;
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

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class AgreementActivity extends AppCompatActivity {
    private WebView webview;
    private static SQLiteDatabase myDb = null;
    private boolean userChecked = false;
    private boolean newsletterChecked = true;
    private static final String TAG = "Agreement Acvitity";
    private Button nextButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agreement);
        Window window = this.getWindow();
        // clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        // finally change the color
        // window.setStatusBarColor(this.getResources().getColor(R.color.colorPrimaryDark));

        try {
            String email, token;
            DatabaseHelper dbHelper = new DatabaseHelper(getApplicationContext());
            myDb = dbHelper.getWritableDatabase();
            Log.d("TAG", "open database successfully");
        } catch (IOException e) {
            Log.d("TAG", "open database failed");
        }

        Log.i("** Token **", SingletonDataHolder.token);
        Log.i("** Email **", SingletonDataHolder.email);
        Log.i("** First Name **", SingletonDataHolder.firstName);
        Log.i("** Last Name **", SingletonDataHolder.lastName);
        Log.i("** Gender **", SingletonDataHolder.gender.toString());
        Log.i("** Birth Yr **", SingletonDataHolder.birthYear.toString());
        Log.i("** Serial **", SingletonDataHolder.deviceSerialNum);

        SpannableString ss = new SpannableString("I agree to EyeQue's Terms of Service and Privacy Policy");
        ClickableSpan clickableSpan1 = new ClickableSpan() {
            @Override
            public void onClick(View textView) {
                Uri uri = Uri.parse(Constants.UrlTermsOfService);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setUnderlineText(false);
            }
        };
        ss.setSpan(clickableSpan1, 20, 36, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        ClickableSpan clickableSpan2 = new ClickableSpan() {
            @Override
            public void onClick(View textView) {
                Uri uri = Uri.parse(Constants.UrlPrivacyPolicy);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setUnderlineText(false);
            }
        };
        ss.setSpan(clickableSpan2, 41, 55, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        TextView textView = (TextView) findViewById(R.id.agreementTextStringView);
        textView.setText(ss);
        textView.setMovementMethod(LinkMovementMethod.getInstance());
        textView.setHighlightColor(Color.TRANSPARENT);

        nextButton = (Button) findViewById(R.id.agreementNextButton);
        nextButton.setEnabled(false);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!userChecked)
                    Toast.makeText(AgreementActivity.this, "Plesae consent the user agreement", Toast.LENGTH_SHORT).show();
                else {
                    UpdateProfile();
                }
            }
        });

    }

    public void userItemClicked(View v) {
        //code to check if this checkbox is checked!
        CheckBox checkBox = (CheckBox) v;
        if (checkBox.isChecked()) {
            userChecked = true;
            nextButton.setEnabled(true);
            nextButton.setTextColor(Color.parseColor("#046EEA"));
        }
        else {
            userChecked = false;
            nextButton.setEnabled(false);
            nextButton.setTextColor(Color.rgb(151, 151, 151));
        }
    }

    public void newsItemClicked(View v) {
        //code to check if this checkbox is checked!
        CheckBox checkBox = (CheckBox) v;
        if (checkBox.isChecked())
            newsletterChecked = true;
        else
            newsletterChecked = false;
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
                tmpParams1.put("attribute_code", "device_number");
                tmpParams1.put("value", SingletonDataHolder.deviceSerialNum);
                attrArray.put(tmpParams1);
                tmpParams2.put("attribute_code", "wear_eyeglasses");
                if (SingletonDataHolder.profileWearEyeglass == true )
                    tmpParams2.put("value", "yes");
                else
                    tmpParams2.put("value", "no");
                attrArray.put(tmpParams2);
                params.put("custom_attributes", attrArray);
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
                    SingletonDataHolder.newRegUser = false;
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
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    // showProgress(false);
                    Log.d("Error.Response", error.toString());
                    Toast.makeText(AgreementActivity.this, "Server error", Toast.LENGTH_SHORT).show();
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
            Toast.makeText(AgreementActivity.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
    }

    private void DbStoreToken() {
        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(Constants.USER_ENTITY_VERSION_COLUMN, 1);
        values.put(Constants.USER_ENTITY_TOKEN_COLUMN, SingletonDataHolder.token);

        // Insert the new row, returning the primary key value of the new row
        long newRowId;
        newRowId = myDb.insert(
                Constants.USER_ENTITY_TABLE,
                null,
                values);
        Log.d("**** Toekn DB ***", Long.toString(newRowId));
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
