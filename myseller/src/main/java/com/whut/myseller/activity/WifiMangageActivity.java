package com.whut.myseller.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;

import com.whut.myseller.interfaces.IBaseView;

/**
 * Created by root on 16-4-1.
 */
public class WifiMangageActivity extends Activity implements IBaseView {
    @Override
    public void setInfo(Object object, int code) {

    }

    @Override
    public Object getInfo(int code) {
        return null;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView();

    }
}
