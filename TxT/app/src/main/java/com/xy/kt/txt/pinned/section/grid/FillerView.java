package com.xy.kt.txt.pinned.section.grid;

/**
 * Author: Z.T on 2016/12/9.
 * Describe:
 */

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Simple view to fill space in grid view.
 *
 * @author Tonic Artos
 */
public class FillerView extends LinearLayout {
    private View mMeasureTarget;

    public FillerView(Context context) {
        super(context);
    }

    public void setMeasureTarget(View lastViewSeen) {
        mMeasureTarget = lastViewSeen;
    }

    public FillerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FillerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if(null != mMeasureTarget)
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(
                    mMeasureTarget.getMeasuredHeight(), MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
