package com.thinker.basethinker.utils;


import android.app.DialogFragment;
import android.app.FragmentManager;

import com.thinker.basethinker.R;
import com.thinker.basethinker.views.ErrorDialogFragment;


/**
 * A convenience class to handle displaying error dialogs.
 */
public class ErrorDialogHandler {

    FragmentManager mFragmentManager;

    public ErrorDialogHandler(FragmentManager fragmentManager) {
        mFragmentManager = fragmentManager;
    }

    public void showError(int code ,String errorMessage) {
        DialogFragment fragment = ErrorDialogFragment.newInstance(
                code, errorMessage);
        fragment.show(mFragmentManager, "error");
    }
}
