package com.thinker.basethinker.wallet.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.thinker.basethinker.R;
import com.thinker.basethinker.bean.RechartTypeBean;
import com.thinker.basethinker.utils.Utils;

import java.util.List;


/**
 * Created by farley on 17/8/21.
 * description:
 */

public class RechartViewAdapter extends BaseAdapter {
    private Context mContext;
    private List<RechartTypeBean> dataList;
    private int selected = 0;
    public RechartViewAdapter(Context context, List<RechartTypeBean> dataList) {
        mContext = context;
        this.dataList = dataList;
    }
    public void setMySelected(int pos){
        selected = pos;
        notifyDataSetChanged();
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
            convertView = inflater.inflate(R.layout.item_my_rechart, null);
            holder = new ViewHolder();
            holder.item_all = (LinearLayout) convertView.findViewById(R.id.item_all);
            holder.needPay = (TextView) convertView.findViewById(R.id.needPay);
            holder.offerMoney = (TextView) convertView.findViewById(R.id.offerMoney);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        RechartTypeBean bean = dataList.get(position);
        holder.needPay.setText(bean.getCurrency() +" "+Utils.object2String(bean.getPayAmount()));
        holder.offerMoney.setText(Utils.object2String(bean.getRemark()));
        if (position == selected){
            holder.item_all.setSelected(true);
            holder.needPay.setSelected(true);
        }else{
            holder.item_all.setSelected(false);
            holder.needPay.setSelected(false);
        }
        return convertView;
    }

    class ViewHolder {
        TextView needPay,offerMoney;
        LinearLayout item_all;
    }
}
