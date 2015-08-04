package com.testapp.twittertestapp.fragments;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.testapp.twittertestapp.R;
import com.testapp.twittertestapp.adapters.SpinnerAdapter;
import com.testapp.twittertestapp.adapters.TweetsAdapter;
import com.testapp.twittertestapp.base.BaseActivity;
import com.testapp.twittertestapp.base.BaseFragment;
import com.testapp.twittertestapp.models.Tweets;
import com.testapp.twittertestapp.rest.model.TokenWrapper;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends BaseFragment {

    private String base64Encoded = null;
    private String access_token = null;

    private RecyclerView mRecyclerView;

    private LinearLayoutManager mLayoutManager;
    private TweetsAdapter mAdapter;
    private FrameLayout mSearchView;
    private EditText searchEditText;
    private ImageView searchButton;

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
        listItems.add("Custom");
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
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                              @Override
                                              public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                                  if (access_token != null) {
                                                      if (position < getSpinnerItems().size() - 1) {
                                                          getTweets(access_token, getSpinnerItems().get(position));
                                                          mSearchView.setVisibility(View.GONE);
                                                      } else if (position == getSpinnerItems().size() - 1) {
                                                          mSearchView.setVisibility(View.VISIBLE);
                                                      }
                                                  }
                                              }

                                              @Override
                                              public void onNothingSelected(AdapterView<?> parent) {

                                              }
                                          }

        );
        spinner.setAdapter(spinnerAdapter);
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
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        mSearchView = (FrameLayout) view.findViewById(R.id.searchLayout);
        searchEditText = (EditText) mSearchView.findViewById(R.id.searchEditText);
        searchButton = (ImageView) mSearchView.findViewById(R.id.searchButton);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (searchEditText.getText().toString().trim().isEmpty()) {
                    Toast.makeText(getActivity(), "Search string cannot be empty", Toast.LENGTH_SHORT).show();
                } else {
                    hideKeyboard();
                    mSearchView.setVisibility(View.GONE);
                    getTweets(access_token, searchEditText.getText().toString().trim());

                }
            }
        });
        mSearchView.setVisibility(View.GONE);

        setupToolBar(view);

        mAdapter = new TweetsAdapter(getActivity());
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
        twitterService.getBearerToken("Basic " + base64Encoded, "client_credentials")
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<TokenWrapper>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable throwable) {
                        Toast.makeText(getActivity(), "Got Error: " + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNext(TokenWrapper tokenWrapper) {
                        access_token = tokenWrapper.getAccess_token();
                        getTweets(access_token, "@hipages");
                    }
                });

    }

    public void getTweets(String access_token, final String searchParam) {
        ((BaseActivity) getActivity()).showProgressDialog(true);

        twitterService.getTweets("Bearer " + access_token, searchParam)
                .observeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Tweets>() {
                    @Override
                    public void call(Tweets tweets) {
                        mAdapter.setTweetDataSet(tweets);
                        mAdapter.notifyDataSetChanged();
                        mRecyclerView.setAdapter(mAdapter);
                        ((BaseActivity) getActivity()).showProgressDialog(false);
                        Toast.makeText(getActivity(), "Got Tweets for " + searchParam, Toast.LENGTH_SHORT).show();
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Toast.makeText(getActivity(), "Got Error: " + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }
}
