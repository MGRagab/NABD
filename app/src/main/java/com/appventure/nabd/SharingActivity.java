package com.appventure.nabd;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.appventure.nabd.helpers.StaticMethods;
import com.google.android.gms.plus.PlusShare;
import android.content.ClipData;
import android.content.ClipboardManager;
import java.util.List;

public class SharingActivity extends Activity {
    ImageView closeShare, shareInsta, shareGoogle, shareFB , sharetw , shareEmail , shareCopy , shareSms;
    TextView txtShare;
    private ClipboardManager myClipboard;
    private ClipData myClip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sharing);
        setupUI();
    }

    public void setupUI() {
        myClipboard = (ClipboardManager)getSystemService(CLIPBOARD_SERVICE);

        shareInsta = (ImageView) findViewById(R.id.shareInsta);
        shareGoogle = (ImageView) findViewById(R.id.shareGoogle);
        shareFB = (ImageView) findViewById(R.id.shareFB);
        sharetw= (ImageView) findViewById(R.id.sharetw);
        shareEmail= (ImageView) findViewById(R.id.shareEmail);
        shareCopy = (ImageView) findViewById(R.id.shareCopy);
        shareSms = (ImageView) findViewById(R.id.shareSms);

        txtShare = (TextView) findViewById(R.id.txtShare);
        txtShare.setTypeface(StaticMethods.getMediumFont(SharingActivity.this));
        closeShare = (ImageView) findViewById(R.id.closeShare);

        shareSms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShareToSms(StaticMethods.SELECTED_FOR_SHARE.getLink(), StaticMethods.SELECTED_FOR_SHARE.getTitle());
            }
        });
        shareCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShareToCopy(StaticMethods.SELECTED_FOR_SHARE.getLink());
            }
        });
        shareEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShareToEmail(StaticMethods.SELECTED_FOR_SHARE.getLink(), StaticMethods.SELECTED_FOR_SHARE.getTitle());
            }
        });
        sharetw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShareToTwitter(StaticMethods.SELECTED_FOR_SHARE.getLink(), StaticMethods.SELECTED_FOR_SHARE.getTitle());
            }
        });
        shareFB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShareToFacebook(StaticMethods.SELECTED_FOR_SHARE.getLink(), StaticMethods.SELECTED_FOR_SHARE.getTitle());
            }
        });
        shareInsta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShareToInstagram(StaticMethods.SELECTED_FOR_SHARE.getLink(), StaticMethods.SELECTED_FOR_SHARE.getTitle());
            }
        });
        shareGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareToGoogle(StaticMethods.SELECTED_FOR_SHARE.getTitle(), StaticMethods.SELECTED_FOR_SHARE.getLink());
            }
        });
        closeShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void ShareToInstagram(String link, String title) {
        Intent share = new Intent(Intent.ACTION_SEND);

        share.setType("image/*");
        Uri bmpUri = StaticMethods.SELECTED_IMAGE;
        share.putExtra(Intent.EXTRA_STREAM, bmpUri);
        String shareText = "من نبض";
        shareText += "  .. " + title + ".... ";
        shareText += " أقرأ المزيد من :";
        shareText += link;
        share.putExtra(Intent.EXTRA_TEXT, shareText);
        share.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        share.setPackage("com.instagram.android");
        startActivity(share);
    }

    private void ShareToFacebook(String link, String title) {
        Intent share = new Intent(Intent.ACTION_SEND);

        share.setType("text/plain");
        share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        share.putExtra(Intent.EXTRA_TEXT, link);
        share.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        share.setPackage("com.facebook.katana");
        startActivity(share);

    }
    private void ShareToTwitter(String link, String title) {
        Intent share = new Intent(Intent.ACTION_SEND);

        share.setType("image/*");
        Uri bmpUri = StaticMethods.SELECTED_IMAGE;
        share.putExtra(Intent.EXTRA_STREAM, bmpUri);
        String shareText = "من نبض";
        shareText += "  .. " + title + ".... ";
        shareText += " أقرأ المزيد من :";
        shareText += link;
        share.putExtra(Intent.EXTRA_TEXT, shareText);
        share.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        share.setPackage("com.twitter.android");
        startActivity(share);
    }
    public void shareToGoogle(String title, String link) {

        String shareText = "من نبض";
        shareText += "  .. " + title + ".... ";
        shareText += " أقرأ المزيد من :";
        shareText += link;
        Intent shareIntent = new PlusShare.Builder(this)
                .setType("image/*")
                .setText(shareText)
                .setStream(StaticMethods.SELECTED_IMAGE)
                .getIntent()
                .setPackage("com.google.android.apps.plus");
        startActivity(shareIntent);

    }

    public  void ShareToEmail (String link, String title){
        Intent share = new Intent(Intent.ACTION_SEND);
        // prompts email clients only
        share.setType("message/rfc822");
        Uri bmpUri = StaticMethods.SELECTED_IMAGE;
        share.putExtra(Intent.EXTRA_STREAM, bmpUri);
        share.putExtra(Intent.EXTRA_EMAIL, "");
        share.putExtra(Intent.EXTRA_SUBJECT, title);
        String shareText = "من نبض";
        shareText += "  .. " + title + ".... ";
        shareText += " أقرأ المزيد من :";
        shareText += link;
        share.putExtra(Intent.EXTRA_TEXT, shareText);
       //+ share.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);


        try {
            // the user can choose the email client
            startActivity(Intent.createChooser(share, "Send Email Via .."));

        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(SharingActivity.this, "No email client installed.",
                    Toast.LENGTH_LONG).show();
        }
    }

    public void ShareToCopy(String link){

        myClip = ClipData.newPlainText("NewsLink", link);
        myClipboard.setPrimaryClip(myClip);

        Toast.makeText(getApplicationContext(), "Link Copied",Toast.LENGTH_SHORT).show();
    }

    public void ShareToSms(String link, String title) {

        Intent smsVIntent = new Intent(Intent.ACTION_VIEW);
        // prompts only sms-mms clients
        smsVIntent.setType("vnd.android-dir/mms-sms");
        String shareText = "من نبض";
        shareText += "  .. " + title + ".... ";
        shareText += " أقرأ المزيد من :";
        shareText += link;
        smsVIntent.putExtra("sms_body", shareText);
        try{
            startActivity(smsVIntent);
        } catch (Exception ex) {
            Toast.makeText(SharingActivity.this, "Your sms has failed...",
                    Toast.LENGTH_SHORT).show();
            ex.printStackTrace();
        }

    }
    @Override
    public void onBackPressed() {
        if (StaticMethods.FILE != null) {
            StaticMethods.FILE.delete();
        }
        finish();
    }
}
