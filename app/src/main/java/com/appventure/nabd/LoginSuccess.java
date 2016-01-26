package com.appventure.nabd;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

public class LoginSuccess extends AppCompatActivity {
    private TextView facebookId;
    private TextView fName;
    private TextView mName;
    private TextView lName;
    private TextView fullName;
    private TextView profileImage;
    private TextView emailId;
    private TextView gender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_success);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        findViews();
        Intent i = getIntent();
        i.getStringExtra("type");
        String facebook_id = i.getStringExtra("facebook_id");
        String f_name = i.getStringExtra("f_name");
        String m_name = i.getStringExtra("m_name");
        String l_name = i.getStringExtra("l_name");
        String full_name = i.getStringExtra("full_name");
        String profile_image = i.getStringExtra("profile_image");
        String email_id = i.getStringExtra("email_id");
        String ggender = i.getStringExtra("gender");
        String b = i.getStringExtra("birthday");
        facebookId.setText(facebook_id);
        fName.setText(f_name);
        mName.setText(m_name);
        lName.setText(l_name);
        fullName.setText(full_name);
        profileImage.setText(profile_image);
        emailId.setText(email_id);
        gender.setText(ggender + " Birthday : "+b);
    }


    private void findViews() {
        facebookId = (TextView) findViewById(R.id.facebook_id);
        fName = (TextView) findViewById(R.id.f_name);
        mName = (TextView) findViewById(R.id.m_name);
        lName = (TextView) findViewById(R.id.l_name);
        fullName = (TextView) findViewById(R.id.full_name);
        profileImage = (TextView) findViewById(R.id.profile_image);
        emailId = (TextView) findViewById(R.id.email_id);
        gender = (TextView) findViewById(R.id.gender);
    }


}
