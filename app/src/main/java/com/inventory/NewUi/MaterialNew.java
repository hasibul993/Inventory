package com.inventory.NewUi;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by PCCS on 9/18/2017.
 */

public class MaterialNew extends TextView {

    private static Typeface sMaterialDesignIcons;

    public MaterialNew(Context context) {
        this(context, null);
    }

    public MaterialNew(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MaterialNew(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        if (isInEditMode()) return;//Won't work in Eclipse graphical layout
        setTypeface();
    }

    private void setTypeface() {
        if (sMaterialDesignIcons == null) {
            sMaterialDesignIcons = Typeface.createFromAsset(getContext().getAssets(), "fonts/MaterialIcons-Regular.ttf");
        }
        setTypeface(sMaterialDesignIcons);
    }
}
