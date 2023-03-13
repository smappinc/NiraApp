package com.nira.niradroid.adapters;

import static android.content.Context.CONNECTIVITY_SERVICE;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

import com.nira.niradroid.R;
import com.nira.niradroid.activities.ReportActivity;
import com.nira.niradroid.activities.ServicesFormsActivity;
import com.nira.niradroid.activities.WebViewActivity;


// An adapter for each item list of the main activity
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>{

    private final ArrayList<String> mTitles;
    private final ArrayList<String> mSubTitles;
    private final ArrayList<String> mLinks;
    private final Context mContext;

    public MyAdapter(Context context, ArrayList<String> titles, ArrayList<String> links, ArrayList<String> subtitles) {
        mTitles = titles;
        mSubTitles = subtitles;
        mLinks = links;
        mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_main, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        // Get the connectivity manager
        ConnectivityManager cm = (ConnectivityManager) mContext.getSystemService(CONNECTIVITY_SERVICE);

        // Get the active network info
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();

        holder.title.setText(mTitles.get(position));
        holder.subTitle.setText(mSubTitles.get(position));
        holder.parentLayout.setOnClickListener(view -> {

            String title = mTitles.get(position);
            String link = mLinks.get(position);

            // If the item position is 2, load Forms dialog
            if(position==2){

                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle("Choose a Category to Access Forms");

                String[] categories = {"Registration of Persons", "Births", "Deaths"};
                builder.setItems(categories, (dialog, which) -> {

                    if(which == 0){
                        Intent reg = new Intent(mContext, ServicesFormsActivity.class);
                        reg.putExtra("title", "Registration Of Persons");
                        reg.putExtra("link", "reg.json");
                        mContext.startActivity(reg);
                    }

                    else if (which ==1){
                        Intent births = new Intent(mContext, ServicesFormsActivity.class);
                        births.putExtra("title", "Births");
                        births.putExtra("link", "births.json");
                        mContext.startActivity(births);
                    }

                    else {
                        Intent deaths = new Intent(mContext, ServicesFormsActivity.class);
                        deaths.putExtra("title", "Deaths");
                        deaths.putExtra("link", "deaths.json");
                        mContext.startActivity(deaths);
                    }

                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }

            // If the item position is 1, load report activity
            else if(position==1) {
                Intent intent = new Intent(mContext, ReportActivity.class);
                intent.putExtra("title", title);
                intent.putExtra("link", link);
                mContext.startActivity(intent);
            }

            // Load twitter
            else if(position==9){

                if (networkInfo != null && networkInfo.isConnected()) {
                    Uri uri = Uri.parse("twitter://user?screen_name=NIRA_Ug");
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    mContext.startActivity(intent);
                }

                else {
                    new android.app.AlertDialog.Builder(mContext) //alert the person knowing they are about to close
                            .setTitle(R.string.No_internet)
                            .setMessage(R.string.Please_Check_your_Mobile_data_or_Wifi_network)
                            .setPositiveButton(R.string.OK, null)
                            //.setNegativeButton("No", null)
                            .show();
                }
            }

            // For the rest of the items, load the webview
            else {

                Intent intent = new Intent(mContext, WebViewActivity.class);
                intent.putExtra("title", title);
                intent.putExtra("link", link);
                mContext.startActivity(intent);

            }

        });
    }

    @Override
    public int getItemCount() {
        return mTitles.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        TextView title, subTitle;
        CardView parentLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            subTitle = itemView.findViewById(R.id.sub_title);
            parentLayout = itemView.findViewById(R.id.parentLyt);
        }
    }
}
