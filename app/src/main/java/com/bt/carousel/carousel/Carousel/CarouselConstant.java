package com.bt.carousel.carousel.Carousel;


import android.content.Context;
import android.util.TypedValue;

import com.bt.carousel.carousel.R;

/**
 * Created by btONF on 2018/6/6.
 */

public class CarouselConstant {
    //样例默认背景色
    public static final int[] DEFAULT_PAGE_BG_COLOR = {R.color.page_1,R.color.page_2,R.color.page_3,R.color.page_4};
    //样例默认背景图
    public static final int DEFAULT_NO_DATA_BG = R.drawable.carousel_default;
    //样例默认出错图
    public static final int DEFAULT_ERROR_BG = R.drawable.carousel_error;
    //样例默认网络图
    public static final String DEFAULT_NET_BG = "https://btonf.top/img/carousel_net.png";

    /**
     * @param dp    dp转px
     * @return
     */
    public static float dp2Px(Context context, float dp){
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dp,context.getResources().getDisplayMetrics());
    }

}
