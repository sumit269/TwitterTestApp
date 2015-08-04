package com.testapp.twittertestapp.fragments;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import com.testapp.twittertestapp.R;
import com.testapp.twittertestapp.TokenWrapper;
import com.testapp.twittertestapp.adapters.SpinnerAdapter;
import com.testapp.twittertestapp.adapters.TweetsAdapter;
import com.testapp.twittertestapp.base.BaseActivity;
import com.testapp.twittertestapp.base.BaseFragment;
import com.testapp.twittertestapp.models.Tweets;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends BaseFragment {

    private String base64Encoded = null;
    private String access_token = null;

    private RecyclerView mRecyclerView;

    protected LinearLayoutManager mLayoutManager;
    protected TweetsAdapter mAdapter;

    public MainActivityFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((BaseActivity) getActivity()).setActionBarTitle(R.string.hipages_tweets);
    }

    private List<String> getSpinnerItems() {
        List<String> listItems = new ArrayList<>();
        listItems.add("@hipages");
        listItems.add("#hipages");
        listItems.add("hipages");
        return listItems;
    }

    private void setupToolBar(View view) {
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);

        View spinnerContainer = LayoutInflater.from(getActivity()).inflate(R.layout.toolbar_spinner, toolbar, false);
        ActionBar.LayoutParams lp = new ActionBar.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        toolbar.addView(spinnerContainer, lp);

        SpinnerAdapter spinnerAdapter = new SpinnerAdapter(getActivity());
        spinnerAdapter.addItems(getSpinnerItems());

        final Spinner spinner = (Spinner) spinnerContainer.findViewById(R.id.toolbar_spinner);
        spinner.setAdapter(spinnerAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                getTweets(getSpinnerItems().get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        ((BaseActivity) getActivity()).setSupportActionBar(toolbar);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);

        mLayoutManager = new LinearLayoutManager(getActivity());
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setHasFixedSize(true);

        mAdapter = new TweetsAdapter(getActivity());
        setupToolBar(view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        try {
            String encodedConsumerKey = URLEncoder.encode(getActivity().getResources().getString(R.string.api_key), "UTF-8");
            String encodedConsumerSecret = URLEncoder.encode(getActivity().getResources().getString(R.string.api_secret), "UTF-8");
            String authString = encodedConsumerKey + ":" + encodedConsumerSecret;
            base64Encoded = Base64.encodeToString(authString.getBytes("UTF-8"), Base64.NO_WRAP);

        } catch (UnsupportedEncodingException ex) {

        }
        getBearerToken();
    }

    private void getBearerToken() {
        ((BaseActivity) getActivity()).showProgressDialog(true);
        twitterService.getBearerToken("Basic " + base64Encoded, "client_credentials", new Callback<TokenWrapper>() {

            @Override
            public void success(TokenWrapper tokenWrapper, Response response) {
                access_token = tokenWrapper.getAccess_token();
                getTweets("@hipages");
            }

            @Override
            public void failure(RetrofitError error) {
                Log.e("TAG", error.getMessage());
            }
        });
    }

    public void getTweets(final String searchParam) {
        ((BaseActivity) getActivity()).showProgressDialog(true);
        twitterService.getTweets("Bearer " + access_token, searchParam, new Callback<Tweets>() {
            @Override
            public void success(Tweets successWrapper, Response response) {
                mAdapter.setTweetDataSet(successWrapper);
                mAdapter.notifyDataSetChanged();
                mRecyclerView.setAdapter(mAdapter);
                ((BaseActivity) getActivity()).showProgressDialog(false);
                Toast.makeText(getActivity(), "Got Tweets for " + searchParam, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void failure(RetrofitError error) {
                Log.e("TAG", error.getMessage());
                Toast.makeText(getActivity(), "Error in getting Tweets: " + error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
