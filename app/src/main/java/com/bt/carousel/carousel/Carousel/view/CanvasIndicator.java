package com.bt.carousel.carousel.Carousel.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.ColorRes;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.bt.carousel.carousel.Carousel.CarouselConstant;
import com.bt.carousel.carousel.Carousel.base.IndicatorBaseView;
import com.bt.carousel.carousel.R;


/**
 * Created by btONF on 2018/6/5.
 */

public class CanvasIndicator extends IndicatorBaseView {
    public static final int LINE_STYLE = 1;
    public static final int CIRCLE_STYLE = 2;

    public final int DEFAULT_SELECTED_INDICATOR_COLOR = R.color.indicator_on;
    public final int DEFAULT_UNSELECTED_INDICATOR_COLOR = R.color.indicator_off;

    private Paint mPaint;

    //圆形或矩形
    public int mIndicatorType;

    public CanvasIndicator(Context context,int type) {
        super(context);
        mIndicatorType = type;
        //由于继承自Framelayout,默认不会进行重绘,需要设置为false才可自动调用onDraw
        setWillNotDraw(false);
    }

    public CanvasIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void build() {
        params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.bottomMargin =(int) CarouselConstant.dp2Px(getContext(),DEFAULT_BOTTOM_MARGIN);
        params.gravity = Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;
        params.width = (int) (indicatorWidth * count + indicatorSpacing  * (count - 1));
        params.height = (int) indicatorHeight;
        setLayoutParams(params);
    }

    @Override
    protected void updateView(int newCount) {
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
        changeStatus();
    }

    @Override
    public void changeStatus() {
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawIndicatedSelect(canvas);
        drawIndicatedBackground(canvas);
    }

    /**
     * @param canvas
     * canvas绘制选中状态下的背景
     * 数量大于1时才进行绘制
     */
    private void drawIndicatedSelect(Canvas canvas) {
        getPaint().setColor(getSelectColor());
        if (mIndicatorType == LINE_STYLE) {
            float cx;
            float cy = 0;
            if (count > 1) {
                cx = (indicatedPosition - 1) * (indicatorWidth + indicatorSpacing);
                canvas.drawRect(cx, cy, cx + indicatorWidth, cy + indicatorHeight, mPaint);
            }
        }else if (mIndicatorType == CIRCLE_STYLE) {
            float cx;
            float cy = indicatorHeight/2;
            if (count > 1) {
                cx = (indicatedPosition - 1) * (indicatorWidth + indicatorSpacing)+indicatorWidth/2;
                canvas.drawCircle(cx, cy, indicatorWidth/2, mPaint);
            }
        }
    }

    /**
     * @param canvas
     * canvas绘制未选中状态背景
     * 数量大于1时才进行绘制
     */
    private void drawIndicatedBackground(Canvas canvas) {
        mPaint.setColor(getUnSelectColor());
        if (mIndicatorType == LINE_STYLE) {
            float cx;
            float cy = 0;
            if (count > 1) {
                for (int i = 0; i < count; i++) {
                    if (i == indicatedPosition - 1) continue;
                    cx = i *  (indicatorWidth+indicatorSpacing);
                    canvas.drawRect(cx, cy, cx+indicatorWidth,cy+indicatorHeight, mPaint);
                }
            }
        }else if (mIndicatorType == CIRCLE_STYLE) {
            float cx;
            float cy = indicatorHeight/2;
            if (count > 1) {
                for (int i = 0; i < count; i++) {
                    if (i == indicatedPosition - 1) continue;
                    cx = i *  (indicatorWidth+indicatorSpacing)+indicatorWidth/2;
                    canvas.drawCircle(cx, cy, indicatorWidth/2, mPaint);
                }
            }
        }
    }

    /**
     * @return 获取paint,获取时自动初始化
     */
    public Paint getPaint(){
        if (mPaint == null){
            mPaint = new Paint();
        }
        return mPaint;
    }

    /**
     * @param select    选中状态背景
     * @param unSelect  未选中状态背景
     */
    @Override
    public CanvasIndicator setBgResource(@ColorRes int select, @ColorRes int unSelect) {
        mIndicatorSelected = getResources().getColor(select);
        mIndicatorUnselected = getResources().getColor(unSelect);
        return this;
    }

    /**
     * @return 未设置返回默认
     */
    public int getSelectColor(){
        if (mIndicatorSelected == 0){
            return getResources().getColor(DEFAULT_SELECTED_INDICATOR_COLOR);
        }
        return mIndicatorSelected;
    }

    /**
     * @return 未设置返回默认
     */
    public int getUnSelectColor(){
        if (mIndicatorUnselected == 0){
            return getResources().getColor(DEFAULT_UNSELECTED_INDICATOR_COLOR);
        }
        return mIndicatorUnselected;
    }

}
