package com.thinker.basethinker.bean;

import com.thinker.basethinker.mvp.BaseBean;

import java.util.List;

/**
 * Created by thinker on 17/12/26.
 */

public class OrderListBean extends BaseBean {
    private List<OrderBean> dataList;

    public OrderListBean(List<OrderBean> dataList) {
        this.dataList = dataList;
    }

    public List<OrderBean> getDataList() {
        return dataList;
    }

    public void setDataList(List<OrderBean> dataList) {
        this.dataList = dataList;
    }
}
