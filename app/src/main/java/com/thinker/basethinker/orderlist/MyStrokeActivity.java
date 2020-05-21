package com.thinker.basethinker.orderlist;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.TextView;


import com.thinker.basethinker.R;
import com.thinker.basethinker.bean.OrderBean;
import com.thinker.basethinker.bean.OrderListBean;
import com.thinker.basethinker.mvp.MvpActivity;
import com.thinker.basethinker.mvp.MvpView;
import com.thinker.basethinker.orderlist.adapter.ItemStrokeAdapter;
import com.thinker.basethinker.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by farley on 17/5/17.
 * description:
 */

public class MyStrokeActivity extends MvpActivity<MyStrokePresenter, MvpView> implements View.OnClickListener, AdapterView.OnItemClickListener, MvpView {
    private MyStrokePresenter presenter;
    private ImageView head_left;
    private TextView head_title;
    private TextView empty_txt;
    private ListView listview;
    private List<OrderBean> dataList = new ArrayList<>();
    private ItemStrokeAdapter adapter;
    private LinearLayout empty_view;

    protected MyStrokePresenter CreatePresenter() {
        return presenter = new MyStrokePresenter(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_stroke);
        initView();
    }

    @Override
    protected void onStart() {
        super.onStart();
        dataList.clear();
        presenter.getMyAllStroke(null);

    }

    private void initView() {
        empty_view = (LinearLayout) findViewById(R.id.empty_view);
        head_title = (TextView) findViewById(R.id.head_title);
        empty_txt = (TextView) findViewById(R.id.empty_txt);
        head_left = (ImageView) findViewById(R.id.head_left);
        listview = (ListView) findViewById(R.id.listview);
        listview.setOnItemClickListener(this);
        head_title.setText(Utils.object2String(getString(R.string.my_stroke_title)));
        head_left.setOnClickListener(this);
        listview.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                // 当不滚动时
                if (scrollState == NumberPicker.OnScrollListener.SCROLL_STATE_IDLE) {
                    // 判断是否滚动到底部
                    if (view.getLastVisiblePosition() == view.getCount() - 1) {
                        //加载更多功能的代码
                        if (dataList.size() > 0) {
                            presenter.getMyAllStroke(dataList.get(dataList.size() - 1).getFinishTime().getTime());
                        }
                    }
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });
    }

    private boolean bluetoothIsService = false;
    private boolean bluetoothIsReceiver = false;
    private boolean isConnectBlue = false;


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.head_left:
                finish();
                break;
            default:
                break;
        }
    }

    public void loadMore(final OrderListBean bean) {
        new Thread() {
            @Override
            public void run() {
                super.run();
                if (bean.getDataList() != null) {
                    dataList.addAll(bean.getDataList());
                }
                if (dataList.size() == 0) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            empty_view.setVisibility(View.VISIBLE);
                        }
                    });
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (adapter == null) {
                                adapter = new ItemStrokeAdapter(MyStrokeActivity.this, dataList);
                                listview.setAdapter(adapter);
                            } else {
                                adapter.notifyDataSetChanged();
                            }
                        }
                    });
                }
            }
        }.start();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        OrderBean tripBO = dataList.get(position);
        Intent detail = new Intent(this, OrderDetailActivity.class);
        detail.putExtra("ORDERCODE", tripBO.getOrderCode());
        startActivity(detail);
    }
}
