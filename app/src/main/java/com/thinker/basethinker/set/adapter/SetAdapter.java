package com.thinker.basethinker.set.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.thinker.basethinker.R;
import com.thinker.basethinker.set.bean.SetData;
import com.thinker.basethinker.utils.Utils;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by farley on 17/5/24.
 * description:
 */

public class SetAdapter extends BaseAdapter {
    private Context mContext;
    private List<SetData> arrays = new ArrayList<>();
    public SetAdapter(Context context,List<SetData> list) {
        mContext = context;
        arrays = list;
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
            convertView = inflater.inflate(R.layout.item_set, null);
            holder = new ViewHolder();
            holder.bottom_view = (View) convertView.findViewById(R.id.bottom_view);
            holder.title = (TextView) convertView.findViewById(R.id.title);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        holder.title.setText(Utils.object2String(arrays.get(position).getTitle()));
        if (position == arrays.size()-1){
            holder.bottom_view.setVisibility(View.GONE);
        }else{
            holder.bottom_view.setVisibility(View.VISIBLE);
        }
        return convertView;
    }
    class ViewHolder{
        TextView title;
        View bottom_view;
    }
}
