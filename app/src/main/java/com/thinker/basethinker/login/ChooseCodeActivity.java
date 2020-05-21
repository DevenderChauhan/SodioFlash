package com.thinker.basethinker.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.thinker.basethinker.R;
import com.thinker.basethinker.login.adapter.ChooseCodeAdapter;
import com.thinker.basethinker.utils.Country;
import com.thinker.basethinker.utils.CountryUtil;
import com.thinker.basethinker.utils.LogUtils;
import com.thinker.basethinker.utils.MyUtils;

import java.util.List;
import java.util.Locale;

/**
 * Created by thinker on 17/12/8.
 */

public class ChooseCodeActivity extends Activity implements View.OnClickListener, AdapterView.OnItemClickListener {
    private ImageView head_left;
    private TextView head_title;
    private ListView listView;
    private ChooseCodeAdapter chooseCodeAdapter;
    private  List<Country> datalist = CountryUtil.getListEn();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyUtils.setToolsStatus(this,getResources().getColor(R.color.color_white));
        setContentView(R.layout.activity_choose_area_code);

        Locale locale = getResources().getConfiguration().locale;
        String language = locale.getLanguage();
        if ("zh".equals(language)){
            datalist = CountryUtil.getList();
        }
        initView();
    }

    private void initView() {
        listView = (ListView) findViewById(R.id.listView);
        head_left = (ImageView) findViewById(R.id.head_left);
        head_title = (TextView) findViewById(R.id.head_title);
        head_title.setText(getString(R.string.login_chopse_code));
        head_left.setOnClickListener(this);

        chooseCodeAdapter = new ChooseCodeAdapter(this,datalist);
        listView.setAdapter(chooseCodeAdapter);
        listView.setOnItemClickListener(this);
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

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Country country = datalist.get(position);
        Intent it = new Intent();
        it.putExtra("COUNTRYCODE",country.getCode());
        setResult(RESULT_OK,it);
        finish();
    }
}
