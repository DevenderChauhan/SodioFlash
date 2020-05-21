package com.thinker.basethinker.bean;

import com.google.gson.annotations.SerializedName;
import com.thinker.basethinker.mvp.BaseBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by farley on 17/8/15.
 * description:
 */

public class AdvImages extends BaseBean {
    @SerializedName("imgList")
    private List<ImagesBean> imgList = new ArrayList();
    @SerializedName("md5")
    private String md5 = null;
    @SerializedName("time")
    private Integer time = null;

    public List<ImagesBean> getImgList() {
        return imgList;
    }

    public void setImgList(List<ImagesBean> imgList) {
        this.imgList = imgList;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }
}
