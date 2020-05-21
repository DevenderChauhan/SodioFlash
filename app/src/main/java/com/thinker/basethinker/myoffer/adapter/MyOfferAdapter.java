package com.thinker.basethinker.myoffer.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.thinker.basethinker.R;
import com.thinker.basethinker.myoffer.bean.MyOfferData;
import com.thinker.basethinker.utils.Utils;

import java.util.List;


/**
 * Created by farley on 17/5/22.
 * description:
 */

public class MyOfferAdapter extends BaseAdapter {
    private Context mContext;
    private List<MyOfferData> dataList;
    public MyOfferAdapter(Context context, List<MyOfferData> dataList) {
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
            convertView = inflater.inflate(R.layout.item_my_offer, null);
            holder = new ViewHolder();
            holder.offer_icon = (ImageView) convertView.findViewById(R.id.offer_icon);
            holder.offer_type = (TextView) convertView.findViewById(R.id.offer_type);
            holder.offer_money = (TextView) convertView.findViewById(R.id.offer_money);
            holder.offer_date = (TextView) convertView.findViewById(R.id.offer_date);
            holder.bottom_view = (View) convertView.findViewById(R.id.bottom_view);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        MyOfferData data = dataList.get(position);
        holder.offer_type.setText(Utils.object2String(data.getOfferType()));
        holder.offer_money.setText(Utils.object2String(data.getCurrency())+Utils.object2String(data.getMoney())+" "+mContext.getString(R.string.my_offer_money));
        holder.offer_date.setText(mContext.getString(R.string.my_offer_date)+" "+Utils.stampToDate4(data.getEffectiveDate().getTime()));
        if (position == dataList.size()-1){
            holder.bottom_view.setVisibility(View.VISIBLE);
        }else{
            holder.bottom_view.setVisibility(View.GONE);
        }
        return convertView;
    }

    class ViewHolder {
        ImageView offer_icon;
        TextView offer_type,offer_money,offer_date;
        View bottom_view;
    }
}
