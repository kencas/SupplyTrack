package com.supplytrack.ui;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.Window;
import android.widget.TextView;

import com.supplytrack.R;

public class CustomProgressDialog
{
    public static Dialog getDialog(Context activity, String title)
    {
        final Dialog dialog = new Dialog(activity, R.style.AlertDialogTheme);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.info_dialog);
        dialog.setTitle(title);
        dialog.setCancelable(true);

        TextView tv = (TextView) dialog.findViewById(R.id.message);

        tv.setText(title);


        return dialog;
    }
}
