package com.thinker.basethinker.views;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.thinker.basethinker.R;
import com.thinker.basethinker.dialog.StanderdDialog;
import com.thinker.basethinker.login.LoginActivity;
import com.thinker.basethinker.mvp.BaseBean;
import com.thinker.basethinker.mvp.ControllerActivity;
import com.thinker.basethinker.utils.PreferencesUtils;


public class ErrorDialogFragment extends DialogFragment {
    public static ErrorDialogFragment newInstance(int titleId, String message) {
        ErrorDialogFragment fragment = new ErrorDialogFragment();

        Bundle args = new Bundle();
        args.putInt("titleId", titleId);
        args.putString("message", message);

        fragment.setArguments(args);

        return fragment;
    }

    public ErrorDialogFragment() {
        // Empty constructor required for DialogFragment
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final int titleId = getArguments().getInt("titleId");
        String message = getArguments().getString("message");

        return new AlertDialog.Builder(getActivity())
            .setMessage(message)
            .setPositiveButton(R.string.error_dialog_ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                    if (titleId == -10) {
                        String token = "";
                        PreferencesUtils.putString(getActivity(), "RADISHSAAS_IS_BIND", token);
                        getActivity().startActivity(new Intent(getActivity(), LoginActivity.class));
//                        ControllerActivity.finishAll();
//                        getActivity().finish();
                    }
                }
            })
            .create();
    }


}