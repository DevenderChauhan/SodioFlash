package com.thinker.basethinker.bean;

import com.google.gson.annotations.SerializedName;
import com.thinker.basethinker.mvp.BaseBean;

/**
 * Created by thinker on 17/12/12.
 */

public class BindPhoneBean extends BaseBean {
    @SerializedName("isExistModile")
    private Boolean isExistModile = null;
    @SerializedName("loginName")
    private String loginName = null;
    @SerializedName("token")
    private String token = null;

    public Boolean getExistModile() {
        return isExistModile;
    }

    public void setExistModile(Boolean existModile) {
        isExistModile = existModile;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
