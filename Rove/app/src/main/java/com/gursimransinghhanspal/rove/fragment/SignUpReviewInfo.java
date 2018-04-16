package com.gursimransinghhanspal.rove.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.gursimransinghhanspal.rove.R;
import com.gursimransinghhanspal.rove.activity.ActivityHomeFeed;
import com.gursimransinghhanspal.rove.activity.CreateAccount;

public class SignUpReviewInfo extends Fragment {
	// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
	private static final String ARG_FRAGMENT_ID = "com.gursimransinghhanspal.rove.fragment.SignUpReviewInfo.fragmentId";
	private static final String ARG_FIRST_NAME = "com.gursimransinghhanspal.rove.fragment.SignUpReviewInfo.firstName";
	private static final String ARG_LAST_NAME = "com.gursimransinghhanspal.rove.fragment.SignUpReviewInfo.lastName";
	private static final String ARG_EMAIL = "com.gursimransinghhanspal.rove.fragment.SignUpReviewInfo.email";

	// TODO: Rename and change types of parameters
	private CreateAccount.FragmentIdentifier mFragmentIdentifier;
	private String mFirstName;
	private String mLastName;
	private String mEmail;

	private View mBackButton;
	private View mCompleteSignupButton;
	private TextView mFirstNameTextView;
	private TextView mLastNameTextView;
	private TextView mEmailTextView;

	private OnFragmentInteractionListener mListener;

	public SignUpReviewInfo() {
		// Required empty public constructor
	}

	// TODO: Rename and change types and number of parameters
	public static SignUpReviewInfo newInstance(CreateAccount.FragmentIdentifier fragmentIdentifier, String firstName, String lastName, String email) {
		SignUpReviewInfo fragment = new SignUpReviewInfo();

		Bundle args = new Bundle();
		args.putSerializable(ARG_FRAGMENT_ID, fragmentIdentifier);
		args.putString(ARG_FIRST_NAME, firstName);
		args.putString(ARG_LAST_NAME, lastName);
		args.putString(ARG_EMAIL, email);
		fragment.setArguments(args);

		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (getArguments() != null) {
			mFragmentIdentifier = (CreateAccount.FragmentIdentifier) getArguments().getSerializable(ARG_FRAGMENT_ID);
			mFirstName = getArguments().getString(ARG_FIRST_NAME);
			mLastName = getArguments().getString(ARG_LAST_NAME);
			mEmail = getArguments().getString(ARG_EMAIL);
		}
	}

	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View fragmentView =  inflater.inflate(R.layout.fragment_sign_up_review_info, container, false);

		mBackButton = fragmentView.findViewById(R.id.btn_back);
		mCompleteSignupButton = fragmentView.findViewById(R.id.fragmentSignUpReviewInfo_completeSignUpButton);
		mFirstNameTextView= fragmentView.findViewById(R.id.fragmentSignUpReviewInfo_firstName_textView);
		mLastNameTextView = fragmentView.findViewById(R.id.fragmentSignUpReviewInfo_lastName_textView);
		mEmailTextView = fragmentView.findViewById(R.id.fragmentSignUpReviewInfo_email_textView);

		mFirstNameTextView.setText(mFirstName);
		mLastNameTextView.setText(mLastName);
		mEmailTextView.setText(mEmail);

		mBackButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (mListener == null)
					return;

				mListener.onBackButtonPressed(mFragmentIdentifier);
			}
		});
		mCompleteSignupButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (mListener == null)
				{
					Toast.makeText(getContext(),"mlistener is null",Toast.LENGTH_SHORT).show();
					return;
				}
				Intent intent =  new Intent(getContext(), ActivityHomeFeed.class);
				startActivity(intent);
				mListener.onCompleteSignUpButtonPressed(mFragmentIdentifier);
			}
		});

		return fragmentView;
	}

	public void setOnInteractionListener(SignUpReviewInfo.OnFragmentInteractionListener interactionListener) {
		this.mListener = interactionListener;
	}

	@Override
	public void onDetach() {
		super.onDetach();
		mListener = null;
	}


	public interface OnFragmentInteractionListener {
		void onBackButtonPressed(CreateAccount.FragmentIdentifier identifier);

		void onCompleteSignUpButtonPressed(CreateAccount.FragmentIdentifier identifier);
	}
}
