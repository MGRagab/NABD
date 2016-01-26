package com.appventure.nabd;

import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.appventure.nabd.helpers.DrawerArrowDrawable;

/**
 * Created by MGRagab on 11/1/2015.
 */
public class BaseActivity extends AppCompatActivity {
    public DrawerArrowDrawable drawerArrowDrawable;
    public float offset;
    public boolean flipped;
    DrawerLayout drawer;
    ImageView accountBtn, settingsBtn, MagsBtn, sourcesBtn, allnewsBtn;

    /*public void intilizeMenu() {
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        final ImageView imageView = (ImageView) findViewById(R.id.drawer_indicator);
        final Resources resources = getResources();

        drawerArrowDrawable = new DrawerArrowDrawable(resources);
        drawerArrowDrawable.setStrokeColor(resources
                .getColor(android.R.color.white));
        imageView.setImageDrawable(drawerArrowDrawable);

        drawer.setDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                offset = slideOffset;

                // Sometimes slideOffset ends up so close to but not quite 1 or
                // 0.
                if (slideOffset >= .995) {
                    flipped = true;
                    drawerArrowDrawable.setFlip(flipped);
                } else if (slideOffset <= .005) {
                    flipped = false;
                    drawerArrowDrawable.setFlip(flipped);
                }

                drawerArrowDrawable.setParameter(offset);
            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawer.isDrawerVisible(Gravity.RIGHT)) {
                    drawer.closeDrawer(Gravity.RIGHT);
                } else {
                    drawer.openDrawer(Gravity.RIGHT);
                }
            }
        });

    }
*/
    public void setupButtomTabBar() {

        accountBtn = (ImageView) findViewById(R.id.accountBtn);
        settingsBtn = (ImageView) findViewById(R.id.settingsBtn);
        MagsBtn = (ImageView) findViewById(R.id.MagsBtn);
        sourcesBtn = (ImageView) findViewById(R.id.sourcesBtn);
        allnewsBtn = (ImageView) findViewById(R.id.allnewsBtn);

        // allnewsBtn.setBackgroundDrawable(getResources().getDrawable(R.drawable.sel));


        allnewsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        settingsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BaseActivity.this, SettingsActivity.class));
            }
        });
        MagsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        sourcesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BaseActivity.this, SourcesActivity.class));
            }
        });
        accountBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
