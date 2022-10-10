package com.xainull.ignouresults.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

import com.xainull.ignouresults.R;
import com.xainull.ignouresults.adapters.JsoupAdapter;
import com.xainull.ignouresults.models.CustomListModel;
import com.xainull.ignouresults.tools.AdManager;
import com.xainull.ignouresults.tools.Tools;

public class JsoupActivity extends AppCompatActivity {

    AdManager adManager;

    private JsoupAdapter adapter;
    private final ArrayList<CustomListModel> parseItems = new ArrayList<>();
    ProgressDialog progressDialog;
    String title, link, category;
    Tools tools;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jsoup);

        tools = new Tools(this);

        Intent intent = getIntent();
        title = intent.getStringExtra("title");
        link =  intent.getStringExtra("link");
        category =  intent.getStringExtra("category");

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new JsoupAdapter(parseItems, this);
        recyclerView.setAdapter(adapter);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTitle(title);

        Content content = new Content();
        content.execute();

        adManager = new AdManager(this);
        adManager.initAds();
        adManager.loadBannerAd(R.id.adView);
        adManager.loadInterstitialAd(1, 1);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);

        MenuItem searchViewItem = menu.findItem(R.id.action_search);
        // Get the search view and set the searchable configuration
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) searchViewItem.getActionView();
        searchView.setQueryHint("Search...");
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false); //Do not iconfy the widget; expand it by default

        SearchView.OnQueryTextListener queryTextListener = new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                newText = newText.toLowerCase();
                ArrayList<CustomListModel> newList = new ArrayList<>();
                for (CustomListModel parseItem : parseItems) {
                    String title = parseItem.getTitle().toLowerCase();

                    // you can specify as many conditions as you like
                    if (title.contains(newText)) {
                        newList.add(parseItem);
                    }
                }
                // create method in adapter
                adapter.setFilter(newList);

                return true;
            }
        };

        searchView.setOnQueryTextListener(queryTextListener);

        return true;

    }

    @SuppressLint("StaticFieldLeak")
    private class Content extends AsyncTask<Void,Void,Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.show();
        }

        @SuppressLint("NotifyDataSetChanged")
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progressDialog.dismiss();
            adapter.notifyDataSetChanged();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            String mUrl = link;

            try {
                Document doc  = Jsoup.connect(mUrl).sslSocketFactory(tools.socketFactory()).get();
//                Element element = doc.getElementById("#div.middleconten2column");
//                Elements tag = element.getElementsByTag("a");

                Elements div = doc.select("div.middleconten2column");
                Elements ul = doc.select(category);
                Elements li = ul.select("a[href]");

                for (Element item : li) {
                    String title = item.text();
                    String url = item.absUrl("href").replace(" ", "%20");
                    parseItems.add(new CustomListModel(title, url));
                }


            }

            catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }
    }

}