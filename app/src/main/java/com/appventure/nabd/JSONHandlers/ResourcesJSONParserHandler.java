package com.appventure.nabd.JSONHandlers;


import android.content.Context;

import com.appventure.nabd.beans.Resource;
import com.appventure.nabd.helpers.Constants;
import com.appventure.nabd.helpers.JSONParser;
import com.appventure.nabd.helpers.SharedPrefrencesClass;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class ResourcesJSONParserHandler {
    public JSONParser jParser = new JSONParser();
    public ArrayList<Resource> objects;

    public JSONArray Articles = null;

    public ArrayList<Resource> getData(Context ctx, int id) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("UDID", "1");
        params.put("id", id + "");
        String request = "{\"userAuth\":\"1\"}";
        JSONObject json = jParser.makeHttpRequest(Constants.APP_URL + "getResources", "POST", params, request);

        try {
            Articles = json.getJSONArray("newsResources");
            objects = new ArrayList<Resource>();
            // looping through All Products
            for (int i = 0; i < Articles.length(); i++) {
                JSONObject c = Articles.getJSONObject(i);
                Resource source = new Resource();
                source.setId(c.getInt("id"));
                source.setTitle(c.getString("title"));
                source.setImage(c.getString("image"));
                source.setIsFollowed(c.getInt("isFollowed"));
                objects.add(source);
            }
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            objects = new ArrayList<>();
        }
        if (objects.size() > 0) {
            SharedPrefrencesClass shared = new SharedPrefrencesClass();
            //shared.saveOFFLINEARTICLES(ctx,(ArrayList<News>)objects);
        }
        return objects;
    }

}
