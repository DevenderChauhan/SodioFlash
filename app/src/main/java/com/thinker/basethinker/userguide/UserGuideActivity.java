package com.thinker.basethinker.userguide;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.thinker.basethinker.R;
import com.thinker.basethinker.mvp.MvpActivity;
import com.thinker.basethinker.set.bean.SetBean;
import com.thinker.basethinker.set.bean.SetData;
import com.thinker.basethinker.userguide.adapter.UserGuideAdapter;
import com.thinker.basethinker.utils.Utils;
import com.thinker.basethinker.utils.WebViewActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by farley on 17/5/18.
 * description:用户指南
 */

public class UserGuideActivity extends MvpActivity<UserGuidePresenter,IUserGuideView> implements IUserGuideView, View.OnClickListener, AdapterView.OnItemClickListener {
    private UserGuidePresenter presenter;
    private ImageView head_left,head_right;
    private TextView head_title;
    private ListView listview;
    private UserGuideAdapter adpter;
    private List<SetData> dataList = new ArrayList<>();
    @Override
    protected UserGuidePresenter CreatePresenter() {
        return presenter = new UserGuidePresenter(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_guide);
        initView();
        presenter.getGuideList(1);//彩虹
//        presenter.getGuideList(8);//小E
    }

    private void initView() {
        head_title = (TextView) findViewById(R.id.head_title);
        head_left = (ImageView) findViewById(R.id.head_left);
        head_right = (ImageView) findViewById(R.id.head_right);
        listview = (ListView) findViewById(R.id.listview);
        head_title.setText(Utils.object2String(getString(R.string.my_ll5)));
        head_right.setVisibility(View.GONE);
        head_left.setOnClickListener(this);
        listview.setOnItemClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.head_left:
                finish();
                break;
            default:
                break;
        }
    }
    public void loadData(SetBean bean){
        dataList.clear();
        dataList.addAll(bean.getDatas());
        if (adpter == null){
            adpter = new UserGuideAdapter(UserGuideActivity.this,dataList);
            listview.setAdapter(adpter);
        }else{
            adpter.notifyDataSetChanged();
        }
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent webview = new Intent(this, WebViewActivity.class);
        webview.putExtra("TITLE", dataList.get(position).getTitle());
        webview.putExtra("VIEWURL", "api/guide_detail_app?id="+dataList.get(position).getId());
        webview.putExtra("NEEDTOKEN",true);
        startActivity(webview);
    }
}
