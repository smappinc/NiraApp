package com.nira.niradroid.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.view.MenuItem;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.Toast;

import com.nira.niradroid.R;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.Objects;

import im.delight.android.webview.AdvancedWebView;


public class WebViewActivity extends AppCompatActivity implements AdvancedWebView.Listener {

    ProgressDialog progressDialog;
    String title, link;
    String newUA = "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:99.0) Gecko/20100101 Firefox/99.0";

    private AdvancedWebView mWebView;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        // Get the connectivity manager
        ConnectivityManager cm = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);

        // Get the active network info
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();

            Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);

            // A progress dialog shown as the webview is loading
            progressDialog = new ProgressDialog(this,R.style.AppCompatAlertDialogStyle);
            progressDialog.setMessage("Loading...");
            progressDialog.setCancelable(false);
            progressDialog.show();


            Intent intent = getIntent();
            title = intent.getStringExtra("title");
            link = intent.getStringExtra("link");

            mWebView = findViewById(R.id.webview);
            mWebView.setListener(this, this);
            mWebView.setMixedContentAllowed(false);

            // Checks if the device is connected to the internet. If so load the url link,
            // if not set content view to no_internet_layout
            if (networkInfo != null && networkInfo.isConnected()) {
            mWebView.loadUrl(link);
            mWebView.getSettings().setSupportMultipleWindows(true);
            mWebView.getSettings().setJavaScriptEnabled(true);
            setTitle(title);


            mWebView.setWebChromeClient(new WebChromeClient() {
                @Override
                public boolean onCreateWindow(WebView view, boolean isDialog, boolean isUserGesture, Message resultMsg) {
                    AdvancedWebView newWebView = new AdvancedWebView(WebViewActivity.this);

                    // Commented out cause of a bug
                    // myParentLayout.addView(newWebView, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
                    WebView.WebViewTransport transport = (WebView.WebViewTransport) resultMsg.obj;
                    transport.setWebView(newWebView);
                    resultMsg.sendToTarget();

                    return true;
                }

            });

                // Trying to display a desktop mode for the webview. Failing miserably
                /*mWebView.getSettings().setUserAgentString(newUA);*/
                mWebView.evaluateJavascript("document.querySelector('meta[name=\"viewport\"]').setAttribute('content', 'width=1024px, initial-scale=' + (window.screen.width / 1024));", null);
                mWebView.getSettings().setLoadWithOverviewMode(true);
                mWebView.getSettings().setUseWideViewPort(true);
                mWebView.getSettings().setBuiltInZoomControls(true);
                mWebView.getSettings().setDisplayZoomControls(false);
                mWebView.getSettings().setSupportZoom(true);

                // Toast to alert the user to switch to landscape mode on their device for more information to be displayed
                FancyToast.makeText(this,getString(R.string.orientation),FancyToast.LENGTH_LONG, FancyToast.DEFAULT,false).show();
                /*mWebView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
                mWebView.setScrollbarFadingEnabled(false);*/

            } else {

                // Dismissing the progress dialog, loading no_internet_layout
                progressDialog.dismiss();
                setContentView(R.layout.no_internet_layout);
                new AlertDialog.Builder(this) //alert the person knowing they are about to close
                        .setTitle(R.string.No_internet)
                        .setMessage(R.string.Please_Check_your_Mobile_data_or_Wifi_network)
                        .setPositiveButton(R.string.OK, (dialog, which) -> finish())
                        //.setNegativeButton("No", null)
                        .show();
            }


    }



    @SuppressLint("NewApi")
    @Override
    protected void onResume() {
        super.onResume();
        mWebView.onResume();
        // ...
    }

    @SuppressLint("NewApi")
    @Override
    protected void onPause() {
        mWebView.onPause();
        // ...
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        mWebView.onDestroy();
        // ...
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        mWebView.onActivityResult(requestCode, resultCode, intent);
        // ...
    }

    @Override
    public void onBackPressed() {
        if (!mWebView.onBackPressed()) { return; }
        // ...
        super.onBackPressed();
    }

    @Override
    public void onPageStarted(String url, Bitmap favicon) {

    }

    @Override
    public void onPageFinished(String url) {
        progressDialog.dismiss();
    }

    @Override
    public void onPageError(int errorCode, String description, String failingUrl) {
        // Dismissing the progress dialog
        progressDialog.dismiss();
    }


    @Override
    public void onDownloadRequested(String url, String suggestedFilename, String mimeType, long contentLength, String contentDisposition, String userAgent) {
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }

    @Override
    public void onExternalPageRequest(String url) {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return true;
    }


}