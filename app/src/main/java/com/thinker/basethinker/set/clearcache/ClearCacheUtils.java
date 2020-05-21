package com.thinker.basethinker.set.clearcache;

import android.content.Context;
import android.widget.Toast;

import com.thinker.basethinker.R;
import com.thinker.basethinker.dialog.StanderdDialog;

/**
 * Created by farley on 17/3/10.
 * description:
 */

public class ClearCacheUtils {
    public OnClearListener onClearListener;
    public interface OnClearListener {
        void clear();
    }
    public static void clearCache(final Context activity, final OnClearListener onClearListener){
        StanderdDialog dialog = new StanderdDialog(activity, activity.getString(R.string.clear_title), activity.getString(R.string.clear_desc),
                activity.getString(R.string.clear_sure), activity.getString(R.string.clear_cancel)
                , new StanderdDialog.OnDialogClickListener() {
            @Override
            public void doAnyClick() {

            }

            @Override
            public void doMainClick() {
                DataCleanManager.clearAllCache(activity);
                Toast.makeText(activity,activity.getString(R.string.clear_success),Toast.LENGTH_SHORT).show();
                onClearListener.clear();

//                Glide.get(activity).clearMemory();//清理内存缓存  可以在UI主线程中进行
            }
        });
        dialog.show();
        dialog.setCanceledOnTouchOutside(false);
    }

}
