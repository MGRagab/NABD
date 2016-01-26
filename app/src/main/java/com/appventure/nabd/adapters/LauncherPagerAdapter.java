package com.appventure.nabd.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.appventure.nabd.CustomViews.GESSButton;
import com.appventure.nabd.CustomViews.GESSLightTextView;
import com.appventure.nabd.MainActivity;
import com.appventure.nabd.R;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by macbookpro on 15-12-08.
 */
public class LauncherPagerAdapter extends PagerAdapter {

    Context mContext;
    LayoutInflater mLayoutInflater;

    public LauncherPagerAdapter(Context context) {
        mContext = context;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((LinearLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View itemView = null;
        itemView = mLayoutInflater.inflate(R.layout.wh_one, container, false);
        ImageView img = (ImageView) itemView.findViewById(R.id.img);
        GESSLightTextView txtText = (GESSLightTextView) itemView.findViewById(R.id.txtText);
        GESSButton btnChooseRes = (GESSButton) itemView.findViewById(R.id.btnChooseRes);
        GESSButton btnSignIN = (GESSButton) itemView.findViewById(R.id.btnSignIN);
        GESSLightTextView txtNoAccount = (GESSLightTextView) itemView.findViewById(R.id.txtNoAccount);

        try {
            if (position == 0) {
                img.setBackgroundDrawable(getAssetImage(mContext, "wh_bg1"));
                txtText.setText("كن أول من يعلم مع تطبيق نبض -أول تطبيق مجانى يقدم لك اخر الاخبار من مصادرك المختارة");
                btnChooseRes.setTextColor(mContext.getResources().getColor(android.R.color.transparent));
                btnChooseRes.setBackgroundColor(mContext.getResources().getColor(android.R.color.transparent));

                btnSignIN.setTextColor(mContext.getResources().getColor(android.R.color.transparent));
                btnSignIN.setBackgroundColor(mContext.getResources().getColor(android.R.color.transparent));

                txtNoAccount.setTextColor(mContext.getResources().getColor(android.R.color.transparent));
                btnChooseRes.setVisibility(View.GONE);
                btnSignIN.setVisibility(View.GONE);


            } else if (position == 1) {
                img.setBackgroundDrawable(getAssetImage(mContext, "wh_bg2"));
                txtText.setText("تابع مصادرك المفضلة من السعودية، مصر، الكويت، قطر، الإمارات، البحرين، ،فلسطين، الأردن، المغرب، تونس، ليبيا، اليمن، لبنان، سوريا، عمان، العراق، السودان و مصادر عربية وعالمية أخرى");
                btnChooseRes.setTextColor(mContext.getResources().getColor(android.R.color.transparent));
                btnChooseRes.setBackgroundColor(mContext.getResources().getColor(android.R.color.transparent));

                btnSignIN.setTextColor(mContext.getResources().getColor(android.R.color.transparent));
                btnSignIN.setBackgroundColor(mContext.getResources().getColor(android.R.color.transparent));

                txtNoAccount.setTextColor(mContext.getResources().getColor(android.R.color.transparent));
                btnChooseRes.setVisibility(View.GONE);
                btnSignIN.setVisibility(View.GONE);

            } else {
                img.setBackgroundDrawable(getAssetImage(mContext, "wh_bg3"));
                txtText.setText("");
                btnChooseRes.setVisibility(View.VISIBLE);
                btnSignIN.setVisibility(View.VISIBLE);

                btnChooseRes.setTextColor(mContext.getResources().getColor(android.R.color.white));
                btnChooseRes.setBackgroundColor(mContext.getResources().getColor(R.color.primary_text));

                btnSignIN.setTextColor(mContext.getResources().getColor(android.R.color.white));
                btnSignIN.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.transparent_white_button));
                btnSignIN.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mContext.startActivity(new Intent(mContext , MainActivity.class));
                    }
                });
                txtNoAccount.setTextColor(mContext.getResources().getColor(android.R.color.white));

            }
        } catch (IOException e) {

        }
        container.addView(itemView);
        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }

    public static Drawable getAssetImage(Context context, String filename) throws IOException {
        AssetManager assets = context.getResources().getAssets();
        InputStream buffer = new BufferedInputStream((assets.open("drawable/" + filename + ".jpg")));
        Bitmap bitmap = BitmapFactory.decodeStream(buffer);
        return new BitmapDrawable(context.getResources(), bitmap);
    }
}



