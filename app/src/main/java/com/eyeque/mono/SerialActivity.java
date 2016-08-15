package com.eyeque.mono;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.text.Html;
import android.net.Uri;
import android.widget.Toast;
import android.view.WindowManager;
import android.view.Window;
import android.widget.TextView;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;


import java.util.HashMap;
import java.util.Map;

public class SerialActivity extends AppCompatActivity {
    private WebView webview;
    private EditText serialEt;
    private String serialNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_serial);
        Window window = this.getWindow();
        // clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        // finally change the color
        // window.setStatusBarColor(this.getResources().getColor(R.color.colorPrimaryDark));

        serialEt = (EditText) findViewById(R.id.serialEditText);
        serialEt.setHint(Html.fromHtml("<small>" + "Serial number" + "</small>" ));

        TextView orderTv = (TextView) findViewById(R.id.deviceOrderTextView);
        orderTv.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            Uri uri =  Uri.parse(Constants.UrlBuyDevice);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
            }
        });

        Button nextButton = (Button) findViewById(R.id.serialNextButton);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String serialNum = serialEt.getText().toString();
                if (serialNum.matches(""))
                    Toast.makeText(SerialActivity.this, "Please enter device serial number", Toast.LENGTH_SHORT).show();
                else
                    CheckSeriallNum(serialNum);
                // Intent agreementIntent = new Intent(getBaseContext(), AgreementActivity.class);
                // startActivity(agreementIntent);
            }
        });
    }

    private void CheckSeriallNum(String serial) {

        serialNum = serial;
        NetConnection conn = new NetConnection();
        if (conn.isConnected(getApplicationContext())) {

            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            // showProgress(true);

            RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
            final String url = Constants.UrlCheckSerialNum;
            final JSONObject params = new JSONObject();
            try {
                params.put("sn", serialNum);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            // final String url = Constants.UrlCheckSerialNum + serial;
            StringRequest postRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String string) {
                    // Parse serial check response
                    try {
                        // Parsing json object response
                        // response will be a json object
                        // JsonParser parser = new JsonParser();
                        // String retVal = parser.parse(string);
                        JSONObject jsonObj = new JSONObject(string);
                        int ret_code = jsonObj.getInt("return_code");
                        if (ret_code == 1) {
                            SingletonDataHolder.deviceSerialNum = serialNum;
                            Intent agreementIntent = new Intent(getBaseContext(), AgreementActivity.class);
                            startActivity(agreementIntent);
                        } else
                            Toast.makeText(SerialActivity.this, "Invalid Serial Number", Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(SerialActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("Error.Response", error.toString());
                    Toast.makeText(SerialActivity.this, "Validation failed", Toast.LENGTH_SHORT).show();
                }
            }) {
                @Override
                public byte[] getBody() throws AuthFailureError {
                    Log.i("JSON data", params.toString());
                    return params.toString().getBytes();
                }

                @Override
                public String getBodyContentType() {
                    return "application/json";
                }

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> headers = new HashMap<>();
                    headers.put("Content-Type", "application/json;charset=UTF-8");
                    return headers;
                }
            };
            RetryPolicy policy = new DefaultRetryPolicy(Constants.NETCONN_TIMEOUT_VALUE, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
            postRequest.setRetryPolicy(policy);
            queue.add(postRequest);
        }
        else
            Toast.makeText(SerialActivity.this, "Network Connection Failed", Toast.LENGTH_SHORT).show();
    }

}
