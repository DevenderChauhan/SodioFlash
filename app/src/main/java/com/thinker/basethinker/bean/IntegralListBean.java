package com.thinker.basethinker.bean;

import com.thinker.basethinker.mvp.BaseBean;

import java.util.List;

/**
 * Created by thinker on 17/12/13.
 */

public class IntegralListBean extends BaseBean {
    private List<IntegralLogBean> dataList;

    public IntegralListBean(List<IntegralLogBean> dataList) {
        this.dataList = dataList;
    }

    public List<IntegralLogBean> getDataList() {
        return dataList;
    }

    public void setDataList(List<IntegralLogBean> dataList) {
        this.dataList = dataList;
    }
}
