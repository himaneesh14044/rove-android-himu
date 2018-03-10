package com.gursimransinghhanspal.rove.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.gursimransinghhanspal.rove.R;
import com.gursimransinghhanspal.rove.activity.CreateAccount;


public class SignUpName extends Fragment {
	private static final String ARG_FRAGMENT_ID = "com.gursimransinghhanspal.rove.fragment.SignUpName.fragmentId";
	private static final String ARG_FIRST_NAME = "com.gursimransinghhanspal.rove.fragment.SignUpName.firstName";
	private static final String ARG_LAST_NAME = "com.gursimransinghhanspal.rove.fragment.SignUpName.lastName";

	// TODO: Rename and change types of parameters
	private CreateAccount.FragmentIdentifier mFragmentIdentifier;
	private String mFirstName;
	private String mLastName;

	private View mBackButton;
	private View mNextButton;
	private EditText mFirstNameInputField;
	private EditText mLastNameInputField;

	private OnFragmentInteractionListener mListener;

	public SignUpName() {
		// Required empty public constructor
	}


	public static SignUpName newInstance(CreateAccount.FragmentIdentifier fragmentIdentifier, String firstName, String lastName) {
		SignUpName fragment = new SignUpName();

		Bundle args = new Bundle();
		args.putSerializable(ARG_FRAGMENT_ID, fragmentIdentifier);
		args.putString(ARG_FIRST_NAME, firstName);
		args.putString(ARG_LAST_NAME, lastName);
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
		}
	}

	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View fragmentView = inflater.inflate(R.layout.fragment_sign_up_name, container, false);

		mBackButton = fragmentView.findViewById(R.id.btn_back);
		mNextButton = fragmentView.findViewById(R.id.btn_next);
		mFirstNameInputField = fragmentView.findViewById(R.id.fragmentSignUpName_firstNameInput_editText);
		mLastNameInputField = fragmentView.findViewById(R.id.fragmentSignUpName_lastNameInput_editText);

		mBackButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (mListener == null)
					return;

				readFields();
				mListener.onBackButtonPressed(mFragmentIdentifier, mFirstName, mLastName);
			}
		});
		mNextButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (mListener == null)
					return;

				readFields();
				mListener.onNextButtonPressed(mFragmentIdentifier, mFirstName, mLastName);
			}
		});

		mFirstNameInputField.setText(mFirstName);
		mLastNameInputField.setText(mLastName);

		return fragmentView;
	}

	public void setOnInteractionListener(OnFragmentInteractionListener interactionListener) {
		this.mListener = interactionListener;
	}

	@Override
	public void onDetach() {
		super.onDetach();
		mListener = null;
	}

	private void readFields() {
		mFirstName = mFirstNameInputField.getText().toString();
		mLastName = mLastNameInputField.getText().toString();
	}

	public interface OnFragmentInteractionListener {
		void onBackButtonPressed(CreateAccount.FragmentIdentifier identifier, String firstName, String lastName);

		void onNextButtonPressed(CreateAccount.FragmentIdentifier identifier, String firstName, String lastName);
	}
}
