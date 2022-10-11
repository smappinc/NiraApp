package com.xainull.ignouresults.activities;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.xainull.ignouresults.BuildConfig;
import com.xainull.ignouresults.adapters.MyAdapter;
import com.xainull.ignouresults.R;

import com.google.android.material.navigation.NavigationView;
import com.xainull.ignouresults.tools.AdManager;
import com.xposed73.ads.sdk.intents.BrowserIntents;
import com.xposed73.ads.sdk.intents.MarketIntents;
import com.xposed73.ads.sdk.intents.ShareIntents;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    //vars
    private final ArrayList<String> mTitles = new ArrayList<>();
    private final ArrayList<String> mLinks = new ArrayList<>();
    private final ArrayList<String> mCategories = new ArrayList<>();

    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;
    private Toolbar toolbar;

    AdManager adManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        //Initialize Drawer
        mDrawerLayout = findViewById(R.id.drawer_layout);
        mNavigationView = findViewById(R.id.nav_view);

        //Initialize Toolbar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Initialize Items
        initDrawer();
        initItems();

        adManager = new AdManager(this);
        adManager.initAds();
        adManager.loadBannerAd(R.id.adView);
        adManager.loadInterstitialAd(1, 1);

    }


    @SuppressLint("UseCompatLoadingForDrawables")
    private void initItems(){

        //Item1
        mTitles.add("Entrance Exams");
        mLinks.add("http://www.ignou.ac.in/ignou/studentzone/results/1");
        mCategories.add("div.middleconten2column");

        //Item2
        mTitles.add("Returned Grade card");
        mLinks.add("https://gradecard.ignou.ac.in/gcreturn/CheckEnrNo.aspx");
        mCategories.add("div.middleconten2column");

        //Item3
        mTitles.add("Open Mate");
        mLinks.add("http://www.ignou.ac.in/ignou/studentzone/results/8");
        mCategories.add("div.middleconten2column");

        //Item4
        mTitles.add("Term End");
        mLinks.add("http://www.ignou.ac.in/ignou/studentzone/results/2");
        mCategories.add("div.middleconten2column");

        //Item5
        mTitles.add("Grade Card");
        mLinks.add("https://gradecard.ignou.ac.in/gradecard/");
        mCategories.add("div.middleconten2column");

        //Item6
        mTitles.add("Revaluation");
        mLinks.add("http://www.ignou.ac.in/ignou/studentzone/results/5");
        mCategories.add("div.middleconten2column");

        //Item7
        mTitles.add("Hall Ticket/ Admit Card");
        mLinks.add("http://www.ignou.ac.in/ignou/studentzone/results/6");
        mCategories.add("div.middleconten2column");

        //Item8
        mTitles.add("Community College");
        mLinks.add("http://www.ignou.ac.in/ignou/studentzone/results/CommunityCollege");
        mCategories.add("div.middleconten2column");

        initRecyclerView();
    }

    private void initRecyclerView(){
        RecyclerView recyclerView = findViewById(R.id.recycler);
        MyAdapter adapter = new MyAdapter(this, mTitles, mLinks, mCategories);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_home) {
            //
        }

        if (id == R.id.action_about_app) {
            showAboutDialog(this);
        }

        if (id == R.id.action_privacy) {
            showPrivacyPolicyDialog(this);
        }

        if (id == R.id.action_rate_app) {
            MarketIntents.from(this).showInGooglePlay(BuildConfig.APPLICATION_ID).show();
        }

        if (id == R.id.action_more_app) {
            BrowserIntents.from(this).openLink("https://play.google.com/store/apps/dev?id=5947475589044688365").show();
        }

        if (id == R.id.action_share_app) {
            ShareIntents.from(this).shareText("Download ",  "Download: " + getString(R.string.app_name) + " App\n\nhttps://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID).show();
        }

        if (id == R.id.action_exit) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Exit")
                    .setMessage("Do you really want to exit?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", (dialog, id1) -> finish())
                    .setNegativeButton("No", (dialog, id12) -> dialog.cancel());
            AlertDialog alert = builder.create();
            alert.show();
        }


        if (mDrawerLayout != null && mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        }

        return true;

    }

    public NavigationView getNavigationView() {
        return mNavigationView;
    }

    void initDrawer() {

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.openDrawer, R.string.closeDrawer) {
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
            }

            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };

        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        mNavigationView.setItemIconTintList(null);
        getNavigationView().setNavigationItemSelectedListener(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.action_share) {
            ShareIntents.from(this).shareText("Download ",  "Download: " + getString(R.string.app_name) + " App\n\nhttps://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID).show();
            return true;
        }

        if (id == R.id.action_notifications) {
            Toast.makeText(MainActivity.this, "Not yet implemented!", Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    //OTHER THINGS

    public static void showPrivacyPolicyDialog(Activity activity) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        LayoutInflater layoutInflater = LayoutInflater.from(activity);

        View view = layoutInflater.inflate(R.layout.dialog_privacy, null);
        ProgressBar progressBar = view.findViewById(R.id.progressBar);
        ImageButton btnClose = view.findViewById(R.id.btn_close);
        WebView webView = view.findViewById(R.id.webView);

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                new android.os.Handler().postDelayed(() -> progressBar.setVisibility(View.INVISIBLE), 500);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url != null && (url.startsWith("http") || url.startsWith("https"))) {
                    activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                }
                return true;
            }
        });
        webView.loadUrl("https://sites.google.com/view/ndmk-apps/privacy");
        builder.setCancelable(false);
        builder.setView(view);

        final AlertDialog dialog = builder.create();
        btnClose.setOnClickListener(v -> new Handler().postDelayed(dialog::dismiss, 250));

        dialog.show();
    }

    public static void showAboutDialog(Activity activity) {
        LayoutInflater layoutInflater = LayoutInflater.from(activity);
        View view = layoutInflater.inflate(R.layout.about_dialog, null);
        TextView txtAppVersion = view.findViewById(R.id.txt_app_version);
        txtAppVersion.setText("Version " + BuildConfig.VERSION_CODE + " (" + BuildConfig.VERSION_NAME + ")");
        final AlertDialog.Builder alert = new AlertDialog.Builder(activity);
        alert.setView(view);
        alert.setPositiveButton("OK", (dialog, which) -> dialog.dismiss());
        alert.show();
    }

}




















