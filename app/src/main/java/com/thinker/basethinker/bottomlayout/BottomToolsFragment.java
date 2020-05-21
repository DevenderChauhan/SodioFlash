package com.thinker.basethinker.bottomlayout;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.thinker.basethinker.IMainView;
import com.thinker.basethinker.R;
import com.thinker.basethinker.utils.MyUtils;

/**
 * Created by thinker on 17/12/18.
 */

public class BottomToolsFragment extends Fragment implements View.OnClickListener {
    private View view;
    private ImageView map_refresh;
    private ImageView map_location;
    private ImageView map_list;
    private ImageView map_help;
    private LinearLayout map_scan_lock;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_main_bottom,
                container, false);
        initView();
        return view;
    }

    private void initView() {
        map_refresh = (ImageView) view.findViewById(R.id.map_refresh);
        map_location = (ImageView)view.findViewById(R.id.map_location);
        map_list = (ImageView)view.findViewById(R.id.map_list);
        map_help = (ImageView)view.findViewById(R.id.map_help);
        map_scan_lock = (LinearLayout) view.findViewById(R.id.map_scan_lock);
        map_refresh.setOnClickListener(this);
        map_location.setOnClickListener(this);
        map_list.setOnClickListener(this);
        map_help.setOnClickListener(this);
        map_scan_lock.setOnClickListener(this);
    }

    public void setUsingGone(){
        map_list.setVisibility(View.GONE);
        map_scan_lock.setVisibility(View.GONE);
    }
    public void setUsingVisiable(){
        map_list.setVisibility(View.VISIBLE);
        map_scan_lock.setVisibility(View.VISIBLE);
    }
    public void isFeedbackIng(boolean isShow){
        if (isShow){
            map_list.setVisibility(View.VISIBLE);
            map_scan_lock.setVisibility(View.VISIBLE);
            map_help.setVisibility(View.VISIBLE);
        }else{
            map_list.setVisibility(View.GONE);
            map_scan_lock.setVisibility(View.GONE);
            map_help.setVisibility(View.GONE);
        }

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.map_refresh:
                setRefreshStart();
                break;
            case R.id.map_location:
                if (getActivity() instanceof IMainView){
                    ((IMainView)getActivity()).setMyLocation();
                }
                break;
            case R.id.map_list:
                if (getActivity() instanceof IMainView){
                    ((IMainView)getActivity()).getNearSeller();
                }
                break;
            case R.id.map_help:
                if (getActivity() instanceof IMainView){
                    ((IMainView)getActivity()).feedback();
                }
                break;
            case R.id.map_scan_lock:
                if (getActivity() instanceof IMainView){
                    ((IMainView)getActivity()).scanLock();
                }
                break;
            default:
                break;
        }
    }
    /**
     * 设置刷新动画
     */
    public void setRefreshStart(){
        if (map_refresh.isEnabled()) {
            if (getActivity() instanceof IMainView) {
                ((IMainView) getActivity()).setRefresh();
                map_refresh.setEnabled(false);
            }
            MyUtils.setAnimotion(map_refresh);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    map_refresh.setEnabled(true);
                    map_refresh.setVisibility(View.VISIBLE);
                }
            }, 3000);
        }
    }
}
