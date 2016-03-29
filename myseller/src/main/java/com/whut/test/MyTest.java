package com.whut.test;

import android.test.InstrumentationTestCase;

import com.whut.myseller.utils.MapToJson;

import org.json.JSONException;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by root on 16-3-21.
 */
public class MyTest extends InstrumentationTestCase{

    public void test() throws JSONException {
     /*   HashMap map = new HashMap();
        map.put("account","xyb");
        map.put("password","123");
        map.put("time", "123123");

        String json = MapToJson.MapToJson(map);
        System.out.println(json);*/



    }

    protected void checkData() {
        ArrayList<Integer> wifi_user_list = new ArrayList<Integer>();
        ArrayList<Integer> time = new ArrayList<Integer>();
        time.add(5);
        time.add(40);
//        wifi_user_list.add(27600/60);
//        wifi_user_list.add(27600/60);
//        wifi_user_list.add(27600/60);
//        wifi_user_list.add(27600/60);
//        wifi_user_list.add(27600/60);
//        wifi_user_list.add(27600/60);
//        wifi_user_list.add(27600/60);
        for(int j=0;j<time.size();j++){

            for (int i = 1; i <= 6; i++) {
                if (time.get(j) < i * 20 ) {
                    wifi_user_list.set(i - 1, wifi_user_list.get(i - 1) + 1);
                    break;
                }
                if (i == 6) {
                    wifi_user_list.set(5,
                            wifi_user_list.get(i - 1) + 1);
                }
            }
        }

        for (int i=0;i<wifi_user_list.size();i++){
            System.out.println("-----------"+wifi_user_list.get(i));
        }

    }



}
