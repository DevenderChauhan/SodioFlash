package com.thinker.basethinker.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.thinker.basethinker.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Farley on 2018/5/2.
 */

public class PhoneDialog extends AlertDialog {
    private ListView listView;
    private List<String> phoneArray = new ArrayList<>();
    private Context mContext;
    private TextView cancel;
    public PhoneDialog(Context context, List<String> phoneArray, OnClickCancelLisener onClickCancelLisener) {
        super(context, R.style.myDialog);
        this.phoneArray = phoneArray;
        mContext = context;
        this.onClickCancelLisener = onClickCancelLisener;
    }
    public interface OnClickCancelLisener{
        void play(int pos);
    }
    private OnClickCancelLisener onClickCancelLisener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_phone);

        initView();
    }

    private void initView() {
        listView = (ListView)findViewById(R.id.listView);
        cancel = (TextView) findViewById(R.id.cancel);
        listView.setAdapter(new ArrayAdapter<String>(mContext,android.R.layout.simple_expandable_list_item_1,phoneArray));
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (onClickCancelLisener != null){
                    onClickCancelLisener.play(i);
                    dismiss();
                }
            }
        });
    }

}
