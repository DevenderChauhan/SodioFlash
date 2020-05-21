package com.thinker.basethinker.personal;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.car.qijia.thinker.photo.view.ImageSelectorActivity;
import com.stripe.android.model.Source;
import com.stripe.android.model.SourceCardData;
import com.stripe.android.view.PaymentMethodsActivity;
import com.thinker.basethinker.R;
import com.thinker.basethinker.bean.PersonalBean;
import com.thinker.basethinker.member.RechartMemberActivity;
import com.thinker.basethinker.mvp.MvpActivity;
import com.thinker.basethinker.mvp.MvpView;
import com.thinker.basethinker.stripe.StripeUtils;
import com.thinker.basethinker.utils.MyUtils;
import com.thinker.basethinker.utils.Utils;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.thinker.basethinker.stripe.StripeUtils.REQUEST_CODE_SELECT_SOURCE;

/**
 * Created by thinker on 17/12/9.
 */

public class PersonalActivity extends MvpActivity<PersonalPresenter,MvpView> implements MvpView, View.OnClickListener {
    private PersonalPresenter myPresenter;
    @Override
    protected PersonalPresenter CreatePresenter() {
        return myPresenter = new PersonalPresenter(this);
    }
    private ImageView head_left;
    private TextView head_title;
    private RelativeLayout person_icon;
    private RelativeLayout person_nickName;
    private CircleImageView icon_my;
    private String myNickName = "";
    private TextView nickName;
    private TextView phone;
    private RelativeLayout modify_paw;
    private RelativeLayout acountBind;
    private RelativeLayout bankCard;
    private LinearLayout  ll_bind_code;
    private TextView  isbindSyscode;
    private ImageView  img_right;
    private LinearLayout ll_phone;
    private LinearLayout ll_modify_pwd;
    private boolean isBindCode = true;//是否绑定了机柜
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyUtils.setToolsStatus(this,getResources().getColor(R.color.color_white));
        setContentView(R.layout.activity_personal);
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        myPresenter.getMyData();
        hideLoading();
    }

    private void initView() {
        modify_paw = (RelativeLayout) findViewById(R.id.modify_paw);
        acountBind = (RelativeLayout) findViewById(R.id.acountBind);
        phone = (TextView) findViewById(R.id.phone);
        isbindSyscode = (TextView) findViewById(R.id.isbindSyscode);
        icon_my = (CircleImageView) findViewById(R.id.icon_my);
        person_icon = (RelativeLayout) findViewById(R.id.person_icon);
        person_nickName = (RelativeLayout) findViewById(R.id.person_nickName);
        bankCard = (RelativeLayout) findViewById(R.id.bankCard);
        head_title = (TextView) findViewById(R.id.head_title);
        nickName = (TextView) findViewById(R.id.nickName);
        head_left = (ImageView) findViewById(R.id.head_left);
        img_right = (ImageView) findViewById(R.id.img_right);
        ll_bind_code = (LinearLayout) findViewById(R.id.ll_bind_code);
        ll_phone = (LinearLayout) findViewById(R.id.ll_phone);
        ll_modify_pwd = (LinearLayout) findViewById(R.id.ll_modify_pwd);
        head_title.setText(getString(R.string.person_title));
        head_left.setOnClickListener(this);
        person_icon.setOnClickListener(this);
        person_nickName.setOnClickListener(this);
        modify_paw.setOnClickListener(this);
        acountBind.setOnClickListener(this);
        bankCard.setOnClickListener(this);
        ll_bind_code.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.head_left:
                finish();
                break;
            case R.id.person_icon:
                ImageSelectorActivity activty = new ImageSelectorActivity();
                activty.start(PersonalActivity.this, 1, ImageSelectorActivity.MODE_SINGLE, true,true,true);
                break;
            case R.id.person_nickName:
                Intent fix = new Intent(PersonalActivity.this,FixUserNameActivity.class);
                fix.putExtra("NICKNAME",myNickName);
                startActivityForResult(fix,103);
                break;
            case R.id.modify_paw:
                startActivity(new Intent(PersonalActivity.this,ModifyPawActivity.class));
                break;
            case R.id.acountBind:
                startActivity(new Intent(PersonalActivity.this,AcountBindActivity.class));
                break;
            case R.id.bankCard:
                showLoading();
                StripeUtils stripeUtils = new StripeUtils(PersonalActivity.this, new StripeUtils.onMyPayLisener() {
                    @Override
                    public void onpay() {
                        Intent payIntent = PaymentMethodsActivity.newIntent(PersonalActivity.this);
                        startActivityForResult(payIntent, REQUEST_CODE_SELECT_SOURCE);
                    }
                });
                stripeUtils.initCustomer();
                break;
            case R.id.ll_bind_code:
                if (!isBindCode) {
                    Intent ss = new Intent(PersonalActivity.this, ModifySyscodeActivity.class);
                    startActivityForResult(ss, 104);
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && requestCode == ImageSelectorActivity.REQUEST_IMAGE){
            ArrayList<String> images = (ArrayList<String>) data.getSerializableExtra(ImageSelectorActivity.REQUEST_OUTPUT);
            if (images.size()>0){
                String headImgUrl = images.get(0);
                Glide.with(this).load(headImgUrl).into(icon_my);
                Bitmap avatarBitMap= BitmapFactory.decodeFile(headImgUrl);
                myPresenter.upIcon(avatarBitMap);
            }
        }

    }

    public void initData(PersonalBean personalBean) {
        if (!TextUtils.isEmpty(personalBean.getHeadImgPath())){
            Glide.with(this).load(personalBean.getHeadImgPath()).into(icon_my);
        }
        myNickName = personalBean.getNickname();
        if (!TextUtils.isEmpty(myNickName)){
            nickName.setText(Utils.object2String(myNickName));
        }
        phone.setText("+"+Utils.object2String(personalBean.getMobile()));
        String syscode = personalBean.getSysCode();
        String offerCode = personalBean.getCabinetAlias();
//        if (!TextUtils.isEmpty(offerCode)){
//            isBindCode = true;
//            isbindSyscode.setVisibility(View.VISIBLE);
//            isbindSyscode.setText(offerCode);
//            isbindSyscode.setTextColor(getResources().getColor(R.color.color_aux_gray));
//            img_right.setVisibility(View.GONE);
//        }else{
//            isbindSyscode.setVisibility(View.VISIBLE);
//            isbindSyscode.setTextColor(getResources().getColor(R.color.color_tip_red));
//            img_right.setVisibility(View.VISIBLE);
//            isBindCode = false;
//        }
        if (personalBean.getBindMobile()){
            ll_phone.setVisibility(View.VISIBLE);
            ll_modify_pwd.setVisibility(View.VISIBLE);
        }else{
            ll_phone.setVisibility(View.GONE);
            ll_modify_pwd.setVisibility(View.GONE);
        }
    }

    public void upIconSuccess() {
        myPresenter.getMyData();
    }
}
