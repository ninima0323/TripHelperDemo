package com.example.kimnahyeon.testscrollview.fragment;

import android.annotation.SuppressLint;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableArrayList;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.example.kimnahyeon.testscrollview.R;
import com.example.kimnahyeon.testscrollview.adapter.ContentAdapter;
import com.example.kimnahyeon.testscrollview.data.Content;
import com.example.kimnahyeon.testscrollview.databinding.FragmentContentBinding;

public class ContentFragment extends Fragment {


    ObservableArrayList<Content> contentList = new ObservableArrayList<>();
    ContentAdapter mAdapter;

    public ContentFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_content, container, false);

       FragmentContentBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_content, container, false);

        mAdapter = new ContentAdapter(getContext(), contentList);
        binding.fgContRv.setAdapter(mAdapter);
        mAdapter.setItems(contentList);

        prepareData();

        return binding.getRoot();
    }

    public void prepareData(){
        contentList.clear();
        int i;
        for(i=0; i<20; i++) {
            if(i%4==0){
                Content content = new Content("교통","지하철",i,"원");
                contentList.add(content);
            }else if(i%4==1){
                Content content = new Content("식사","스테이크",i,"원");
                contentList.add(content);
            }else if(i%4==2){
                Content content = new Content("관람","성입장",i,"원");
                contentList.add(content);
            }else{
                Content content = new Content("쇼핑","엽서",i,"원");
                contentList.add(content);
            }

        }
        mAdapter.notifyDataSetChanged();
    }
}
