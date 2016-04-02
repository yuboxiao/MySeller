package com.whut.myseller.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.whut.myseller.R;
import com.whut.myseller.interfaces.IBaseView;

/**
 * Created by root on 16-4-1.
 */
public class VIPFragment extends Fragment implements IBaseView,View.OnClickListener{


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.vip_list,null);
        return view;

    }

    @Override
    public void setInfo(Object object, int code) {

    }

    @Override
    public Object getInfo(int code) {
        return null;
    }

    @Override
    public void onClick(View v) {

    }
}
