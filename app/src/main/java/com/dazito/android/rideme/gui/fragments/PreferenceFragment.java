package com.dazito.android.rideme.gui.fragments;

import android.app.FragmentManager;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.util.Log;

import com.dazito.android.rideme.R;

/**
 * Created by Pedro on 27-02-2015.
 */
public class PreferenceFragment extends android.preference.PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener, Preference.OnPreferenceClickListener {

    private static final String TAG = PreferenceFragment.class.getSimpleName();

    private EditTextPreference mMaxRadius;
    private SharedPreferences mSharedPreferences;
    private CheckBoxPreference mCheckBoxPreference;
    private Preference mFeedback;

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
        addPreferencesFromResource(R.xml.preferences);

        mMaxRadius = (EditTextPreference) findPreference(getResources().getString(R.string.pref_radius_key));

        mSharedPreferences = getPreferenceManager().getSharedPreferences();
        String maxRadius = mSharedPreferences.getString(getResources().getString(R.string.pref_radius_key), "Unknown");
        mMaxRadius.setSummary("Max Radius: " + maxRadius);

        mCheckBoxPreference = (CheckBoxPreference) findPreference(getString(R.string.pref_order_list_key));

        mFeedback = findPreference(getString(R.string.pref_feedback));

        mFeedback.setOnPreferenceClickListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        Log.d(TAG, "onSharedPreferenceChanged");
        Log.d(TAG, "key parameter: " + key);
        if (key.equalsIgnoreCase(getResources().getString(R.string.pref_radius_key))) {
            Preference connectionPref = findPreference(key);
            // Set summary to be the user-description for the selected value
            connectionPref.setSummary(sharedPreferences.getString(key, "Unknown") + " km");
        }
        else if (key.equalsIgnoreCase(getString(R.string.pref_order_list_key))) {
            Preference orderByPreference = findPreference(key);
        }
    }



    @Override
    public void onResume() {
        super.onResume();
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public boolean onPreferenceClick(Preference preference) {
        sendSuggestion();
        return true;
    }

    private void sendSuggestion() {
        FragmentManager fragmentManager = getFragmentManager();
        FeedbackDialogFragment feedbackDialogFragment = FeedbackDialogFragment.newInstance();
        feedbackDialogFragment.show(fragmentManager, FeedbackDialogFragment.class.getSimpleName());
    }
}
