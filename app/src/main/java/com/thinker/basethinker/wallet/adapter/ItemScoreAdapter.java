package com.thinker.basethinker.wallet.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.thinker.basethinker.R;
import com.thinker.basethinker.bean.IntegralLogBean;
import com.thinker.basethinker.utils.Utils;

import java.util.List;

/**
 * Created by thinker on 17/12/13.
 */

public class ItemScoreAdapter extends BaseAdapter {
    private Context mContext;
    private List<IntegralLogBean> dataList;
    public ItemScoreAdapter(Context context, List<IntegralLogBean> dataList) {
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
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        IntegralLogBean data = dataList.get(position);
        holder.time.setText(Utils.stampToDate3(data.getCreateTime().getTime()));
        holder.type.setText(Utils.object2String(data.getLogInfo()));
        String score;
        if (data.getLogIntegral() > 0){
            score = "+"+data.getLogIntegral();
        }else{
            score = "-"+data.getLogIntegral();
        }
        holder.money.setText(Utils.object2String(score)+" "+mContext.getString(R.string.score_title));

        return convertView;
    }

    class ViewHolder {
        TextView time,type,money;
    }
}