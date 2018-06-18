package com.bt.carousel.carousel.Carousel.view;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.bt.carousel.carousel.Carousel.CarouselConstant;
import com.bt.carousel.carousel.Carousel.base.IndicatorBaseView;


/**
 * Created by 18030693 on 2018/6/14.
 */

public class DrawableIndicator extends IndicatorBaseView {
    public DrawableIndicator(Context context) {
        super(context);
    }
    private int mIndicatorSelected = 0;
    private int mIndicatorUnselected = 0;

    /**
     *  数据变化时重新载入布局
     */
    @Override
    public void initView() {
        removeAllViews();
        for (int i=0;i<count;i++){
            ImageView imageView = new ImageView(getContext());
            FrameLayout.LayoutParams layoutParams=new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.width = (int)indicatorWidth;
            layoutParams.height = (int)indicatorHeight;
            layoutParams.leftMargin= (int) (i*(indicatorWidth+indicatorSpacing));
            imageView.setLayoutParams(layoutParams);
            imageView.setBackgroundResource(getUnSelect());
            addView(imageView);
        }
        refreshView();
    }

    /**
     * @param select    选中状态背景
     * @param unSelect  未选中状态背景
     */
    @Override
    public void setBgResource(@DrawableRes int select,@DrawableRes int unSelect) {
        mIndicatorSelected = select;
        mIndicatorUnselected = unSelect;
    }


    /**
     * @return 获取选中,未选中状态背景,未设置返回默认值
     */
    public int getSelect(){
        if (mIndicatorSelected == 0){
            return CarouselConstant.DEFAULT_SELECTED_INDICATOR_DRAWABLE;
        }
        return mIndicatorSelected;
    }
    public int getUnSelect(){
        if (mIndicatorUnselected == 0){
            return CarouselConstant.DEFAULT_UNSELECTED_INDICATOR_DRAWABLE;
        }
        return mIndicatorUnselected;
    }


    /**
     * 翻页时刷新界面方法
     */
    @Override
    public void refreshView() {
        if (count!=0) {
            getChildAt((count + indicatedPosition - 1) % count).setBackgroundResource(getSelect());
            getChildAt((count + indicatedPosition - 2) % count).setBackgroundResource(getUnSelect());
            getChildAt((count + indicatedPosition) % count).setBackgroundResource(getUnSelect());
        }
    }
}
