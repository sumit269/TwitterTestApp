package com.testapp.twittertestapp.base;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.testapp.twittertestapp.R;
import com.testapp.twittertestapp.models.Error;

/**
 * Created by SumitBhatia on 3/08/15.
 */
public class BaseActivity extends AppCompatActivity {

    public void setActionBarTitle(int title) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
        }
    }

    public void showProgressDialog(boolean showProgress) {
        ProgressBar progressBar = (ProgressBar) findViewById(R.id.loading_spinner);
        if (progressBar != null) {
            progressBar.setVisibility((showProgress && !progressBar.isShown()) ? View.VISIBLE : View.GONE);
        }
    }

    public Error createNetworkError() {
        Error error = new Error();
        error.setError("Network error - please check your connection and try again.");
        return error;
    }

    public Error createNotConnectedError() {
        Error error = new Error();
        error.setError("Please check your connection and try again.");
        return error;
    }

    public void handleFailure(Error error) {
        if (error == null) {
            return;
        }
        Toast.makeText(this, error.getError(), Toast.LENGTH_SHORT).show();
    }
}
