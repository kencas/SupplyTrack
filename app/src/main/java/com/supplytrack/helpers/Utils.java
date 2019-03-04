package com.supplytrack.helpers;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

public class Utils {
    public static void alertView( String message, Context ctx ) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(ctx);
        dialog.setTitle( "Supply Track" )
                //.setIcon(R.drawable.ic_launcher_foreground)
                .setMessage(message)
//     .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//      public void onClick(DialogInterface dialoginterface, int i) {
//          dialoginterface.cancel();
//          }})
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialoginterface, int i) {
                    }
                }).show();
    }
}
