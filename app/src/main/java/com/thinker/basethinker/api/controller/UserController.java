package com.thinker.basethinker.api.controller;


import com.thinker.basethinker.api.CommonController;
import com.thinker.basethinker.mvp.BaseBean;
import com.thinker.basethinker.set.bean.SetBean;
import com.thinker.basethinker.set.bean.SetData;
import com.thinker.basethinker.utils.PropertiesUtils;


import rx.Observable;
import rx.functions.Func1;
import vc.thinker.colours.client.api.UsercontrollerApi;
import vc.thinker.colours.client.model.ListResponseOfUserGuideBO;
import vc.thinker.colours.client.model.SingleResponseOfboolean;

/**
 * Created by farley on 17/5/22.
 * description:
 */

public class UserController extends CommonController {
    private UsercontrollerApi usercontrollerApi;

    public UserController(UsercontrollerApi usercontrollerApi) {
        this.usercontrollerApi = usercontrollerApi;
    }
    /**
     * 获取h5页面
     * @param type
     * @return
     */
    public Observable<SetBean> getGuideList(Integer type){
        return usercontrollerApi.userGuideListUsingGET(type)
                .map(new Func1<ListResponseOfUserGuideBO, SetBean>() {
                    @Override
                    public SetBean call(ListResponseOfUserGuideBO listResponseOfUserGuideBO) {
                        if (listResponseOfUserGuideBO.getSuccess()){
                            return new SetBean(PropertiesUtils.copyBeanListProperties(listResponseOfUserGuideBO.getItems(), SetData.class));
                        }else{
                            return toErrorBean(listResponseOfUserGuideBO.getError(),listResponseOfUserGuideBO.getErrorDescription(),SetBean.class);
                        }
                    }
                });
    }

    /**
     * 退出登录
     * @return
     */
    public Observable<BaseBean> logout(){
        return usercontrollerApi.logoutUsingGET()
                .map(new Func1<SingleResponseOfboolean, BaseBean>() {
                    @Override
                    public BaseBean call(SingleResponseOfboolean singleResponseOfboolean) {
                        if (singleResponseOfboolean.getSuccess()){
                            return new BaseBean("退出成功",0);
                        }else{
                            return new BaseBean(singleResponseOfboolean.getErrorDescription(),-1);
                        }

                    }
                });
    }

}
