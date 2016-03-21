package com.whut.test;

import android.test.InstrumentationTestCase;

import com.whut.myseller.utils.MapToJson;

import org.json.JSONException;

import java.util.HashMap;

/**
 * Created by root on 16-3-21.
 */
public class MyTest extends InstrumentationTestCase{

    public void test() throws JSONException {
        HashMap map = new HashMap();
        map.put("account","xyb");
        map.put("password","123");
        map.put("time", "123123");

        String json = MapToJson.MapToJson(map);
        System.out.println(json);
    }

}
