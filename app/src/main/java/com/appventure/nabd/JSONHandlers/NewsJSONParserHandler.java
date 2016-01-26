package com.appventure.nabd.JSONHandlers;


import android.content.Context;

import com.appventure.nabd.beans.News;
import com.appventure.nabd.beans.Source;
import com.appventure.nabd.beans.Tag;
import com.appventure.nabd.helpers.Constants;
import com.appventure.nabd.helpers.JSONParser;
import com.appventure.nabd.helpers.SharedPrefrencesClass;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class NewsJSONParserHandler {
    public static JSONParser jParser = new JSONParser();
    public static ArrayList<Object> objects;

    public static JSONArray Articles = null;

    public static ArrayList<Object> getData(Context ctx) {
        HashMap<String, String> params = new HashMap<String, String> ();
        params.put("UDID" , "1");
       // String request = "{\"userAuth\":\"1\"}";
        String request = "";
        JSONObject json = jParser.makeHttpRequest(Constants.APP_URL+"getNewsList", "POST", params, request);

        try {
            Articles = json.getJSONArray("news");
            objects = new ArrayList<Object>();
            // looping through All Products
            for (int i = 0; i < Articles.length(); i++) {
                JSONObject c = Articles.getJSONObject(i);
                JSONObject SJ = c.getJSONObject("source");
                Source source = new Source();
                source.setId(SJ.getInt("id"));
                source.setTitle(SJ.getString("title"));
                source.setImage(SJ.getString("image"));
                source.setType(SJ.getString("category"));

                News current = new News();

                current.setLink(c.getString("newsLink"));
                current.setSavesNumber(c.getInt("savesNumber"));
                current.setImage(c.getString("image"));
                current.setSinceWhen(c.getString("sinceWhen"));
                current.setTitle(c.getString("title"));
                current.setDetails(c.getString("details"));
                current.setCommentsNumber(c.getInt("commentsNumber"));
                current.setIsFavorited(c.getBoolean("isFavorited"));

                ArrayList<Tag> tags = new ArrayList<>();
                try {
                    JSONArray tArr = c.getJSONArray("tags");
                    for (int j = 0; j < tArr.length(); j++) {
                        JSONObject t = tArr.getJSONObject(j);
                        Tag tCurrent = new Tag();
                        tCurrent.setId(t.getInt("id"));
                        tCurrent.setName(t.getString("name"));
                        tags.add(tCurrent);
                    }
                } catch (Exception e) {
                    tags = new ArrayList<>();
                }
                current.setSource(source);
                current.setTags(tags);
                objects.add(current);
            }
        } catch (Exception e) {
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
