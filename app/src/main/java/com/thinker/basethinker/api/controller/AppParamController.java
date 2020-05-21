package com.thinker.basethinker.api.controller;


import com.thinker.basethinker.api.CommonController;
import com.thinker.basethinker.bean.AdvImages;
import com.thinker.basethinker.bean.InvateAndShateBean;
import com.thinker.basethinker.bean.SystemParamsBean;
import com.thinker.basethinker.utils.PropertiesUtils;

import rx.Observable;
import rx.functions.Func1;
import vc.thinker.colours.client.api.AppparamcontrollerApi;
import vc.thinker.colours.client.model.SingleResponseOfImgBo;
import vc.thinker.colours.client.model.SingleResponseOfShareSetBO;
import vc.thinker.colours.client.model.SingleResponseOfSysSettingBO;

/**
 * Created by farley on 17/5/23.
 * description:
 */

public class AppParamController extends CommonController {
    private AppparamcontrollerApi appparamcontrollerApi;

    public AppParamController(AppparamcontrollerApi appparamcontrollerApi) {
        this.appparamcontrollerApi = appparamcontrollerApi;
    }
    /**
     * 获取邀请 和 分享的配置信息
     *
     * @return
     */
    public Observable<InvateAndShateBean> getInvateAndShareParams() {
        return appparamcontrollerApi.queryAppShareUsingGET()
                .map(new Func1<SingleResponseOfShareSetBO, InvateAndShateBean>() {
                    @Override
                    public InvateAndShateBean call(SingleResponseOfShareSetBO singleResponseOfShareSetBO) {
                        if (singleResponseOfShareSetBO.getSuccess()) {
                            return PropertiesUtils.copyBeanProperties(singleResponseOfShareSetBO.getItem(), InvateAndShateBean.class);
                        } else {
                            return toErrorBean(singleResponseOfShareSetBO.getError(), singleResponseOfShareSetBO.getErrorDescription(), InvateAndShateBean.class);
                        }
                    }
                });
    }
    /**
     * 获取系统配置
     *
     * @return
     */
    public Observable<SystemParamsBean> getSystemParams() {
        return appparamcontrollerApi.querySysSetUsingGET()
                .map(new Func1<SingleResponseOfSysSettingBO, SystemParamsBean>() {
                    @Override
                    public SystemParamsBean call(SingleResponseOfSysSettingBO singleResponseOfSysSettingBO) {
                        if (singleResponseOfSysSettingBO.getSuccess()) {
                            SystemParamsBean bean;
                            bean = PropertiesUtils.copyBeanProperties(singleResponseOfSysSettingBO.getItem(), SystemParamsBean.class);
                            return bean;
                        } else
                            return toErrorBean(singleResponseOfSysSettingBO.getError(), singleResponseOfSysSettingBO.getErrorDescription(), SystemParamsBean.class);
                    }
                });

    }
    /**
     * 获取广告图片
     *
     * @return
     */
    public Observable<AdvImages> getAdvImages() {
        return appparamcontrollerApi.queryInitImgUsingGET()
                .map(new Func1<SingleResponseOfImgBo, AdvImages>() {
                    @Override
                    public AdvImages call(SingleResponseOfImgBo singleResponseOfShareSetBO) {
                        if (singleResponseOfShareSetBO.getSuccess()) {
                            return PropertiesUtils.copyBeanProperties(singleResponseOfShareSetBO.getItem(), AdvImages.class);
                        } else {
                            return toErrorBean(singleResponseOfShareSetBO.getError(), singleResponseOfShareSetBO.getErrorDescription(), AdvImages.class);
                        }
                    }
                });
    }
}
