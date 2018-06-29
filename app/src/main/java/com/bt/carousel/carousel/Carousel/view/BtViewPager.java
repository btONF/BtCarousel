package com.bt.carousel.carousel.Carousel.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.bt.carousel.carousel.Carousel.base.ViewPagerBaseView;

/**
 * Created by btONF on 2018/6/29.
 */

public class BtViewPager extends ViewPagerBaseView{
    public BtViewPager(Context context) {
        super(context);
    }

    public BtViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public ViewPagerBaseView build() {
        params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        setLayoutParams(params);
        return this;
    }

    @Override
    public ViewPagerBaseView updateView(int newCount) {
        count = newCount;
        if (count == 0){
            setVisibility(GONE);
        } else if (count == 1) {
            setCurrentItem(0, false);
            setVisibility(VISIBLE);
        } else {
            setCurrentItem(1, false);
            setVisibility(VISIBLE);
        }
        return this;
    }


}
