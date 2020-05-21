package com.thinker.basethinker.mvp;

/**
 * Created by farley on 17/3/13.
 * description:父bean
 */

public class BaseBean {
    //如果code为 -10， 需要跳转登录页面

    private int error_code=0;
    private String result;

    public BaseBean() {
    }

    public BaseBean(String result) {
        this.result = result;
    }

    public BaseBean(String result, int error_code) {
        this.result = result;
        this.error_code = error_code;
    }

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
