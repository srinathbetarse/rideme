package com.dazito.android.rideme.gui.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.dazito.android.rideme.R;
import com.dazito.android.rideme.webservices.dazito.request.Feedback;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the

 * to handle interaction events.
 * Use the {@link FeedbackDialogFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FeedbackDialogFragment extends DialogFragment implements DialogInterface.OnClickListener {

    private final static String TAG = FeedbackDialogFragment.class.getSimpleName();

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private TextView mEmail;
    private TextView mName;
    private TextView mMessage;

    private FeedbackDialogCommunicator mFeedbackDialogCommunicator;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment SuggestionsDialogFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FeedbackDialogFragment newInstance() {
        FeedbackDialogFragment fragment = new FeedbackDialogFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public FeedbackDialogFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        View view = inflater.inflate(R.layout.fragment_feedback_dialog, container, false);
//
//        return view;
//    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_feedback_dialog, null);

        mName = (TextView) view.findViewById(R.id.name);
        mMessage = (TextView) view.findViewById(R.id.message);
        mEmail = (TextView) view.findViewById(R.id.email);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setNegativeButton("Cancel", this );
        builder.setPositiveButton("Send", this);

        if(savedInstanceState != null) {

        }

        builder.setView(view);
        return builder.create();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            mFeedbackDialogCommunicator = (FeedbackDialogCommunicator) activity;
        }
        catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement FeedbackDialogCommunicator");
        }
    }

    @Override
    public void onDetach() {
        mFeedbackDialogCommunicator = null;
        super.onDetach();
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        if(which == -1) { // Send
            Feedback feedback = new Feedback("adasdsad", mName.getText().toString(), mEmail.getText().toString(), mMessage.getText().toString());
            mFeedbackDialogCommunicator.sendFeedback(feedback);
        }
        else if (which == -2) { // Cancel
            return;
        }
    }

//    private void showAlertDialog(String errorMessage) {
//        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//        builder.setNegativeButton("Ok", this);
//        builder.setMessage(errorMessage);
//        builder.show();
//    }

    // TODO: Rename method, update argument and hook method into UI event
//    public void onButtonPressed(Uri uri) {
//        if (mListener != null) {
//            mListener.onFragmentInteraction(uri);
//        }
//    }

//    @Override
//    public void onAttach(Activity activity) {
//        super.onAttach(activity);
//        try {
//            mListener = (OnFragmentInteractionListener) activity;
//        } catch (ClassCastException e) {
//            throw new ClassCastException(activity.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
//    }

//    @Override
//    public void onDetach() {
//        super.onDetach();
//        mListener = null;
//    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface FeedbackDialogCommunicator {
        // TODO: Update argument type and name
        public void sendFeedback(Feedback feedback);
    }

}
