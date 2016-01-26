package com.appventure.nabd;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

import com.appventure.nabd.helpers.ParseUtils;
import com.appventure.nabd.helpers.StaticMethods;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsConstants;
import com.facebook.appevents.AppEventsLogger;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        StaticMethods.DEVICEID = getId();
        FacebookSdk.sdkInitialize(getApplicationContext());
       /* AppEventsLogger logger = AppEventsLogger.newLogger(SplashActivity.this);
        logger.logEvent("APP OPENED");*/
        //printKeyHash(this);
        Thread logotimer = new Thread() {
            public void run() {
                try {
                    int logotime = 0;
                    while (logotime < 4000) {
                        sleep(100);
                        logotime += 100;
                    }
                    startActivity(new Intent(SplashActivity.this , MainActivity.class));
                    finish();
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        };
        logotimer.start();
    }

    @Override
    public void onBackPressed() {

    }

    public String getId() {
        String id = android.provider.Settings.System.getString(super.getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
        Log.e("DEVICE ", id);
        return id;
    }

   @Override
    protected void onResume() {
        super.onResume();
        Log.e("onResume" ,  "Called");
        AppEventsLogger.activateApp(this);
        // ...
    }

    // Optional: App Deactivation Event
    @Override
    protected void onPause() {
        super.onPause();
        Log.e("onPause", "Called");
        AppEventsLogger.deactivateApp(this);
    }

    public static String printKeyHash(Activity context) {
        PackageInfo packageInfo;
        String key = null;
        try {
            //getting application package name, as defined in manifest
            String packageName = context.getApplicationContext().getPackageName();

            //Retriving package info
            packageInfo = context.getPackageManager().getPackageInfo(packageName,
                    PackageManager.GET_SIGNATURES);

            Log.e("Package Name=", context.getApplicationContext().getPackageName());

            for (Signature signature : packageInfo.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                key = new String(Base64.encode(md.digest(), 0));

                // String key = new String(Base64.encodeBytes(md.digest()));
                Log.e("Key Hash=", key);
            }
        } catch (PackageManager.NameNotFoundException e1) {
            Log.e("Name not found", e1.toString());
        }
        catch (NoSuchAlgorithmException e) {
            Log.e("No such an algorithm", e.toString());
        } catch (Exception e) {
            Log.e("Exception", e.toString());
        }

        return key;
    }
}
