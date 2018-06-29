package com.bt.carousel.carousel.Carousel.base;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.widget.FrameLayout;

/**
 * Created by btONF on 2018/6/29.
 */

public abstract class ViewPagerBaseView  extends ViewPager {

    //接口 >> 传入indicator实现的接口
    private ViewPagerScrollListener mViewPagerScrollListener;
    public FrameLayout.LayoutParams params;
    public int count = 0;
    public ViewPagerBaseView(Context context) {
        this(context, null);
    }

    public ViewPagerBaseView(Context context, AttributeSet attrs) {
        super(context, attrs);
        addOnPageChangeListener(mOnPageChangeListener);
    }

    private OnPageChangeListener mOnPageChangeListener = new OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            if (mViewPagerScrollListener!=null) {
                mViewPagerScrollListener.onPageSelected(position, count);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            //伪循环
            if (state == SCROLL_STATE_IDLE && count > 2) {
                if (getCurrentItem() == 0 ) {
                    setCurrentItem(count - 2, false);
                }
                if (getCurrentItem() == count - 1) {
                    setCurrentItem(1, false);
                }

            }
        }
    };

    public interface ViewPagerScrollListener {
        void onPageSelected(int position, int childCount);
    }

    public void setViewPagerScrollListener(ViewPagerScrollListener viewPagerScrollListener) {
        mViewPagerScrollListener = viewPagerScrollListener;
    }
    //绑定适配器
    public void setViewPager(PagerAdapter baseAdapter){
        setAdapter(baseAdapter);
        updateView(baseAdapter.getCount());
    }
    //构造params
    public abstract ViewPagerBaseView build();
    //更新内部视图
    public abstract ViewPagerBaseView updateView(int newCount);


}
