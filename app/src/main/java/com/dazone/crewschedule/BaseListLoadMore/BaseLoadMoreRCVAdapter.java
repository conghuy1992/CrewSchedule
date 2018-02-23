package com.dazone.crewschedule.BaseListLoadMore;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.List;

public abstract class BaseLoadMoreRCVAdapter<T> extends BaseRecyclerViewAdapter<T> {
    protected final int VIEW_ITEM = 1;
    protected final int VIEW_PROG = 0;

    protected int visibleThreshold = 2;
    protected int lastVisibleItem, totalItemCount;
    protected boolean loading = true;
    protected OnLoadMoreListener onLoadMoreListener;

    public BaseLoadMoreRCVAdapter(List<T> myDataSet, RecyclerView recyclerView) {
        super(myDataSet);
        if(recyclerView.getLayoutManager()instanceof LinearLayoutManager) {

            final LinearLayoutManager linearLayoutManager = (LinearLayoutManager)recyclerView.getLayoutManager();
            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);

                    totalItemCount = linearLayoutManager.getItemCount();
                    lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
                    if (!loading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                        // End has been reached
                        // Do something
                        if(onLoadMoreListener!=null) {
                            onLoadMoreListener.onLoadMore();
                        }
                        loading = true;
                    }
                }
            });
        }
    }

    @Override
    public int getItemViewType(int position) {
        return dataSet.get(position)!=null? VIEW_ITEM: VIEW_PROG;
    }

    @Override
    public abstract RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType);
    @Override
    public abstract void onBindViewHolder(RecyclerView.ViewHolder holder, int position);

    public void setLoaded(){
        loading = false;
    }
    public void setLoading(){
        loading = true;
    }

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
    }

    public interface OnLoadMoreListener{
        void onLoadMore();
    }

    public void updateRecyclerView(List<T> list){
        dataSet = list;
        notifyDataSetChanged();
    }
}
