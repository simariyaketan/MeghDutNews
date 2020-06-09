package com.meghdut.news.viewpager;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.viewpager.widget.ViewPager;

public class UninterceptableViewPager extends ViewPager {

    public UninterceptableViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean ret = super.onInterceptTouchEvent(ev);
        if (ret)
            getParent().requestDisallowInterceptTouchEvent(true);
        return ret;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        boolean ret = super.onTouchEvent(ev);
        if (ret)
            getParent().requestDisallowInterceptTouchEvent(true);
        return ret;
    }
}
