package com.xainull.ignouresults.tools;

import android.app.Activity;

import com.xainull.ignouresults.BuildConfig;
import com.xposed73.ads.sdk.format.AdNetwork;
import com.xposed73.ads.sdk.format.BannerAd;
import com.xposed73.ads.sdk.format.InterstitialAd;
import com.xposed73.ads.sdk.format.MediumRectangleAd;
import com.xposed73.ads.sdk.format.NativeAd;

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

    public void loadBannerAd(int placement) {
        bannerAd = new BannerAd.Builder(activity)
                .setAdStatus(Constant.AD_STATUS)
                .setAdNetwork(Constant.AD_NETWORK)
                .setBackupAdNetwork(Constant.BACKUP_AD_NETWORK)
                .setAdMobBannerId(Constant.ADMOB_BANNER_ID)
                .setFanBannerId(Constant.FAN_BANNER_ID)
                .setPlacementStatus(placement)
                .setDarkTheme(false)
                .build();
    }

    public void loadMediumRectangleAd(int placement) {
        mediumRectangleAd = new MediumRectangleAd.Builder(activity)
                .setAdStatus(Constant.AD_STATUS)
                .setAdNetwork(Constant.AD_NETWORK)
                .setBackupAdNetwork(Constant.BACKUP_AD_NETWORK)
                .setAdMobBannerId(Constant.ADMOB_BANNER_ID)
                .setFanBannerId(Constant.FAN_MREC_ID)
                .setDarkTheme(false)
                .setPlacementStatus(placement)
                .build();
    }

    public void loadInterstitialAd(int placement, int interval) {
        interstitialAd = new InterstitialAd.Builder(activity)
                .setAdStatus(Constant.AD_STATUS)
                .setAdNetwork(Constant.AD_NETWORK)
                .setBackupAdNetwork(Constant.BACKUP_AD_NETWORK)
                .setAdMobInterstitialId(Constant.ADMOB_INTERSTITIAL_ID)
                .setFanInterstitialId(Constant.FAN_INTERSTITIAL_ID)
                .setInterval(interval)
                .setPlacementStatus(placement)
                .build();
    }

    public void showInterstitialAd() {
        interstitialAd.show();
    }

    public void loadNativeAd(int placement) {
        nativeAd = new NativeAd.Builder(activity)
                .setAdStatus(Constant.AD_STATUS)
                .setAdNetwork(Constant.AD_NETWORK)
                .setBackupAdNetwork(Constant.BACKUP_AD_NETWORK)
                .setAdMobNativeId(Constant.ADMOB_NATIVE_ID)
                .setFanNativeId(Constant.FAN_NATIVE_ID)
                .setPlacementStatus(placement)
                .setDarkTheme(false)
                .build();
    }


}
