package com.xyz.php.utils;

import android.support.design.widget.Snackbar;
import android.view.View;

/**
 * 18/10/2017
 */

public class SnackbarUtils {

    public static void simple(View view, String msg, String action) {
        final Snackbar snackbar = Snackbar.make(view, msg, Snackbar.LENGTH_SHORT);
        snackbar.setAction(action, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                snackbar.dismiss();
            }
        });
        snackbar.show();
    }

    public static void simple(View view, String msg) {
        simple(view, msg, "OK");
    }
}
