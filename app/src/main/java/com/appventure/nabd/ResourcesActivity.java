package com.appventure.nabd;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.appventure.nabd.JSONHandlers.FollowResourceJSONParserHandler;
import com.appventure.nabd.JSONHandlers.ResourcesJSONParserHandler;
import com.appventure.nabd.JSONHandlers.SourcesTypesJSONParserHandler;
import com.appventure.nabd.adapters.ResourcesListAdapter;
import com.appventure.nabd.beans.FollowResponse;
import com.appventure.nabd.beans.Resource;
import com.appventure.nabd.beans.newsSourcesTypes;
import com.appventure.nabd.helpers.StaticMethods;

import java.util.ArrayList;

public class ResourcesActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener,
        ResourcesListAdapter.customButtonListener {

    public int followPressed = -1;
    public int followID;
    public FollowResponse response;
    TextView txtType, txtHeader;
    ImageView btnBack;
    Spinner spinner;
    ArrayList<newsSourcesTypes> sourcesTypes;
    int sourceID;
    int typeID;
    ProgressDialog progressDialog;
    ProgressBar progressBar;
    ListView list;
    ArrayList<Resource> resources;
    ResourcesListAdapter adapter;
    Handler sourceTypeshandler = new Handler() {

        public void handleMessage(android.os.Message msg) {

            updateSpinner();

        }

    };
    Handler Resourceshandler = new Handler() {

        public void handleMessage(android.os.Message msg) {

            updateResources();

        }

    };
    Handler Followhandler = new Handler() {

        public void handleMessage(android.os.Message msg) {

            updateFollow();

        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resources);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setupUI();

    }

    public void setupUI() {
        txtHeader = (TextView) findViewById(R.id.txtHeader);
        txtHeader.setTypeface(StaticMethods.getBoldFont(ResourcesActivity.this));
        txtType = (TextView) findViewById(R.id.txtType);
        txtType.setTypeface(StaticMethods.getTextlightFont(ResourcesActivity.this));
        btnBack = (ImageView) findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        spinner = (Spinner) findViewById(R.id.spinner);
        Intent in = getIntent();
        String title = in.getStringExtra("title");
        txtHeader.setText(title);
        sourceID = Integer.parseInt(in.getStringExtra("id"));
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        list = (ListView) findViewById(R.id.list);
        sourcesTypes = null;
        getSourceTypes();
    }

    public void getSourceTypes() {
        sourcesTypes = new ArrayList<>();
        progressDialog = ProgressDialog.show(ResourcesActivity.this, null, "جارى التحميل...");
        progressDialog.setCancelable(false);
        GetSourceTypesFromService gstfs = new GetSourceTypesFromService();
        gstfs.start();
    }

    public void updateSpinner() {
        progressDialog.dismiss();
        String[] types = new String[sourcesTypes.size()];
        for (int i = 0; i < sourcesTypes.size(); i++) {
            types[i] = sourcesTypes.get(i).getTitle();
        }
        ArrayAdapter<String> adapter_state = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, types);
        adapter_state
                .setDropDownViewResource(R.layout.spinner_item);
        spinner.setAdapter(adapter_state);
        spinner.setOnItemSelectedListener(this);

    }


    public void onItemSelected(AdapterView<?> parent, View view, int position,
                               long id) {
        spinner.setSelection(position);
        String selState = (String) spinner.getSelectedItem();
        typeID = Integer.parseInt(sourcesTypes.get(position).getId());
        getResourcess();
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub

    }

    public void getResourcess() {
        resources = null;
        resources = new ArrayList<>();
        list = (ListView) findViewById(R.id.list);
        adapter = null;
        if (progressBar.getVisibility() == View.GONE) {
            list.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
        }
        GetResourcesFromService grfs = new GetResourcesFromService();
        grfs.start();
    }

    public void updateResources() {
        followPressed = -1;
        adapter = new ResourcesListAdapter(ResourcesActivity.this, resources);
        adapter.setCustomButtonListner(ResourcesActivity.this);
        list.setAdapter(adapter);
        progressBar.setVisibility(View.GONE);
        list.setVisibility(View.VISIBLE);
    }

    public void updateFollow() {
        if (response.getStatus() == 1) {
            resources.get(followPressed).setIsFollowed(response.getIsFollowed());
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onBackPressed() {
        followPressed = -10;
        finish();
    }

    public void followResource(int id) {
        followID = id;
        response = new FollowResponse();
        SetFollowResource fr = new SetFollowResource();
        fr.start();
    }


    class GetSourceTypesFromService extends Thread {
        @Override
        public void run() {
            super.run();
            SourcesTypesJSONParserHandler sj = new SourcesTypesJSONParserHandler();
            sourcesTypes = sj.getData(ResourcesActivity.this, sourceID);
            sourceTypeshandler.sendEmptyMessage(0);
        }
    }

    class GetResourcesFromService extends Thread {
        @Override
        public void run() {
            super.run();
            ResourcesJSONParserHandler resourcesJSONParserHandler = new ResourcesJSONParserHandler();
            resources = resourcesJSONParserHandler.getData(ResourcesActivity.this, typeID);
            Resourceshandler.sendEmptyMessage(0);
        }
    }

    public class SetFollowResource extends Thread {
        @Override
        public void run() {
            super.run();
            FollowResourceJSONParserHandler followResourceJSONParserHandler = new FollowResourceJSONParserHandler();
            response = followResourceJSONParserHandler.getData(ResourcesActivity.this, followID);
            Followhandler.sendEmptyMessage(0);
        }
    }

    @Override
    public void onButtonClickListner(int position, int value) {
        followPressed = position;
        followResource(value);

    }
}
