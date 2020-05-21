package com.thinker.basethinker.toplayout;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.thinker.basethinker.R;


/**
 * Created by farley on 17/7/3.
 * description:彩虹雨伞---------->>>>>问题反馈提醒
 */

public class AskFeedbackFragment extends Fragment {
    private View view;

    public AskFeedbackFragment() {
    }

    @SuppressLint("ValidFragment")
    public AskFeedbackFragment(String km, String min, String location) {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_umbrella_ask_feedback,
                container, false);
        initView();
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    private void initView() {
    }
}

