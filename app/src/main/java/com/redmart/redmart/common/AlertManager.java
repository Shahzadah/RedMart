package com.redmart.redmart.common;

import android.app.AlertDialog;
import android.content.Context;
import android.view.View;

import com.redmart.redmart.R;

/**
 * This class stands for displaying Alerts for Errors.
 */
public class AlertManager {

    /**
     * This method will show Error
     *
     * @param context      : Context for Alert
     * @param header       : Header text
     * @param description  : Description text
     */
    public static AlertDialog showError(final Context context, String header, String description) {
        return showError(context, header, description, null);
    }

    /**
     * This method will show Error
     *
     * @param context      : Context for Alert
     * @param header       : Header text
     * @param description  : Description text
     * @param clickListener: OK button click listener
     */
    public static AlertDialog showError(final Context context, String header, String description, final View.OnClickListener clickListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(false);
        final AlertDialog alert = builder.create();
        DialogHelper viewHelper = new DialogHelper(context, header, description, context.getString(R.string.ok), new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alert.dismiss();
                if(clickListener != null){
                    clickListener.onClick(view);
                }
            }
        });
        alert.setView(viewHelper.getDialogView(), 0, 0, 0, 0);
        alert.show();
        return alert;
    }

    /**
     * This method will open the alert dialog and will give the callback on click of buttons.
     *
     * @param context : Context
     * @param header : Header
     * @param description : Description
     * @param clickListener : Click Listener
     */
    public static AlertDialog showConfirmation(final Context context, String header, String description, final OnDialogClickListener clickListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(false);
        final AlertDialog alert = builder.create();
        DialogHelper viewHelper = new DialogHelper(context,
                header, description, context.getString(R.string.no), context.getString(R.string.yes), new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alert.dismiss();
                if(clickListener != null) {
                    clickListener.onClickNo();
                }
            }
        }, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alert.dismiss();
                if(clickListener != null) {
                    clickListener.onClickYes();
                }
            }
        });
        alert.setView(viewHelper.getDialogView(), 0, 0, 0, 0);
        alert.show();
        return alert;
    }

    public interface OnDialogClickListener {
        void onClickYes();

        void onClickNo();
    }
}