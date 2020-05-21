package com.thinker.basethinker.messagecenter.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.thinker.basethinker.R;
import com.thinker.basethinker.bean.MessageData;
import com.thinker.basethinker.utils.Utils;

import java.util.List;


/**
 * Created by farley on 17/7/3.
 * description:
 */

public class MessageAdapter extends BaseAdapter {
    private Context mContext;
    private List<MessageData> dataList;
    public MessageAdapter(Context context, List<MessageData> dataList) {
        mContext = context;
        this.dataList = dataList;
    }

    @Override
    public int getCount() {
        return dataList.size();
//        return 10;
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
            convertView = inflater.inflate(R.layout.item_message, null);
            holder = new ViewHolder();
            holder.img_time = (TextView) convertView.findViewById(R.id.img_time);
            holder.img_title = (TextView) convertView.findViewById(R.id.img_title);
            holder.img_url = (ImageView) convertView.findViewById(R.id.img_url);
            holder.text_time = (TextView) convertView.findViewById(R.id.text_time);
            holder.text_content = (TextView) convertView.findViewById(R.id.text_content);
            holder.img_ll = (LinearLayout) convertView.findViewById(R.id.img_ll);
            holder.text_ll = (LinearLayout) convertView.findViewById(R.id.text_ll);
            holder.bottom_view = (View) convertView.findViewById(R.id.bottom_view);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        MessageData data = dataList.get(position);
        if (data.getImageText() == null){
            holder.img_ll.setVisibility(View.GONE);
            holder.text_ll.setVisibility(View.GONE);
            return convertView;
        }
        if (data.getImageText()){
            holder.img_ll.setVisibility(View.VISIBLE);
            holder.text_ll.setVisibility(View.GONE);
            holder.img_time.setText(Utils.stampToDate3(data.getSendTime().getTime()));
            holder.img_title.setText(Utils.object2String(data.getTitle()));
            if (Utils.isEmptryStr(data.getCover())) {
                Glide.with(mContext).load(data.getCover()).into(holder.img_url);
            }
        }else{
            holder.img_ll.setVisibility(View.GONE);
            holder.text_ll.setVisibility(View.VISIBLE);
            holder.text_time.setText(Utils.stampToDate3(data.getSendTime().getTime()));
            holder.text_content.setText(Utils.object2String(data.getContent()));
        }
        if (position == dataList.size()-1){
            holder.bottom_view.setVisibility(View.VISIBLE);
        }else{
            holder.bottom_view.setVisibility(View.GONE);
        }
        return convertView;
    }

    class ViewHolder {
        TextView img_time;
        TextView img_title;
        ImageView img_url;

        TextView text_time;
        TextView text_content;
        View bottom_view;
        LinearLayout text_ll,img_ll;
    }
}
