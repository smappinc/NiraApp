package com.nira.niradroid.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.nira.niradroid.R;
import com.nira.niradroid.adapters.ServiceFormsAdapter;
import com.nira.niradroid.models.ServicesFormsModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Objects;

public class ServicesFormsActivity extends AppCompatActivity {

    String title, link;
    private final ArrayList<ServicesFormsModel> servicesFormsModels = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services_forms);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Intent intent = getIntent();
        title = intent.getStringExtra("title");
        link =  intent.getStringExtra("link");

        getListData(link);
        setUIRef();
        setTitle(title);

    }

    private void getListData(String url){

        String myJSONStr = loadJSONFromAsset(url);

        try {

            JSONArray array = new JSONArray(myJSONStr);

            for (int i = 0; i < array.length(); i++) {

                //Create a temp object of the model class
                ServicesFormsModel model = new ServicesFormsModel();
                JSONObject jsonObject = array.getJSONObject(i);

                //Get Details
                model.setSubtitle(jsonObject.getString("sub_title"));
                model.setName(jsonObject.getString("title"));
                model.setUrl(jsonObject.getString("url"));

                //Add forms object to the list
                servicesFormsModels.add(model);
            }

        } catch (JSONException e)
        {
            e.printStackTrace();
        }
    }

    public String loadJSONFromAsset(String fileName){

        String json;
        try {
            InputStream is = getAssets().open(fileName);
            int size = is.available();
            byte[] buffer = new byte[size];

            //noinspection ResultOfMethodCallIgnored
            is.read(buffer);
            is.close();
            json = new String(buffer, StandardCharsets.UTF_8);
        }
        catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    private void setUIRef(){
        RecyclerView recyclerView = findViewById(R.id.recycler);
        ServiceFormsAdapter myRecyclerViewAdapter = new ServiceFormsAdapter(ServicesFormsActivity.this, servicesFormsModels);
        recyclerView.setAdapter(myRecyclerViewAdapter);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.scheduleLayoutAnimation();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return true;
    }
}