package com.thinker.basethinker.mvp;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;

import com.thinker.basethinker.R;
import com.thinker.basethinker.views.LoadingView;


/**
 * Created by farley on 17/3/8.
 * description:
 */

public class LoadingDialog extends AlertDialog {
    public LoadingDialog(Context context) {
        super(context, R.style.myDialog);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading_dialog);
        LoadingView loading = (LoadingView) findViewById(R.id.loading);
        loading.startAnimator();
    }
}
