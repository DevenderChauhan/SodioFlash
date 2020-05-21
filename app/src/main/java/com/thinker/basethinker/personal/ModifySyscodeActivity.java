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
import com.thinker.basethinker.bean.PersonalBean;
import com.thinker.basethinker.mvp.MvpActivity;
import com.thinker.basethinker.mvp.MvpView;
import com.thinker.basethinker.utils.MyUtils;
import com.thinker.basethinker.utils.Utils;

/**
 * Created by thinker on 18/3/6.
 */

public class ModifySyscodeActivity extends MvpActivity<ModifySyscodePresenter, MvpView> implements MvpView {
    private ImageView head_left;
    private ImageView cancel;
    private EditText nickName;
    private TextView save;
    private String nickname;
    private ModifySyscodePresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyUtils.setToolsStatus(this, getResources().getColor(R.color.color_white));
        setContentView(R.layout.activity_modify_code);
        initView();
        initData();
    }

    private void initData() {
        PersonalBean personalBean = MyUtils.getPersonData();
        if (personalBean.getSysCode() != null && !TextUtils.isEmpty(personalBean.getSysCode())) {
            save.setVisibility(View.INVISIBLE);
            nickName.setEnabled(false);
        } else {
            save.setVisibility(View.VISIBLE);
            nickName.setEnabled(true);
        }
        if (personalBean.getCabinetAlias() != null) {
            nickName.setText(personalBean.getCabinetAlias() + "");
        }
    }

    @Override
    protected ModifySyscodePresenter CreatePresenter() {
        return presenter = new ModifySyscodePresenter(this);
    }

    private void initView() {
        nickName = (EditText) findViewById(R.id.nickName);
        save = (TextView) findViewById(R.id.save);
        cancel = (ImageView) findViewById(R.id.cancel);
        head_left = (ImageView) findViewById(R.id.head_left);
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
                    presenter.modifyCode(name);
                } else {
                    Toast.makeText(ModifySyscodeActivity.this, getString(R.string.syscode_is_empty), Toast.LENGTH_SHORT).show();
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

    public void setSuccessDo() {
        Toast.makeText(ModifySyscodeActivity.this, getString(R.string.modifysuccess), Toast.LENGTH_SHORT).show();
        Intent it = new Intent();
        setResult(RESULT_OK, it);
        finish();
    }
}