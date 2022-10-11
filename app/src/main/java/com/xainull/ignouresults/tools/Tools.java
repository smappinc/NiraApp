package com.xainull.ignouresults.tools;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.util.Base64;
import android.webkit.MimeTypeMap;

import com.xainull.ignouresults.BuildConfig;
import com.xainull.ignouresults.R;
import com.xainull.ignouresults.activities.JsoupActivity;
import com.xainull.ignouresults.activities.WebViewActivity;

import java.nio.charset.StandardCharsets;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class Tools {

    Context context;

    public Tools(Context context) {
        this.context = context;
    }

    public SSLSocketFactory socketFactory() {
        TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return null;
            }

            public void checkClientTrusted(X509Certificate[] certs, String authType) {
            }

            public void checkServerTrusted(X509Certificate[] certs, String authType) {
            }
        }};

        try {
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            return sslContext.getSocketFactory();
        } catch (NoSuchAlgorithmException | KeyManagementException e) {
            throw new RuntimeException("Failed to create a SSL socket factory", e);
        }
    }

    public static String getMimeType(String url) {
        String type = null;
        String extension = MimeTypeMap.getFileExtensionFromUrl(url);
        if (extension != null) {
            type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
        }
        return type;
    }

    public void loadWebView(Context context, String link){
        Intent intent = new Intent(context, WebViewActivity.class);
        intent.putExtra("link", link);
        context.startActivity(intent);

//        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
//        context.startActivity(intent);

//        new AlertDialog.Builder(context)
//                .setTitle("Choose option")
//                .setMessage("Would you like to open the link in browser?")
//                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int which) {
//
//                    }
//                })
//
//                .setNegativeButton("No", null)
//                .show();

    }

    public void loadJsoupActivity(Context context, String title, String link, String category){
        Intent jsoupIntent = new Intent(context, JsoupActivity.class);
        jsoupIntent.putExtra("title", title);
        jsoupIntent.putExtra("link", link);
        jsoupIntent.putExtra("category", category);
        context.startActivity(jsoupIntent);
    }

}
