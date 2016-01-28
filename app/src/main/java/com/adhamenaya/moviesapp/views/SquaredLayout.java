package com.adhamenaya.moviesapp.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * Created by AENAYA on 7/12/2015.
 */
public class SquaredLayout extends LinearLayout {

    public SquaredLayout(Context context){
        super(context);
    }

    public SquaredLayout(Context context, AttributeSet attSet){
        super(context,attSet);
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(getMeasuredHeight(),getMeasuredHeight());
    }
}
