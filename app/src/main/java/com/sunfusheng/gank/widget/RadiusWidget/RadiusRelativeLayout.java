package com.sunfusheng.gank.widget.RadiusWidget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import com.sunfusheng.gank.R;
import com.sunfusheng.gank.util.ViewUtil;

/**
 * @author sunfusheng on 2018/1/20.
 */
public class RadiusRelativeLayout extends RelativeLayout {

    public RadiusRelativeLayout(Context context) {
        this(context, null);
    }

    public RadiusRelativeLayout(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.DefaultAttr);
    }

    public RadiusRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        RadiusGradientDrawable drawable = RadiusGradientDrawable.fromAttributeSet(context, attrs, defStyleAttr);
        ViewUtil.setBackgroundKeepingPadding(this, drawable);
    }

}
