package com.bt.carousel.carousel.Carousel.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.FrameLayout;

import com.bt.carousel.carousel.Carousel.CarouselConstant;


/**
 * Created by 18030693 on 2018/6/6.
 */

public abstract class IndicatorBaseView extends FrameLayout{

    //通用指示器属性
    public float indicatorWidth ;
    public float indicatorHeight ;
    public float indicatorSpacing ;
    //指示器个数
    public int count;
    //当前位置
    protected int indicatedPosition;

    boolean isFirstMeasure = true;

    public IndicatorBaseView(Context context) {
        this(context,null);
    }

    public IndicatorBaseView(Context context,  AttributeSet attrs) {
        this(context, attrs,0);
    }

    public IndicatorBaseView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initDefaultData();
    }

    private void initDefaultData() {
        indicatorWidth = dp2Px(CarouselConstant.DEFAULT_INDICATOR_WIDTH);
        indicatorHeight = dp2Px(CarouselConstant.DEFAULT_INDICATOR_HEIGHT);
        indicatorSpacing = dp2Px(CarouselConstant.DEFAULT_INDICATOR_SPACING);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (isFirstMeasure) {
            isFirstMeasure = false;
            indicatedPosition = 1;
        }
    }

    /**
     * @param dp    dp转px
     * @return
     */
    public float dp2Px(float dp){
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dp,getContext().getResources().getDisplayMetrics());
    }

    /**
     * @param count 数据源count
     */
    public void setCount(int count) {
        if (count>2) {
            this.count = count-2;
        }else {
            this.count = 1;
        }
        initView();
    }

    public abstract void setBgResource(int select,int unSelect);
    public abstract void refreshView();
    public abstract void initView();
}