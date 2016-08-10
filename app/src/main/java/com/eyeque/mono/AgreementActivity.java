package com.eyeque.mono;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
import java.util.HashMap;
import java.util.Map;

public class AgreementActivity extends AppCompatActivity {
    private WebView webview;
    private boolean userChecked = false;
    private boolean newsletterChecked = true;
    private static final String TAG = "Agreement Acvitity";

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

        Button nextButton = (Button) findViewById(R.id.agreementNextButton);
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
        if (checkBox.isChecked())
            userChecked = true;
        else
            userChecked = false;
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
            final JSONObject tmpParams = new JSONObject();
            final JSONArray attrArray = new JSONArray();
            final JSONObject finalParams = new JSONObject();

            try {
                finalParams.put("token", SingletonDataHolder.token);
                params.put("email", SingletonDataHolder.email);
                params.put("firstname", SingletonDataHolder.firstName);
                params.put("lastname", SingletonDataHolder.lastName);
                params.put("website_id", 1);
                params.put("store_id", 1);
                params.put("gender", SingletonDataHolder.gender);
                params.put("dob", Integer.toString(SingletonDataHolder.birthYear) + "-01-01");
                tmpParams.put("attribute_code", "device_number");
                tmpParams.put("value", SingletonDataHolder.deviceSerialNum);
                attrArray.put(tmpParams);
                params.put("custom_attributes", attrArray);
                finalParams.put("customer", params);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            StringRequest postRequest = new StringRequest(Request.Method.PUT, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.i(TAG, response);
                    // Pass authentication
                    // showProgress(false);
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
            Toast.makeText(AgreementActivity.this, "Network Connection Failed", Toast.LENGTH_SHORT).show();
    }
}
