package com.xainull.ignouresults.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.icu.text.CaseMap;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.xainull.ignouresults.BuildConfig;
import com.xainull.ignouresults.R;
import com.xainull.ignouresults.tools.AdManager;
import com.xainull.ignouresults.tools.Tools;

public class DetailActivity extends AppCompatActivity {

    AdManager adManager;
    Button loadLocal, loadGlobal;
    String title, url;
    TextView textView;

    private ClipboardManager myClipboard;
    private ClipData myClip;
    ImageButton copyBtn, shareBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        loadLocal= findViewById(R.id.openLocal);
        loadGlobal= findViewById(R.id.openExternal);
        textView = findViewById(R.id.headline);
        copyBtn = findViewById(R.id.copyLink);
        shareBtn = findViewById(R.id.shareLink);
        myClipboard = (ClipboardManager)getSystemService(CLIPBOARD_SERVICE);

        Intent intent = getIntent();
        title = intent.getStringExtra("title");
        url =  intent.getStringExtra("url");

        //setTitle(title);
        textView.setText(title);

        adManager = new AdManager(this);
        adManager.initAds();
        adManager.loadBannerAd(R.id.adView);
        adManager.loadNativeAd(R.id.nativeAd);
        adManager.loadInterstitialAd(1, 1);

        loadLocal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Tools(DetailActivity.this).loadWebView(DetailActivity.this, url);
            }
        });

        loadGlobal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);
                adManager.showInterstitialAd();
            }
        });

        copyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myClip = ClipData.newPlainText("text", url);
                myClipboard.setPrimaryClip(myClip);
                Toast.makeText(getApplicationContext(), "Link Copied!", Toast.LENGTH_SHORT).show();
            }
        });

        shareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, title + "\n\n" + url + "\n\n" + "Shared from: IGNOU Results App \n\nDownload the app from below link:\n" + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID);
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return true;
    }
}