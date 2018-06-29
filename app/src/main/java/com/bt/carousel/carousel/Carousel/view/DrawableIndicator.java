package com.bt.carousel.carousel.Carousel.view;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.bt.carousel.carousel.Carousel.CarouselConstant;
import com.bt.carousel.carousel.Carousel.base.IndicatorBaseView;
import com.bt.carousel.carousel.R;


/**
 * Created by btONF on 2018/6/14.
 */

public class DrawableIndicator extends IndicatorBaseView {
    public static final int DEFAULT_SELECTED_INDICATOR_DRAWABLE = R.drawable.red_dot;
    public static final int DEFAULT_UNSELECTED_INDICATOR_DRAWABLE = R.drawable.gray_dot;

    public DrawableIndicator(Context context) {
        super(context);
    }

    /**
     *  数据变化时重新载入布局
     */
    @Override
    protected void updateView(int newCount) {
        removeAllViews();
        if (newCount>2) {
            count = newCount-2;
            setVisibility(VISIBLE);
            params.width = (int) (indicatorWidth * count + indicatorSpacing  * (count - 1));
            params.height = (int) indicatorHeight;
            setLayoutParams(params);
        }else {
            count = 1;
            setVisibility(GONE);
        }
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
        changeStatus();
    }

    /**
     * @param select    选中状态背景
     * @param unSelect  未选中状态背景
     */
    @Override
    public DrawableIndicator setBgResource(@DrawableRes int select,@DrawableRes int unSelect) {
        mIndicatorSelected = select;
        mIndicatorUnselected = unSelect;
        return this;
    }

    /**
     * @return 获取选中,未选中状态背景,未设置返回默认值
     */
    public int getSelect(){
        if (mIndicatorSelected == 0){
            return DEFAULT_SELECTED_INDICATOR_DRAWABLE;
        }
        return mIndicatorSelected;
    }

    public int getUnSelect(){
        if (mIndicatorUnselected == 0){
            return DEFAULT_UNSELECTED_INDICATOR_DRAWABLE;
        }
        return mIndicatorUnselected;
    }


    /**
     * 翻页时刷新界面方法
     */
    @Override
    public void changeStatus() {
        if (count!=0) {
            getChildAt((count + indicatedPosition - 1) % count).setBackgroundResource(getSelect());
            getChildAt((count + indicatedPosition - 2) % count).setBackgroundResource(getUnSelect());
            getChildAt((count + indicatedPosition) % count).setBackgroundResource(getUnSelect());
        }
    }

    //构造并加载params
    @Override
    public void build() {
        params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.bottomMargin =(int) CarouselConstant.dp2Px(getContext(),DEFAULT_BOTTOM_MARGIN);
        params.gravity = Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;
        setLayoutParams(params);
    }

}
