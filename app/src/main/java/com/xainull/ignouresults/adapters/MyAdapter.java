package com.xainull.ignouresults.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

import com.squareup.picasso.Picasso;
import com.xainull.ignouresults.R;
import com.xainull.ignouresults.activities.MainActivity;
import com.xainull.ignouresults.tools.AdManager;
import com.xainull.ignouresults.tools.Tools;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>{

    AdManager adManager;

    private final ArrayList<String> mTitles;
    private final ArrayList<String> mLinks;
    private final ArrayList<String> mCategories;
    private final Context mContext;

    public MyAdapter(Context context, ArrayList<String> titles, ArrayList<String> links, ArrayList<String> categories) {
        mTitles = titles;
        mLinks = links;
        mCategories = categories;
        mContext = context;

        adManager = new AdManager((MainActivity)context);
        adManager.initAds();
        adManager.loadInterstitialAd(1, 10);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_dashboard, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        if(position==0){
            Picasso.get().load(R.drawable.test).into(holder.image);

        }
        else if(position==1){
            Picasso.get().load(R.drawable.return_grade).into(holder.image);
        }
        else if(position==2){
            Picasso.get().load(R.drawable.open_mate).into(holder.image);
        }
        else if(position==3){
            Picasso.get().load(R.drawable.term4).into(holder.image);
        }
        else if(position==4){
            Picasso.get().load(R.drawable.grade_card).into(holder.image);
        }
        else if(position==5){
            Picasso.get().load(R.drawable.exam).into(holder.image);
        }
        else if(position==6){
            Picasso.get().load(R.drawable.ticket).into(holder.image);
        }
        else if(position==7){
            Picasso.get().load(R.drawable.college).into(holder.image);
        }

        holder.title.setText(mTitles.get(position));
        holder.parentLayout.setOnClickListener(view -> {

            adManager.showInterstitialAd();

            String title = mTitles.get(position);
            String link = mLinks.get(position);
            String category = mCategories.get(position);

            if (position==1){
                new Tools(mContext).loadWebView(mContext, link);
            }
            else if(position==4){
                new Tools(mContext).loadWebView(mContext, link);
            }

            else {
                new Tools(mContext).loadJsoupActivity(mContext, title, link, category);
            }

        });
    }

    @Override
    public int getItemCount() {
        return mTitles.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        ImageView image;
        TextView title;
        CardView parentLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            title = itemView.findViewById(R.id.title);
            parentLayout = itemView.findViewById(R.id.parentLyt);
        }
    }
}
