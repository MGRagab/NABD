package com.appventure.nabd;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.appventure.nabd.adapters.LauncherPagerAdapter;
import com.appventure.nabd.helpers.ParallaxPageTransformer;
import com.flyco.pageindicator.indicator.FlycoPageIndicaor;

public class LauncherActivity extends FragmentActivity {

    private static final int NUM_PAGES = 3;
    FlycoPageIndicaor indicator;
    private PagerAdapter mPagerAdapter;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);
        mViewPager = (ViewPager) findViewById(R.id.pager);
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        initUI();
    }

    public void initUI() {
        mPagerAdapter = new LauncherPagerAdapter(this);
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mPagerAdapter);
        indicator = (FlycoPageIndicaor) findViewById(R.id.indicator_circle);
        ParallaxPageTransformer pageTransformer = new ParallaxPageTransformer()
                .addViewToParallax(new ParallaxPageTransformer.ParallaxTransformInformation(R.id.img, 2, 2))
                .addViewToParallax(new ParallaxPageTransformer.ParallaxTransformInformation(R.id.txtText, -0.65f,
                        -101.1986f))
                .addViewToParallax(new ParallaxPageTransformer.ParallaxTransformInformation(R.id.nabdLogo, -0.65f,
                        -101.1986f))
                .addViewToParallax(new ParallaxPageTransformer.ParallaxTransformInformation(R.id.btnChooseRes, -0.65f,
                        -101.1986f))
                .addViewToParallax(new ParallaxPageTransformer.ParallaxTransformInformation(R.id.btnSignIN, -0.65f,
                        -101.1986f))
                .addViewToParallax(new ParallaxPageTransformer.ParallaxTransformInformation(R.id.txtNoAccount, -0.65f,
                        -101.1986f));
        mViewPager.setPageTransformer(true, pageTransformer);
        indicator.setViewPager(mViewPager);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }
}
