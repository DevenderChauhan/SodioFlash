package com.thinker.basethinker.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.thinker.basethinker.R;


/**
 * Created by farley on 17/3/10.
 * description:标准dialog
 */

public class StanderdDialog extends AlertDialog implements View.OnClickListener {
    private TextView do_main,do_any,dialog_title,dialog_content;//主操作和辅助操作按钮
    private View view_vertical;//分割线
    private OnDialogClickListener onDialogClickListener;
    private String titleStr = "标题";//标题
    private String mainStr = "主操作";//主操作按钮
    private String anyStr = "辅助操作";//辅助操作按钮
    private String contentStr = "dialog内容";//dialog内容
    private boolean showView = false;//控制辅助操作间是否隐藏
    private boolean showtitle = false;//控制标题是否可见



    public interface OnDialogClickListener{
        void doAnyClick();
        void doMainClick();
    }
//    private volatile static StanderdDialog standerdDialog;
//    public static StanderdDialog getInstatnce(Context context, String content , String mainStr, OnDialogClickListener listener){
//        if (standerdDialog == null){
//            synchronized (StanderdDialog.class){
//                if (standerdDialog == null){
//                    standerdDialog = new StanderdDialog(context,content,mainStr,listener);
//                }
//            }
//        }
//        return standerdDialog;
//    }
    //标准的有标题 两个操作按钮
    public StanderdDialog(Context context, String title, String content, String mainStr, String anyStr, OnDialogClickListener listener) {
        super(context, R.style.myDialog);
        onDialogClickListener = listener;
        this.titleStr = title;
        this.anyStr = anyStr;
        this.mainStr = mainStr;
        this.contentStr = content;

    }
    //有标题 只有主操作按钮
    public StanderdDialog(Context context, String title, String content , String mainStr, OnDialogClickListener listener) {
        super(context, R.style.myDialog);
        onDialogClickListener = listener;
        this.titleStr = title;
        this.mainStr = mainStr;
        this.contentStr = content;
        this.showView = true;
    }
    //只有主操作按钮 标题也没有
    public StanderdDialog(Context context, String content , String mainStr, OnDialogClickListener listener) {
        super(context, R.style.myDialog);
        onDialogClickListener = listener;
        this.mainStr = mainStr;
        this.contentStr = content;
        this.showView = true;
        showtitle = true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_standerd);

        initView();
    }

    private void initView() {
        do_main = (TextView) findViewById(R.id.do_main);
        do_any = (TextView) findViewById(R.id.do_any);
        dialog_title= (TextView) findViewById(R.id.dialog_title);
        dialog_content= (TextView) findViewById(R.id.dialog_content);
        view_vertical = findViewById(R.id.view_vertical);

        do_any.setOnClickListener(this);
        do_main.setOnClickListener(this);

        //隐藏辅助操作按钮和中间的分割线
        if (showView){
            view_vertical.setVisibility(View.GONE);
            do_any.setVisibility(View.GONE);
        }
        //隐藏标题
        if (showtitle){
            dialog_title.setVisibility(View.GONE);
        }
        dialog_title.setText(titleStr);
        dialog_content.setText(contentStr);
        do_main.setText(mainStr);
        do_any.setText(anyStr);
    }
    public void setMyNewMessage(String msg) {
        if (dialog_content != null) {
            dialog_content.setText(contentStr);
        }
    }
    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.do_any) {
            dismiss();
            onDialogClickListener.doAnyClick();
        }else if (i == R.id.do_main){
            dismiss();
            onDialogClickListener.doMainClick();
        }
    }
}
