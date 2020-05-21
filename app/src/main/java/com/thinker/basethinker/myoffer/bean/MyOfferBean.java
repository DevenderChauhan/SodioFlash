package com.thinker.basethinker.myoffer.bean;


import com.thinker.basethinker.mvp.BaseBean;

import java.util.List;

/**
 * Created by farley on 17/5/22.
 * description:
 */

public class MyOfferBean extends BaseBean {
    private List<MyOfferData> dataList;

    public MyOfferBean(List<MyOfferData> dataList) {
        this.dataList = dataList;
    }

    public List<MyOfferData> getDataList() {
        return dataList;
    }

    public void setDataList(List<MyOfferData> dataList) {
        this.dataList = dataList;
    }
}
