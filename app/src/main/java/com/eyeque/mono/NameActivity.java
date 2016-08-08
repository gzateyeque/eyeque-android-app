package com.eyeque.mono;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.text.Html;
import android.view.WindowManager;
import android.view.Window;
import android.widget.Toast;
import android.util.Log;

public class NameActivity extends AppCompatActivity {
    private WebView webview;
    private EditText firstnameEt;
    private EditText lastnameEt;

    // Tag for log message
    private static final String TAG = NameActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name);

        Window window = this.getWindow();
        // clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        // finally change the color
        // window.setStatusBarColor(this.getResources().getColor(R.color.colorPrimaryDark));

        firstnameEt = (EditText) findViewById(R.id.firstNameEditText);
        firstnameEt.setHint(Html.fromHtml("<small>" + "First Name" + "</small>" ));
        lastnameEt = (EditText) findViewById(R.id.lastNameEditText);
        lastnameEt.setHint(Html.fromHtml("<small>" + "Last Name" + "</small>" ));

        Button nextButton = (Button) findViewById(R.id.nameNextButton);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String firstname = firstnameEt.getText().toString();
                String lastname = lastnameEt.getText().toString();
                if (firstname.matches(""))
                    Toast.makeText(NameActivity.this, "Please enter your first name", Toast.LENGTH_SHORT).show();
                else if (lastname.matches(""))
                    Toast.makeText(NameActivity.this, "Please enter your last name", Toast.LENGTH_SHORT).show();
                else {
                    SingletonDataHolder.setFirstName(firstname);
                    SingletonDataHolder.setLastName(lastname);
                    Intent genderIntent = new Intent(getBaseContext(), GenderActivity.class);
                    startActivity(genderIntent);
                }
            }
        });
    }

    public static boolean isEmptyString(String text) {
        return (text == null || text.trim().equals("null") || text.trim()
                .length() <= 0);
    }
}

