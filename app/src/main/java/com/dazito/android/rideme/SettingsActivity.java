package com.dazito.android.rideme;

import android.os.Bundle;
import android.view.MenuItem;

import com.dazito.android.rideme.gui.fragments.FeedbackDialogFragment;
import com.dazito.android.rideme.gui.fragments.PreferenceFragment;
import com.dazito.android.rideme.webservices.dazito.Dazito;
import com.dazito.android.rideme.webservices.dazito.request.Feedback;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Pedro on 27-02-2015.
 */
public class SettingsActivity extends BaseActivity implements FeedbackDialogFragment.FeedbackDialogCommunicator{

    private static final String TAG = SettingsActivity.class.getSimpleName();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pref_with_actionbar);

        // Set the toolbar
        activateToolbarWithHomeEnabled();

        getFragmentManager().beginTransaction().replace(R.id.content_frame, new PreferenceFragment()).commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
            setActivityExitUpDownAnimation();
            return true;
        }
        else {
            return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setActivityExitUpDownAnimation();
    }

    @Override
    public void sendFeedback(Feedback feedback) {
        if(feedback.getMessage().isEmpty()) {
//                showAlertDialog("Please, fill in your message.");
            return;
        }
        Dazito dazito = new Dazito();
        dazito.sendFeedback(feedback, new Callback<Feedback>() {
            @Override
            public void success(Feedback feedback1, Response response) {
                //showAlertDialog("Thank you for your feedback.");
            }

            @Override
            public void failure(RetrofitError error) {
//                    showAlertDialog("Error: " + error.getMessage());
            }
        });
    }
}
