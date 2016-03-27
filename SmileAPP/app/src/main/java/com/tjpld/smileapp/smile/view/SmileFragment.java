package com.tjpld.smileapp.smile.view;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tjpld.smileapp.R;
import com.tjpld.smileapp.config.model.SmileContentModel;
import com.tjpld.smileapp.smile.data.SmileAdapter;
import com.tjpld.smileapp.smile.presenter.ISmilePresenter;
import com.tjpld.smileapp.smile.presenter.ISmileView;
import com.tjpld.smileapp.smile.presenter.SmileCompl;

import java.util.ArrayList;
import java.util.List;

/**
 * 显示笑话的页面
 */
public class SmileFragment extends Fragment implements ISmileView {
    public RecyclerView mRecycle;
    private ISmilePresenter mPresenter;
    private List<SmileContentModel> mData = new ArrayList<>();
    private SmileAdapter mAdapter;

    public SmileFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_smile, container, false);
        initRecycleView(view);
        mAdapter = new SmileAdapter(getActivity(), mData);
        mPresenter = new SmileCompl(getActivity(), this);
        mPresenter.loadSmileData();
        return view;
    }
    public void initRecycleView(View view){
        mRecycle= (RecyclerView) view.findViewById(R.id.rl_smile);
        mRecycle.setLayoutManager(new LinearLayoutManager(getActivity()));
    }
    @Override
    public void refreshSmileData() {
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void loadSmileData(List<SmileContentModel> data) {
        mData.clear();
        mData.addAll(data);
        mRecycle.setAdapter(mAdapter);
    }
}
