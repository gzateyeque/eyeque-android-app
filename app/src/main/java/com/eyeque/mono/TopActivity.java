package com.eyeque.mono;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.app.Fragment;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
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
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnMenuTabSelectedListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class TopActivity extends AppCompatActivity
        implements AttachDeviceFragment.OnFragmentInteractionListener,
                   Test2Fragment.OnFragmentInteractionListener,
                    DashboardFragment.OnFragmentInteractionListener,
                    SettingFragment.OnFragmentInteractionListener {

    private CoordinatorLayout coordinatorLayout;
    private static final String TAG = "Home";
    private static boolean checkDeviceCompatibility = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.activity_top);
        setContentView(R.layout.activity_top);

        Window window = this.getWindow();
        // clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        // finally change the color
        // window.setStatusBarColor(this.getResources().getColor(R.color.colorPrimaryDark));

        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.top_activity);
        // get fragment manager
        /*
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Fragment dashboardFragment = new DashboardFragment();
        Fragment testFragment = new TestFragment();
        // ft.add(R.id.frame_container, dashboardFragment, "dashboard");
        ft.add(R.id.frame_container, testFragment, "test");
        ft.commit();
        */

        BottomBar bottomBar = BottomBar.attach(this, savedInstanceState);
        bottomBar.setItemsFromMenu(R.menu.three_buttons_menu, new OnMenuTabSelectedListener() {
            @Override
            public void onMenuItemSelected(int itemId) {
                selectMenuItem(itemId);
                /*
                switch (itemId) {
                    case R.id.home_item:
                        // setContentView(R.layout.activity_top);
                        Snackbar.make(coordinatorLayout, "Home Item Selected", Snackbar.LENGTH_LONG).show();
                        ft.add(R.id.frame_container, dashboardFragment, "dashboard");
                        ft.addToBackStack(null);
                        ft.commit();
                        break;
                    case R.id.test_item:
                        Intent i = new Intent(getBaseContext(), MainActivity.class);
                        i.putExtra("subjectId", 21);
                        i.putExtra("deviceId", 3);
                        i.putExtra("serverId", 1);
                        startActivity(i);
                        break;
                    case R.id.account_item:
                        Snackbar.make(coordinatorLayout, "Account Item Selected", Snackbar.LENGTH_LONG).show();
                        // ft.remove(dashboardFragment);
                        ft.add(R.id.frame_container, testFragment, "test");
                        ft.addToBackStack(null);
                        ft.commit();
                        break;
                }
                */
            }
        });

        // Set the color for the active tab. Ignored on mobile when there are more than three tabs.
        // bottomBar.setActiveTabColor("#C2185B");
        bottomBar.setActiveTabColor("#046EEA");
        selectMenuItem(R.id.home_item);
        CheckPhoneCompatibility();

        // ft.add(R.id.frame_container, new DashboardFragment(), "dashboard");
        // alternatively add it with a tag
        // trx.add(R.id.your_placehodler, new YourFragment(), "detail");
        // ft.commit();


        // Use the dark theme. Ignored on mobile when there are more than three tabs.
        //bottomBar.useDarkTheme(true);

        // Use custom text appearance in tab titles.
        //bottomBar.setTextAppearance(R.style.MyTextAppearance);

        // Use custom typeface that's located at the "/src/main/assets" directory. If using with
        // custom text appearance, set the text appearance first.
        //bottomBar.setTypeFace("MyFont.ttf");
    }

    public void selectMenuItem(int menuItem) {
        // Create a new fragment and specify the fragment to show based on nav item clicked
        Fragment fragment = null;
        Class fragmentClass;
        switch(menuItem) {
            case R.id.home_item:
                fragmentClass = DashboardFragment.class;
                break;
            case R.id.test_item:
                if (checkDeviceCompatibility)
                    fragmentClass = AttachDeviceFragment.class;
                else
                    fragmentClass = Test2Fragment.class;
                /***
                if (SingletonDataHolder.phoneType == "Galaxy 6")
                    fragmentClass = AttachDeviceFragment.class;
                else
                    fragmentClass = Test2Fragment.class;
                 ***/
                break;
            case R.id.account_item:
                fragmentClass = SettingFragment.class;
                break;
            default:
                fragmentClass = DashboardFragment.class;
        }

        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Insert the fragment by replacing any existing fragment
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.frame_container, fragment).commit();

    }

    @Override
    public void onAttachDeviceFragmentInteraction(Uri uri) {}

    @Override
    public void onTest2FragmentInteraction(Uri uri) {}

    @Override
    public void onDashboardFragmentInteraction(Uri uri) {}

    @Override
    public void onSettingFragmentInteraction(Uri uri) {}

    private void CheckPhoneCompatibility() {

        checkDeviceCompatibility = false;
        NetConnection conn = new NetConnection();
        if (conn.isConnected(getApplicationContext())) {

            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            // showProgress(true);

            RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
            final String url = Constants.UrlPhoneConfig;
            final JSONObject params = new JSONObject();
            try {
                params.put("name", SingletonDataHolder.devicName);
                params.put("phoneBrand", SingletonDataHolder.phoneBrand);
                params.put("phoneModel", SingletonDataHolder.phoneModel);
                params.put("phoneType", SingletonDataHolder.phoneType);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            StringRequest postRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String string) {
                    // Parse serial check response
                    try {
                        Log.i("*** JSON Device ***", string);
                        JSONObject jsonObj = new JSONObject(string);
                        checkDeviceCompatibility = true;
                        SingletonDataHolder.phoneType = jsonObj.getString("phone_type");
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(TopActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("Error.Response", error.toString());
                    Toast.makeText(TopActivity.this, "Phone incompatible", Toast.LENGTH_SHORT).show();
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
                    String authString = "Bearer " + SingletonDataHolder.getToken();
                    headers.put("Content-Type", "application/json;charset=UTF-8");
                    headers.put("Authorization", authString);
                    Log.i("$$$---HEADER---$$$", headers.toString());
                    return headers;
                }
            };
            RetryPolicy policy = new DefaultRetryPolicy(Constants.NETCONN_TIMEOUT_VALUE, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
            postRequest.setRetryPolicy(policy);
            queue.add(postRequest);
        }
        else
            Toast.makeText(TopActivity.this, "Please connect to the Internet", Toast.LENGTH_SHORT).show();
    }
}
