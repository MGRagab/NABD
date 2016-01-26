package com.appventure.nabd;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.appventure.nabd.beans.Tag;
import com.appventure.nabd.helpers.StaticMethods;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class NewDetailsActivity extends AppCompatActivity {
    protected ImageLoader imageLoader = ImageLoader.getInstance();
    LinearLayout TagsLayout;
    TextView itemHeaderTitle, itemHeaderTime, itemTitle, itemCommentsAndSaves, itemDesc, txtHeader, itemHeaderType;
    ImageView btnFav, img, btnShare, btnBack;
    CircleImageView sourceImage;
    DisplayImageOptions options;
    public static void initImageLoader(Context context) {
        // This configuration tuning is custom. You can tune every option, you
        // may tune some of them,
        // or you can create default configuration by
        // ImageLoaderConfiguration.createDefault(this);
        // method.
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                context).threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                .diskCacheSize(50 * 1024 * 1024)
                        // 50 Mb
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .writeDebugLogs() // Remove for release app
                .build();
        // Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(config);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_new_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setupUI();

        setData();

    }

    public void setupUI() {
        TagsLayout = (LinearLayout) findViewById(R.id.TagsLayout);
        txtHeader = (TextView) findViewById(R.id.txtHeader);
        txtHeader.setTypeface(StaticMethods.getTextlightFont(NewDetailsActivity.this));

        itemHeaderTitle = (TextView) findViewById(R.id.itemHeaderTitle);
        itemHeaderTitle.setTypeface(StaticMethods.getTextlightFont(NewDetailsActivity.this));

        itemHeaderType = (TextView) findViewById(R.id.itemHeaderType);
        itemHeaderType.setTypeface(StaticMethods.getTextlightFont(NewDetailsActivity.this));

        itemHeaderTime = (TextView) findViewById(R.id.itemHeaderTime);
        itemHeaderTime.setTypeface(StaticMethods.getTextlightFont(NewDetailsActivity.this));

        itemTitle = (TextView) findViewById(R.id.itemTitle);
        itemTitle.setTypeface(StaticMethods.getMediumFont(NewDetailsActivity.this));

        itemDesc = (TextView) findViewById(R.id.itemDesc);
        itemDesc.setTypeface(StaticMethods.getTextlightFont(NewDetailsActivity.this));

        itemCommentsAndSaves = (TextView) findViewById(R.id.itemCommentsAndSaves);
        itemCommentsAndSaves.setTypeface(StaticMethods.getlightFont(NewDetailsActivity.this));

        img = (ImageView) findViewById(R.id.itemImageN);
        btnFav = (ImageView) findViewById(R.id.btnFav);
        btnBack = (ImageView) findViewById(R.id.btnBack);
        sourceImage = (CircleImageView) findViewById(R.id.itemHeaderImage);
        btnShare = (ImageView) findViewById(R.id.btnShare);

        initImageLoader(NewDetailsActivity.this);
        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.color.colorPrimaryDark)
                .showImageForEmptyUri(R.color.colorPrimaryDark)
                .showImageOnFail(R.color.colorPrimaryDark)
                .cacheInMemory(true).cacheOnDisk(true).considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565).build();

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    public void setData() {
        ArrayList<Tag> tags = StaticMethods.SELECTED_FOR_VIEW.getTags();
        LayoutInflater vi = (LayoutInflater) getApplicationContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = vi.inflate(R.layout.tag_layout, null);


        for (int i = 0; i < tags.size(); i++) {
            final TextView textView = (TextView) View.inflate(this,
                    R.layout.tag_layout, null);
            textView.setTypeface(StaticMethods.getMediumFont(NewDetailsActivity.this));
            textView.setText(tags.get(i).getName());
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            lp.setMargins(8, 0, 8, 8);
            textView.setLayoutParams(lp);
            textView.setId(i);
            TagsLayout.addView(textView);
        }


        if (StaticMethods.SELECTED_FOR_VIEW.getTitle().length()>50){
            txtHeader.setText(StaticMethods.SELECTED_FOR_VIEW.getTitle().substring(0,30)+"...");
        }else {
            txtHeader.setText(StaticMethods.SELECTED_FOR_VIEW.getTitle());
        }
        itemHeaderTitle.setText(StaticMethods.SELECTED_FOR_VIEW.getSource().getTitle());
        itemHeaderTime.setText(StaticMethods.SELECTED_FOR_VIEW.getSinceWhen());
        itemTitle.setText(StaticMethods.SELECTED_FOR_VIEW.getTitle());
        itemDesc.setText(StaticMethods.SELECTED_FOR_VIEW.getDetails());
        String csf = StaticMethods.SELECTED_FOR_VIEW.getCommentsNumber() + " " + " تعليقات";
        csf += " . " + StaticMethods.SELECTED_FOR_VIEW.getSavesNumber() + " " + " حفظ مجلات";
        itemCommentsAndSaves.setText(csf);

        imageLoader.displayImage(StaticMethods.SELECTED_FOR_VIEW.getImage(), img,
                options, new SimpleImageLoadingListener() {
                    @Override
                    public void onLoadingStarted(String imageUri, View view) {
                        // holder.progressBar.setProgress(0);
                        // progressBar.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onLoadingFailed(String imageUri, View view,
                                                FailReason failReason) {
                        // progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onLoadingComplete(String imageUri, View view,
                                                  Bitmap loadedImage) {
                        // progressBar.setVisibility(View.GONE);
                    }
                }, new ImageLoadingProgressListener() {
                    @Override
                    public void onProgressUpdate(String imageUri, View view,
                                                 int current, int total) {
                        // holder.progressBar.setProgress(Math.round(100.0f
                        // * current / total));
                    }
                });


        imageLoader.displayImage(StaticMethods.SELECTED_FOR_VIEW.getSource().getImage(), sourceImage,
                options, new SimpleImageLoadingListener() {
                    @Override
                    public void onLoadingStarted(String imageUri, View view) {
                        // holder.progressBar.setProgress(0);
                        // progressBar.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onLoadingFailed(String imageUri, View view,
                                                FailReason failReason) {
                        // progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onLoadingComplete(String imageUri, View view,
                                                  Bitmap loadedImage) {
                        // progressBar.setVisibility(View.GONE);
                    }
                }, new ImageLoadingProgressListener() {
                    @Override
                    public void onProgressUpdate(String imageUri, View view,
                                                 int current, int total) {
                        // holder.progressBar.setProgress(Math.round(100.0f
                        // * current / total));
                    }
                });


        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StaticMethods.SELECTED_FOR_SHARE = StaticMethods.SELECTED_FOR_VIEW;
                StaticMethods.SELECTED_IMAGE = getLocalBitmapUri(img);
                startActivity(new Intent(NewDetailsActivity.this, SharingActivity.class));

            }
        });
    }

    public Uri getLocalBitmapUri(ImageView imageView) {
        // Extract Bitmap from ImageView drawable
        Drawable drawable = imageView.getDrawable();
        Bitmap bmp = null;
        if (drawable instanceof BitmapDrawable) {
            bmp = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        } else {
            return null;
        }
        // Store image to default external storage directory
        Uri bmpUri = null;
        try {
            File file = new File(Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_DOWNLOADS), "share_image_" + System.currentTimeMillis() + ".png");
            file.getParentFile().mkdirs();
            FileOutputStream out = new FileOutputStream(file);

/*            Canvas canvas = new Canvas(bmp);

            Paint paint = new Paint();
            paint.setColor(Color.WHITE); // Text Color
            paint.setStrokeWidth(12); // Text Size
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OVER)); // Text Overlapping Pattern
            // some more settings...

            canvas.drawBitmap(bmp, 0, 0, paint);
            canvas.drawText("Testing...", 10, 10, paint);
*/
            bmp.compress(Bitmap.CompressFormat.PNG, 90, out);
            out.close();
            bmpUri = Uri.fromFile(file);
            StaticMethods.FILE = file;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bmpUri;
    }

    @Override
    public void onBackPressed() {
      finish();
    }
}
