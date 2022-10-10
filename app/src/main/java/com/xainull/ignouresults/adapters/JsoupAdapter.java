package com.xainull.ignouresults.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import com.xainull.ignouresults.R;
import com.xainull.ignouresults.activities.DetailActivity;
import com.xainull.ignouresults.activities.JsoupActivity;
import com.xainull.ignouresults.models.CustomListModel;
import com.xainull.ignouresults.tools.AdManager;

public class JsoupAdapter extends RecyclerView.Adapter < JsoupAdapter.ViewHolder > {

    AdManager adManager;

    private ArrayList <CustomListModel> Items;
    private final Context context;

    public JsoupAdapter(ArrayList <CustomListModel> Items, Context context) {
        this.Items = Items;
        this.context = context;

        adManager = new AdManager((JsoupActivity)context);
        adManager.initAds();
        adManager.loadInterstitialAd(1, 6);
    }

    @NonNull
    @Override
    public JsoupAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_jsoup, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull JsoupAdapter.ViewHolder holder, int position) {

        CustomListModel Item = Items.get(position);
        holder.textView.setText(Item.getTitle());

    }

    @Override
    public int getItemCount() {
        return Items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView textView;
        //ImageView imageView;

        public ViewHolder(@NonNull View view) {
            super(view);
            textView = view.findViewById(R.id.title);
            //imageView = view.findViewById(R.id.image);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

            int position = getAdapterPosition();
            CustomListModel parseItem = Items.get(position);

            Intent intent = new Intent(context, DetailActivity.class);
            intent.putExtra("title", parseItem.getTitle());
            intent.putExtra("url", parseItem.getUrl());
            context.startActivity(intent);

            adManager.showInterstitialAd();

        }
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setFilter(ArrayList <CustomListModel> newList) {
        Items = new ArrayList<>();
        Items.addAll(newList);
        notifyDataSetChanged();
    }
}