package com.redmart.redmart.common;

import android.content.Context;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.redmart.redmart.R;

/**
 * Helper class for Alert based on the single and Multiple button variations.
 */
class DialogHelper {

    private Context mContext;
    private LayoutInflater inflater;
    private View mDialogView;

    private String mHeaderMessage;
    private String mErrorMessage;
    private String mButton1Text;
    private String mButton2Text;
    private View.OnClickListener mButton1ClickListener;
    private View.OnClickListener mButton2ClickListener;

    //Constructor for 1 button
    DialogHelper(Context context, String header, String message, String button1, View.OnClickListener button1ClickListener) {
        mContext = context;
        mHeaderMessage = header;
        mErrorMessage = message;
        mButton1Text = button1;
        mButton1ClickListener = button1ClickListener;

        initializeViewForOneButton();
    }

    //Constructor for 2 buttons
    DialogHelper(Context context, String header, String message, String button1, String button2,
                 View.OnClickListener button1ClickListener, View.OnClickListener button2ClickListener) {
        mContext = context;
        mErrorMessage = message;
        mHeaderMessage = header;
        mButton1Text = button1;
        mButton2Text = button2;
        mButton1ClickListener = button1ClickListener;
        mButton2ClickListener = button2ClickListener;

        initializeViewForTwoButtons();
    }

    /**
     * This method stands for initializing single button
     */
    private void initializeViewForOneButton() {
        inflater = LayoutInflater.from(mContext);
        final ViewGroup nullParent = null;
        mDialogView = inflater.inflate(R.layout.custom_error_dialog, nullParent);

        TextView errorHeaderTv = mDialogView.findViewById(R.id.errorHeaderTextView);
        TextView errorMessageTv = mDialogView.findViewById(R.id.errorMessageTextView);
        mDialogView.findViewById(R.id.errorMessageBtns).setVisibility(View.GONE);
        TextView button1 = mDialogView.findViewById(R.id.okayBtn);
        button1.setVisibility(View.VISIBLE);

        if (!TextUtils.isEmpty(mHeaderMessage)) {
            errorHeaderTv.setVisibility(View.VISIBLE);
            errorHeaderTv.setText(mHeaderMessage);
        } else {
            errorHeaderTv.setVisibility(View.GONE);
        }

        if (!TextUtils.isEmpty(mErrorMessage)) {
            errorMessageTv.setVisibility(View.VISIBLE);
            errorMessageTv.setText(mErrorMessage);
        } else {
            errorMessageTv.setVisibility(View.GONE);
        }
        button1.setTypeface(null, Typeface.BOLD);
        button1.setText(mButton1Text);
        button1.setOnClickListener(mButton1ClickListener);
    }

    /**
     * This method stands for initializing two buttons
     */
    private void initializeViewForTwoButtons() {
        inflater = LayoutInflater.from(mContext);
        final ViewGroup nullParent = null;
        mDialogView = inflater.inflate(R.layout.custom_error_dialog, nullParent);

        TextView errorHeaderTv = mDialogView.findViewById(R.id.errorHeaderTextView);
        TextView errorMessageTv = mDialogView.findViewById(R.id.errorMessageTextView);
        TextView button1 = mDialogView.findViewById(R.id.negativeBtn);
        TextView button2 = mDialogView.findViewById(R.id.positiveBtn);
        mDialogView.findViewById(R.id.okayBtn).setVisibility(View.GONE);

        if (!TextUtils.isEmpty(mHeaderMessage)) {
            errorHeaderTv.setVisibility(View.VISIBLE);
            errorHeaderTv.setText(mHeaderMessage);
        } else {
            errorHeaderTv.setVisibility(View.GONE);
        }

        if (!TextUtils.isEmpty(mErrorMessage)) {
            errorMessageTv.setVisibility(View.VISIBLE);
            errorMessageTv.setText(mErrorMessage);
        } else {
            errorMessageTv.setVisibility(View.GONE);
        }

        button1.setTypeface(null, Typeface.NORMAL);
        button1.setText(mButton1Text);
        button1.setOnClickListener(mButton1ClickListener);
        button2.setTypeface(null, Typeface.BOLD);
        button2.setText(mButton2Text);
        button2.setOnClickListener(mButton2ClickListener);
    }

    /**
     * This method will return the dialog view
     *
     * @return : Dialog View
     */
    View getDialogView() {
        return mDialogView;
    }
}
