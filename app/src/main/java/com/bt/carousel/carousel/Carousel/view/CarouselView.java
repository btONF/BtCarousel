package com.bt.carousel.carousel.Carousel.view;

import android.content.Context;
import android.util.AttributeSet;

import com.bt.carousel.carousel.Carousel.CarouselConstant;
import com.bt.carousel.carousel.Carousel.base.CarouselBaseView;


/**
 * Created by 18030693 on 2018/6/6.
 */

public class CarouselView extends CarouselBaseView {

    public CarouselView(Context context) {
        super(context);
    }
    public CarouselView( Context context,  AttributeSet attrs) {
        super(context, attrs);
    }
    public CarouselView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * @return 默认圆形指示器
     */
    public CarouselBaseView indicator(){

        return indicator(CarouselConstant.CIRCLE_STYLE);
    }

    /**
     * @param type 指示器类型
     * @return
     */
    public CarouselBaseView indicator(int type){
        clearIndicator();
        switch (type){
            case CarouselConstant.CIRCLE_STYLE:
            case CarouselConstant.LINE_STYLE:
                indicator =new CanvasIndicator(getContext(),type);
                break;
            case CarouselConstant.IMG_STYLE:
                indicator =new DrawableIndicator(getContext());
                break;
            default:
                break;
        }
        return this;
    }



}
