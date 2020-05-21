package com.thinker.basethinker.set.bean;


import com.thinker.basethinker.mvp.BaseBean;

import java.util.List;

/**
 * Created by farley on 17/5/24.
 * description:
 */

public class SetBean extends BaseBean {
    List<SetData> datas;

    public SetBean(List<SetData> datas) {
        this.datas = datas;
    }

    public List<SetData> getDatas() {
        return datas;
    }

    public void setDatas(List<SetData> datas) {
        this.datas = datas;
    }
}
