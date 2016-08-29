package com.eyeque.mono;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
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
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class AccountActivity extends AppCompatActivity {

    private static final String TAG = "Account Acvitity";
    private EditText mEmailView;
    private EditText firstnameEt;
    private EditText lastnameEt;
    private EditText birthYearEt;
    private CheckBox maleCheckBox;
    private CheckBox femaleCheckBox;
    private boolean maleChecked;
    private boolean femaleChecked;
    private String firstName;
    private String lastName;
    private Integer yearNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        Window window = this.getWindow();
        // clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

        // Set up the account form.
        mEmailView = (EditText) findViewById(R.id.email);
        if (!SingletonDataHolder.email.matches(""))
            mEmailView.setText(SingletonDataHolder.email);
        else
            mEmailView.setText("Your registered email");
        mEmailView.setEnabled(false);

        firstnameEt = (EditText) findViewById(R.id.firstName);
        // firstnameEt.requestFocus();
        if (!SingletonDataHolder.firstName.equals(""))
            firstnameEt.setText(SingletonDataHolder.firstName);
        lastnameEt = (EditText) findViewById(R.id.lastName);
        if (!SingletonDataHolder.lastName.equals(""))
            lastnameEt.setText(SingletonDataHolder.lastName);
        birthYearEt = (EditText) findViewById(R.id.brithYear);
        if (SingletonDataHolder.birthYear != 0)
            birthYearEt.setText(Integer.toString(SingletonDataHolder.birthYear));

        maleCheckBox = (CheckBox) findViewById(R.id.maleCheckbox);
        femaleCheckBox = (CheckBox) findViewById(R.id.femaleCheckbox);

        if (SingletonDataHolder.gender == 1) {
            maleCheckBox.setChecked(true);
            maleChecked = true;
            femaleChecked = false;
        }  else {
            femaleCheckBox.setChecked(true);
            maleChecked = false;
            femaleChecked = true;
        }

        birthYearEt = (EditText) findViewById(R.id.brithYear);
        Button saveButton = (Button) findViewById(R.id.account_save_button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firstName = firstnameEt.getText().toString();
                lastName = lastnameEt.getText().toString();
                if (firstName.matches("") || lastName.matches("")) {
                    Toast.makeText(AccountActivity.this, "Please enter your firstname and last name", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!maleChecked && !femaleChecked) {
                    Toast.makeText(AccountActivity.this, "Please choose the gender", Toast.LENGTH_SHORT).show();
                    return;
                }
                else {
                    String dob = birthYearEt.getText().toString();
                    try {
                        yearNum = Integer.parseInt(dob);
                        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
                        if (yearNum >= (currentYear - 100) && yearNum <= (currentYear - 10)) {
                            UpdateProfile();
                        } else {
                            Toast.makeText(AccountActivity.this, "The birth year is out of range", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    } catch (NumberFormatException e) {
                        Toast.makeText(AccountActivity.this, "The birth year is out of range", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                UpdateProfile();
            }
        });
    }

    public void maleClicked(View v) {
        //code to check if this checkbox is checked!
        CheckBox checkBox = (CheckBox) v;
        if (checkBox.isChecked()) {
            maleChecked = true;
            femaleChecked = false;
            femaleCheckBox.setChecked(false);
            femaleCheckBox.setSelected(false);
        }
        else
            maleChecked = false;
    }

    public void femaleClicked(View v) {
        //code to check if this checkbox is checked!
        CheckBox checkBox = (CheckBox) v;
        if (checkBox.isChecked()) {
            femaleChecked = true;
            maleChecked = false;
            maleCheckBox.setChecked(false);
            maleCheckBox.setSelected(false);
        }
        else
            femaleChecked = false;
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
                params.put("firstname", firstName);
                params.put("lastname", lastName);
                params.put("website_id", 1);
                params.put("store_id", 1);
                if (maleChecked)
                    params.put("gender", 1);
                else
                    params.put("gender", 2);
                params.put("dob", birthYearEt.getText().toString() + "-01-01");
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
                    SingletonDataHolder.firstName = firstName;
                    SingletonDataHolder.lastName = lastName;
                    SingletonDataHolder.birthYear = yearNum;
                    if (maleChecked)
                        SingletonDataHolder.gender = 1;
                    else
                        SingletonDataHolder.gender = 2;
                    Toast.makeText(AccountActivity.this, "Profile Saved", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    // showProgress(false);
                    Log.d("Error.Response", error.toString());
                    Toast.makeText(AccountActivity.this, "Server error", Toast.LENGTH_SHORT).show();
                }
            }) {
                @Override
                public byte[] getBody() throws AuthFailureError {
                    Log.i("$$$---AJSON---$$$", finalParams.toString());
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
            Toast.makeText(AccountActivity.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
    }
}
