package com.adhamenaya.moviesapp.utils;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by AENAYA on 7/12/2015.
 */
public class RectangleImageView extends ImageView {

    public RectangleImageView(Context context){
        super(context);
    }

    public RectangleImageView(Context context,AttributeSet attSet){
        super(context,attSet);
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        //Set the height to be the double of the width
        setMeasuredDimension(getMeasuredWidth(),getMeasuredWidth()*2);
    }
}
