package com.thinker.basethinker.wallet.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.thinker.basethinker.R;
import com.thinker.basethinker.bean.PersonalBean;
import com.thinker.basethinker.bean.RechartHistoryBean;
import com.thinker.basethinker.utils.MyUtils;
import com.thinker.basethinker.utils.Utils;

import java.util.List;


/**
 * Created by farley on 17/8/22.
 * description:
 */

public class YueListAdapter extends BaseAdapter {
    private Context mContext;
    private List<RechartHistoryBean> dataList;

    public YueListAdapter(Context context, List<RechartHistoryBean> dataList) {
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
            convertView = inflater.inflate(R.layout.item_wallet, null);
            holder = new ViewHolder();
            holder.time = (TextView) convertView.findViewById(R.id.time);
            holder.type = (TextView) convertView.findViewById(R.id.type);
            holder.money = (TextView) convertView.findViewById(R.id.money);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        RechartHistoryBean data = dataList.get(position);
        holder.time.setText(Utils.stampToDate3(data.getCreateTime().getTime()));

        holder.type.setText(Utils.object2String(data.getLogInfo()));
        Double thisAmount = null;
        if(data.getLogAmount() != null){
            thisAmount = Math.abs(data.getLogAmount());
        }
        PersonalBean personalBean = MyUtils.getPersonData();
        if (data.getLogAmount() > 0){
            holder.money.setText("+"+Utils.object2String(personalBean.getCurrency()) + Utils.object2String(thisAmount));
            holder.money.setTextColor(mContext.getResources().getColor(R.color.color_tip_red));
        }else{
            holder.money.setText("-" +Utils.object2String(personalBean.getCurrency())+ Utils.object2String(thisAmount));
            holder.money.setTextColor(mContext.getResources().getColor(R.color.color_deep_gray));
        }


        return convertView;
    }

    class ViewHolder {
        TextView time, type, money;
    }
}