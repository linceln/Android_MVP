package com.xyz.php.utils;

import android.text.Selection;
import android.text.Spannable;
import android.widget.EditText;

/**
 * 2017/5/23.
 */

public class CursorUtil {

    /**
     * 将光标放在EditText的最后
     *
     * @param et
     */
    public static void setCursorToTheEnd(EditText et) {
        CharSequence charSequence = et.getText();
        if (charSequence != null) {
            Spannable spanText = (Spannable) charSequence;
            Selection.setSelection(spanText, charSequence.length());
        }
    }
}
