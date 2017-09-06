package com.baway.recyclerviewdemo2;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRv;
    private SwipeRefreshLayout mSrl;
    private List<String> list = new ArrayList<>();
    private MyAdapter adapter;
    private Handler handler = new Handler();
    private LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        //设置布局加载器
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRv.setLayoutManager(linearLayoutManager);

        //设置适配器
        for (int i = 0; i < 50; i++) {
            list.add("item" + i);
        }

        adapter = new MyAdapter(this, list);
        mRv.setAdapter(adapter);

        mSrl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        adapter.refresh(initData());
                        mSrl.setRefreshing(false);
                    }
                }, 3000);
            }
        });

        mRv.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if (linearLayoutManager.findLastVisibleItemPosition() == adapter.getItemCount() - 1) {
                        //加载更多
                        adapter.loadMore(initData());
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }

    private List<String> initData() {
        ArrayList<String> l = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            l.add("我是新数据" + i);
        }
        return l;
    }

    private void initView() {
        mRv = (RecyclerView) findViewById(R.id.rv);
        mSrl = (SwipeRefreshLayout) findViewById(R.id.srl);
    }
}
