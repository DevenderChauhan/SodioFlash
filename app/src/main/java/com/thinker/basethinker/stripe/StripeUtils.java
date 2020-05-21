package com.thinker.basethinker.stripe;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.stripe.android.CustomerSession;
import com.stripe.android.PaymentConfiguration;
import com.stripe.android.Stripe;
import com.stripe.android.TokenCallback;
import com.stripe.android.model.Card;
import com.stripe.android.model.Customer;
import com.stripe.android.model.Token;
import com.stripe.android.view.PaymentMethodsActivity;
import com.thinker.basethinker.R;
import com.thinker.basethinker.api.Config;
import com.thinker.basethinker.stripe.service.ExampleEphemeralKeyProvider;
import com.thinker.basethinker.utils.ErrorDialogHandler;

/**
 * Created by thinker on 17/12/13.
 */

public class StripeUtils {
    private Stripe stripe;
    private ErrorDialogHandler mErrorDialogHandler;
    private Activity activity;
    private Card cardToSave;
    public static final int REQUEST_CODE_SELECT_SOURCE = 55;
    public StripeUtils(Activity activity,onMyPayLisener onMyPayLisener){
        this.activity = activity;
        this.onMyPayLisener = onMyPayLisener;
    }
    public StripeUtils(Activity activity,Card cardToSave) {
        this.activity = activity;
        stripe = new Stripe(activity, Config.MYSTRIPEKEY);
        mErrorDialogHandler = new ErrorDialogHandler(activity.getFragmentManager());
        this.cardToSave = cardToSave;
    }

    public void stripePayForCard(){
        if (cardToSave == null) {
            mErrorDialogHandler.showError(0,activity.getString(R.string.invalid));
            return;
        }
        if (!cardToSave.validateCard()) {
            mErrorDialogHandler.showError(0,activity.getString(R.string.auth_failed));
            return;
        }
        stripe.createToken(cardToSave, new TokenCallback() {
            @Override
            public void onError(Exception error) {
                // Show localized error message
                mErrorDialogHandler.showError(0,error.getLocalizedMessage());
            }

            @Override
            public void onSuccess(Token token) {
                // Send token to your server
                Log.d("farley", "token=" + token.toString());
                mErrorDialogHandler.showError(0,"Send token to your server");
            }
        });
    }
    public void initCustomer(){
        CustomerSession.initCustomerSession(
                new ExampleEphemeralKeyProvider(
                        new ExampleEphemeralKeyProvider.ProgressListener() {
                            @Override
                            public void onStringResponse(String string) {
                                Log.d("farley","initCustomerSession="+string);
                                if (string.startsWith("Error: ")) {
                                    mErrorDialogHandler.showError(0,string);
                                }
                            }
                        }));
        CustomerSession.getInstance().retrieveCurrentCustomer(
                new CustomerSession.CustomerRetrievalListener() {
                    @Override
                    public void onCustomerRetrieved(@NonNull Customer customer) {
                        Log.d("farley","onCustomerRetrieved="+customer.getSources().size());
                        if (customer.getSources() != null && customer.getSources().size() > 0){
                            onMyPayLisener.onpay();
                        }else {
                            launchWithCustomer();
                        }
                    }

                    @Override
                    public void onError(int errorCode, @Nullable String errorMessage) {
                        Log.d("farley","onError=");
                    }
                });
    }
    private void launchWithCustomer() {
        Intent payIntent = PaymentMethodsActivity.newIntent(activity);
        activity.startActivityForResult(payIntent, REQUEST_CODE_SELECT_SOURCE);
    }
    public interface onMyPayLisener{
        void onpay();
    }
    private onMyPayLisener onMyPayLisener;

}
