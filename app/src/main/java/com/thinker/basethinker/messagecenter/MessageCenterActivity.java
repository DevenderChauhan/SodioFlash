package com.thinker.basethinker.messagecenter;

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
import com.thinker.basethinker.bean.MessageBean;
import com.thinker.basethinker.bean.MessageData;
import com.thinker.basethinker.messagecenter.adapter.MessageAdapter;
import com.thinker.basethinker.mvp.MvpActivity;
import com.thinker.basethinker.utils.Utils;
import com.thinker.basethinker.utils.WebViewActivity;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by farley on 17/7/3.
 * description:消息中心----------彩虹雨伞
 */

public class MessageCenterActivity extends MvpActivity<MessageCenterPresenter, IMessageView> implements IMessageView, View.OnClickListener, AdapterView.OnItemClickListener {
    private MessageCenterPresenter myPresenter;
    private ImageView head_left, head_right;
    private TextView head_title;
    private ListView listview;
    private MessageAdapter adapter;
    private List<MessageData> dataList = new ArrayList();
    private LinearLayout empty_view;

    @Override
    protected MessageCenterPresenter CreatePresenter() {
        return myPresenter = new MessageCenterPresenter(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_center);
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        myPresenter.getMessage(null);
        dataList.clear();
    }

    private void initView() {
        empty_view = (LinearLayout) findViewById(R.id.empty_view);
        head_title = (TextView) findViewById(R.id.head_title);
        head_left = (ImageView) findViewById(R.id.head_left);
        head_right = (ImageView) findViewById(R.id.head_right);
        listview = (ListView) findViewById(R.id.listview);
        head_title.setText(Utils.object2String(getString(R.string.news_title)));
        head_right.setVisibility(View.GONE);
        head_left.setOnClickListener(this);
        listview.setOnItemClickListener(this);
        listview.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                // 当不滚动时
                if (scrollState == NumberPicker.OnScrollListener.SCROLL_STATE_IDLE) {
                    // 判断是否滚动到底部
                    if (view.getLastVisiblePosition() == view.getCount() - 1) {
                        //加载更多功能的代码
                        if (dataList.size() > 0) {
                            myPresenter.getMessage(dataList.get(dataList.size() - 1).getSendTime().getTime());
                        }
                    }
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });
    }

    public void loadMore(final MessageBean bean) {
        new Thread() {
            @Override
            public void run() {
                if (bean.getContent() != null && dataList.size() == 0) {
                    dataList.addAll(bean.getContent());
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
                                    adapter = new MessageAdapter(MessageCenterActivity.this, dataList);
                                    listview.setAdapter(adapter);
                                } else {
                                    adapter.notifyDataSetChanged();
                                }
                            }
                        });
                    }
                }
                if (dataList.size() == 0) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            empty_view.setVisibility(View.VISIBLE);
                        }
                    });
                }
            }
        }.start();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (dataList.get(position).getImageText() != null && dataList.get(position).getImageText()) {
            Intent webview = new Intent(this, WebViewActivity.class);
            webview.putExtra("TITLE", getString(R.string.news_detail));
            webview.putExtra("VIEWURL", "/api/message/sys_view/" + dataList.get(position).getImageTextId());
            webview.putExtra("NEEDTOKEN", true);
            startActivity(webview);
        }
    }

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
}
