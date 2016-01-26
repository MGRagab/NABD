package com.appventure.nabd.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.appventure.nabd.R;
import com.appventure.nabd.SharingActivity;
import com.appventure.nabd.beans.News;
import com.appventure.nabd.helpers.StaticMethods;
import com.facebook.ads.MediaView;
import com.facebook.ads.NativeAd;
import com.facebook.ads.NativeAdScrollView;
import com.facebook.ads.NativeAdView;
import com.facebook.ads.NativeAdsManager;
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

/**
 * Created by MGRagab on 6/6/2015.
 */
public class ArticlesListAdapterObjects extends ArrayAdapter<Object> {
    private static int AD_INDEX = 2;
    private static int AD_INDEX_SCROLL = 10;
    private final Context context;
    private final ArrayList<Object> values;
    protected ImageLoader imageLoader = ImageLoader.getInstance();
    DisplayImageOptions options;
    TextView itemHeaderTitle, itemHeaderTime, itemTitle, itemCommentsAndSaves, itemHeaderType;
    ImageView btnFav, img, btnShare;
    CircleImageView sourceImage;
    private Activity activity;
    private LayoutInflater inflater;
    private NativeAdScrollView ad;
    private NativeAd Nativead;
    private int in = 0;

    public ArticlesListAdapterObjects(Context context, ArrayList<Object> array) {
        super(context, R.layout.new_list_item, array);
        this.context = context;
        this.values = array;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        initImageLoader(context);
        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.headerlogo)
                .showImageForEmptyUri(R.drawable.headerlogo)
                .showImageOnFail(R.drawable.headerlogo)
                .cacheInMemory(true).cacheOnDisk(true).considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565).build();
    }

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
                .build();
        // Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(config);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        if (position == AD_INDEX && Nativead != null) {
            // Return the native ad view
            return (View) values.get(position);
        } else if (position == AD_INDEX_SCROLL && ad != null) {
            return (View) values.get(position);
        } else {

            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View rowView = null;
            rowView = inflater.inflate(R.layout.new_list_item, parent,
                    false);
            // initialize the elements
            itemHeaderTitle = (TextView) rowView.findViewById(R.id.itemHeaderTitle);
            itemHeaderTitle.setTypeface(StaticMethods.getTextlightFont(context));

            itemHeaderType = (TextView) rowView.findViewById(R.id.itemHeaderType);
            itemHeaderType.setTypeface(StaticMethods.getTextlightFont(context));

            itemHeaderTime = (TextView) rowView.findViewById(R.id.itemHeaderTime);
            itemHeaderTime.setTypeface(StaticMethods.getTextlightFont(context));

            itemTitle = (TextView) rowView.findViewById(R.id.itemTitle);
            itemTitle.setTypeface(StaticMethods.getMediumFont(context));

            itemCommentsAndSaves = (TextView) rowView.findViewById(R.id.itemCommentsAndSaves);
            itemCommentsAndSaves.setTypeface(StaticMethods.getlightFont(context));

            img = (ImageView) rowView.findViewById(R.id.itemImage);
            btnFav = (ImageView) rowView.findViewById(R.id.btnFav);
            sourceImage = (CircleImageView) rowView.findViewById(R.id.itemHeaderImage);
            btnShare = (ImageView) rowView.findViewById(R.id.btnShare);
            News current = (News) values.get(position);
            itemHeaderTitle.setText(current.getSource().getTitle());
            itemHeaderTime.setText(current.getSinceWhen());
            itemTitle.setText(current.getTitle());
            String csf = current.getCommentsNumber() + " " + " تعليقات";
            csf += " . " + current.getSavesNumber() + " " + " حفظ مجلات";
            itemCommentsAndSaves.setText(csf);

            imageLoader.displayImage(current.getImage(), img,
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


            imageLoader.displayImage(current.getSource().getImage(), sourceImage,
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

            if (current.getIsFavorited()) {
                btnFav.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.fav_sel));
            }
            btnShare.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    StaticMethods.SELECTED_FOR_SHARE = (News) values.get(position);
                    StaticMethods.SELECTED_IMAGE = getLocalBitmapUri(img);
                    context.startActivity(new Intent(context, SharingActivity.class));
                    //shareImage(values.get(position).getImage(), values.get(position).getTitle(), holder.img);
                }
            });
            return rowView;
        }
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

    public synchronized void addNativeAd(NativeAdScrollView ad, NativeAdsManager manager) {
        View adView = inflater.inflate(R.layout.hscrollinlist, null);
        LinearLayout lin = (LinearLayout) adView.findViewById(R.id.hscrollContainer);
        if (ad != null) {

            lin.removeView(ad);
        }

        ad = new NativeAdScrollView(context, manager,
                NativeAdView.Type.HEIGHT_300);


        lin.addView(ad);
        this.ad = ad;
        values.add(AD_INDEX_SCROLL, lin);
        this.notifyDataSetChanged();
    }


    public synchronized void addNativeAd(NativeAd ad) {
        if (ad == null) {
            return;
        }
        if (this.Nativead != null) {
            // Clean up the old ad before inserting the new one
            this.Nativead.unregisterView();
            this.values.remove(AD_INDEX);
            this.Nativead = null;
            this.notifyDataSetChanged();
        }
        this.Nativead = ad;
        View adView = inflater.inflate(R.layout.ad_unit, null);
        this.inflateAd(ad, adView, context);
        values.add(AD_INDEX, adView);
        this.notifyDataSetChanged();
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
