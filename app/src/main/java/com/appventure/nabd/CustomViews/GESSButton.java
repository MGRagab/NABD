package com.appventure.nabd.CustomViews;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;

import com.appventure.nabd.helpers.StaticMethods;

/**
 * Created by macbookpro on 15-12-08.
 */
public class GESSButton extends Button {
    public GESSButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    public GESSButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);

    }

    public GESSButton(Context context) {
        super(context);
        init(null);
    }

    private void init(Context context) {

        setTypeface(StaticMethods.getlightFont(context));

    }
}
