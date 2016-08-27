package com.eyeque.mono;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.widget.ImageView;
import android.graphics.Typeface;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.Canvas;
import android.graphics.Color;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.view.ViewGroup.LayoutParams;
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

import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DashboardFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DashboardFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DashboardFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private TextView scoreTv;
    private TextView eyeglassNumDescTv;
    private ProgressBar progressBar;
    private Button newEyeglassNumberBtn;
    private TableLayout eyeglassTableLayout;
    private WebView visionSummarydWebView;
    private WebView trackingDataOdWebView;
    private WebView trackingDataOsWebView;
    private TableRow pdTblRow;
    private TableRow dateTblRow;
    private TableRow herderTableRow;
    private TableRow odTableRow;
    private TableRow osTableRow;
    private OnFragmentInteractionListener mListener;

    public DashboardFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DashboardFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DashboardFragment newInstance(String param1, String param2) {
        DashboardFragment fragment = new DashboardFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        int color;
        View rootView = inflater.inflate(R.layout.fragment_dashboard, container, false);

        final TextView eyeglassTitleTv = (TextView) rootView.findViewById(R.id.visionRecordTitle);
        eyeglassTitleTv.setText("EYEGLASS NUMBER ("
                                + SingletonDataHolder.firstName
                                + " "
                                + SingletonDataHolder.lastName
                                + ")");
        // GetDahsboardInfo();

        // Progress bar
        scoreTv = (TextView) rootView.findViewById(R.id.scoreText);
        eyeglassNumDescTv = (TextView) rootView.findViewById(R.id.eyeglassNumberDescription);
        progressBar = (ProgressBar) rootView.findViewById(R.id.progessSeekBar);
        newEyeglassNumberBtn = (Button) rootView.findViewById(R.id.newEyeglassNumber);
        eyeglassTableLayout=(TableLayout) rootView.findViewById(R.id.resultTableLayout);

        // Populate EyeGlass record
        /**
        int numRow = 1;
        for (int i = 0; i < numRow; i++) {

            // Add datetime of the eyeglass number
            TableRow dateTblRow = new TableRow(this.getContext());
            dateTblRow.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
            TextView dataTextView = new TextView(this.getContext());
            dataTextView.setText("2016-06-20 15:37");
            //textview.getTextColors(R.color.)
            dataTextView.setTextColor(Color.BLACK);

            TableRow.LayoutParams dateTextViewParams = new TableRow.LayoutParams();
            // dateTextViewParams.width = LayoutParams.MATCH_PARENT;
            // dateTextViewParams.height = LayoutParams.WRAP_CONTENT;
            dateTextViewParams.column = 0;
            dateTextViewParams.span = 4;
            dateTextViewParams.gravity = Gravity.CENTER;
            dateTextViewParams.topMargin = 20;
            dateTblRow.addView(dataTextView, dateTextViewParams);
            eyeglassTableLayout.addView(dateTblRow);

            // Add datetime of the eyeglass number
            TableRow  herderTableRow = new TableRow(this.getContext());
            herderTableRow.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));

            TextView sphTextView = new TextView(this.getContext());
            sphTextView.setTextColor(Color.BLACK);
            sphTextView.setGravity(1);
            sphTextView.setText("SPHERICAL");
            sphTextView.setTextColor(Color.BLACK);
            sphTextView.setTypeface(null, Typeface.BOLD);
            TableRow.LayoutParams sphTextViewParams = new TableRow.LayoutParams();
            sphTextViewParams.width = LayoutParams.MATCH_PARENT;
            sphTextViewParams.height = LayoutParams.WRAP_CONTENT;
            sphTextViewParams.column = 1;
            sphTextViewParams.gravity = Gravity.CENTER;
            herderTableRow.addView(sphTextView, sphTextViewParams);

            TextView cylTextView = new TextView(this.getContext());
            cylTextView.setTextColor(Color.BLACK);
            cylTextView.setGravity(1);
            cylTextView.setText("CYLINDRICAL");
            cylTextView.setLayoutParams(new TableRow.LayoutParams(2));
            cylTextView.setTextColor(Color.BLACK);
            cylTextView.setTypeface(null, Typeface.BOLD);
            herderTableRow.addView(cylTextView);

            TextView axisTextView = new TextView(this.getContext());
            axisTextView.setTextColor(Color.BLACK);
            axisTextView.setGravity(1);
            axisTextView.setText("AXIS");
            axisTextView.setLayoutParams(new TableRow.LayoutParams(3));
            axisTextView.setTextColor(Color.BLACK);
            axisTextView.setTypeface(null, Typeface.BOLD);
            herderTableRow.addView(axisTextView);
            eyeglassTableLayout.addView(herderTableRow, new TableLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));


            //Add OD Values of eyeglass number
            TableRow  odTableRow = new TableRow(this.getContext());
            odTableRow.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));

            TextView odHeaderTextView = new TextView(this.getContext());
            odHeaderTextView.setTextColor(Color.BLACK);
            odHeaderTextView.setGravity(1);
            odHeaderTextView.setText("OD (Right)");
            odHeaderTextView.setTextColor(Color.BLACK);
            odHeaderTextView.setTypeface(null, Typeface.BOLD);
            TableRow.LayoutParams odHeaderTextViewParams = new TableRow.LayoutParams();
            odHeaderTextViewParams.width = LayoutParams.MATCH_PARENT;
            odHeaderTextViewParams.height = LayoutParams.WRAP_CONTENT;
            odHeaderTextViewParams.column = 0;
            odHeaderTextViewParams.gravity = Gravity.CENTER;
            odTableRow.addView(odHeaderTextView, odHeaderTextViewParams);

            TextView odSphTextView = new TextView(this.getContext());
            odSphTextView.setTextColor(Color.BLACK);
            odSphTextView.setGravity(1);
            odSphTextView.setText("-2.25");
            odSphTextView.setTextColor(Color.BLACK);
            odSphTextView.setTypeface(null, Typeface.BOLD);
            TableRow.LayoutParams odSphTextViewParams = new TableRow.LayoutParams();
            odSphTextViewParams.width = LayoutParams.MATCH_PARENT;
            odSphTextViewParams.height = LayoutParams.WRAP_CONTENT;
            odSphTextViewParams.column = 1;
            odSphTextViewParams.gravity = Gravity.CENTER;
            odTableRow.addView(odSphTextView, odSphTextViewParams);


            TextView odCylTextView = new TextView(this.getContext());
            odCylTextView.setTextColor(Color.BLACK);
            odCylTextView.setGravity(1);
            odCylTextView.setText("-0.50");
            odCylTextView.setLayoutParams(new TableRow.LayoutParams(2));
            odCylTextView.setTextColor(Color.BLACK);
            odCylTextView.setTypeface(null, Typeface.BOLD);
            TableRow.LayoutParams odCylTextViewParams = new TableRow.LayoutParams();
            odCylTextViewParams.width = LayoutParams.MATCH_PARENT;
            odCylTextViewParams.height = LayoutParams.WRAP_CONTENT;
            odCylTextViewParams.column = 2;
            odCylTextViewParams.gravity = Gravity.CENTER;
            odTableRow.addView(odCylTextView, odCylTextViewParams);

            TextView odAxisTextView = new TextView(this.getContext());
            odAxisTextView.setTextColor(Color.BLACK);
            odAxisTextView.setGravity(1);
            odAxisTextView.setText("24");
            odAxisTextView.setLayoutParams(new TableRow.LayoutParams(3));
            odAxisTextView.setTextColor(Color.BLACK);
            odAxisTextView.setTypeface(null, Typeface.BOLD);
            TableRow.LayoutParams odAxisTextViewParams = new TableRow.LayoutParams();
            odAxisTextViewParams.width = LayoutParams.MATCH_PARENT;
            odAxisTextViewParams.height = LayoutParams.WRAP_CONTENT;
            odAxisTextViewParams.column = 3;
            odAxisTextViewParams.gravity = Gravity.CENTER;
            odTableRow.addView(odAxisTextView, odAxisTextViewParams);

            eyeglassTableLayout.addView(odTableRow, new TableLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));

            // Add OS Values of eyeglass number
            TableRow  osTableRow = new TableRow(this.getContext());
            // osTableRow.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
            TableRow.LayoutParams osTAbleRowParams = new TableRow.LayoutParams();
            osTAbleRowParams.bottomMargin = 20;

            TextView osHeaderTextView = new TextView(this.getContext());
            osHeaderTextView.setTextColor(Color.BLACK);
            osHeaderTextView.setGravity(1);
            osHeaderTextView.setText("OS (Left)");
            osHeaderTextView.setTextColor(Color.BLACK);
            osHeaderTextView.setTypeface(null, Typeface.BOLD);
            TableRow.LayoutParams osHeaderTextViewParams = new TableRow.LayoutParams();
            osHeaderTextViewParams.width = LayoutParams.MATCH_PARENT;
            osHeaderTextViewParams.height = LayoutParams.WRAP_CONTENT;
            osHeaderTextViewParams.column = 0;
            osHeaderTextViewParams.gravity = Gravity.CENTER;
            osTableRow.addView(osHeaderTextView, osHeaderTextViewParams);

            TextView osSphTextView = new TextView(this.getContext());
            osSphTextView.setTextColor(Color.BLACK);
            osSphTextView.setGravity(1);
            osSphTextView.setText("-2.75");
            osSphTextView.setTextColor(Color.BLACK);
            osSphTextView.setTypeface(null, Typeface.BOLD);
            TableRow.LayoutParams osSphTextViewParams = new TableRow.LayoutParams();
            osSphTextViewParams.width = LayoutParams.MATCH_PARENT;
            osSphTextViewParams.height = LayoutParams.WRAP_CONTENT;
            osSphTextViewParams.column = 1;
            osSphTextViewParams.gravity = Gravity.CENTER;
            osTableRow.addView(osSphTextView, osSphTextViewParams);


            TextView osCylTextView = new TextView(this.getContext());
            osCylTextView.setTextColor(Color.BLACK);
            osCylTextView.setGravity(1);
            osCylTextView.setText("-0.25");
            osCylTextView.setLayoutParams(new TableRow.LayoutParams(2));
            osCylTextView.setTextColor(Color.BLACK);
            osCylTextView.setTypeface(null, Typeface.BOLD);
            TableRow.LayoutParams osCylTextViewParams = new TableRow.LayoutParams();
            osCylTextViewParams.width = LayoutParams.MATCH_PARENT;
            osCylTextViewParams.height = LayoutParams.WRAP_CONTENT;
            osCylTextViewParams.column = 2;
            osCylTextViewParams.gravity = Gravity.CENTER;
            osTableRow.addView(osCylTextView, osCylTextViewParams);

            TextView osAxisTextView = new TextView(this.getContext());
            osAxisTextView.setTextColor(Color.BLACK);
            osAxisTextView.setGravity(1);
            osAxisTextView.setText("153");
            osAxisTextView.setLayoutParams(new TableRow.LayoutParams(3));
            osAxisTextView.setTextColor(Color.BLACK);
            osAxisTextView.setTypeface(null, Typeface.BOLD);
            TableRow.LayoutParams osAxisTextViewParams = new TableRow.LayoutParams();
            osAxisTextViewParams.width = LayoutParams.MATCH_PARENT;
            osAxisTextViewParams.height = LayoutParams.WRAP_CONTENT;
            osAxisTextViewParams.column = 3;
            osAxisTextViewParams.gravity = Gravity.CENTER;
            osTableRow.addView(osAxisTextView, osAxisTextViewParams);

            eyeglassTableLayout.addView(osTableRow, osTAbleRowParams);

            if (i < numRow - 1) {
                TextView divLine = new TextView(this.getContext());
                divLine.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, 1));
                divLine.setBackgroundColor(Color.rgb(51, 51, 51));
                eyeglassTableLayout.addView(divLine);
            }
        }

         ***/

        /***
        BitmapFactory.Options myOptions = new BitmapFactory.Options();
        myOptions.inDither = true;
        myOptions.inScaled = false;
        myOptions.inPreferredConfig = Bitmap.Config.ARGB_8888;// important
        myOptions.inPurgeable = true;

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.vision_map_orange3,myOptions);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        color = Color.rgb(29, 173, 243);
        // paint.setColor(Color.WHITE);
        paint.setColor(color);

        Bitmap workingBitmap = Bitmap.createBitmap(bitmap);
        Bitmap mutableBitmap = workingBitmap.copy(Bitmap.Config.ARGB_8888, true);

        Canvas canvas = new Canvas(mutableBitmap);
        paint.setColor(Color.WHITE);
        canvas.drawCircle(200, 310, 20, paint);
        canvas.drawCircle(310, 285, 20, paint);
        paint.setColor(color);
        canvas.drawCircle(200, 310, 12, paint);
        canvas.drawCircle(310, 285, 12, paint);
         ***/


        // Show vision summary webview
        visionSummarydWebView = (WebView) rootView.findViewById(R.id.visionSummaryWebView);
        // WebSettings settings0 = visionSummarydWebView.getSettings();
        // settings0.setJavaScriptEnabled(true);
        // webview.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        visionSummarydWebView.getSettings().setLoadWithOverviewMode(true);
        visionSummarydWebView.getSettings().setUseWideViewPort(true);
        visionSummarydWebView.getSettings().setAppCachePath( getActivity().getCacheDir().getAbsolutePath() );
        visionSummarydWebView.getSettings().setAllowFileAccess( true );
        visionSummarydWebView.getSettings().setAppCacheEnabled( true );
        visionSummarydWebView.getSettings().setJavaScriptEnabled( true );
        visionSummarydWebView.getSettings().setCacheMode( WebSettings.LOAD_DEFAULT ); // load online by default




        // visionSummarydWebView.loadUrl(SingletonDataHolder.urlVisionSummary);

        // Rendering tracking data
        trackingDataOdWebView = (WebView) rootView.findViewById(R.id.trackingDataOdWebView);
        trackingDataOdWebView.getSettings().setLoadWithOverviewMode(true);
        trackingDataOdWebView.getSettings().setUseWideViewPort(true);
        trackingDataOdWebView.getSettings().setAppCachePath( getActivity().getCacheDir().getAbsolutePath() );
        trackingDataOdWebView.getSettings().setAllowFileAccess( true );
        trackingDataOdWebView.getSettings().setAppCacheEnabled( true );
        trackingDataOdWebView.getSettings().setJavaScriptEnabled( true );
        trackingDataOdWebView.getSettings().setCacheMode( WebSettings.LOAD_DEFAULT ); // load online by default

        // trackingDataOdWebView.loadUrl(SingletonDataHolder.urlOdTracking);

        trackingDataOsWebView = (WebView) rootView.findViewById(R.id.trackingDataOsWebView);
        trackingDataOsWebView.getSettings().setLoadWithOverviewMode(true);
        trackingDataOsWebView.getSettings().setUseWideViewPort(true);
        trackingDataOsWebView.getSettings().setAppCachePath( getActivity().getCacheDir().getAbsolutePath() );
        trackingDataOsWebView.getSettings().setAllowFileAccess( true );
        trackingDataOsWebView.getSettings().setAppCacheEnabled( true );
        trackingDataOsWebView.getSettings().setJavaScriptEnabled( true );
        trackingDataOsWebView.getSettings().setCacheMode( WebSettings.LOAD_DEFAULT ); // load online by default

        GetDahsboardInfo();
        NetConnection conn = new NetConnection();
        if (!conn.isConnected(getActivity())) {
            visionSummarydWebView.getSettings().setCacheMode( WebSettings.LOAD_CACHE_ELSE_NETWORK );
        }
        if (!conn.isConnected(getActivity())) {
            trackingDataOdWebView.getSettings().setCacheMode( WebSettings.LOAD_CACHE_ELSE_NETWORK );
        }
        if (!conn.isConnected(getActivity())) {
            trackingDataOsWebView.getSettings().setCacheMode( WebSettings.LOAD_CACHE_ELSE_NETWORK );
        }
        // trackingDataOsWebView.loadUrl(SingletonDataHolder.urlOSTracking);

        // Log.i("**** DEBUG ****", "Recall onCreate()");
        // Inflate the layout for this fragment
        // return inflater.inflate(R.layout.fragment_dashboard, container, false);
        return rootView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onDashboardFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onDashboardFragmentInteraction(Uri uri);
    }

    private void GetDahsboardInfo() {

        String url = Constants.UrlDashboard;
        NetConnection conn = new NetConnection();
        if (conn.isConnected(getActivity())) {

            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            // showProgress(true);
            RequestQueue queue = Volley.newRequestQueue(getActivity());

            final JSONObject params = new JSONObject();
            final JSONArray eyeglassNumArray = new JSONArray();

            StringRequest postRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.i("*** Dashboard Info ***", response);
                    try {
                        final JSONObject result = new JSONObject(response);
                        final JSONObject eyeglassResult = result.getJSONObject("eyeglassnumbers");
                        final JSONArray eyeglassNumList = eyeglassResult.getJSONArray("purchasednumbers");

                        SingletonDataHolder.urlOdTracking = result.getString("url_spheod");
                        SingletonDataHolder.urlOSTracking = result.getString("url_spheos");
                        SingletonDataHolder.urlVisionSummary = result.getString("url_vision_summary");
                        SingletonDataHolder.pupillaryDistance = eyeglassResult.getInt("pd") + 62;
                        SingletonDataHolder.currentTestScore = eyeglassResult.getInt("score");
                        SingletonDataHolder.eyeglassNumPurchasable = eyeglassResult.getBoolean("purchasable");
                        SingletonDataHolder.eyeglassNumCount = eyeglassNumList.length();

                        Log.i("*****PD******", Integer.toString(SingletonDataHolder.pupillaryDistance));
                        Log.i("*** Purchasable ***", Boolean.toString(SingletonDataHolder.eyeglassNumPurchasable));

                        SingletonDataHolder.eyeglassNumberList = new SingletonDataHolder.EyeglassNumber[SingletonDataHolder.eyeglassNumCount];
                        for (int i = 0; i < SingletonDataHolder.eyeglassNumCount; i++) {
                            JSONObject eyeglassNumber = eyeglassNumList.getJSONObject(i);
                            SingletonDataHolder.eyeglassNumberList[i] = new SingletonDataHolder.EyeglassNumber(
                                    eyeglassNumber.getDouble("sphOD"),
                                    eyeglassNumber.getDouble("cylOD"),
                                    eyeglassNumber.getInt("axisOD"),
                                    eyeglassNumber.getDouble("sphOS"),
                                    eyeglassNumber.getDouble("cylOS"),
                                    eyeglassNumber.getInt("axisOS"),
                                    eyeglassNumber.getString("createdAt"));
                        }
                        loadData();
                        loadEyeglassNumber();
                        // Log.i("*** Tracking OD ***", result.getString("url_spheod"));
                        // Log.i("*** Tracking OS ***", result.getString("url_spheos"));
                        // Log.i("*** Score ***", Integer.toString(SingletonDataHolder.currentTestScore));
                        // Log.i("*** Purchasable ***", Boolean.toString(SingletonDataHolder.eyeglassNumPurchasable));
                        // trackingDataOdWebView.clearCache(true);
                        visionSummarydWebView.loadUrl(SingletonDataHolder.urlVisionSummary);
                        trackingDataOdWebView.loadUrl(SingletonDataHolder.urlOdTracking);
                        trackingDataOsWebView.loadUrl(SingletonDataHolder.urlOSTracking);
                    } catch (JSONException e) {
                        Toast.makeText(getActivity(),
                                "Operation failed, please try it again", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    // showProgress(false);
                    Log.d("Error.Response", error.toString());
                    Toast.makeText(getActivity(), "Server Error", Toast.LENGTH_SHORT).show();
                }
            }) {

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
                    return headers;
                }
            };
            RetryPolicy policy = new DefaultRetryPolicy(Constants.NETCONN_TIMEOUT_VALUE, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
            postRequest.setRetryPolicy(policy);
            queue.add(postRequest);
        }
        else {
            loadData();
            loadEyeglassNumber();
            visionSummarydWebView.loadUrl(SingletonDataHolder.urlVisionSummary);
            trackingDataOdWebView.loadUrl(SingletonDataHolder.urlOdTracking);
            trackingDataOsWebView.loadUrl(SingletonDataHolder.urlOSTracking);
        }
    }

    private void loadData() {
        scoreTv.setText("Your test score: " + Integer.toString(SingletonDataHolder.currentTestScore));
        if (SingletonDataHolder.currentTestScore >= 100) {
            progressBar.setProgress(100);
            eyeglassNumDescTv.setText("Your new eyeglass number is available");
        } else {
            progressBar.setProgress(SingletonDataHolder.currentTestScore);
            eyeglassNumDescTv.setText("Your eyeglass number will be avaiable once your test score reaches 100");
        }

        if (SingletonDataHolder.eyeglassNumPurchasable)
            newEyeglassNumberBtn.setClickable(true);
        else {
            newEyeglassNumberBtn.setClickable(false);
            newEyeglassNumberBtn.setBackgroundColor(Color.LTGRAY);
            newEyeglassNumberBtn.setTextColor(Color.WHITE);
        }
    }

    private void loadEyeglassNumber() {

        if (pdTblRow != null)
            eyeglassTableLayout.removeView(pdTblRow);
        if (dateTblRow != null)
            eyeglassTableLayout.removeView(dateTblRow);
        if (odTableRow != null)
        eyeglassTableLayout.removeView(odTableRow);
        if (osTableRow != null)
            eyeglassTableLayout.removeView(osTableRow);
        if (osTableRow != null)
            eyeglassTableLayout.removeView(herderTableRow);

        if (SingletonDataHolder.pupillaryDistance > 0) {
            pdTblRow = new TableRow(getActivity());
            pdTblRow.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
            TextView pdTextView = new TextView(getActivity());
            pdTextView.setText("PD: " + Integer.toString(SingletonDataHolder.pupillaryDistance));
            pdTextView.setTextColor(Color.BLACK);
            TableRow.LayoutParams pdTextViewParams = new TableRow.LayoutParams();
            pdTextViewParams.column = 0;
            pdTextViewParams.span = 4;
            pdTextViewParams.gravity = Gravity.CENTER;
            pdTextViewParams.topMargin = 20;
            pdTblRow.addView(pdTextView, pdTextViewParams);
            eyeglassTableLayout.addView(pdTblRow);
        }
        for (int i = 0; i < SingletonDataHolder.eyeglassNumCount; i++) {

            // Add datetime of the eyeglass number
            // dateTblRow = new TableRow(this.getContext());
            dateTblRow = new TableRow(getActivity());
            dateTblRow.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
            TextView dataTextView = new TextView(getActivity());
            dataTextView.setText(SingletonDataHolder.eyeglassNumberList[i].createdAt);
            //textview.getTextColors(R.color.)
            dataTextView.setTextColor(Color.BLACK);

            TableRow.LayoutParams dateTextViewParams = new TableRow.LayoutParams();
            // dateTextViewParams.width = LayoutParams.MATCH_PARENT;
            // dateTextViewParams.height = LayoutParams.WRAP_CONTENT;
            dateTextViewParams.column = 0;
            dateTextViewParams.span = 4;
            dateTextViewParams.gravity = Gravity.CENTER;
            dateTextViewParams.topMargin = 20;
            dateTblRow.addView(dataTextView, dateTextViewParams);
            eyeglassTableLayout.addView(dateTblRow);

            // Add datetime of the eyeglass number
            herderTableRow = new TableRow(getActivity());
            herderTableRow.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));

            TextView sphTextView = new TextView(getActivity());
            sphTextView.setTextColor(Color.BLACK);
            sphTextView.setGravity(1);
            sphTextView.setText("SPHERICAL");
            sphTextView.setTextColor(Color.BLACK);
            sphTextView.setTypeface(null, Typeface.BOLD);
            TableRow.LayoutParams sphTextViewParams = new TableRow.LayoutParams();
            sphTextViewParams.width = LayoutParams.MATCH_PARENT;
            sphTextViewParams.height = LayoutParams.WRAP_CONTENT;
            sphTextViewParams.column = 1;
            sphTextViewParams.gravity = Gravity.CENTER;
            herderTableRow.addView(sphTextView, sphTextViewParams);

            TextView cylTextView = new TextView(getActivity());
            cylTextView.setTextColor(Color.BLACK);
            cylTextView.setGravity(1);
            cylTextView.setText("CYLINDRICAL");
            cylTextView.setLayoutParams(new TableRow.LayoutParams(2));
            cylTextView.setTextColor(Color.BLACK);
            cylTextView.setTypeface(null, Typeface.BOLD);
            herderTableRow.addView(cylTextView);

            TextView axisTextView = new TextView(getActivity());
            axisTextView.setTextColor(Color.BLACK);
            axisTextView.setGravity(1);
            axisTextView.setText("AXIS");
            axisTextView.setLayoutParams(new TableRow.LayoutParams(3));
            axisTextView.setTextColor(Color.BLACK);
            axisTextView.setTypeface(null, Typeface.BOLD);
            herderTableRow.addView(axisTextView);
            eyeglassTableLayout.addView(herderTableRow, new TableLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));


            //Add OD Values of eyeglass number
            odTableRow = new TableRow(getActivity());
            odTableRow.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));

            TextView odHeaderTextView = new TextView(getActivity());
            odHeaderTextView.setTextColor(Color.BLACK);
            odHeaderTextView.setGravity(1);
            odHeaderTextView.setText("OD (Right)");
            odHeaderTextView.setTextColor(Color.BLACK);
            odHeaderTextView.setTypeface(null, Typeface.BOLD);
            TableRow.LayoutParams odHeaderTextViewParams = new TableRow.LayoutParams();
            odHeaderTextViewParams.width = LayoutParams.MATCH_PARENT;
            odHeaderTextViewParams.height = LayoutParams.WRAP_CONTENT;
            odHeaderTextViewParams.column = 0;
            odHeaderTextViewParams.gravity = Gravity.CENTER;
            odTableRow.addView(odHeaderTextView, odHeaderTextViewParams);

            TextView odSphTextView = new TextView(getActivity());
            odSphTextView.setTextColor(Color.BLACK);
            odSphTextView.setGravity(1);
            odSphTextView.setText(String.format("%.2f", SingletonDataHolder.eyeglassNumberList[i].odSph));
            odSphTextView.setTextColor(Color.BLACK);
            odSphTextView.setTypeface(null, Typeface.BOLD);
            TableRow.LayoutParams odSphTextViewParams = new TableRow.LayoutParams();
            odSphTextViewParams.width = LayoutParams.MATCH_PARENT;
            odSphTextViewParams.height = LayoutParams.WRAP_CONTENT;
            odSphTextViewParams.column = 1;
            odSphTextViewParams.gravity = Gravity.CENTER;
            odTableRow.addView(odSphTextView, odSphTextViewParams);


            TextView odCylTextView = new TextView(getActivity());
            odCylTextView.setTextColor(Color.BLACK);
            odCylTextView.setGravity(1);
            odCylTextView.setText(String.format("%.2f", SingletonDataHolder.eyeglassNumberList[i].odCyl));
            odCylTextView.setLayoutParams(new TableRow.LayoutParams(2));
            odCylTextView.setTextColor(Color.BLACK);
            odCylTextView.setTypeface(null, Typeface.BOLD);
            TableRow.LayoutParams odCylTextViewParams = new TableRow.LayoutParams();
            odCylTextViewParams.width = LayoutParams.MATCH_PARENT;
            odCylTextViewParams.height = LayoutParams.WRAP_CONTENT;
            odCylTextViewParams.column = 2;
            odCylTextViewParams.gravity = Gravity.CENTER;
            odTableRow.addView(odCylTextView, odCylTextViewParams);

            TextView odAxisTextView = new TextView(getActivity());
            odAxisTextView.setTextColor(Color.BLACK);
            odAxisTextView.setGravity(1);
            odAxisTextView.setText(Integer.toString(SingletonDataHolder.eyeglassNumberList[i].odAxis));
            odAxisTextView.setLayoutParams(new TableRow.LayoutParams(3));
            odAxisTextView.setTextColor(Color.BLACK);
            odAxisTextView.setTypeface(null, Typeface.BOLD);
            TableRow.LayoutParams odAxisTextViewParams = new TableRow.LayoutParams();
            odAxisTextViewParams.width = LayoutParams.MATCH_PARENT;
            odAxisTextViewParams.height = LayoutParams.WRAP_CONTENT;
            odAxisTextViewParams.column = 3;
            odAxisTextViewParams.gravity = Gravity.CENTER;
            odTableRow.addView(odAxisTextView, odAxisTextViewParams);

            eyeglassTableLayout.addView(odTableRow, new TableLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));

            // Add OS Values of eyeglass number
            osTableRow = new TableRow(getActivity());
            // osTableRow.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
            TableRow.LayoutParams osTAbleRowParams = new TableRow.LayoutParams();
            osTAbleRowParams.bottomMargin = 20;

            TextView osHeaderTextView = new TextView(getActivity());
            osHeaderTextView.setTextColor(Color.BLACK);
            osHeaderTextView.setGravity(1);
            osHeaderTextView.setText("OS (Left)");
            osHeaderTextView.setTextColor(Color.BLACK);
            osHeaderTextView.setTypeface(null, Typeface.BOLD);
            TableRow.LayoutParams osHeaderTextViewParams = new TableRow.LayoutParams();
            osHeaderTextViewParams.width = LayoutParams.MATCH_PARENT;
            osHeaderTextViewParams.height = LayoutParams.WRAP_CONTENT;
            osHeaderTextViewParams.column = 0;
            osHeaderTextViewParams.gravity = Gravity.CENTER;
            osTableRow.addView(osHeaderTextView, osHeaderTextViewParams);

            TextView osSphTextView = new TextView(getActivity());
            osSphTextView.setTextColor(Color.BLACK);
            osSphTextView.setGravity(1);
            osSphTextView.setText(String.format("%.2f", SingletonDataHolder.eyeglassNumberList[i].osSph));
            osSphTextView.setTextColor(Color.BLACK);
            osSphTextView.setTypeface(null, Typeface.BOLD);
            TableRow.LayoutParams osSphTextViewParams = new TableRow.LayoutParams();
            osSphTextViewParams.width = LayoutParams.MATCH_PARENT;
            osSphTextViewParams.height = LayoutParams.WRAP_CONTENT;
            osSphTextViewParams.column = 1;
            osSphTextViewParams.gravity = Gravity.CENTER;
            osTableRow.addView(osSphTextView, osSphTextViewParams);


            TextView osCylTextView = new TextView(getActivity());
            osCylTextView.setTextColor(Color.BLACK);
            osCylTextView.setGravity(1);
            osCylTextView.setText(String.format("%.2f", SingletonDataHolder.eyeglassNumberList[i].osCyl));
            osCylTextView.setLayoutParams(new TableRow.LayoutParams(2));
            osCylTextView.setTextColor(Color.BLACK);
            osCylTextView.setTypeface(null, Typeface.BOLD);
            TableRow.LayoutParams osCylTextViewParams = new TableRow.LayoutParams();
            osCylTextViewParams.width = LayoutParams.MATCH_PARENT;
            osCylTextViewParams.height = LayoutParams.WRAP_CONTENT;
            osCylTextViewParams.column = 2;
            osCylTextViewParams.gravity = Gravity.CENTER;
            osTableRow.addView(osCylTextView, osCylTextViewParams);

            TextView osAxisTextView = new TextView(getActivity());
            osAxisTextView.setTextColor(Color.BLACK);
            osAxisTextView.setGravity(1);
            osAxisTextView.setText(Integer.toString(SingletonDataHolder.eyeglassNumberList[i].osAxis));
            osAxisTextView.setLayoutParams(new TableRow.LayoutParams(3));
            osAxisTextView.setTextColor(Color.BLACK);
            osAxisTextView.setTypeface(null, Typeface.BOLD);
            TableRow.LayoutParams osAxisTextViewParams = new TableRow.LayoutParams();
            osAxisTextViewParams.width = LayoutParams.MATCH_PARENT;
            osAxisTextViewParams.height = LayoutParams.WRAP_CONTENT;
            osAxisTextViewParams.column = 3;
            osAxisTextViewParams.gravity = Gravity.CENTER;
            osTableRow.addView(osAxisTextView, osAxisTextViewParams);

            eyeglassTableLayout.addView(osTableRow, osTAbleRowParams);

            if (i < SingletonDataHolder.eyeglassNumCount - 1) {
                TextView divLine = new TextView(getActivity());
                divLine.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, 1));
                divLine.setBackgroundColor(Color.rgb(51, 51, 51));
                eyeglassTableLayout.addView(divLine);
            }
        }
    }
}
