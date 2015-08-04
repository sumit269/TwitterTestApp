package com.testapp.twittertestapp.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.testapp.twittertestapp.R;
import com.testapp.twittertestapp.base.BaseActivity;
import com.testapp.twittertestapp.fragments.MainActivityFragment;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
        }

        //DrawerLayout can be added here for navigation drawer purposes

        if (savedInstanceState == null) {
            Fragment fragment = new MainActivityFragment();
            getSupportFragmentManager().beginTransaction().add(R.id.fragmentContainer, fragment).commit();
        }
    }
}
