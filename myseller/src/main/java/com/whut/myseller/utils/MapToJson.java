package com.whut.myseller.utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;
import java.util.Set;

/**
 * Created by root on 16-3-21.
 */
public class MapToJson {

    public static String MapToJson(Map map) throws JSONException {
        JSONObject jsonObject = new JSONObject();
          Set<String> keySet=map.keySet();
        for(String key :keySet){
            jsonObject.put(key,map.get(key));
        }
        return jsonObject.toString();
    }
}
