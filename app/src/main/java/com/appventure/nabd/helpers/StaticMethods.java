package com.appventure.nabd.helpers;

import android.content.Context;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.appventure.nabd.R;
import com.appventure.nabd.beans.News;
import com.facebook.ads.MediaView;
import com.facebook.ads.NativeAd;

import java.io.File;


public class StaticMethods {

    public static News SELECTED_FOR_SHARE ;
    public static News SELECTED_FOR_VIEW ;
    public static Uri SELECTED_IMAGE ;
    public static File FILE ;
    public static String DEVICEID ;
    public static Typeface getBoldFont(Context context) {
        Typeface boldtypeface = Typeface.createFromAsset(context.getAssets(),
                "fonts/bold.otf");
        return boldtypeface;
    }
    public static Typeface getMediumFont(Context context) {
        Typeface lighttypeface = Typeface.createFromAsset(context.getAssets(),
                "fonts/medium.otf");
        return lighttypeface;
    }
    public static Typeface getlightFont(Context context) {
        Typeface lighttypeface = Typeface.createFromAsset(context.getAssets(),
                "fonts/light.otf");
        return lighttypeface;
    }

    public static Typeface getTextlightFont(Context context) {
        Typeface lighttypeface = Typeface.createFromAsset(context.getAssets(),
                "fonts/textlight.otf");
        return lighttypeface;
    }

    public static boolean checkInternetConenction(Context ctx) {
        // get Connectivity Manager object to check connection
        ConnectivityManager connec =(ConnectivityManager)ctx.getSystemService(ctx.CONNECTIVITY_SERVICE);

        // Check for network connections
        if ( connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTED ||

                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED ) {
           // Toast.makeText(ctx, " Connected ", Toast.LENGTH_SHORT).show();
            return true;
        }else if (
                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.DISCONNECTED ||
                        connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.DISCONNECTED  ) {
           // Toast.makeText(ctx, " Not Connected ", Toast.LENGTH_SHORT).show();
            return false;
        }
        return false;
    }

    public static boolean isEmailValid(String email) {
        //TODO change for your own logic
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }

    public static boolean isPasswordValid(String password) {
        //TODO change for your own logic
        return password.length() > 4;
    }

    public static void inflateAd(NativeAd nativeAd, View adView, Context context) {
        // Create native UI using the ad metadata.

        ImageView nativeAdIcon = (ImageView) adView.findViewById(R.id.nativeAdIcon);
        TextView nativeAdTitle = (TextView) adView.findViewById(R.id.nativeAdTitle);
        TextView nativeAdBody = (TextView) adView.findViewById(R.id.nativeAdBody);
        MediaView nativeAdMedia = (MediaView) adView.findViewById(R.id.nativeAdMedia);
        TextView nativeAdSocialContext = (TextView) adView.findViewById(R.id.nativeAdSocialContext);
        Button nativeAdCallToAction = (Button) adView.findViewById(R.id.nativeAdCallToAction);

        // Setting the Text
        nativeAdSocialContext.setText(nativeAd.getAdSocialContext());
        nativeAdCallToAction.setText(nativeAd.getAdCallToAction());
        nativeAdCallToAction.setVisibility(View.VISIBLE);
        nativeAdTitle.setText(nativeAd.getAdTitle());
        nativeAdBody.setText(nativeAd.getAdBody());

        // Downloading and setting the ad icon.
        NativeAd.Image adIcon = nativeAd.getAdIcon();
        NativeAd.downloadAndDisplayImage(adIcon, nativeAdIcon);

        // Downloading and setting the cover image.
        NativeAd.Image adCoverImage = nativeAd.getAdCoverImage();
        int bannerWidth = adCoverImage.getWidth();
        int bannerHeight = adCoverImage.getHeight();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        int screenWidth = metrics.widthPixels;
        int screenHeight = metrics.heightPixels;
        nativeAdMedia.setLayoutParams(new LinearLayout.LayoutParams(
                screenWidth,
                Math.min((int) (((double) screenWidth / (double) bannerWidth) * bannerHeight), screenHeight / 3)
        ));
        nativeAdMedia.setNativeAd(nativeAd);

        // Wire up the View with the native ad, the whole nativeAdContainer will be clickable
        nativeAd.registerViewForInteraction(adView);

        // Or you can replace the above call with the following function to specify the clickable areas.
        // nativeAd.registerViewForInteraction(adView,
        //     Arrays.asList(nativeAdCallToAction, nativeAdMedia));
    }
}
