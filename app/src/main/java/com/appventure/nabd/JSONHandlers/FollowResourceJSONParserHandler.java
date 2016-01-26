package com.appventure.nabd.JSONHandlers;


import android.content.Context;
import android.util.Log;

import com.appventure.nabd.beans.FollowResponse;
import com.appventure.nabd.helpers.Constants;
import com.appventure.nabd.helpers.JSONParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class FollowResourceJSONParserHandler {
    public JSONParser jParser = new JSONParser();
    public FollowResponse object;

    public JSONArray Articles = null;

    public FollowResponse getData(Context ctx, int id) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("UDID", "1");
        params.put("sourceId", "\"" + id + "\"");
        String request = "{\"UDID\":\"1\" , \"sourceId\":\"" + id + "\"}";
        //String request ="" ;
        JSONObject json = jParser.makeHttpRequest(Constants.APP_URL + "followSource", "POST", params, request);

        try {
            object = new FollowResponse();
            // looping through All Products
            Log.e("JSON", json.toString());
            object.setStatus(json.getInt("status"));
            object.setIsFollowed(json.getInt("isFollowed"));
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            object.setStatus(0);
            object.setIsFollowed(0);
        }

        return object;
    }

}
