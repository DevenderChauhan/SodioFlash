package com.thinker.basethinker.bean;


import com.thinker.basethinker.mvp.BaseBean;

import java.util.List;

/**
 * Created by farley on 17/5/24.
 * description:
 */

public class WalletBean extends BaseBean {
    List<WalletItemData> datas;

    public WalletBean(List<WalletItemData> datas) {
        this.datas = datas;
    }

    public List<WalletItemData> getDatas() {
        return datas;
    }

    public void setDatas(List<WalletItemData> datas) {
        this.datas = datas;
    }
}
