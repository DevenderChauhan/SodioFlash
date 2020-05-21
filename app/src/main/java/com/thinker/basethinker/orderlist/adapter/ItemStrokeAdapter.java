package com.thinker.basethinker.orderlist.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.thinker.basethinker.R;
import com.thinker.basethinker.bean.OrderBean;
import com.thinker.basethinker.utils.Utils;

import java.util.List;


/**
 * Created by farley on 17/5/17.
 * description:
 */

public class ItemStrokeAdapter extends BaseAdapter {
    private Context mContext;
    private List<OrderBean> dataList;
    public ItemStrokeAdapter(Context context, List<OrderBean> dataList) {
        mContext = context;
        this.dataList = dataList;
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public Object getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
       final ViewHolder holder;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_my_stroke, null);
            holder = new ViewHolder();
            holder.time = (TextView) convertView.findViewById(R.id.time);
            holder.code = (TextView) convertView.findViewById(R.id.code);
            holder.money = (TextView) convertView.findViewById(R.id.money);
            holder.bug_tip = (TextView) convertView.findViewById(R.id.bug_tip);
            holder.top_view = (View) convertView.findViewById(R.id.top_view);
            holder.bottom_view = (View) convertView.findViewById(R.id.bottom_view);
            holder.end_view = (View) convertView.findViewById(R.id.end_view);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        OrderBean data = dataList.get(position);
        holder.time.setText(Utils.stampToDate5(data.getCreateTime().getTime()));
        holder.code.setText("NO."+Utils.object2String(data.getOrderCode()));
            holder.money.setText(mContext.getString(R.string.my_stroke_money) + Utils.object2String(data.getPrice())
                    + Utils.object2String(data.getCurrency()));

        if (position == 0){
            holder.top_view.setVisibility(View.VISIBLE);
        }else{
            holder.top_view.setVisibility(View.GONE);
        }
        if (position == dataList.size()-1){
            holder.end_view.setVisibility(View.VISIBLE);
        }else{
            holder.end_view.setVisibility(View.GONE);
        }
        if ("pb_buy".equals(data.getPayType())) {
            holder.bug_tip.setVisibility(View.VISIBLE);
        }else{
            holder.bug_tip.setVisibility(View.GONE);
        }
        return convertView;
    }

    class ViewHolder {
        TextView time,code,money,bug_tip;
        View bottom_view,top_view,end_view;
    }
}
