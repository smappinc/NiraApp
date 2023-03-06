package com.nira.niradroid.adapters;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.snackbar.Snackbar;
import com.nira.niradroid.activities.WebViewActivity;
import com.squareup.picasso.Picasso;
import com.nira.niradroid.R;
import com.nira.niradroid.models.ServicesFormsModel;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;


// Forms adapter logic, read through to understand
public class ServiceFormsAdapter extends RecyclerView.Adapter<ServiceFormsAdapter.MyViewHolder> {

    Context context;
    private final ArrayList<ServicesFormsModel> mList;

    public ServiceFormsAdapter(Context context, ArrayList<ServicesFormsModel> mList){
        this.mList = mList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_services, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position){

        String subtitle = mList.get(position).getSubtitle();
        String title = mList.get(position).getName();
        String url = mList.get(position).getUrl();

        holder.mTitle.setText(title);
        holder.mSubTitle.setText(subtitle);

        if(url.contains("http")){
            Picasso.get().load(R.drawable.html_icon).into(holder.mIcon);
        }
        else {
            Picasso.get().load(R.drawable.pdf_icon).into(holder.mIcon);
        }

        holder.mParentLayout.setOnClickListener(v -> {

            if(url.contains("http")){
                Intent intent = new Intent(context, WebViewActivity.class);
                intent.putExtra("title", title);
                intent.putExtra("link", url);
                context.startActivity(intent);
            }

            else {
                File outDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString());
                copyAssets(url, outDir.toString());
                showSnackBar(v, url);
            }

        });
    }

    @Override
    public int getItemCount(){
        return mList.size();
    }

    @Override
    public int getItemViewType(int position){
        return position;
    }

    @Override
    public long getItemId(int position){
        return position;
    }

    static class MyViewHolder extends RecyclerView.ViewHolder{

        private final TextView mTitle, mSubTitle;
        private final ImageView mIcon;
        private final CardView mParentLayout;

        public MyViewHolder(View view){
            super(view);
            mParentLayout = view.findViewById(R.id.lytParent);
            mSubTitle = view.findViewById(R.id.subTitle);
            mTitle = view.findViewById(R.id.title);
            mIcon = view.findViewById(R.id.icon);
        }
    }

    private void copyAssets(String path, String outPath) {
        AssetManager assetManager = context.getAssets();
        String[] assets;
        try {
            assets = assetManager.list(path);
            if (assets.length == 0) {
                copyFile(path, outPath);
            } else {
                String fullPath = outPath + "/" + path;
                File dir = new File(fullPath);
                if (!dir.exists())
                    if (!dir.mkdir()) Log.e("BUGTREE: ", "No create external directory: " + dir );
                for (String asset : assets) {
                    copyAssets(path + "/" + asset, outPath);
                }
            }
        } catch (IOException ex) {
            Log.e("BUGTREE: ", "I/O Exception", ex);
        }
    }

    private void copyFile(String filename, String outPath) {
        AssetManager assetManager = context.getAssets();
        InputStream in;
        OutputStream out;
        try {
            in = assetManager.open(filename);
            String newFileName = outPath + "/" + filename;
            out = new FileOutputStream(newFileName);

            byte[] buffer = new byte[1024];
            int read;
            while ((read = in.read(buffer)) != -1) {
                out.write(buffer, 0, read);
            }
            in.close();
            out.flush();
            out.close();

        } catch (Exception e) {
            Log.e("BUGTREE: ", e.getMessage());
        }

    }

    public void openPDF(String mFilePath){

        Intent intent;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            File file = new File(mFilePath);
            Uri uri = FileProvider.getUriForFile(context, context.getPackageName() + ".provider", file);
            intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(uri);
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            context.startActivity(intent);
        } else {
            intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(Uri.parse(mFilePath), "application/pdf");
            intent = Intent.createChooser(intent, "Open File");
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }
    }

    public void openPDFViewer(){

        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/example.pdf");
        Uri path = Uri.fromFile(file);
        Intent pdfOpenintent = new Intent(Intent.ACTION_VIEW);
        pdfOpenintent.setDataAndType(path, "application/pdf");
        pdfOpenintent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        try {
            context.startActivity(pdfOpenintent);
        }
        catch (ActivityNotFoundException e) {
            Toast.makeText(context, "No Application available to view PDF", Toast.LENGTH_SHORT).show();
        }
    }

    public void showSnackBar(View view, String fileName){
        Snackbar snackbar = Snackbar.make(view, "Saved at: Downloads\n" + fileName , Snackbar.LENGTH_SHORT);
        snackbar.show();
    }

}
