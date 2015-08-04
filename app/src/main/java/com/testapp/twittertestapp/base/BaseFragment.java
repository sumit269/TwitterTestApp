package com.testapp.twittertestapp.base;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.testapp.twittertestapp.TwitterTestApp;
import com.testapp.twittertestapp.models.Error;
import com.testapp.twittertestapp.rest.service.TwitterService;

import retrofit.RetrofitError;

/**
 * Created by SumitBhatia on 3/08/15.
 */
public class BaseFragment extends Fragment {

    protected BaseActivity baseActivity;
    protected TwitterService twitterService;
    protected TwitterTestApp twitterTestApp;

    protected TwitterTestApp getApplication() {
        return (TwitterTestApp) getActivity().getApplication();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        baseActivity = (BaseActivity) getActivity();
        twitterTestApp = ((TwitterTestApp) (baseActivity.getApplication()));
        twitterService = twitterTestApp.getService();

    }

    public Error createNetworkError() {
        return baseActivity.createNetworkError();
    }

    public Error createNotConnectedError() {
        return baseActivity.createNotConnectedError();
    }

    public void handleFailure(Error error) {
        baseActivity.handleFailure(error);
    }

    public void handleFailure(RetrofitError retrofitError) {
        if (retrofitError.getKind() == RetrofitError.Kind.NETWORK) {
            handleFailure(createNetworkError());
        } else {
            handleFailure(createErrorFromResponse(retrofitError));
        }
    }

    public Error createErrorFromResponse(RetrofitError retrofitError) {
        Error error = null;
        try {
            error = (Error) retrofitError.getBodyAs(Error.class);
            if (error == null) {
                error = new Error();
                error.setError(retrofitError.getCause().getMessage());
            }
        } catch (Exception e) {

            e.printStackTrace();
        }
        return error;
    }

    protected void hideKeyboard() {
        View view = getActivity().getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
