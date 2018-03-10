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

public class SignUpEmail extends Fragment {
	// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
	private static final String ARG_FRAGMENT_ID = "com.gursimransinghhanspal.rove.fragment.SignUpEmail.fragmentId";
	private static final String ARG_EMAIL = "com.gursimransinghhanspal.rove.fragment.SignUpEmail.email";

	// TODO: Rename and change types of parameters
	private CreateAccount.FragmentIdentifier mFragmentIdentifier;
	private String mEmail;

	private View mBackButton;
	private View mNextButton;
	private EditText mEmailInputField;

	private OnFragmentInteractionListener mListener;

	public SignUpEmail() {
		// Required empty public constructor
	}

	// TODO: Rename and change types and number of parameters
	public static SignUpEmail newInstance(CreateAccount.FragmentIdentifier fragmentIdentifier, String email) {
		SignUpEmail fragment = new SignUpEmail();

		Bundle args = new Bundle();
		args.putSerializable(ARG_FRAGMENT_ID, fragmentIdentifier);
		args.putString(ARG_EMAIL, email);
		fragment.setArguments(args);

		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (getArguments() != null) {
			mFragmentIdentifier = (CreateAccount.FragmentIdentifier) getArguments().getSerializable(ARG_FRAGMENT_ID);
			mEmail = getArguments().getString(ARG_EMAIL);
		}
	}

	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View fragmentView = inflater.inflate(R.layout.fragment_sign_up_email, container, false);

		mBackButton = fragmentView.findViewById(R.id.btn_back);
		mNextButton = fragmentView.findViewById(R.id.btn_next);
		mEmailInputField = fragmentView.findViewById(R.id.fragmentSignUpEmail_emailInput_editText);

		mBackButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (mListener == null)
					return;

				readFields();
				mListener.onBackButtonPressed(mFragmentIdentifier, mEmail);
			}
		});
		mNextButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (mListener == null)
					return;

				readFields();
				mListener.onNextButtonPressed(mFragmentIdentifier, mEmail);
			}
		});

		mEmailInputField.setText(mEmail);

		return fragmentView;
	}

	public void setOnInteractionListener(SignUpEmail.OnFragmentInteractionListener interactionListener) {
		this.mListener = interactionListener;
	}

	@Override
	public void onDetach() {
		super.onDetach();
		mListener = null;
	}

	private void readFields() {
		mEmail = mEmailInputField.getText().toString();
	}

	public interface OnFragmentInteractionListener {
		void onBackButtonPressed(CreateAccount.FragmentIdentifier identifier, String email);

		void onNextButtonPressed(CreateAccount.FragmentIdentifier identifier, String email);
	}
}
