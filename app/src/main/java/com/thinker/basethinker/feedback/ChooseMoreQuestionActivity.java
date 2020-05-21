package com.thinker.basethinker.feedback;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.thinker.basethinker.R;
import com.thinker.basethinker.feedback.adapter.MoreQuestionAdapter;
import com.thinker.basethinker.feedback.bean.FeedbackTypeListBean;
import com.thinker.basethinker.feedback.bean.FeedbackTypeListData;
import com.thinker.basethinker.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by farley on 17/5/22.
 * description:
 */

public class ChooseMoreQuestionActivity extends Activity implements View.OnClickListener {
    private ImageView head_left, head_right;
    private TextView head_title;
    private ListView listview;
    private FeedbackTypeListBean myBean;
    private List<FeedbackTypeListData> strList = new ArrayList<>();
    private MoreQuestionAdapter adapter;
    private int selected = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_more_question);
        selected = getIntent().getIntExtra("POSITION",0);
        Gson gson = new Gson();
        myBean = gson.fromJson(getIntent().getStringExtra("STRLIST"), FeedbackTypeListBean.class);
        strList.clear();
        if (myBean != null) {
            strList.addAll(myBean.getDatas());
        }
        initView();
    }
    private void initView() {
        listview = (ListView) findViewById(R.id.listview);
        head_title = (TextView) findViewById(R.id.head_title);
        head_left = (ImageView) findViewById(R.id.head_left);
        head_right = (ImageView) findViewById(R.id.head_right);
        head_title.setText(Utils.object2String(getString(R.string.feedback_title)));
        head_right.setVisibility(View.GONE);
        head_left.setOnClickListener(this);
        setData();
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.head_left:
                Intent it = new Intent();
                it.putExtra("MOREQUESTION",strList.get(selected+6).getId());
                it.putExtra("MOREPOSITION",selected);
                setResult(199,it);
                finish();
                break;
            default:
                break;
        }
    }

    @Override
    public void onBackPressed() {
        Intent it = new Intent();
        it.putExtra("MOREQUESTION",strList.get(selected+6).getId());
        it.putExtra("MOREPOSITION",selected);
        setResult(199,it);
        finish();
    }

    private void setData(){
        adapter = new MoreQuestionAdapter(this,strList,selected);
        listview.setAdapter(adapter);
        adapter.setOnMySelectedListener(new MoreQuestionAdapter.OnMySeletcedListener() {
            @Override
            public void onSelected(int position) {
                adapter.setSelected(position);
                Intent it = new Intent();
                it.putExtra("MOREQUESTION",strList.get(position+6).getId());
                it.putExtra("MOREPOSITION",position);
                setResult(199,it);
                finish();
            }
        });
    }
}
