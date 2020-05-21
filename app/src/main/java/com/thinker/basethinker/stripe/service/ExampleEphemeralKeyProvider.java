package com.thinker.basethinker.stripe.service;

import android.support.annotation.NonNull;
import android.support.annotation.Size;
import android.util.Log;

import com.stripe.android.EphemeralKeyProvider;
import com.stripe.android.EphemeralKeyUpdateListener;
import com.thinker.basethinker.api.APIControllerFactory;
import com.thinker.basethinker.api.controller.StripeController;
import com.thinker.basethinker.mvp.BaseBean;
import com.thinker.basethinker.utils.LogUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;
import vc.thinker.colours.client.api.StripecontrollerApi;

/**
 * An implementation of {@link EphemeralKeyProvider} that can be used to generate
 * ephemeral keys on the backend.
 */
public class ExampleEphemeralKeyProvider implements EphemeralKeyProvider {

    private @NonNull
    CompositeSubscription mCompositeSubscription;
    private @NonNull
    StripecontrollerApi mStripeService;
    private @NonNull
    ProgressListener mProgressListener;
    private StripeController stripeController = APIControllerFactory.getStripeController();
    public ExampleEphemeralKeyProvider(@NonNull ProgressListener progressListener) {
        Log.d("farley","create ExampleEphemeralKeyProvider");
//        Retrofit retrofit = RetrofitFactory.getInstance();
//        mStripeService = retrofit.create(StripeService.class);

        mCompositeSubscription = new CompositeSubscription();
        mProgressListener = progressListener;
    }

    @Override
    public void createEphemeralKey(@NonNull @Size(min = 4) String apiVersion,
                                   @NonNull final EphemeralKeyUpdateListener keyUpdateListener) {
        Map<String, String> apiParamMap = new HashMap<>();
        apiParamMap.put("api_version", apiVersion);
        Log.d("farley","apiVersion="+apiVersion);
        mCompositeSubscription.add(

                stripeController.getEphemeralKeyUsingPOST(apiVersion)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action1<BaseBean>() {
                            @Override
                            public void call(BaseBean response) {
                                if (response.getError_code() == 0) {
                                    String rawKey = response.getResult();
                                    keyUpdateListener.onKeyUpdate(rawKey);
                                    mProgressListener.onStringResponse(rawKey);
                                }else{
                                    LogUtils.e(response.getResult());
                                }
                            }
                        }, new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {
                                mProgressListener.onStringResponse(throwable.getMessage());
                            }
                        }));
    }


    public interface ProgressListener {
        void onStringResponse(String string);
    }
}
