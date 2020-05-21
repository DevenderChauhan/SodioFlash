package com.thinker.basethinker.googleutils;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.thinker.basethinker.R;
import com.thinker.basethinker.app.MyApplication;
import com.thinker.basethinker.utils.Utils;

/**
 * Created by thinker on 17/12/19.
 */

public class MyInfoWindow implements GoogleMap.InfoWindowAdapter {
    private Context mContext = MyApplication.getIntance();
    private LatLng latLng;
    private String snippet;
    private String agentName;
    @Override
    public View getInfoWindow(Marker marker) {
        initData(marker);
        View  view = initView();
        return view;
    }

    private void initData(Marker marker) {
        latLng = marker.getPosition();
        snippet = marker.getSnippet();
        agentName = marker.getTitle();
    }

    private View initView() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.marker_info_window, null);
        TextView time = (TextView) view.findViewById(R.id.time);
        TextView distance = (TextView) view.findViewById(R.id.distance);
//        int myTime = Integer.parseInt(agentName);
//        int myDistance = Integer.parseInt(snippet);
//        String[] timearray = Utils.timeTurnFormat(myTime);
//        if (timearray[0].equals("0")){
//            agentName = "<font color=\"#ED242C\" size=\"18\"> "+timearray[1]+"</font>" + "分钟";
//        }else{
//            agentName = "<font size=\"18\" color=\"#ED242C\" > "+timearray[0]+"</font>"+"小时"+"<font color=\"#ED242C\" size=\"18\"> "+timearray[1]+"</font>"+"分钟";
//        }
//        time.setText(Html.fromHtml(agentName));
//        distance.setText(Utils.distanceTurnFormat(myDistance));
        time.setText(agentName);
        distance.setText(snippet);
        return view;
    }

    @Override
    public View getInfoContents(Marker marker) {
        return null;
    }
}
