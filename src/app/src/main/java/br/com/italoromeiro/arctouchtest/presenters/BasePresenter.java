package br.com.italoromeiro.arctouchtest.presenters;

import android.util.Log;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import br.com.italoromeiro.arctouchtest.presenters.actions.NetworkAction;
import br.com.italoromeiro.arctouchtest.rest.RestController;
import br.com.italoromeiro.arctouchtest.views.activities.BaseActivity;

/**
 * Created by italo on 08/08/16.
 */
@EBean
public class BasePresenter {
    private static final String TAG = BasePresenter.class.getSimpleName();

    @RootContext
    BaseActivity activity;

    @Bean
    RestController api;

    private NetworkAction mRetriable;

    public BasePresenter() {
    }

    @Background
    public void executeAction(NetworkAction retriable) {
        mRetriable = retriable;
        treatAction();
    }

    private void treatAction() {
        try {
            mRetriable.doBeforeAction();
        } catch (Throwable doBeforeActionError) {
            Log.e(TAG, doBeforeActionError.getMessage(), doBeforeActionError);
        }

        try {
            mRetriable.doAction();
        } catch (Throwable doActionError) {
            Log.e(TAG, doActionError.getMessage(), doActionError);
            try {
                mRetriable.doError(doActionError);
            } catch (Throwable doErrorError) {
                Log.e(TAG, doErrorError.getMessage(), doErrorError);
            }
        }

        try {
            mRetriable.doAfterAction();
        } catch (Throwable doAfterActionError) {
            Log.e(TAG, doAfterActionError.getMessage(), doAfterActionError);
        }
    }

    public void showDialogProgress() {
        activity.showDialogProgress();
    }

    public void dismissDialogProgress() {
        activity.dismissDialogProgress();
    }
}
