package com.thinker.basethinker.feedback.bean;


import com.thinker.basethinker.mvp.BaseBean;

import java.util.List;

/**
 * Created by farley on 17/5/30.
 * description:
 */

public class FeedbackTypeListBean  extends BaseBean {
   List<FeedbackTypeListData> datas;

    public FeedbackTypeListBean(List<FeedbackTypeListData> datas) {
        this.datas = datas;
    }

    public List<FeedbackTypeListData> getDatas() {
        return datas;
    }

    public void setDatas(List<FeedbackTypeListData> datas) {
        this.datas = datas;
    }
}
