package com.whut.myseller.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.whut.myseller.R;

/**
 * Created by root on 16-3-28.
 */
public class TodayCountFragment extends Fragment{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view =
        inflater.inflate(R.layout.fragment_today_count,null);

        return view;
    }
}
