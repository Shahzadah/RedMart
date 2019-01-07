package com.redmart.redmart.common;

import android.app.Dialog;
import android.content.Context;
import android.widget.TextView;

import com.redmart.redmart.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DialogManager {

    private static ProgressDialog mDialog;

    public static ProgressDialog show(Context context) {
        mDialog = new ProgressDialog(context);
        mDialog.show();
        return mDialog;
    }

    public static void dismiss() {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
        }
    }

    public static class ProgressDialog extends Dialog {

        @BindView(R.id.title_text)
        TextView tvTitle;

        @BindView(R.id.content_text)
        TextView tvContent;

        private ProgressDialog(Context context) {
            super(context);
            init();
        }

        private void init() {
            setContentView(R.layout.progress_dialog);
            ButterKnife.bind(this);
        }

        public void setTitle(String title) {
            tvTitle.setText(title);
        }

        public void setContent(String content) {
            tvContent.setText(content);
        }
    }
}