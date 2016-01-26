package com.appventure.nabd.JSONHandlers;


import android.content.Context;
import android.util.Log;

import com.appventure.nabd.beans.newsSources;
import com.appventure.nabd.beans.newsSourcesTypes;
import com.appventure.nabd.helpers.Constants;
import com.appventure.nabd.helpers.JSONParser;
import com.appventure.nabd.helpers.SharedPrefrencesClass;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class SourcesTypesJSONParserHandler {
    public JSONParser jParser = new JSONParser();
    public ArrayList<newsSourcesTypes> objects;
    public Gson gson;

    public JSONArray Articles = null;

    public ArrayList<newsSourcesTypes> getData(Context ctx, int id) {
        String request = "{\"id\":" + id + "}";
        gson = new Gson();
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("id", id + "");

        JSONObject json = jParser.makeHttpRequest(Constants.APP_URL + "getSourcesTypes", "POST", params, request);

        try {
            Log.e("news Source Types", json.toString());
            Articles = json.getJSONArray("newsSourcesTypes");
            objects = new ArrayList<newsSourcesTypes>();
            // looping through All Products
            for (int i = 0; i < Articles.length(); i++) {
                newsSourcesTypes current = gson.fromJson(Articles.getJSONObject(i).toString(), newsSourcesTypes.class);
                objects.add(current);

            }
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            objects = new ArrayList<>();
        }

        return objects;
    }

}
