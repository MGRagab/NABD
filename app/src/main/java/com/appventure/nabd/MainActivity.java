package com.appventure.nabd;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.appventure.nabd.JSONHandlers.NewsJSONParserHandler;
import com.appventure.nabd.adapters.ArticlesListAdapterObjects;
import com.appventure.nabd.beans.News;
import com.appventure.nabd.helpers.ParseUtils;
import com.appventure.nabd.helpers.SharedPrefrencesClass;
import com.appventure.nabd.helpers.StaticMethods;
import com.facebook.ads.AdError;
import com.facebook.ads.NativeAd;
import com.facebook.ads.NativeAdScrollView;
import com.facebook.ads.NativeAdsManager;

import java.util.ArrayList;

public class MainActivity extends BaseActivity implements AdapterView.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener, NativeAdsManager.Listener {

    public static ArrayList<Object> articles;
    TextView txtHeader;
    ListView list;
    ArticlesListAdapterObjects adapter;
    ImageView listingBtn;
    String emailParse = "mohamed.gamal091@gmail.com";
    SharedPrefrencesClass shared;
    Toolbar toolbar;
    private CoordinatorLayout coordinatorLayout;
    private SwipeRefreshLayout swipeRefreshLayout;
    private NativeAdsManager manager, listNativeAdsManager;
    Handler handler = new Handler() {

        public void handleMessage(android.os.Message msg) {

            updateView();

        }

    };
    private NativeAdScrollView scrollView, scrollView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setupUI();


    }


    public void setupUI() {
        // intilizeMenu();
        setupButtomTabBar();
        //  allnewsBtn.setBackgroundDrawable(getResources().getDrawable(R.drawable.unsel));
        txtHeader = (TextView) findViewById(R.id.txtHeader);
        txtHeader.setTypeface(StaticMethods.getBoldFont(MainActivity.this));
        list = (ListView) findViewById(R.id.list);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id
                .coordinatorLayout);
        shared = new SharedPrefrencesClass();
        // articles = shared.getOFFLINEARTICLES(MainActivity.this);

        swipeRefreshLayout.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        swipeRefreshLayout.setRefreshing(true);
                                        checkForInternet();
                                    }
                                }
        );

        listingBtn = (ImageView) findViewById(R.id.listingBtn);
        listingBtn.setVisibility(View.GONE);
        listingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, FlipViewActivity.class));
            }
        });

    }


    public void checkForInternet() {
        if (StaticMethods.checkInternetConenction(MainActivity.this)) {
            swipeRefreshLayout.setRefreshing(false);
            doActionIfInternetExist();
        } else {
            swipeRefreshLayout.setRefreshing(false);
            doActionIfInternetNot();
        }
    }

    public void doActionIfInternetExist() {
        swipeRefreshLayout.setOnRefreshListener(this);
        ParseUtils.verifyParseConfiguration(this);
        ParseUtils.subscribeWithEmail(emailParse);
        getNews();
    }

    public void doActionIfInternetNot() {
        if (articles.size() > 0) {
            adapter = new ArticlesListAdapterObjects(MainActivity.this, articles);
            list.setAdapter(adapter);
            list.setVisibility(View.VISIBLE);
            list.setOnItemClickListener(this);
        }
        Snackbar snackbar = Snackbar.make(coordinatorLayout, "لا يوجد اتصال بالانترنت", Snackbar.LENGTH_LONG)
                .setAction("حاول مرة اخرى", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        checkForInternet();
                    }
                });

        snackbar.setActionTextColor(Color.WHITE);
        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.RED);
        snackbar.show();
    }


    @Override
    public void onRefresh() {
        getNews();
    }

    public void getNews() {
        swipeRefreshLayout.setRefreshing(true);
        articles = new ArrayList<>();
        getNewsFromService getNewsFromService = new getNewsFromService();
        getNewsFromService.start();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        StaticMethods.SELECTED_FOR_VIEW = (News) articles.get(position);
        Intent intent = new Intent(MainActivity.this, NewDetailsActivity.class);
        startActivity(intent);
    }

    public void updateView() {

        swipeRefreshLayout.setRefreshing(false);
        if (articles != null) {
            if (articles.size() > 0) {
                adapter = new ArticlesListAdapterObjects(MainActivity.this, articles);
        /*AvocarrotInstream myFeedAdapter = new AvocarrotInstream(adapter,MainActivity.this, "3dbab458941a2446e2b48ac866b42027f5cac288", "0aa0d2193aca5716b25bfaee403a91f588953a08");
        myFeedAdapter.setSandbox(true);
        myFeedAdapter.setLogger(true, "ALL");
        myFeedAdapter.setFrequency(1, 4);*/


                manager = new NativeAdsManager(this, "1029159260470325_1043681482351436", 3);
                manager.setListener(this);
                manager.loadAds(NativeAd.MediaCacheFlag.ALL);


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
                        Toast.makeText(MainActivity.this, "Native ads manager failed to load: " + error.getErrorMessage(),
                                Toast.LENGTH_SHORT).show();
                    }

                });
                listNativeAdsManager.loadAds();
                list.setAdapter(adapter);
                list.setVisibility(View.VISIBLE);
                list.setOnItemClickListener(this);
            }
        } else {
            if (StaticMethods.checkInternetConenction(MainActivity.this)) {
                Snackbar snackbar = Snackbar.make(coordinatorLayout, "حدث خطأ فى تحميل البيانات", Snackbar.LENGTH_LONG)
                        .setAction("حاول مرة اخرة", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                doActionIfInternetExist();
                            }
                        });

                snackbar.setActionTextColor(Color.WHITE);
                View sbView = snackbar.getView();
                TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
                textView.setTextColor(Color.RED);
                snackbar.show();
            } else {
                Snackbar snackbar = Snackbar.make(coordinatorLayout, "لا يوجد اتصال بالانترنت", Snackbar.LENGTH_LONG)
                        .setAction("حاول مرة اخرى", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                doActionIfInternetNot();
                            }
                        });
                snackbar.setActionTextColor(Color.WHITE);
                View sbView = snackbar.getView();
                TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
                textView.setTextColor(Color.RED);
                snackbar.show();
            }
        }
       /* manager2 = new NativeAdsManager(this, "1029159260470325_1043681482351436", 1);
        manager2.setListener(new NativeAdsManager.Listener() {
            @Override
            public void onAdsLoaded() {
                Toast.makeText(MainActivity.this, "Ads Scroll loaded", Toast.LENGTH_SHORT).show();
                adapter.addNativeAdScroll(scrollView2, manager2);
            }

            @Override
            public void onAdError(AdError adError) {
                Toast.makeText(MainActivity.this, "Ad Scroll error: " + adError.getErrorMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        manager2.loadAds(NativeAd.MediaCacheFlag.ALL);*/
    }


    public void onAdsLoaded() {
        //Toast.makeText(this, "Ads loaded", Toast.LENGTH_SHORT).show();
        adapter.addNativeAd(scrollView, manager);
    }

    @Override
    public void onAdError(AdError error) {
        Toast.makeText(this, "Ad error: " + error.getErrorMessage(), Toast.LENGTH_SHORT).show();
    }

    public class getNewsFromService extends Thread {
        @Override
        public void run() {
            super.run();
            if (articles != null) {
                if (articles.size() > 0) {
                    articles = (ArrayList<Object>) NewsJSONParserHandler.getData(MainActivity.this);
                }
            }
            handler.sendEmptyMessage(0);

        }
    }


}
