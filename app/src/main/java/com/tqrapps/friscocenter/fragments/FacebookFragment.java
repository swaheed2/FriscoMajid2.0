package com.tqrapps.friscocenter.fragments;


import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import com.tqrapps.friscocenter.R;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 * Use the {@link FacebookFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FacebookFragment extends Fragment  {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    WebView facebookWeb;
    String WEB_URL = "http://www.facebook.com/plugins/likebox.php?href=https%3A%2F%2Fwww.facebook.com%2FFriscoMasjid&width=400px&height=700&colorscheme=light&show_faces=false&header=false&stream=true&show_border=false&appId=744004655693608";
    ProgressDialog progressDialog;
    SwipeRefreshLayout swipeView;
    private ViewTreeObserver.OnScrollChangedListener mOnScrollChangedListener;

    public FacebookFragment() {

    }

    // TODO: Rename and change types and number of parameters
    public static FacebookFragment newInstance(String param1, String param2) {
        FacebookFragment fragment = new FacebookFragment();
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
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.facebook_feed, container, false);
        facebookWeb = (WebView) view.findViewById(R.id.facebookWeb);
        facebookWeb.setWebViewClient(new WebViewClient());
        WebSettings webSettings = facebookWeb.getSettings();
        webSettings.setJavaScriptEnabled(true);
        facebookWeb.setVerticalScrollbarOverlay(true);
        facebookWeb.setHorizontalScrollbarOverlay(true);
        facebookWeb.canGoBack();
        facebookWeb.isClickable();
        facebookWeb.loadUrl(WEB_URL);

        progressDialog = new ProgressDialog(getActivity());

        swipeView = (SwipeRefreshLayout) view.findViewById(R.id.swipeFb);
        swipeView.setProgressBackgroundColor(R.color.blue_light);
        swipeView.setColorSchemeColors(R.color.red, R.color.app_green_dark, R.color.blue_light, R.color.orange_light);
        swipeView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                try {
                    progressDialog.setMessage("loading...");
                    progressDialog.show();
                }
                catch (Exception e){

                }
                TypedValue typed_value = new TypedValue();
                getActivity().getTheme().resolveAttribute(android.support.v7.appcompat.R.attr.actionBarSize, typed_value, true);
                swipeView.setProgressViewOffset(false, 0, getResources().getDimensionPixelSize(typed_value.resourceId));
                facebookWeb.loadUrl(WEB_URL);

            }
        });
        facebookWeb.setWebViewClient(new WebViewClient() {
            public void onPageFinished(WebView view, String url) {
                swipeView.setRefreshing(false);
                try {
                    progressDialog.dismiss();
                }
                catch (Exception e){

                }
            }

            public void onPageStarted(WebView view, String url, Bitmap favicon) {


            }

            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                StringBuilder html = new StringBuilder();
                html.append("<html>");
                html.append("<head>");
                //  html.append("<link rel=stylesheet href='css/style.css'>");
                html.append("</head>");
                html.append("<body>");
                html.append("<h1>Error</h1>");
                html.append("<h2>");
                html.append(description + "</h2>");
                html.append("</body></html>");

                facebookWeb.loadDataWithBaseURL("file:///android_asset/", html.toString(), "text/html", "UTF-8", "");
            }
        });
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        swipeView.getViewTreeObserver().addOnScrollChangedListener(mOnScrollChangedListener =
                new ViewTreeObserver.OnScrollChangedListener() {
                    @Override
                    public void onScrollChanged() {
                        if (facebookWeb.getScrollY() == 0)
                            swipeView.setEnabled(true);
                        else
                            swipeView.setEnabled(false);

                    }
                });
    }
    @Override
    public void onStop() {
        swipeView.getViewTreeObserver().removeOnScrollChangedListener(mOnScrollChangedListener);
        super.onStop();
    }

}
