package com.appventure.nabd;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.appventure.nabd.FlipLibrary.flip.FlipViewController;
import com.appventure.nabd.JSONHandlers.NewsJSONParserHandler;
import com.appventure.nabd.adapters.ArticlesListAdapter;
import com.appventure.nabd.adapters.ArticlesListAdapterObjects;
import com.appventure.nabd.beans.News;
import com.appventure.nabd.beans.Source;
import com.appventure.nabd.beans.Tag;
import com.appventure.nabd.helpers.StaticMethods;
import com.facebook.ads.AdError;
import com.facebook.ads.NativeAd;
import com.facebook.ads.NativeAdsManager;

import java.util.ArrayList;

public class FlipViewActivity extends BaseActivity  {
    private FlipViewController flipView;
    public  static ArrayList<Object> articles ;
    LinearLayout flipviewContaner ;
    ProgressBar progressBar ;
    TextView txtHeader ;
    ImageView listingBtn;
    private NativeAdsManager  listNativeAdsManager;
    ArticlesListAdapterObjects adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_flip_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setupUI();
        getNews();
    }

    public  void setupUI (){
        //intilizeMenu();
        setupButtomTabBar();
        txtHeader = (TextView) findViewById(R.id.txtHeader);
        txtHeader.setTypeface(StaticMethods.getTextlightFont(FlipViewActivity.this));
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        flipviewContaner = (LinearLayout) findViewById(R.id.flipviewContaner);
        flipView = new FlipViewController(this, FlipViewController.VERTICAL);
        flipviewContaner.setVisibility(View.GONE);
        listingBtn = (ImageView) findViewById(R.id.listingBtn);
        listingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    public  void getNews (){
        articles = new ArrayList<>();
        getNewsFromService getNewsFromService = new getNewsFromService();
        getNewsFromService.start();
    }



    public class getNewsFromService extends  Thread{
        @Override
        public void run() {
            super.run();
            articles = (ArrayList<Object>) NewsJSONParserHandler.getData(FlipViewActivity.this);
            handler.sendEmptyMessage(0);
        }
    }
    Handler handler = new Handler() {

        public void handleMessage(android.os.Message msg) {

            updateView();

        }

    };


    public void updateView(){
        progressBar.setVisibility(View.GONE);
       adapter =  new ArticlesListAdapterObjects(this, articles);
        listNativeAdsManager = new NativeAdsManager(this, "1029159260470325_1043301265722791", 2);
        listNativeAdsManager.setListener(new NativeAdsManager.Listener() {

            @Override
            public void onAdsLoaded() {
                NativeAd ad = listNativeAdsManager.nextNativeAd();
                // ad.setAdListener(MainActivity.this);
                adapter.addNativeAd(ad);
            }

            @Override
            public void onAdError(AdError error) {
                Toast.makeText(FlipViewActivity.this, "Native ads manager failed to load: " + error.getErrorMessage(),
                        Toast.LENGTH_SHORT).show();
            }

        });
        listNativeAdsManager.loadAds();

        flipView.setAdapter(adapter);
        flipviewContaner.addView(flipView);
        flipviewContaner.setVisibility(View.VISIBLE);
        flipviewContaner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position =  flipView.getSelectedItemPosition();
                StaticMethods.SELECTED_FOR_VIEW = (News)articles.get(position);
                startActivity(new Intent(FlipViewActivity.this , NewDetailsActivity.class));
            }
        });
    }


}
