package com.appventure.nabd;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.appventure.nabd.JSONHandlers.SourcesJSONParserHandler;
import com.appventure.nabd.adapters.SourcesListAdapter;
import com.appventure.nabd.beans.newsSources;

import java.util.ArrayList;

/**
 * Created by MGRagab on 11/5/2015.
 */
public class OneFragment extends Fragment implements AdapterView.OnItemClickListener {

    ProgressBar progressBar;
    ListView list;
    ArrayAdapter adapter;
    ArrayList<newsSources> sources;
    Handler handler = new Handler() {

        public void handleMessage(android.os.Message msg) {

            updateView();

        }

    };

    public OneFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_one, container, false);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        list = (ListView) view.findViewById(R.id.list);
        sources = new ArrayList<>();
        getSrouces();
        return view;
    }

    public void getSrouces() {
        GetSourcesFromService gsfs = new GetSourcesFromService();
        gsfs.start();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent in = new Intent(getActivity().getApplicationContext(), ResourcesActivity.class);
        in.putExtra("id", sources.get(position).getId()+"");
        in.putExtra("title" , sources.get(position).getTitle());
        getActivity().startActivity(in);
    }

    public void updateView() {
        progressBar.setVisibility(View.GONE);
        adapter = new SourcesListAdapter(getActivity().getApplicationContext(), sources);
        list.setAdapter(adapter);
        list.setVisibility(View.VISIBLE);
        list.setOnItemClickListener(this);
    }

    class GetSourcesFromService extends Thread {
        @Override
        public void run() {
            super.run();
            sources = (ArrayList<newsSources>) (SourcesJSONParserHandler.getData(getActivity().getApplicationContext()));
            handler.sendEmptyMessage(0);
        }
    }
}
