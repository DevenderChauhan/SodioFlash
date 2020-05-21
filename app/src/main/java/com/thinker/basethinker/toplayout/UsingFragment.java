package com.thinker.basethinker.toplayout;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.thinker.basethinker.IMainView;
import com.thinker.basethinker.R;
import com.thinker.basethinker.bean.OrderBean;
import com.thinker.basethinker.eventbus.MessageEvent;
import com.thinker.basethinker.utils.LogUtils;
import com.thinker.basethinker.utils.Utils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Observable;
import java.util.Observer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by thinker on 17/12/25.
 */

public class UsingFragment extends Fragment {
    private View view;
    private OrderBean orderBean;
    private TextView orderId;
    private TextView orderQuestion;
    private TextView orderRules;
    private TextView order_min;
    private TextView order_price;
    private TextView money_unit;
    private TextView buy_box_btn;
    @SuppressLint("ValidFragment")
    public UsingFragment(OrderBean orderBean) {
        this.orderBean = orderBean;
    }

    public UsingFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_using,
                container, false);
        initView();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);
    }

    private void initView() {
        buy_box_btn = (TextView) view.findViewById(R.id.buy_box_btn);
        orderId = (TextView) view.findViewById(R.id.orderId);
        money_unit = (TextView)view.findViewById(R.id.money_unit);
        orderQuestion =(TextView) view.findViewById(R.id.orderQuestion);
        orderRules = (TextView)view.findViewById(R.id.orderRules);
        order_min = (TextView)view.findViewById(R.id.order_min);
        order_price = (TextView)view.findViewById(R.id.order_price);
        setData(orderBean);
        orderQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getActivity() instanceof IMainView){
                    ((IMainView)getActivity()).feedback();
                }
            }
        });
        buy_box_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getActivity() instanceof IMainView){
                    ((IMainView)getActivity()).buyBoxFun();
                }
            }
        });
    }
    public void setData(OrderBean orderBean){
        if (orderBean == null){
            return;
        }
        orderId.setText(Utils.object2String(orderBean.getOrderCode()));
        orderRules.setText(Html.fromHtml(getThemeColorStr(Utils.object2String(orderBean.getChargeRuleDesc()))));
        order_min.setText(Utils.object2String(orderBean.getRideTime()));
        order_price.setText(Utils.object2String(orderBean.getPrice()));
        money_unit.setText(getActivity().getString(R.string.using_price)+"("+Utils.object2String(orderBean.getCurrency())+")");
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMyEvent(MessageEvent event) {
        switch (event.getCode()) {
            case 0:
                OrderBean orderBean = event.getOrderBean();
                if (orderBean == null){
                    return;
                }
                setData(orderBean);
                break;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
    }
    private String getThemeColorStr(String string){
//        String themeColor = "#"+Integer.toHexString(getResources().getColor(R.color.color_theme));
        String themeColor = "#F02022";
        themeColor = themeColor.toUpperCase();
        String result = " ";
        if (TextUtils.isEmpty(string)){
            return result;
        }

        String ex = "<s>(.*?)</s>";
        Pattern p = Pattern.compile(ex);
        Matcher m = p.matcher(string);
        while (m.find()) {
            int i = 1;
            String str = "<font color=\'"+themeColor+"\' >"+m.group(i)+"</font>";

            string = string.replace("<s>"+m.group(i)+"</s>",str);
            i++;
        }

        return string;
    }
}
