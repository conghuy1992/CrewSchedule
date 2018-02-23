package com.dazone.crewschedule.BaseListLoadMore;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dazone.crewschedule.HTTPs.HttpRequest;
import com.dazone.crewschedule.R;

import java.util.ArrayList;
import java.util.List;

public abstract class ListFragment<T> extends Fragment {
    protected BaseLoadMoreRCVAdapter adapterList;
    protected List<T> dataSet;
    protected HttpRequest mHttpRequest;
    protected RecyclerView rvMainList;
    protected LinearLayout recycler_header,recycler_footer;
    protected RelativeLayout list_content_rl;
    protected TextView no_item_found;
    protected SwipeRefreshLayout swipeRefreshLayout;
    protected int limit  = 20;
    protected String str_lastID  = "";
    protected int index  = 0;
    protected RecyclerView.LayoutManager layoutManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHttpRequest = HttpRequest.getInstance();
        dataSet = new ArrayList<>();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_list, container, false);
        rvMainList = (RecyclerView) v.findViewById(R.id.rv_main);
        recycler_header = (LinearLayout) v.findViewById(R.id.recycler_header);
        recycler_footer = (LinearLayout) v.findViewById(R.id.recycler_footer);
        list_content_rl = (RelativeLayout) v.findViewById(R.id.list_content_rl);
        no_item_found = (TextView) v.findViewById(R.id.no_item_found);

        swipeRefreshLayout = (SwipeRefreshLayout) v.findViewById(R.id.swipeRefreshLayout);
        setupRecyclerView();
        initSwipeRefresh();
        initList();
        return v;
    }
    protected void setupRecyclerView()
    {
        rvMainList.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        rvMainList.setLayoutManager(layoutManager);
        initAdapter();
        rvMainList.setAdapter(adapterList);
    }
    public void enableLoadingMore()
    {
        adapterList.setOnLoadMoreListener(new BaseLoadMoreRCVAdapter.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                addMoreItem();
            }
        });
    }
    public void disableSwipeRefresh()
    {
        swipeRefreshLayout.setEnabled(false);
    }
    protected void initSwipeRefresh() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                str_lastID = "";
                index = 0;
                dataSet.clear();
                addMoreItem();
            }
        });
    }
    protected void closeSwipeRefresh()
    {
        if(swipeRefreshLayout==null)
            return;
        if(swipeRefreshLayout.isRefreshing())
        {
            swipeRefreshLayout.setRefreshing(false);
        }
    }
    protected  abstract void initAdapter();
    protected abstract void addMoreItem();
    protected void initList()
    {
        dataSet.add(null);
        adapterList.notifyItemChanged(dataSet.size() - 1);
        addMoreItem();
    }

    protected void checkListReturn(List<T> list)
    {
        if(dataSet==null)
        {
            dataSet = new ArrayList<>();
        }
        if(swipeRefreshLayout.isRefreshing())
        {
            swipeRefreshLayout.setRefreshing(false);
        }
        if(dataSet.size()!=0)
        {
            if(dataSet.get(dataSet.size() - 1)==null)
            {
                dataSet.remove(dataSet.size() - 1);
            }
        }
        dataSet.addAll(list);
        if(index==0) {
            adapterList.notifyDataSetChanged();
        }
        else
        {
            adapterList.notifyItemRangeInserted(index,list.size());
        }
        if(list.size()>=limit)
        {
            adapterList.setLoaded();
        }
        index = dataSet.size();
    }
}
