package com.example.kimnahyeon.testscrollview.fragment;

import android.annotation.SuppressLint;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableArrayList;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.example.kimnahyeon.testscrollview.R;
import com.example.kimnahyeon.testscrollview.adapter.StatisticsAdapter;
import com.example.kimnahyeon.testscrollview.data.Statistics;
import com.example.kimnahyeon.testscrollview.databinding.FragmentStatisticsBinding;
public class StatisticsFragment extends Fragment{

    ObservableArrayList<Statistics> statList = new ObservableArrayList<>();
    StatisticsAdapter mAdapter;

    public StatisticsFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        FragmentStatisticsBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_statistics, container, false);

        mAdapter = new StatisticsAdapter(getContext(), statList);
        binding.fgStatRv.setAdapter(mAdapter);
        mAdapter.setItems(statList);

        prepareData();

        return binding.getRoot();
    }

    public void prepareData(){
        statList.clear();
        int i;
        for(i=0; i<20; i++) {
            Statistics stat = new Statistics("통계"+i,"내용"+i);
            statList.add(stat);
        }
        mAdapter.notifyDataSetChanged();
    }
}
