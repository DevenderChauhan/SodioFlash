package com.thinker.basethinker.wallet.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.thinker.basethinker.R;
import com.thinker.basethinker.bean.PersonalBean;
import com.thinker.basethinker.bean.WalletItemData;
import com.thinker.basethinker.utils.MyUtils;
import com.thinker.basethinker.utils.Utils;

import java.util.List;


/**
 * Created by farley on 17/5/18.
 * description:
 */

public class ItemWalletAdapter extends BaseAdapter {
    private Context mContext;
    private List<WalletItemData> dataList;
    public ItemWalletAdapter(Context context, List<WalletItemData> dataList) {
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
        WalletItemData data = dataList.get(position);
        holder.time.setText(Utils.stampToDate3(data.getCreateTime().getTime()));
        PersonalBean bean = MyUtils.getPersonData();
        if (data.getType().contentEquals("1")){
            holder.type.setText(mContext.getString(R.string.wallet_list_recharge));
            holder.money.setText("-"+Utils.object2String(bean.getCurrency())+Utils.object2String(data.getAmount()));
            holder.money.setTextColor(mContext.getResources().getColor(R.color.color_deep_gray));
        }else if (data.getType().contentEquals("2") || data.getType().contentEquals("3")){
            holder.type.setText(mContext.getString(R.string.wallet_list_refound));
            holder.money.setText("+"+Utils.object2String(bean.getCurrency())+Utils.object2String(data.getAmount()));
            holder.money.setTextColor(mContext.getResources().getColor(R.color.color_tip_red));
        }else if (data.getType().contentEquals("4") ){
            holder.type.setText(mContext.getString(R.string.wallet_list_buy));
            holder.money.setText(Utils.object2String(bean.getCurrency())+Utils.object2String(data.getAmount()));
            holder.money.setTextColor(mContext.getResources().getColor(R.color.color_tip_red));
        }

        return convertView;
    }

    class ViewHolder {
        TextView time,type,money;
    }
}