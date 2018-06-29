package com.bt.carousel.carousel.Carousel.base;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.bt.carousel.carousel.Carousel.CarouselConstant;


/**
 * Created by btONF on 2018/6/6.
 */

public abstract class IndicatorBaseView extends FrameLayout{
    public FrameLayout.LayoutParams params;
    public static final float DEFAULT_INDICATOR_WIDTH = 4.0f;
    public static final float DEFAULT_INDICATOR_HEIGHT = 4.0f;
    public static final float DEFAULT_INDICATOR_SPACING = 4.0f;
    public static final float DEFAULT_BOTTOM_MARGIN = 5.0f;
    //通用背景
    protected int mIndicatorSelected = 0;
    protected int mIndicatorUnselected = 0;
    //通用指示器属性
    public float indicatorWidth ;
    public float indicatorHeight ;
    public float indicatorSpacing ;
    //指示器个数
    public int count;
    //当前位置
    protected int indicatedPosition;


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

    /**
     * 默认值初始化
     */
    private void initDefaultData() {
        indicatorWidth = CarouselConstant.dp2Px(getContext(),DEFAULT_INDICATOR_WIDTH);
        indicatorHeight = CarouselConstant.dp2Px(getContext(),DEFAULT_INDICATOR_HEIGHT);
        indicatorSpacing = CarouselConstant.dp2Px(getContext(),DEFAULT_INDICATOR_SPACING);
    }

    /**
     * @param width 指示器每个item宽度
     * @return
     */
    public IndicatorBaseView width(float width){
        indicatorWidth =  CarouselConstant.dp2Px(getContext(), width);
        return this;
    }

    /**
     * @param height 指示器每个item高度
     * @return
     */
    public IndicatorBaseView height(float height){
        indicatorHeight = CarouselConstant.dp2Px(getContext(),height);
        return this;
    }

    /**
     * @param spacing 指示器每个item之间的距离
     * @return
     */
    public IndicatorBaseView spacing(float spacing){
        indicatorSpacing = CarouselConstant.dp2Px(getContext(),spacing);
        return this;
    }

    /**
     * ViewPager内的接口实现,用于与ViewPager绑定
     */
    public ViewPagerBaseView.ViewPagerScrollListener viewPagerScrollListener = new ViewPagerBaseView.ViewPagerScrollListener() {
        @Override
        public void onPageSelected(int position,int childCount) {
            if ( childCount>2) {
                int realCount = childCount-2;
                if (position%realCount==0) {
                    indicatedPosition = realCount;
                }else {
                    indicatedPosition = position%realCount;
                }
                //刷新状态
                changeStatus();
            }
        }
    };
    //设置指示器item选中(未选中)背景
    public abstract IndicatorBaseView setBgResource(int select,int unSelect);
    //更新视图
    protected abstract void updateView(int newCount);
    //刷新状态
    public abstract void changeStatus();
    //根据参数构造params
    public abstract void build();
}