package com.thinker.basethinker.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.thinker.basethinker.R;
import com.thinker.basethinker.mvp.MvpActivity;
import com.thinker.basethinker.mvp.MvpView;
import com.thinker.basethinker.utils.WebViewActivity;


/**
 * Created by Farley on 2018/4/20.
 */

public class AddInfoActivity extends MvpActivity<AddInfoPresenter,MvpView> implements MvpView {
    private TextView head_title;
    private TextView registRules;
    private ImageView head_left;
    private LinearLayout ll_choose_contry;
    private EditText edt_contry;
    private EditText edt_medCode;
    private Button sure;
    private AddInfoPresenter myPresenter;
    private String contryCode;
    private static int CHOOSECOUNTRYCODE = 1001;//选择国家
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addinfo);
        initView();
    }

    @Override
    protected AddInfoPresenter CreatePresenter() {
        return myPresenter = new AddInfoPresenter(this);
    }

    private void initView() {
        sure = (Button) findViewById(R.id.sure);
        head_title = (TextView) findViewById(R.id.head_title);
        registRules = (TextView) findViewById(R.id.registRules);
        head_left = (ImageView) findViewById(R.id.head_left);
        ll_choose_contry = (LinearLayout) findViewById(R.id.ll_choose_contry);
        edt_contry = (EditText) findViewById(R.id.edt_contry);
        edt_medCode = (EditText) findViewById(R.id.edt_medCode);
        head_title.setText(getString(R.string.add_info_title));
        head_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        ll_choose_contry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(AddInfoActivity.this , ChooseCodeActivity.class), CHOOSECOUNTRYCODE);
            }
        });
        registRules.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent webview = new Intent(AddInfoActivity.this, WebViewActivity.class);
                webview.putExtra("TITLE", getString(R.string.regist_desc_red));
                webview.putExtra("VIEWURL", "share/use_car_rules");
                webview.putExtra("NEEDTOKEN",false);
                startActivity(webview);
            }
        });
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(contryCode)){
                    Toast.makeText(AddInfoActivity.this,"",Toast.LENGTH_LONG).show();
                    return;
                }
                String medCode = edt_medCode.getText().toString().trim();
                myPresenter.thirdPartyPerfectInfoUsingGET(contryCode,medCode);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == CHOOSECOUNTRYCODE && data != null) {
                String code = data.getStringExtra("COUNTRYCODE");
                contryCode = code;
                if ("60".equals(code)){
                    edt_contry.setText(getString(R.string.malaysia));
                }else if ("86".equals(code)){
                    edt_contry.setText(getString(R.string.china));
                }else if("65".equals(code)){
                    edt_contry.setText(getString(R.string.singapore));
                }
            }
        }
    }
}
