package com.xy.kt.txt;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Author: Z.T on 2016/12/9.
 * Describe:
 */

public class Atext extends TextView {
    public Atext(Context context) {
        super(context);
    }

    public Atext(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public Atext(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        super.setText(text, type);
    }
}
