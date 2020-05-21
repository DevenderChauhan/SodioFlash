package com.thinker.basethinker.bean;

import com.thinker.basethinker.mvp.BaseBean;

import java.util.List;

/**
 * Created by thinker on 17/12/21.
 * 所有商户
 */

public class AllSellerBean extends BaseBean {

    private List<SellerBean> dataList;
    public AllSellerBean() {

    }
    public AllSellerBean(List<SellerBean> dataList) {
        this.dataList = dataList;
    }

    public List<SellerBean> getDataList() {
        return dataList;
    }

    public void setDataList(List<SellerBean> dataList) {
        this.dataList = dataList;
    }
}
