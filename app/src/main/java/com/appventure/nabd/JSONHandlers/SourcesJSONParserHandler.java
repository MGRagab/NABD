package com.appventure.nabd.JSONHandlers;


import android.content.Context;

import com.appventure.nabd.beans.News;
import com.appventure.nabd.beans.Source;
import com.appventure.nabd.beans.Tag;
import com.appventure.nabd.beans.newsSources;
import com.appventure.nabd.helpers.Constants;
import com.appventure.nabd.helpers.JSONParser;
import com.appventure.nabd.helpers.SharedPrefrencesClass;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class SourcesJSONParserHandler {
    public static JSONParser jParser = new JSONParser();
    public static ArrayList<newsSources> objects;

    public static JSONArray Articles = null;

    public static ArrayList<newsSources> getData(Context ctx) {
        HashMap<String, String> params = new HashMap<String, String> ();
        params.put("UDID" , "1");
        String request = "{\"userAuth\":\"1\"}";
        JSONObject json = jParser.makeHttpRequest(Constants.APP_URL+"getSources", "POST", params, request);

        try {
            Articles = json.getJSONArray("newsSources");
            objects = new ArrayList<newsSources>();
            // looping through All Products
            for (int i = 0; i < Articles.length(); i++) {
                JSONObject c = Articles.getJSONObject(i);
                newsSources source = new newsSources();
                source.setId(c.getInt("id"));
                source.setTitle(c.getString("title"));
                source.setImage(c.getString("image"));

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
