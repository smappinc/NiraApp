package com.xainull.ignouresults.tools;

import android.app.Activity;

import com.afaqsdk.ads.format.AdNetwork;
import com.afaqsdk.ads.format.BannerAd;
import com.afaqsdk.ads.format.InterstitialAd;
import com.afaqsdk.ads.format.MediumRectangleAd;
import com.afaqsdk.ads.format.NativeAd;
import com.xainull.ignouresults.BuildConfig;

public class AdManager {

    AdNetwork.Initialize adNetwork;
    BannerAd.Builder bannerAd;
    MediumRectangleAd.Builder mediumRectangleAd;
    InterstitialAd.Builder interstitialAd;
    NativeAd.Builder nativeAd;

    Activity activity;

    public AdManager(Activity activity) {
        this.activity = activity;
        adNetwork = new AdNetwork.Initialize(activity);
        bannerAd = new BannerAd.Builder(activity);
        interstitialAd = new InterstitialAd.Builder(activity);
        nativeAd = new NativeAd.Builder(activity);
    }

    public void initAds() {
        adNetwork = new AdNetwork.Initialize(activity)
                .setAdStatus(Constant.AD_STATUS)
                .setAdNetwork(Constant.AD_NETWORK)
                .setBackupAdNetwork(Constant.BACKUP_AD_NETWORK)
                .setAdMobAppId(null)
                .setDebug(BuildConfig.DEBUG)
                .build();
    }

    public void loadBannerAd() {
        bannerAd = new BannerAd.Builder(activity)
                .setAdStatus(Constant.AD_STATUS)
                .setAdNetwork(Constant.AD_NETWORK)
                .setBackupAdNetwork(Constant.BACKUP_AD_NETWORK)
                .setAdMobBannerId(Constant.ADMOB_BANNER_ID)
                .setFanBannerId(Constant.FAN_BANNER_ID)
                .setDarkTheme(false)
                .build();
    }

    public void loadMediumRectangleAd() {
        mediumRectangleAd = new MediumRectangleAd.Builder(activity)
                .setAdStatus(Constant.AD_STATUS)
                .setAdNetwork(Constant.AD_NETWORK)
                .setBackupAdNetwork(Constant.BACKUP_AD_NETWORK)
                .setAdMobBannerId(Constant.ADMOB_BANNER_ID)
                .setFanBannerId(Constant.FAN_MREC_ID)
                .setDarkTheme(false)
                .build();
    }

    public void loadInterstitialAd() {
        interstitialAd = new InterstitialAd.Builder(activity)
                .setAdStatus(Constant.AD_STATUS)
                .setAdNetwork(Constant.AD_NETWORK)
                .setBackupAdNetwork(Constant.BACKUP_AD_NETWORK)
                .setAdMobInterstitialId(Constant.ADMOB_INTERSTITIAL_ID)
                .setFanInterstitialId(Constant.FAN_INTERSTITIAL_ID)
                .setInterval(7)
                .build();
    }

    public void showInterstitialAd() {
        interstitialAd.show();
    }

    public void loadNativeAd() {
        nativeAd = new NativeAd.Builder(activity)
                .setAdStatus(Constant.AD_STATUS)
                .setAdNetwork(Constant.AD_NETWORK)
                .setBackupAdNetwork(Constant.BACKUP_AD_NETWORK)
                .setAdMobNativeId(Constant.ADMOB_NATIVE_ID)
                .setFanNativeId(Constant.FAN_NATIVE_ID)
                .setDarkTheme(false)
                .setNativeAdStyle("radio")
                .build();
    }

}