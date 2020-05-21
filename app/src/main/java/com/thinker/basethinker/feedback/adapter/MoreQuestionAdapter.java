package com.thinker.basethinker.feedback.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.thinker.basethinker.R;
import com.thinker.basethinker.feedback.bean.FeedbackTypeListData;
import com.thinker.basethinker.utils.Utils;

import java.util.List;


/**
 * Created by farley on 17/5/22.
 * description:
 */

public class MoreQuestionAdapter extends BaseAdapter {
    private Context mContext;
    private List<FeedbackTypeListData> strList;
    private int seleted = 0;
    private OnMySeletcedListener onMySeletcedListener;
    public MoreQuestionAdapter(Context context,List<FeedbackTypeListData> strList,int selected) {
        mContext = context;
        this.strList = strList;
        this.seleted = selected;
    }

    @Override
    public int getCount() {
        return strList.size()-6;
    }

    @Override
    public Object getItem(int position) {
        return strList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    public void setSelected(int posSelect){
        seleted = posSelect;
        notifyDataSetChanged();
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null){
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_more_question,null);
            holder = new ViewHolder();
            holder.rr_bg = (RelativeLayout) convertView.findViewById(R.id.rr_bg);
            holder.itemName = (TextView) convertView.findViewById(R.id.itemName);
            holder.selected = (ImageView) convertView.findViewById(R.id.selected);
            holder.view = (View) convertView.findViewById(R.id.view);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        holder.itemName.setText(Utils.object2String(strList.get(position+6).getTypeName()));
        if (position == seleted){
            holder.selected.setVisibility(View.VISIBLE);
        }else{
            holder.selected.setVisibility(View.GONE);
        }
        if (position == strList.size()-7){
            holder.view.setVisibility(View.GONE);
        }else{
            holder.view.setVisibility(View.VISIBLE);
        }
        holder.rr_bg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onMySeletcedListener.onSelected(position);
            }
        });
        return convertView;
    }
    class ViewHolder{
        TextView itemName;
        ImageView selected;
        View view;
        RelativeLayout rr_bg;
    }
    public void setOnMySelectedListener(OnMySeletcedListener onMySeletcedListener){
        this.onMySeletcedListener = onMySeletcedListener;
    }
    public interface OnMySeletcedListener{
        void onSelected(int position);
    }
}
