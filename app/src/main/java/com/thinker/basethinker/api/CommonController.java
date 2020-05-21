package com.thinker.basethinker.api;


import com.google.gson.Gson;
import com.thinker.basethinker.mvp.BaseBean;

import vc.thinker.colours.client.model.SimpleResponse;

/**
 * Created by huan on 2016/2/29.
 */
public class CommonController {
    private static final String TAG = "CommonController";

    protected SimpleResult toSimpleResult(SimpleResponse simpleResponse) {
        SimpleResult simpleResult = new SimpleResult();
        simpleResult.setSuccess(simpleResponse != null && simpleResponse.getSuccess());
        simpleResult.setMessage(simpleResponse.getErrorDescription());
        return simpleResult;
    }

    protected BaseBean toBaseBean(SimpleResponse simpleResponse) {
        if (simpleResponse.getSuccess())
            return new BaseBean();
        else
            return new BaseBean(simpleResponse.getErrorDescription(), -1);
    }


    protected <T extends BaseBean> T toErrorBean(String errorCode, String errorDesc, Class<T> clazz)
    {
        try {
            T t = clazz.newInstance();
            if (errorCode.equals("401") || errorCode.equals("403")){
                t.setError_code(-10);
                t.setResult("登录超时，请重新登录");
            }else if (errorCode.equals("407") ){
                t.setError_code(407);
                t.setResult(errorDesc);
            }else if (errorCode.equals("409") ){
                t.setError_code(409);
                t.setResult(errorDesc);
            }else if (errorCode.equals("408") ){
                t.setError_code(408);
                t.setResult(errorDesc);
            }else{
                t.setError_code(-1);
                t.setResult(errorDesc);
            }
            return t;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    protected <T> T copyObjectByGson(Object sources, Class<T> clazz) {
        Gson gson = new Gson();
        String resultString = gson.toJson(sources);
        return gson.fromJson(resultString, clazz);
    }
}
