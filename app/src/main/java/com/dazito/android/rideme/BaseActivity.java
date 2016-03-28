package com.dazito.android.rideme;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;

/**
 * Created by Pedro on 07-02-2015.
 */
public class BaseActivity extends ActionBarActivity {

    private Toolbar mToolbar;

    protected Toolbar activateToolbar() {
        if(mToolbar == null) {
            mToolbar = (Toolbar) findViewById(R.id.app_bar);
            if(mToolbar != null) {
                // Let the toolbar replace the actionbar
                setSupportActionBar(mToolbar);
            }
        }

        return mToolbar;
    }

    // Adds the <- arrow as "Go Back Button"
    protected Toolbar activateToolbarWithHomeEnabled() {
        activateToolbar();

        if(mToolbar != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        return mToolbar;
    }

    public void setActivityEnterUpDownAnimation() {
        overridePendingTransition(R.animator.slide_in_from_bottom, R.animator.slide_out_from_top);
    }

    public void setActivityExitUpDownAnimation() {
        overridePendingTransition(R.animator.slide_in_top, R.animator.slide_out_bottom);
    }

    public void setActivityLeftRightEnterAnimation() {

    }

    public void setActivityLeftRightExitAnimation() {

    }
}