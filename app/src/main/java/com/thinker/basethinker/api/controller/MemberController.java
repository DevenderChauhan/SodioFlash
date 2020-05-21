package com.thinker.basethinker.api.controller;

import com.thinker.basethinker.api.CommonController;
import com.thinker.basethinker.api.Config;
import com.thinker.basethinker.bean.AuthCredentials;
import com.thinker.basethinker.bean.BasicCostBean;
import com.thinker.basethinker.bean.BindPhoneBean;
import com.thinker.basethinker.bean.ChergeBean;
import com.thinker.basethinker.bean.IntegralListBean;
import com.thinker.basethinker.bean.IntegralLogBean;
import com.thinker.basethinker.bean.PersonalBean;
import com.thinker.basethinker.bean.UserMoneyBean;
import com.thinker.basethinker.bean.WalletBean;
import com.thinker.basethinker.bean.WalletItemData;
import com.thinker.basethinker.feedback.bean.FeedBackUpLoadBean;
import com.thinker.basethinker.feedback.bean.FeedbackTypeListBean;
import com.thinker.basethinker.feedback.bean.FeedbackTypeListData;
import com.thinker.basethinker.member.bean.MemberVipCardBean;
import com.thinker.basethinker.member.bean.VipListBean;
import com.thinker.basethinker.member.bean.VipListPayBean;
import com.thinker.basethinker.member.bean.VipPayBean;
import com.thinker.basethinker.mvp.BaseBean;
import com.thinker.basethinker.myoffer.bean.MyOfferBean;
import com.thinker.basethinker.myoffer.bean.MyOfferData;
import com.thinker.basethinker.pay.bean.PayDetails;
import com.thinker.basethinker.set.bean.SetBean;
import com.thinker.basethinker.set.bean.SetData;
import com.thinker.basethinker.utils.LogUtils;
import com.thinker.basethinker.utils.PropertiesUtils;

import org.apache.oltu.oauth2.client.OAuthClient;
import org.apache.oltu.oauth2.client.URLConnectionClient;
import org.apache.oltu.oauth2.client.request.OAuthClientRequest;
import org.apache.oltu.oauth2.client.response.OAuthJSONAccessTokenResponse;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.types.GrantType;

import javax.security.auth.login.LoginException;

import rx.Observable;
import rx.functions.Func1;
import vc.thinker.colours.client.ApiClient;
import vc.thinker.colours.client.api.MembercontrollerApi;
import vc.thinker.colours.client.model.FeedbackVO;
import vc.thinker.colours.client.model.ListResponseOfAPIMembershipCardBO;
import vc.thinker.colours.client.model.ListResponseOfFeedbackTypeBO;
import vc.thinker.colours.client.model.ListResponseOfUserGuideBO;
import vc.thinker.colours.client.model.PageResponseOfAPIIntegralLogBO;
import vc.thinker.colours.client.model.PageResponseOfAPIUserCouponBO;
import vc.thinker.colours.client.model.PageResponseOfAPIUserDepositPayLogBo;
import vc.thinker.colours.client.model.PageResponseOfAPIVipPayLogBO;
import vc.thinker.colours.client.model.PageResponseOfUserCouponBO;
import vc.thinker.colours.client.model.PageResponseOfUserDepositLogBO;
import vc.thinker.colours.client.model.RealnameVO;
import vc.thinker.colours.client.model.SimpleResponse;
import vc.thinker.colours.client.model.SingleResponseOfAPIBasicCostBO;
import vc.thinker.colours.client.model.SingleResponseOfAPIDepositBO;
import vc.thinker.colours.client.model.SingleResponseOfAPIUserMoneyBO;
import vc.thinker.colours.client.model.SingleResponseOfMemberBO;
import vc.thinker.colours.client.model.SingleResponseOfPayDetailsBO;
import vc.thinker.colours.client.model.SingleResponseOfThirdPartyBoundModileBO;
import vc.thinker.colours.client.model.SingleResponseOfboolean;
import vc.thinker.colours.client.model.SingleResponseOfdouble;
import vc.thinker.colours.client.model.SingleResponseOfstring;

/**
 * Created by farley on 17/8/17.
 * description:
 */

public class MemberController extends CommonController {
    private MembercontrollerApi membercontrollerApi;

    public MemberController(MembercontrollerApi membercontrollerApi) {
        this.membercontrollerApi = membercontrollerApi;
    }
    //第三方完善信息
    public Observable<BaseBean> thirdPartyPerfectInfoUsingGET(String contry,String alisa) {
        return membercontrollerApi.thirdPartyPerfectInfoUsingGET(contry,alisa)
                .map(new Func1<SimpleResponse, BaseBean>() {
                    @Override
                    public BaseBean call(SimpleResponse simpleResponse) {
                        return toBaseBean(simpleResponse);
                    }
                });
    }
    //充电宝购买接口
    public Observable<BaseBean> paymetPbUsingGET() {
        return membercontrollerApi.paymetPbUsingGET()
                .map(new Func1<SimpleResponse, BaseBean>() {
                    @Override
                    public BaseBean call(SimpleResponse simpleResponse) {
                        return toBaseBean(simpleResponse);
                    }
                });
    }
    //获取验证码
    public Observable<BaseBean> getAuth(String phoneNum) {
        return membercontrollerApi.sendModileVerifycodeUsingGET(phoneNum)
                .map(new Func1<SimpleResponse, BaseBean>() {
                    @Override
                    public BaseBean call(SimpleResponse simpleResponse) {
                        return toBaseBean(simpleResponse);
                    }
                });
    }
    //获取基础会员费用
    public Observable<BasicCostBean> getBasicCostUsingGET() {
        return membercontrollerApi.getBasicCostUsingGET()
                .map(new Func1<SingleResponseOfAPIBasicCostBO, BasicCostBean>() {
                    @Override
                    public BasicCostBean call(SingleResponseOfAPIBasicCostBO simpleResponse) {
                        if (simpleResponse.getSuccess()) {
                            return PropertiesUtils.copyBeanProperties(simpleResponse.getItem(), BasicCostBean.class);
                        }else{
                            return toErrorBean(simpleResponse.getError(),simpleResponse.getErrorDescription(),BasicCostBean.class);
                        }
                    }
                });
    }
    //注册
    public Observable<BaseBean> registeredUsingPOST(String countryCode,String phoneNum,String verifycode,String password,String sysCode) {
        return membercontrollerApi.registeredUsingPOST(countryCode,phoneNum,verifycode,password,sysCode)
                .map(new Func1<SimpleResponse, BaseBean>() {
                    @Override
                    public BaseBean call(SimpleResponse simpleResponse) {
                        return toBaseBean(simpleResponse);
                    }
                });
    }
    //根据手机号修改密码
    public Observable<BaseBean> updatePasswordByMobileUsingPOST(String countryCode,String phoneNum,String verifycode,String password) {
        return membercontrollerApi.updatePasswordByMobileUsingPOST(countryCode,phoneNum,verifycode,password)
                .map(new Func1<SimpleResponse, BaseBean>() {
                    @Override
                    public BaseBean call(SimpleResponse simpleResponse) {
                        return toBaseBean(simpleResponse);
                    }
                });
    }
    /**
     * 修改头像
     * @return
     */
    public Observable<BaseBean> saveUserHead(String imgUrl){
        return membercontrollerApi.modifyUserHeadUsingGET(imgUrl)
                .map(new Func1<SimpleResponse, BaseBean>() {
                    @Override
                    public BaseBean call(SimpleResponse simpleResponse) {
                        return  toBaseBean(simpleResponse);
                    }
                });
    }
    //第三方登录
    public Observable<BaseBean> thirdPartyLoginUsingPOST(String openId,String accessToken,String queryType) {
        return membercontrollerApi.thirdPartyLoginUsingPOST(openId,accessToken,queryType)
                .map(new Func1<SingleResponseOfstring, BaseBean>() {
                    @Override
                    public BaseBean call(SingleResponseOfstring simpleResponse) {
                        LogUtils.e(simpleResponse.getErrorDescription()+"；"+simpleResponse.getError());
                        BaseBean baseBean = new BaseBean();
                        if (simpleResponse.getSuccess()){
                            baseBean.setError_code(0);
                            baseBean.setResult(simpleResponse.getItem());
                        }else {
                            baseBean.setError_code(-1);
                            baseBean.setResult(simpleResponse.getErrorDescription());
                        }

                        return baseBean;
                    }
                });
    }
    /**
     * 获取会员明细
     * @return
     */
    public Observable<VipListPayBean> getVipListM(Long lgtime){
        return membercontrollerApi.findVipPayListUsingGET(lgtime)
                .map(new Func1<PageResponseOfAPIVipPayLogBO, VipListPayBean>() {
                    @Override
                    public VipListPayBean call(PageResponseOfAPIVipPayLogBO listResponseOfAPIMembershipCardBO) {
                        if (listResponseOfAPIMembershipCardBO.getSuccess()){
                            return new VipListPayBean(PropertiesUtils.copyBeanListProperties(listResponseOfAPIMembershipCardBO.getContent(), VipPayBean.class));
                        }else{
                            return toErrorBean(listResponseOfAPIMembershipCardBO.getError(),listResponseOfAPIMembershipCardBO.getErrorDescription(),VipListPayBean.class);
                        }
                    }
                });
    }
    /**
     * 获取会员列表
     * @return
     */
    public Observable<VipListBean> getVipList(){
        return membercontrollerApi.findMembershipCardListUsingGET()
                .map(new Func1<ListResponseOfAPIMembershipCardBO, VipListBean>() {
                    @Override
                    public VipListBean call(ListResponseOfAPIMembershipCardBO listResponseOfAPIMembershipCardBO) {
                        if (listResponseOfAPIMembershipCardBO.getSuccess()){
                            return new VipListBean(PropertiesUtils.copyBeanListProperties(listResponseOfAPIMembershipCardBO.getItems(), MemberVipCardBean.class));
                        }else{
                            return toErrorBean(listResponseOfAPIMembershipCardBO.getError(),listResponseOfAPIMembershipCardBO.getErrorDescription(),VipListBean.class);
                        }
                    }
                });
    }
    /**
     * 充值押金
     *
     * @param payType
     * @return
     */
    public Observable<PayDetails> buyVip(Long cardId,String payType) {
        return membercontrollerApi.paymetVIPUsingPOST(cardId,payType)
                .map(new Func1<SingleResponseOfPayDetailsBO, PayDetails>() {
                    @Override
                    public PayDetails call(SingleResponseOfPayDetailsBO singleResponseOfWeiXinPaymetBO) {
                        return copyObjectByGson(singleResponseOfWeiXinPaymetBO.getItem(), PayDetails.class);
                    }
                });
    }
    /**
     * 登录
     *
     * @param mobile
     * @param validCode
     * @return
     */

    public Observable<String> doLogin(String mobile, String validCode) {
        return Observable.just(new AuthCredentials(mobile, validCode)).flatMap(new Func1<AuthCredentials, Observable<String>>() {
            @Override
            public Observable<String> call(AuthCredentials credentials) {
                try {
                    OAuthClientRequest request = OAuthClientRequest
                            .tokenLocation(ApiClient.tokenUrl)
                            .setClientId(Config.appClientId)
                            .setClientSecret(Config.appClientSecret)
                            .setUsername(credentials.getUsername())
                            .setPassword(credentials.getPassword())
                            .setGrantType(GrantType.PASSWORD)
                            .buildBodyMessage();
                    request.addHeader("Accept-Language",ApiClient.language);
                    request.addHeader("api-agent",ApiClient.userAgent);
                    OAuthClient client = new OAuthClient(new URLConnectionClient());

                    OAuthJSONAccessTokenResponse accessTokenResponse = client.accessToken(request);
                    client.shutdown();
                    return Observable.just(accessTokenResponse.getAccessToken());
                } catch (OAuthSystemException e) {
                    e.printStackTrace();
                } catch (OAuthProblemException e) {
                    e.printStackTrace();
                    return Observable.error(new LoginException(e.getDescription()));
                }

                return Observable.error(new LoginException("登录失败"));
            }
        });
    }
    /**
     * 修改昵称
     * @return
     */
    public Observable<BaseBean> saveUserNickname(String name){
        return membercontrollerApi.modifyUserNicknameUsingGET(name)
                .map(new Func1<SimpleResponse, BaseBean>() {
                    @Override
                    public BaseBean call(SimpleResponse simpleResponse) {
                        return  toBaseBean(simpleResponse);
                    }
                });
    }
    /**
     * 修改绑定机器码
     * @return
     */
    public Observable<BaseBean> updateBindingSysCodeUsingGET(String syscode){
        return membercontrollerApi.updateBindingSysCodeUsingGET(syscode)
                .map(new Func1<SimpleResponse, BaseBean>() {
                    @Override
                    public BaseBean call(SimpleResponse simpleResponse) {
                        return  toBaseBean(simpleResponse);
                    }
                });
    }
    /**
     * 兑换邀请码
     * @return
     */
    public Observable<BaseBean> exchangeCodeUsingPOST(String inviteCode){
        return membercontrollerApi.exchangeCodeUsingPOST(inviteCode)
                .map(new Func1<SimpleResponse, BaseBean>() {
                    @Override
                    public BaseBean call(SimpleResponse simpleResponse) {
                        return  toBaseBean(simpleResponse);
                    }
                });
    }
    /**
     * 获取个人信息
     *
     * @return
     */
    public Observable<PersonalBean> getPersonalData() {
        return membercontrollerApi.getMyProfileUsingGET()
                .map(new Func1<SingleResponseOfMemberBO, PersonalBean>() {
                    @Override
                    public PersonalBean call(SingleResponseOfMemberBO singleResponseOfMemberBO) {
                        if (singleResponseOfMemberBO.getSuccess()) {
                            PersonalBean bean ;
                            bean = PropertiesUtils.copyBeanProperties(singleResponseOfMemberBO.getItem(), PersonalBean.class);
                            return bean;
                        }
                        else
                            return toErrorBean(singleResponseOfMemberBO.getError(), singleResponseOfMemberBO.getErrorDescription(), PersonalBean.class);
                    }
                });
    }
    /**
     * 获取我的优惠券
     * @return
     */
    public Observable<MyOfferBean> getMyOffer(){
        return membercontrollerApi.findCouponListUsingPOST()
                .map(new Func1<PageResponseOfAPIUserCouponBO, MyOfferBean>() {
                    @Override
                    public MyOfferBean call(PageResponseOfAPIUserCouponBO pageResponseOfUserCouponBO) {
                        if (pageResponseOfUserCouponBO.getSuccess()){
                            return new MyOfferBean(PropertiesUtils.copyBeanListProperties(pageResponseOfUserCouponBO.getContent(), MyOfferData.class));
                        }else{
                            return toErrorBean(pageResponseOfUserCouponBO.getError(),pageResponseOfUserCouponBO.getErrorDescription(),MyOfferBean.class);
                        }
                    }
                });
    }
    /**
     * 第三方绑定
     * @return
     */
    public Observable<BaseBean> boundThirdPartyUsingPOST(String openId, String accessToken, String third_party_type){
        return membercontrollerApi.boundThirdPartyUsingPOST(openId,accessToken,third_party_type)
                .map(new Func1<SimpleResponse, BaseBean>() {
                    @Override
                    public BaseBean call(SimpleResponse pageResponseOfUserCouponBO) {
                        return toBaseBean(pageResponseOfUserCouponBO);
                    }
                });
    }
    /**
     * 用户绑定手机号
     * @return
     */
    public Observable<BindPhoneBean> bindphone(String countryCode, String phoneNum, String verifycode, String password){
        return membercontrollerApi.boundModileUsingPOST(countryCode,phoneNum,verifycode,password)
                .map(new Func1<SingleResponseOfThirdPartyBoundModileBO, BindPhoneBean>() {
                    @Override
                    public BindPhoneBean call(SingleResponseOfThirdPartyBoundModileBO pageResponseOfUserCouponBO) {
                        if (pageResponseOfUserCouponBO.getSuccess()){
                            return (PropertiesUtils.copyBeanProperties(pageResponseOfUserCouponBO.getItem(), BindPhoneBean.class));
                        }else{
                            return toErrorBean(pageResponseOfUserCouponBO.getError(),pageResponseOfUserCouponBO.getErrorDescription(),BindPhoneBean.class);
                        }
                    }
                });
    }
    /**
     * 解绑
     * @return
     */
    public Observable<BaseBean> unbundledThirdPartyUsingPOST(String type){
        return membercontrollerApi.unbundledThirdPartyUsingPOST(type)
                .map(new Func1<SimpleResponse, BaseBean>() {
                    @Override
                    public BaseBean call(SimpleResponse simpleResponse) {
                        return  toBaseBean(simpleResponse);
                    }
                });
    }
    /**
     * 获取我的钱包
     * @return
     */
    public Observable<WalletBean> getMyWallet(Long time){
        return membercontrollerApi.depositLogUsingGET(time)
                .map(new Func1<PageResponseOfAPIUserDepositPayLogBo, WalletBean>() {
                    @Override
                    public WalletBean call(PageResponseOfAPIUserDepositPayLogBo pageResponseOfUserDepositLogBO) {
                        if (pageResponseOfUserDepositLogBO.getSuccess()){
                            return new WalletBean(PropertiesUtils.copyBeanListProperties(pageResponseOfUserDepositLogBO.getContent(), WalletItemData.class));
                        }else{
                            return toErrorBean(pageResponseOfUserDepositLogBO.getError(),pageResponseOfUserDepositLogBO.getErrorDescription(),WalletBean.class);
                        }
                    }
                });
    }
    /**
     * 获取押金金额
     *
     * @return
     */
    public Observable<ChergeBean> getCherge() {
        return membercontrollerApi.getDepositUsingGET()
                .map(new Func1<SingleResponseOfAPIDepositBO, ChergeBean>() {
                    @Override
                    public ChergeBean call(SingleResponseOfAPIDepositBO singleResponseOfdouble) {
                        if (singleResponseOfdouble.getSuccess()) {
                            return PropertiesUtils.copyBeanProperties(singleResponseOfdouble.getItem(),ChergeBean.class);
                        } else {
                            return toErrorBean(singleResponseOfdouble.getError(), singleResponseOfdouble.getErrorDescription(), ChergeBean.class);
                        }

                    }
                });
    }
    /**
     * 退押金
     * @return
     */
    public Observable<BaseBean> repounDisposit(){
        return membercontrollerApi.refundDepositUsingPOST()
                .map(new Func1<SimpleResponse, BaseBean>() {
                    @Override
                    public BaseBean call(SimpleResponse simpleResponse) {
                        return  toBaseBean(simpleResponse);
                    }
                });
    }
    /**
     * 充值押金
     *
     * @param payType
     * @return
     */
    public Observable<PayDetails> recharge(String payType) {
        return membercontrollerApi.paymetUsingPOST(payType)
                .map(new Func1<SingleResponseOfPayDetailsBO, PayDetails>() {
                    @Override
                    public PayDetails call(SingleResponseOfPayDetailsBO singleResponseOfWeiXinPaymetBO) {
                        return copyObjectByGson(singleResponseOfWeiXinPaymetBO.getItem(), PayDetails.class);
                    }
                });
    }
    /**
     * 缴纳基础会员费
     *
     * @param payType
     * @return
     */
    public Observable<PayDetails> paymetBasicCostUsing(String payType) {
        return membercontrollerApi.paymetBasicCostUsingPOST(payType)
                .map(new Func1<SingleResponseOfPayDetailsBO, PayDetails>() {
                    @Override
                    public PayDetails call(SingleResponseOfPayDetailsBO singleResponseOfWeiXinPaymetBO) {
                        return copyObjectByGson(singleResponseOfWeiXinPaymetBO.getItem(), PayDetails.class);
                    }
                });
    }
    /**
     * 获取积分列表
     */
    public Observable<IntegralListBean> findIntegralListUsingGET(Long ltTime,String type) {
        return membercontrollerApi.findIntegralListUsingGET(ltTime,type)
                .map(new Func1<PageResponseOfAPIIntegralLogBO, IntegralListBean>() {
                    @Override
                    public IntegralListBean call(PageResponseOfAPIIntegralLogBO pageResponseOfAPIIntegralLogBO) {
                        if (pageResponseOfAPIIntegralLogBO.getSuccess()){
                            return new IntegralListBean(PropertiesUtils.copyBeanListProperties(pageResponseOfAPIIntegralLogBO.getContent(),IntegralLogBean.class));
                        }else {
                            return toErrorBean(pageResponseOfAPIIntegralLogBO.getError(),pageResponseOfAPIIntegralLogBO.getErrorDescription(),IntegralListBean.class);
                        }
                    }
                });
    }
    /**
     * 获取余额
     */
    public Observable<UserMoneyBean> getMyBalanceUsingGET() {
        return membercontrollerApi.getMyBalanceUsingGET()
                .map(new Func1<SingleResponseOfAPIUserMoneyBO, UserMoneyBean>() {
                    @Override
                    public UserMoneyBean call(SingleResponseOfAPIUserMoneyBO pageResponseOfAPIIntegralLogBO) {
                        if (pageResponseOfAPIIntegralLogBO.getSuccess()){
                            return PropertiesUtils.copyBeanProperties(pageResponseOfAPIIntegralLogBO.getItem(),UserMoneyBean.class);
                        }else{
                            return toErrorBean(pageResponseOfAPIIntegralLogBO.getError(),pageResponseOfAPIIntegralLogBO.getErrorDescription(),UserMoneyBean.class);
                        }
                    }
                });
    }
    /**
     * 获取问题反馈问题列表
     * @param type
     * @return
     */
    public Observable<FeedbackTypeListBean> getFeedBackTypeList(String type){
        return membercontrollerApi.findFeedbackTypeListUsingGET(type)
                .map(new Func1<ListResponseOfFeedbackTypeBO, FeedbackTypeListBean>() {
                    @Override
                    public FeedbackTypeListBean call(ListResponseOfFeedbackTypeBO listResponseOfFeedbackTypeBO) {
                        if (listResponseOfFeedbackTypeBO.getSuccess()){
                            return new FeedbackTypeListBean(PropertiesUtils.copyBeanListProperties(listResponseOfFeedbackTypeBO.getItems(), FeedbackTypeListData.class));
                        }else{
                            return toErrorBean(listResponseOfFeedbackTypeBO.getError(),listResponseOfFeedbackTypeBO.getErrorDescription(),FeedbackTypeListBean.class);
                        }
                    }
                });
    }
    /**
     * 问题反馈
     * @return
     */
    public Observable<BaseBean> feedback(FeedBackUpLoadBean bean){
        FeedbackVO feedbackVO = new FeedbackVO();
        feedbackVO.setFeedDesc(bean.getFeedDesc());
        feedbackVO.setImgUrl1(bean.getImgUrl1());
        feedbackVO.setImgUrl2(bean.getImgUrl2());
        feedbackVO.setImgUrl3(bean.getImgUrl3());
        feedbackVO.setImgUrl4(bean.getImgUrl4());
        feedbackVO.setSysCode(bean.getSysCode());
        feedbackVO.setTypeId(bean.getTypeId());
        feedbackVO.setOrderCode(bean.getOrderCode());
        return membercontrollerApi.feedbackUsingPOST(feedbackVO)
                .map(new Func1<SimpleResponse, BaseBean>() {
                    @Override
                    public BaseBean call(SimpleResponse simpleResponse) {
                        return  toBaseBean(simpleResponse);
                    }
                });
    }

}

