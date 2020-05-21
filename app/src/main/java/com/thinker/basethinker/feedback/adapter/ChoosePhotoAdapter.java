package com.thinker.basethinker.feedback.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.thinker.basethinker.R;

import java.util.List;


/**
 * Created by farley on 17/5/22.
 * description:
 */

public class ChoosePhotoAdapter extends RecyclerView.Adapter<ChoosePhotoAdapter.ViewHolder> {
    private Context mContext;
    private OnItemClickLitener mOnItemClickLitener;
    private List<String> placeArray;
    private int selected = -1;
    private boolean canclick = true;//可否点击,默认可以
    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }

    public ChoosePhotoAdapter(Context context, List<String> placeArray) {
        mContext = context;
        this.placeArray = placeArray;

    }
    public void refreshData(List<String> placeArray){
        this.placeArray = placeArray;
        notifyDataSetChanged();
    }
    public void setItemEnable(boolean Click){
        this.canclick = Click;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder holder = new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_up_photo, parent,
                false));
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        if (placeArray.size() > position) {
            final String placeId = placeArray.get(position);
            Glide.with(mContext).load(placeId).into(holder.img);
        }else{
            holder.img.setImageDrawable(mContext.getResources().getDrawable(R.drawable.upload_pictures));
        }
        holder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickLitener.onItemClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (placeArray.size() == 0){
            return 1;
        }else if (placeArray.size() < photoTimes){
            return placeArray.size()+1;
        }else {
            return placeArray.size();
        }

    }
    private int photoTimes = 4;
    public void setPhotoTimes(int photoTimes){
        this.photoTimes = photoTimes;
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        public ViewHolder(View itemView) {
            super(itemView);
            img = (ImageView) itemView.findViewById(R.id.img);
        }
    }

    public interface OnItemClickLitener {
        void onItemClick(int position);
    }
}
