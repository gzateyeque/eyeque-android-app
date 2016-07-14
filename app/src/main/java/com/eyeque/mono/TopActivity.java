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
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnMenuTabSelectedListener;

public class TopActivity extends AppCompatActivity implements DashboardFragment.OnFragmentInteractionListener {
    private CoordinatorLayout coordinatorLayout;
    private static final String TAG = "Home";

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
        window.setStatusBarColor(this.getResources().getColor(R.color.colorPrimaryDark));

        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.top_activity);

        BottomBar bottomBar = BottomBar.attach(this, savedInstanceState);
        bottomBar.setItemsFromMenu(R.menu.three_buttons_menu, new OnMenuTabSelectedListener() {
            @Override
            public void onMenuItemSelected(int itemId) {
                switch (itemId) {
                    case R.id.home_item:
                        // setContentView(R.layout.activity_top);
                        Snackbar.make(coordinatorLayout, "Home Item Selected", Snackbar.LENGTH_LONG).show();
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
                        break;
                }
            }
        });

        // Set the color for the active tab. Ignored on mobile when there are more than three tabs.
        // bottomBar.setActiveTabColor("#C2185B");
        bottomBar.setActiveTabColor("#046EEA");

        // get fragment manager
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.frame_container, new DashboardFragment(), "dashboard");
        // alternatively add it with a tag
        // trx.add(R.id.your_placehodler, new YourFragment(), "detail");
        ft.commit();


        // Use the dark theme. Ignored on mobile when there are more than three tabs.
        //bottomBar.useDarkTheme(true);

        // Use custom text appearance in tab titles.
        //bottomBar.setTextAppearance(R.style.MyTextAppearance);

        // Use custom typeface that's located at the "/src/main/assets" directory. If using with
        // custom text appearance, set the text appearance first.
        //bottomBar.setTypeFace("MyFont.ttf");
    }

    public void onFragmentInteraction(Uri uri) {}
}