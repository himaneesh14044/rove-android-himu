package com.gursimransinghhanspal.rove.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Window;
import android.widget.EditText;

import com.gursimransinghhanspal.rove.R;
import com.gursimransinghhanspal.rove.misc.MakeDiaryDialogInterface;

public class EditDiary extends Dialog {

	private Context mActivityContext;
	private MakeDiaryDialogInterface mActivityInterface;

	private EditText mTitleEditText;

	public EditDiary(@NonNull Context context) {
		super(context, true, null);

		mActivityContext = context;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.dialog_edit_diary);


	}
}
