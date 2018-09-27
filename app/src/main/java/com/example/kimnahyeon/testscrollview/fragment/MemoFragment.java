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
import com.example.kimnahyeon.testscrollview.adapter.MemoAdapter;
import com.example.kimnahyeon.testscrollview.data.Memo;
import com.example.kimnahyeon.testscrollview.databinding.FragmentMemoBinding;

public class MemoFragment extends Fragment {

    ObservableArrayList<Memo> memoList = new ObservableArrayList<>();
    MemoAdapter mAdapter;

    public MemoFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_memo, container, false);

        FragmentMemoBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_memo, container, false);

        mAdapter = new MemoAdapter(getContext(), memoList);
        binding.fgMemoRv.setAdapter(mAdapter);
        mAdapter.setItems(memoList);

        prepareData();

        return binding.getRoot();
    }

    public void prepareData(){
        memoList.clear();
        int i;
        for(i=0; i<20; i++) {
            Memo memo = new Memo(i,"제목"+i,"내용"+i);
            memoList.add(memo);
        }
        mAdapter.notifyDataSetChanged();
    }
}
