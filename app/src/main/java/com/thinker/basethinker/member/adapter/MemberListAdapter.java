package com.thinker.basethinker.member.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.thinker.basethinker.R;
import com.thinker.basethinker.bean.PersonalBean;
import com.thinker.basethinker.member.bean.VipPayBean;
import com.thinker.basethinker.utils.MyUtils;
import com.thinker.basethinker.utils.Utils;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by farley on 17/8/18.
 * description:
 */

public class MemberListAdapter  extends BaseAdapter {
    private Context mContext;
    private int pos = -1;
    private List<VipPayBean> arrays = new ArrayList<>();
    public MemberListAdapter(Context context, List<VipPayBean> list) {
        mContext = context;
        arrays = list;
    }
    public void setSelectedPos(int pos){
        this.pos = pos;
        notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        return arrays.size();
    }

    @Override
    public Object getItem(int position) {
        return arrays.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null){
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_member_vip_list, null);
            holder = new ViewHolder();
            holder.bottom_view = (View) convertView.findViewById(R.id.bottom_view);
            holder.paystatus = (TextView) convertView.findViewById(R.id.paystatus);
            holder.payTime = (TextView) convertView.findViewById(R.id.payTime);
            holder.payMoney = (TextView) convertView.findViewById(R.id.payMoney);
            holder.payfrom = (TextView) convertView.findViewById(R.id.payfrom);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        VipPayBean bean = arrays.get(position);
        PersonalBean personalBean = MyUtils.getPersonData();
//        String name = bean.getCardName()+"<font color=\"#ffbb4a\">"+bean.getCardAmount()+"</font>"+"元";
        if (bean.getStatus() == 1){
            holder.paystatus.setText(mContext.getString(R.string.member_paying));
        }else if(bean.getStatus() == 2){
            holder.paystatus.setText(mContext.getString(R.string.member_pay_success));
        }
        holder.payTime.setText(Utils.stampToDate3(bean.getCreateTime().getTime()));
        holder.payMoney.setText(Utils.object2String(personalBean.getCurrency())+Utils.object2String(bean.getAmount()));
        holder.payfrom.setText(Utils.object2String(bean.getPaymentMarkName()));
        /*if (position == arrays.size()-1){
            holder.bottom_view.setVisibility(View.GONE);
        }else{
            holder.bottom_view.setVisibility(View.VISIBLE);
        }*/
        return convertView;
    }
    class ViewHolder{
        TextView paystatus,payTime;
        TextView payMoney,payfrom;
        View bottom_view;
    }
}