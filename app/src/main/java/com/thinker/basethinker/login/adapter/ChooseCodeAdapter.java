package com.thinker.basethinker.login.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.thinker.basethinker.R;
import com.thinker.basethinker.utils.Country;

import java.util.List;

/**
 * Created by thinker on 17/12/8.
 */

public class ChooseCodeAdapter extends BaseAdapter {
    private Context mContext;
    private List<Country> dataList;

    public ChooseCodeAdapter(Context mContext, List<Country> dataList) {
        this.mContext = mContext;
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
            convertView = inflater.inflate(R.layout.choose_code_item, null);
            holder = new ViewHolder();
            holder.topView = (View) convertView.findViewById(R.id.topView);
            holder.bottomView = (View) convertView.findViewById(R.id.bottomView);
            holder.diverd = (View) convertView.findViewById(R.id.diverd);
            holder.countryName = (TextView) convertView.findViewById(R.id.countryName);
            holder.countryCode = (TextView) convertView.findViewById(R.id.countryCode);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        Country country = dataList.get(position);
        holder.countryName.setText(country.getName());
        holder.countryCode.setText("+"+country.getCode());
        if (position == 0){
            holder.topView.setVisibility(View.VISIBLE);
        }else{
            holder.topView.setVisibility(View.GONE);
        }
        if (position == dataList.size()-1){
            holder.bottomView.setVisibility(View.VISIBLE);
            holder.diverd.setVisibility(View.GONE);
        }else{
            holder.bottomView.setVisibility(View.GONE);
            holder.diverd.setVisibility(View.VISIBLE);
        }
        return convertView;
    }
    class ViewHolder{
        TextView countryName,countryCode;
        View topView,bottomView,diverd;
    }
}
