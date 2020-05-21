package com.thinker.basethinker.utils;

import android.util.Log;

/**
 * Created by thinker on 17/12/12.
 */

public class LogUtils {
    private static boolean flag = true;
     public static void e(String msg){
         if (flag) {
             Log.e("farley", msg);
         }
     }
}
