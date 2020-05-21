package com.thinker.basethinker.member.adapter;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.thinker.basethinker.R;
import com.thinker.basethinker.bean.PersonalBean;
import com.thinker.basethinker.member.bean.MemberVipCardBean;
import com.thinker.basethinker.utils.MyUtils;
import com.thinker.basethinker.utils.Utils;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by farley on 17/8/17.
 * description:
 */

public class MemberVipAdapter extends BaseAdapter {
    private Context mContext;
    private int pos = -1;
    private List<MemberVipCardBean> arrays = new ArrayList<>();
    public MemberVipAdapter(Context context, List<MemberVipCardBean> list) {
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
            convertView = inflater.inflate(R.layout.item_member_vip, null);
            holder = new ViewHolder();
            holder.bottom_view = (View) convertView.findViewById(R.id.bottom_view);
            holder.bottom_test_view = (View) convertView.findViewById(R.id.bottom_test_view);
            holder.vipName = (TextView) convertView.findViewById(R.id.vipName);
            holder.vipDesc = (TextView) convertView.findViewById(R.id.vipDesc);
            holder.fill = (ImageView) convertView.findViewById(R.id.fill);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        MemberVipCardBean bean = arrays.get(position);
        PersonalBean personalBean = MyUtils.getPersonData();
        String name = bean.getCardName()+" "+personalBean.getCurrency()+" "+"<font color=\"#ED242C\">"+bean.getCardAmount()+"</font>";
        holder.vipName.setText(Html.fromHtml(name));
        holder.vipDesc.setText(Utils.object2String(bean.getCardDesc()));
        if (pos == position){
            holder.fill.setVisibility(View.VISIBLE);
        }else{
            holder.fill.setVisibility(View.GONE);
        }
        if (position == arrays.size()-1){
            holder.bottom_view.setVisibility(View.GONE);
            holder.bottom_test_view.setVisibility(View.VISIBLE);
        }else{
            holder.bottom_view.setVisibility(View.VISIBLE);
            holder.bottom_test_view.setVisibility(View.GONE);
        }
        return convertView;
    }
    class ViewHolder{
        TextView vipName,vipDesc;
        ImageView fill;
        View bottom_view;
        View bottom_test_view;
    }
}
