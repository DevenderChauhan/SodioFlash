package com.thinker.basethinker.userguide.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.thinker.basethinker.R;
import com.thinker.basethinker.set.bean.SetData;
import com.thinker.basethinker.utils.Utils;

import java.util.List;


/**
 * Created by farley on 17/5/18.
 * description:
 */

public class UserGuideAdapter extends BaseAdapter {
    private Context mContext;
    private List<SetData> dataList;
    public UserGuideAdapter(Context context,List<SetData> dataList) {
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
        ViewHolder holder;
        if (convertView == null){
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_guide, null);
            holder = new ViewHolder();
            holder.name = (TextView) convertView.findViewById(R.id.name);
            holder.top_view = (View) convertView.findViewById(R.id.top_view);
            holder.bottom_view = (View) convertView.findViewById(R.id.bottom_view);
            holder.bottom_gone = (View) convertView.findViewById(R.id.bottom_gone);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        holder.name.setText(Utils.object2String(dataList.get(position).getTitle()));
        if (position == 0){
            holder.top_view.setVisibility(View.VISIBLE);
        }else{
            holder.top_view.setVisibility(View.GONE);
        }
        if (position == dataList.size()-1){
            holder.bottom_view.setVisibility(View.GONE);
            holder.bottom_gone.setVisibility(View.VISIBLE);
        }else{
            holder.bottom_view.setVisibility(View.VISIBLE);
            holder.bottom_gone.setVisibility(View.GONE);
        }
        return convertView;
    }
    class ViewHolder{
        TextView name;
        View top_view,bottom_view,bottom_gone;
    }
}
