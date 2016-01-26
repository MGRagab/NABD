package com.appventure.nabd.CustomViews;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import com.appventure.nabd.helpers.StaticMethods;

/**
 * Created by macbookpro on 15-12-08.
 */
public class GESSBoldTextView extends TextView {
    public GESSBoldTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    public GESSBoldTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);

    }

    public GESSBoldTextView(Context context) {
        super(context);
        init(null);
    }

    private void init(Context context) {

        setTypeface(StaticMethods.getBoldFont(context));

    }

}
