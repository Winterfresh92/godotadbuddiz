package org.godotengine.godot;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import com.purplebrain.adbuddiz.sdk.AdBuddiz;
import com.purplebrain.adbuddiz.sdk.AdBuddizLogLevel;
import com.purplebrain.adbuddiz.sdk.AdBuddizRewardedVideoDelegate;
import com.purplebrain.adbuddiz.sdk.AdBuddizRewardedVideoError;

import com.purplebrain.adbuddiz.sdk.AdBuddizDelegate;
import com.purplebrain.adbuddiz.sdk.AdBuddizError;
import com.purplebrain.adbuddiz.sdk.AdBuddizLogLevel;

public class GodotAdBuddiz extends Godot.SingletonBase {
    private static final String TAG = "AdBuddiz";
    private Activity activity = null;
    private boolean active = false;
    private Integer adCallbackId = 0;

    public GodotAdBuddiz(Activity activity) {
        registerClass("AdBuddiz", new String[] {
            "init", "showRewardedVideo", "showInterstitial", "setAdCallbackId"
        });

        this.activity = activity;
    }

    static public Godot.SingletonBase initialize(Activity p_activity) {
        return new GodotAdBuddiz(p_activity);
    }

    public void init(boolean test, String publisherKey) {
        if(active) {
            return;
        }

        AdBuddiz.setLogLevel(AdBuddizLogLevel.Info);
        AdBuddiz.setPublisherKey(publisherKey);

        if (test) {
            AdBuddiz.setTestModeActive();
            AdBuddiz.setDelegate(delegate);
        }

        AdBuddiz.RewardedVideo.setDelegate(rewardDelegate);
        AdBuddiz.cacheAds(activity);
        AdBuddiz.RewardedVideo.fetch(activity);

        active = true;
    }

    public void showRewardedVideo() {
        if (active) {
            AdBuddiz.RewardedVideo.show(activity);
        }
    }

    public void showInterstitial() {
        if (active) {
            AdBuddiz.showAd(activity);
        }
    }

    protected void onMainDestroy() {
        if (active) {
            AdBuddiz.onDestroy();
            active = false;
        }
    }

    public int getAdCallbackId() {
        return adCallbackId;
    }

    public void setAdCallbackId(int adCallbackId) {
        this.adCallbackId = adCallbackId;
    }

    private AdBuddizDelegate delegate = new AdBuddizDelegate() {
        @Override
        public void didCacheAd() {
            Log.i(TAG, "didCacheAd");
            Toast.makeText(activity, "didCacheAd", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void didShowAd() {
            Log.i(TAG, "didShowAd");
            Toast.makeText(activity, "didShowAd", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void didFailToShowAd(AdBuddizError error) {
            String msg = "FailToShow: " + error.name();
            Log.i(TAG, msg);
            Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void didClick() {
            Log.i(TAG, "didClick");
            Toast.makeText(activity, "didClick", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void didHideAd() {
            Log.i(TAG, "didHideAd");
            Toast.makeText(activity, "didHideAd", Toast.LENGTH_SHORT).show();
        }
    };

    private AdBuddizRewardedVideoDelegate rewardDelegate = new AdBuddizRewardedVideoDelegate() {
        @Override
        public void didComplete() {
            GodotLib.calldeferred(adCallbackId, "ad_did_complete", new Object[]{});
        }

        @Override
        public void didFetch() {
        }

        @Override
        public void didFail(AdBuddizRewardedVideoError error) {
        }

        @Override
        public void didNotComplete() {
        }
    };
}