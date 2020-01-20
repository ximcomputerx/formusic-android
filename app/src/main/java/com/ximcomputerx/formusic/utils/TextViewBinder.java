package com.ximcomputerx.formusic.utils;

import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;

public class TextViewBinder {

    public static void setTextView(TextView textView, CharSequence content, CharSequence emptyTip) {
        if (!TextUtils.isEmpty(content)) {
            textView.setText(content);
        } else {
            if (!TextUtils.isEmpty(emptyTip)) {
                textView.setText(emptyTip);
            } else {
                textView.setText("");
            }
        }
    }

    public static void setTextView(TextView textView, CharSequence content) {
        setTextView(textView, content, null);
    }

    public static void setTextView(EditText editText, CharSequence content) {
        if (!TextUtils.isEmpty(content)) {
            editText.setText(content);
        } else {
            editText.setText("");
        }
    }

}
