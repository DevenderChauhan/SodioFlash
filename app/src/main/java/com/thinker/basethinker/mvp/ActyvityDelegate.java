package com.thinker.basethinker.mvp;

/**
 * Created by farley on 2017/2/23.
 * description:与activity生命周期一致，目的是为了通过activity的生命周期去控制是否要attachView：
 */

public interface ActyvityDelegate {
    public void onCreate();
    public void onPause();
    public void onResume();
    public void onStop();
    public void onDestroy();
}
