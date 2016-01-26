package com.appventure.nabd.adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.appventure.nabd.R;
import com.appventure.nabd.ResourcesActivity;
import com.appventure.nabd.beans.Resource;
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

/**
 * Created by MGRagab on 6/6/2015.
 */
public class ResourcesListAdapter extends ArrayAdapter<Resource> {
    private final Context context;
    private final ArrayList<Resource> values;
    protected ImageLoader imageLoader = ImageLoader.getInstance();
    DisplayImageOptions options;
    ViewHolder holder;
    customButtonListener customListner;
    private Activity activity;

    public ResourcesListAdapter(Context context, ArrayList<Resource> array) {
        super(context, R.layout.resource_list_item, array);
        this.context = context;
        this.values = array;
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

    public void setCustomButtonListner(customButtonListener listener) {
        this.customListner = listener;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View rowView = convertView;
        holder = new ViewHolder();

        if (rowView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(R.layout.resource_list_item, parent,
                    false);
            // initialize the elements

            holder.itemTitle = (TextView) rowView.findViewById(R.id.itemText);
            holder.itemTitle.setTypeface(StaticMethods.getMediumFont(context));
            holder.sourceImage = (CircleImageView) rowView.findViewById(R.id.itemImage);
            holder.btnFollow = (Button) rowView.findViewById(R.id.btnFollow);

            rowView.setTag(holder);
        } else {
            holder = (ViewHolder) rowView.getTag();
        }


        holder.itemTitle.setText(values.get(position).getTitle());


        imageLoader.displayImage(values.get(position).getImage(), holder.sourceImage,
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

        if (values.get(position).getIsFollowed() == 1) {
            holder.btnFollow.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.follow_btn_sel_shape));
            holder.btnFollow.setText("متابع");
            holder.btnFollow.setTextColor(context.getResources().getColor(R.color.primary_text));
            holder.btnFollow.setTypeface(StaticMethods.getTextlightFont(context));

        } else {
            holder.btnFollow.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.follow_btn_unsel_shape));
            holder.btnFollow.setText("تابع المصدر");
            holder.btnFollow.setTextColor(context.getResources().getColor(android.R.color.white));
            holder.btnFollow.setTypeface(StaticMethods.getTextlightFont(context));
        }

        holder.btnFollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int isFollowed = values.get(position).getIsFollowed();
                int id = values.get(position).getId();
                if (customListner != null) {
                    customListner.onButtonClickListner(position, id);
                }
            }
        });

        return rowView;
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

    public interface customButtonListener {
        public void onButtonClickListner(int position, int followed);
    }

    public class ViewHolder {
        TextView itemTitle;
        CircleImageView sourceImage;
        Button btnFollow;
    }


}
