package com.thinker.basethinker.bottomlayout;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.thinker.basethinker.IMainView;
import com.thinker.basethinker.R;
import com.thinker.basethinker.bean.SellerBean;
import com.thinker.basethinker.nearbyseller.NearBySellerActivity;
import com.thinker.basethinker.nearbyseller.SellerDetailActivity;
import com.thinker.basethinker.utils.LogUtils;
import com.thinker.basethinker.utils.MyUtils;
import com.thinker.basethinker.utils.Utils;

/**
 * Created by thinker on 17/12/21.
 */

public class SellerDetailFragment extends Fragment {
    private View view;
    private SellerBean sellerBean;

    public SellerDetailFragment() {
    }

    @SuppressLint("ValidFragment")
    public SellerDetailFragment(SellerBean sellerBean) {
        this.sellerBean = sellerBean;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_seller_detail,
                container, false);
        initView();
        return view;
    }
    private TextView sellerName;
    private TextView sellerTime;
    private TextView sellerAddress;
    private TextView borrowNum;
    private TextView type1Num;
    private TextView type2Num;
    private TextView type3Num;
    private ImageView sellerIcon;
    private LinearLayout fragment_seller;
    private LinearLayout nva_btn;
    private LinearLayout toDetail;
    private void initView() {
        sellerName = (TextView) view.findViewById(R.id.sellerName);
        sellerIcon = (ImageView) view.findViewById(R.id.sellerIcon);
        sellerAddress = (TextView)view.findViewById(R.id.sellerAddress);
        sellerTime = (TextView)view.findViewById(R.id.sellerTime);
        borrowNum = (TextView)view.findViewById(R.id.borrowNum);
        type1Num = (TextView)view.findViewById(R.id.type1Num);
        type2Num = (TextView)view.findViewById(R.id.type2Num);
        type3Num =(TextView) view.findViewById(R.id.type3Num);
        nva_btn = (LinearLayout) view.findViewById(R.id.nva_btn);
        toDetail = (LinearLayout)view.findViewById(R.id.toDetail);
        fragment_seller = (LinearLayout)view.findViewById(R.id.fragment_seller);
        fragment_seller.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
        setData(sellerBean);
        nva_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyUtils.turnToGoogleMap(getActivity(),sellerBean.getLocationLat(),sellerBean.getLocationLon());
            }
        });

    }

    public void setData(final SellerBean sellerBean) {
        if (!TextUtils.isEmpty(sellerBean.getSellerLogo())){
            Glide.with(getActivity()).load(sellerBean.getSellerLogo()).into(sellerIcon);
        }
        sellerName.setText(Utils.object2String(sellerBean.getSellerName()));
        sellerAddress.setText(Utils.object2String(sellerBean.getLocationAddress()) + Utils.object2String(sellerBean.getLocationDesc()));
        sellerTime.setText(Utils.object2String(sellerBean.getServiceTime()));
        borrowNum.setText(getActivity().getString(R.string.seller_msg_return) + Utils.object2String(sellerBean.getIdlePositionNum()) + getActivity().getString(R.string.seller_msg_unit));
        if (sellerBean.getBatteryType2Count() == null || sellerBean.getBatteryType3Count() == null || sellerBean.getBatteryType4Count() == null){
            return;
        }
        if (sellerBean.getBatteryType2Count() < 1){
            type1Num.setSelected(true);
        }else{
            type1Num.setSelected(false);
        }
        if (sellerBean.getBatteryType3Count() < 1){
            type2Num.setSelected(true);
        }else{
            type2Num.setSelected(false);
        }
        if (sellerBean.getBatteryType4Count() < 1){
            type3Num.setSelected(true);
        }else{
            type3Num.setSelected(false);
        }
        type1Num.setText(Utils.object2String(sellerBean.getBatteryType2Count()) + getActivity().getString(R.string.seller_msg_borrow));
        type2Num.setText(Utils.object2String(sellerBean.getBatteryType3Count()) + getActivity().getString(R.string.seller_msg_borrow));
        type3Num.setText(Utils.object2String(sellerBean.getBatteryType4Count()) + getActivity().getString(R.string.seller_msg_borrow));
        toDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getActivity() instanceof IMainView){
                    ((IMainView)getActivity()).sellerDetail(sellerBean.getUid());
                }

            }
        });
    }
}
