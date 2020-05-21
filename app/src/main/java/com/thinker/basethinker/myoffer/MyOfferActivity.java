package com.thinker.basethinker.myoffer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;


import com.thinker.basethinker.R;
import com.thinker.basethinker.mvp.MvpActivity;
import com.thinker.basethinker.mvp.MvpView;
import com.thinker.basethinker.myoffer.adapter.MyOfferAdapter;
import com.thinker.basethinker.myoffer.bean.MyOfferBean;
import com.thinker.basethinker.myoffer.bean.MyOfferData;
import com.thinker.basethinker.utils.MyUtils;
import com.thinker.basethinker.utils.Utils;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by farley on 17/5/22.
 * description:我的优惠
 */

public class MyOfferActivity extends MvpActivity<MyOfferPresenter,MvpView> implements MvpView, View.OnClickListener, AdapterView.OnItemClickListener {
    private MyOfferPresenter presenter;
    private ImageView head_left, head_right;
    private TextView head_title;
    private ListView listview;
    private MyOfferAdapter adapter;
    private LinearLayout empty_view;
    private List<MyOfferData> dataList = new ArrayList<>();
    private Boolean type = false;//默认不可点击
    @Override
    protected MyOfferPresenter CreatePresenter() {
        return presenter = new MyOfferPresenter(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyUtils.setToolsStatus(this,getResources().getColor(R.color.color_white));
        setContentView(R.layout.activity_my_offer);
        type = getIntent().getBooleanExtra("TYPE",false);
        initView();
        presenter.getMyOffer();
    }
    private void initView() {
        empty_view = (LinearLayout) findViewById(R.id.empty_view);
        head_title = (TextView) findViewById(R.id.head_title);
        head_left = (ImageView) findViewById(R.id.head_left);
        head_right = (ImageView) findViewById(R.id.head_right);
        listview = (ListView) findViewById(R.id.listview);
        head_title.setText(Utils.object2String(getString(R.string.my_offer_title)));
        head_right.setVisibility(View.GONE);
        head_left.setOnClickListener(this);
        listview.setOnItemClickListener(this);
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
    public void setData(MyOfferBean bean){
        if (bean.getDataList() == null){
            empty_view.setVisibility(View.VISIBLE);
            return;
        }
        if (bean.getDataList().size() == 0){
            empty_view.setVisibility(View.VISIBLE);
        }else {
            dataList.clear();
            dataList.addAll(bean.getDataList());
            adapter = new MyOfferAdapter(this, bean.getDataList());
            listview.setAdapter(adapter);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (type) {
            Intent it = new Intent();
            it.putExtra("CID", dataList.get(position).getId());
            it.putExtra("MONEY", dataList.get(position).getMoney());
            setResult(RESULT_OK, it);
            finish();
        }
    }
}
