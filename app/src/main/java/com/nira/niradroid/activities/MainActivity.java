package com.nira.niradroid.activities;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import com.nira.niradroid.BuildConfig;
import com.nira.niradroid.adapters.MyAdapter;
import com.nira.niradroid.R;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    //variables
    private final ArrayList<String> mTitles = new ArrayList<>();
    private final ArrayList<String> mSubTitles = new ArrayList<>();
    private final ArrayList<String> mLinks = new ArrayList<>();

    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        String myPref = sharedPreferences.getString("type", "");

        // App theme switch
        switch (myPref) {
            case "light":
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                break;
            case "dark":
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                break;
            case "default":
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
                break;
        }

        //Initialize Drawer
        mDrawerLayout = findViewById(R.id.drawer_layout);
        mNavigationView = findViewById(R.id.nav_view);

        //Initialize Toolbar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Initialize Items
        initDrawer();
        initItems();

    }


    @SuppressLint("UseCompatLoadingForDrawables")
    private void initItems(){

        //Item1
        mTitles.add(getString(R.string.key_services));
        mSubTitles.add(getString(R.string.key_services_subtitle));
        mLinks.add("https://www.nira.go.ug/home");

        //Item2
        mTitles.add(getString(R.string.report));
        mSubTitles.add(getString(R.string.report_issues_subtitle));
        mLinks.add("https://www.nira.go.ug/publications");

        //Item3
        mTitles.add(getString(R.string.services_and_forms));
        mSubTitles.add(getString(R.string.services_and_forms_subtitle));
        mLinks.add("https://www.nira.go.ug/home");

        //Item4
        mTitles.add(getString(R.string.form_fees));
        mSubTitles.add(getString(R.string.form_fees_subtitle));
        mLinks.add("https://www.nira.go.ug/fees");

        //Item5
        mTitles.add(getString(R.string.contact_us));
        mSubTitles.add(getString(R.string.contact_us_subtitle));
        mLinks.add("https://www.nira.go.ug/contact-us");

        //Item6
        mTitles.add(getString(R.string.news));
        mSubTitles.add(getString(R.string.news_subtitle));
        mLinks.add("https://www.nira.go.ug/news");

        //Item7
        mTitles.add(getString(R.string.publications));
        mSubTitles.add(getString(R.string.publications_subtitle));
        mLinks.add("https://www.nira.go.ug/publications");

        //Item8
        mTitles.add(getString(R.string.careers));
        mSubTitles.add(getString(R.string.careers_subtitle));
        mLinks.add("https://www.nira.go.ug/careers");

        //Item9
        mTitles.add(getString(R.string.tenders));
        mSubTitles.add(getString(R.string.tenders_subtitle));
        mLinks.add("https://www.nira.go.ug/tenders");

        //Item10
        mTitles.add(getString(R.string.tweets_by_nira));
        mSubTitles.add(getString(R.string.tweets_from_nira_subtitle));
        mLinks.add("twitter://user?screen_name=NIRA_Ug");


        initRecyclerView();
    }

    private void initRecyclerView(){
        RecyclerView recyclerView = findViewById(R.id.recycler);
        MyAdapter adapter = new MyAdapter(this, mTitles, mLinks, mSubTitles);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_home) {
            // Do something, idk
        }

        if (id == R.id.action_about) {
            showAboutDialog(this);
        }

        if (id == R.id.action_setting){
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
        }

        if (id == R.id.action_exit) {
            showExitDialog();
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
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:0800211700"));
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    //Initialize about dialog
    @SuppressLint("SetTextI18n")
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

    //Initialize exit dialog
    public void showExitDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.exit))
                .setMessage(getString(R.string.exit_warning))
                .setCancelable(false)
                .setPositiveButton(getString(R.string.exit_yes), (dialog, id1) -> finish())
                .setNegativeButton(getString(R.string.exit_no), (dialog, id12) -> dialog.cancel());
        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            showExitDialog();
            //super.onBackPressed();
        }
    }

}




















