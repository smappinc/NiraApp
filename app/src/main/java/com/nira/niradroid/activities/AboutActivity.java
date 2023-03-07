package com.nira.niradroid.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.nira.niradroid.R;

import mehdi.sakout.aboutpage.AboutPage;
import mehdi.sakout.aboutpage.Element;

import java.util.Objects;

public class AboutActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View aboutPage = new AboutPage(this)
                .isRTL(false)
                .setImage(R.drawable.nira_logo_full)
                .setDescription(getString(R.string.about_app_desc))
                .addItem(new Element().setTitle(getString(R.string.app_version)))
                .addGroup("Connect with us")
                .addWebsite("https://nira.go.ug/")
                .addTwitter("NIRA_ug")
                .addPlayStore("com.nira.niradroid")
                .addItem(getCopyRightsElement())
                .create();

        setContentView(aboutPage);
    }


    Element getCopyRightsElement() {
        Element copyRightsElement = new Element();
        final String copyrights = String.format(getString(R.string.copyright_txt));
        copyRightsElement.setTitle(copyrights);
        copyRightsElement.setIconDrawable(R.mipmap.ic_launcher_round);
        copyRightsElement.setGravity(Gravity.CENTER);
        copyRightsElement.setOnClickListener(v -> Toast.makeText(AboutActivity.this, copyrights, Toast.LENGTH_SHORT).show());
        return copyRightsElement;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return true;
    }
}
