package com.thinker.basethinker.nearbyseller.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.thinker.basethinker.R;
import com.thinker.basethinker.bean.AllSellerBean;
import com.thinker.basethinker.bean.SellerBean;
import com.thinker.basethinker.utils.Utils;

/**
 * Created by thinker on 17/12/26.
 */

public class NearSellerAdapter extends BaseAdapter {
    private Context mContext;
    private AllSellerBean allSellerBean;
    public NearSellerAdapter(Context mContext, AllSellerBean allSellerBean) {
        this.mContext = mContext;
        this.allSellerBean = allSellerBean;
    }

    @Override
    public int getCount() {
        return allSellerBean.getDataList().size();
    }

    @Override
    public Object getItem(int position) {
        return allSellerBean.getDataList().get(position);
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
            convertView = inflater.inflate(R.layout.item_near_seller, null);
            holder = new ViewHolder();
            holder.sellerIcon = (ImageView) convertView.findViewById(R.id.sellerIcon);
            holder.sellerName = (TextView)convertView.findViewById(R.id.sellerName);
            holder.sellerAddress = (TextView)convertView.findViewById(R.id.sellerAddress);
            holder.sellerTime = (TextView)convertView.findViewById(R.id.sellerTime);
            holder.sellerDistance = (TextView)convertView.findViewById(R.id.sellerDistance);
            holder.diver = (View)convertView.findViewById(R.id.diver);
            holder.bootom = (View)convertView.findViewById(R.id.bootom);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        SellerBean sellerBean = allSellerBean.getDataList().get(position);
        if (!TextUtils.isEmpty(sellerBean.getSellerLogo())){
            Glide.with(mContext).load(sellerBean.getSellerLogo()+"_120x120").into(holder.sellerIcon);
        }
        holder.sellerName.setText(Utils.object2String(sellerBean.getSellerName()));
        holder.sellerTime.setText(Utils.object2String(sellerBean.getServiceTime()));
        holder.sellerAddress.setText(Utils.object2String(sellerBean.getLocationAddress()+sellerBean.getLocationDesc()));
        if (sellerBean.getDistance() != null) {
            if (sellerBean.getDistance() > 999){
                holder.sellerDistance.setText(Utils.object2String((float)sellerBean.getDistance()/1000) + "km");
            }else {
                holder.sellerDistance.setText(Utils.object2String(sellerBean.getDistance()) + "m");
            }
        }
        if (allSellerBean.getDataList().size()-1 == position){
            holder.diver.setVisibility(View.GONE);
            holder.bootom.setVisibility(View.VISIBLE);
        }else{
            holder.diver.setVisibility(View.VISIBLE);
            holder.bootom.setVisibility(View.GONE);
        }
        return convertView;
    }
    class ViewHolder{
        ImageView sellerIcon;
        TextView sellerName;
        TextView sellerAddress;
        TextView sellerTime;
        TextView sellerDistance;
        View diver;
        View bootom;
    }
}
