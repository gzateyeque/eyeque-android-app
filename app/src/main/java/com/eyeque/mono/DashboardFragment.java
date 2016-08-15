package com.eyeque.mono;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.Canvas;
import android.graphics.Color;
import android.webkit.WebView;
import android.widget.Toast;
import android.widget.TextView;

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
    private WebView trackingDataOdWebView;
    private WebView trackingDataOsWebView;

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


        final ImageView imageView = (ImageView) rootView.findViewById(R.id.visionMap);
        imageView.setAdjustViewBounds(true);
        imageView.setImageBitmap(mutableBitmap);

        // Rendering tracking data
        this.trackingDataOdWebView = (WebView) rootView.findViewById(R.id.trackingDataOdWebView);
        WebSettings settings1 = trackingDataOdWebView.getSettings();
        settings1.setJavaScriptEnabled(true);
        // webview.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        trackingDataOdWebView.getSettings().setLoadWithOverviewMode(true);
        trackingDataOdWebView.getSettings().setUseWideViewPort(true);
        trackingDataOdWebView.loadUrl(Constants.UrlTrackingDataOd);

        this.trackingDataOsWebView = (WebView) rootView.findViewById(R.id.trackingDataOsWebView);
        WebSettings settings2 = trackingDataOsWebView.getSettings();
        settings2.setJavaScriptEnabled(true);
        // webview.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        trackingDataOsWebView.getSettings().setLoadWithOverviewMode(true);
        trackingDataOsWebView.getSettings().setUseWideViewPort(true);
        trackingDataOsWebView.loadUrl(Constants.UrlTrackingDataOs);

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
}
