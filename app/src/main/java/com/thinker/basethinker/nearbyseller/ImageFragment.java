package com.thinker.basethinker.nearbyseller;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.thinker.basethinker.R;
import com.thinker.basethinker.utils.LogUtils;
import com.thinker.basethinker.utils.Utils;


/**
 * Created by thinker on 17/12/26.
 */

public class ImageFragment  extends Fragment {
    private   String PATH = "path";

    /*public static ImageFragment getInstance(String path) {
        ImageFragment fragment = new ImageFragment();
        Bundle bundle = new Bundle();
        bundle.putString(PATH, path);
        LogUtils.e("PATH="+path);
        fragment.setArguments(bundle);
        return fragment;
    }*/
    public ImageFragment(){

    }
    @SuppressLint("ValidFragment")
    public ImageFragment(String path){
        PATH = path;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View contentView = inflater.inflate(R.layout.fragment_image_seller, container, false);
        ImageView imageView = (ImageView) contentView.findViewById(R.id.preview_image);
        LogUtils.e("PATH="+PATH);
        if (Utils.isEmptryStr(PATH)) {
            Glide.with(container.getContext())
                    .load(PATH)
                    .into(imageView);
        }
        return contentView;
    }
}
