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

public class SignUpPassword extends Fragment {
	// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
	private static final String ARG_FRAGMENT_ID = "com.gursimransinghhanspal.rove.fragment.SignUpPassword.fragmentId";
	private static final String ARG_PASSWORD = "com.gursimransinghhanspal.rove.fragment.SignUpPassword.password";

	// TODO: Rename and change types of parameters
	private CreateAccount.FragmentIdentifier mFragmentIdentifier;
	private String mPassword;

	private View mBackButton;
	private View mNextButton;
	private EditText mPasswordInputField;

	private OnFragmentInteractionListener mListener;

	public SignUpPassword() {
		// Required empty public constructor
	}

	public static SignUpPassword newInstance(CreateAccount.FragmentIdentifier fragmentIdentifier, String password) {
		SignUpPassword fragment = new SignUpPassword();

		Bundle args = new Bundle();
		args.putSerializable(ARG_FRAGMENT_ID, fragmentIdentifier);
		args.putString(ARG_PASSWORD, password);
		fragment.setArguments(args);

		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (getArguments() != null) {
			mFragmentIdentifier = (CreateAccount.FragmentIdentifier) getArguments().getSerializable(ARG_FRAGMENT_ID);
			mPassword = getArguments().getString(ARG_PASSWORD);
		}
	}

	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View fragmentView = inflater.inflate(R.layout.fragment_sign_up_password, container, false);

		mBackButton = fragmentView.findViewById(R.id.btn_back);
		mNextButton = fragmentView.findViewById(R.id.btn_next);
		mPasswordInputField = fragmentView.findViewById(R.id.fragmentSignUpPassword_passwordInput_editText);

		mBackButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (mListener == null)
					return;

				readFields();
				mListener.onBackButtonPressed(mFragmentIdentifier, mPassword);
			}
		});
		mNextButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (mListener == null)
					return;

				readFields();
				mListener.onNextButtonPressed(mFragmentIdentifier, mPassword);
			}
		});

		mPasswordInputField.setText(mPassword);

		return fragmentView;
	}

	public void setOnInteractionListener(SignUpPassword.OnFragmentInteractionListener interactionListener) {
		this.mListener = interactionListener;
	}

	@Override
	public void onDetach() {
		super.onDetach();
		mListener = null;
	}

	private void readFields() {
		mPassword = mPasswordInputField.getText().toString();
	}

	public interface OnFragmentInteractionListener {
		void onBackButtonPressed(CreateAccount.FragmentIdentifier identifier, String password);

		void onNextButtonPressed(CreateAccount.FragmentIdentifier identifier, String password);
	}
}
