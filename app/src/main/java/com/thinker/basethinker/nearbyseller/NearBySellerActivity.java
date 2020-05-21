package com.thinker.basethinker.nearbyseller;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.thinker.basethinker.R;
import com.thinker.basethinker.bean.AllSellerBean;
import com.thinker.basethinker.nearbyseller.adapter.NearSellerAdapter;
import com.thinker.basethinker.utils.LogUtils;
import com.thinker.basethinker.utils.MyUtils;
import com.uuzuche.lib_zxing.activity.CodeUtils;

/**
 * Created by thinker on 17/12/26.
 */

public class NearBySellerActivity extends Activity implements View.OnClickListener, AdapterView.OnItemClickListener {
    private ImageView head_left;
    private TextView head_title;
    private ListView listView;
    private NearSellerAdapter nearSellerAdapter;
    private int CHOOSECODE = 131;//去详情
    private AllSellerBean bean;
    private LinearLayout empty_view;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyUtils.setToolsStatus(this,getResources().getColor(R.color.color_white));
        setContentView(R.layout.activity_near_seller);
        initView();
        initData();
    }

    private void initData() {
        bean = MyUtils.getSellerData();
        if (bean == null){
            empty_view.setVisibility(View.VISIBLE);
            return;
        }
        if (bean.getDataList() == null){
            empty_view.setVisibility(View.VISIBLE);
            return;
        }
        empty_view.setVisibility(View.GONE);
        if (nearSellerAdapter == null){
            nearSellerAdapter = new NearSellerAdapter(this,bean);
            listView.setAdapter(nearSellerAdapter);
        }else{
            nearSellerAdapter.notifyDataSetChanged();
        }
    }

    private void initView() {
        head_left = (ImageView) findViewById(R.id.head_left);
        head_title = (TextView)findViewById(R.id.head_title);
        listView = (ListView) findViewById(R.id.listView);
        empty_view = (LinearLayout) findViewById(R.id.empty_view);

        
        head_title.setText(getString(R.string.near_seller_title));
        head_left.setOnClickListener(this);
        listView.setOnItemClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.head_left:
                finish();
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent it = new  Intent(NearBySellerActivity.this,SellerDetailActivity.class);
        it.putExtra("UUID",bean.getDataList().get(position).getUid());
        startActivityForResult(it,CHOOSECODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CHOOSECODE//扫描后返回
                && resultCode == Activity.RESULT_OK){
            String result = data.getStringExtra("CODE");
            LogUtils.e("code="+ result);
            Intent it = new Intent();
            it.putExtra("CODE",result);
            setResult(RESULT_OK,it);
            finish();
        }
    }
}
