package com.thinker.basethinker.personal;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.thinker.basethinker.R;
import com.thinker.basethinker.mvp.MvpActivity;
import com.thinker.basethinker.mvp.MvpView;
import com.thinker.basethinker.utils.MyUtils;
import com.thinker.basethinker.utils.Utils;

/**
 * Created by farley on 17/5/31.
 * description:
 */

public class FixUserNameActivity extends MvpActivity<FixUserNamePresenter,MvpView> implements MvpView {
    private ImageView head_left;
    private ImageView cancel;
    private TextView head_title;
    private EditText nickName;
    private TextView save;
    private String nickname;
    private FixUserNamePresenter presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyUtils.setToolsStatus(this,getResources().getColor(R.color.color_white));
        setContentView(R.layout.activity_fix_username);
        nickname = getIntent().getStringExtra("NICKNAME");
        initView();

    }

    @Override
    protected FixUserNamePresenter CreatePresenter() {
        return presenter = new FixUserNamePresenter(this);
    }

    private void initView() {
        nickName = (EditText) findViewById(R.id.nickName);
        save = (TextView) findViewById(R.id.save);
        head_title = (TextView) findViewById(R.id.head_title);
        cancel = (ImageView) findViewById(R.id.cancel);
        head_left = (ImageView) findViewById(R.id.head_left);
        head_title.setText(Utils.object2String(getString(R.string.fix_username)));
        nickName.setText(Utils.object2String(nickname));
        head_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nickName.getText().toString().trim();
                if (!TextUtils.isEmpty(name)) {
                    presenter.upNicName(name);
                }else{
                    Toast.makeText(FixUserNameActivity.this,getString(R.string.nickname_is_empty),Toast.LENGTH_SHORT).show();
                }
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nickName.setText("");
            }
        });
    }
    public void setSuccessDo(){
        Toast.makeText(FixUserNameActivity.this,getString(R.string.modifysuccess),Toast.LENGTH_SHORT).show();
        String name = nickName.getText().toString().trim();
        Intent it = new Intent();
        it.putExtra("NICKNAME",name);
        setResult(RESULT_OK,it);
        finish();
    }
}
