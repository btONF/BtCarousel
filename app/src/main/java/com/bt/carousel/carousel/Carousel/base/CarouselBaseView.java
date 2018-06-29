package com.bt.carousel.carousel.Carousel.base;

import android.content.Context;
import android.database.DataSetObserver;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;


/**
 * Created by btONF on 2018/6/7.
 */

public class CarouselBaseView extends FrameLayout {
    private static final String TAG = "CarouselBaseView";
    private final int SWITCH_VIEW_PAGER = 1;
    public final int DEFAULT_TIME_PERIOD = 3000;
    public final float DEFAULT_VIEW_PAGER_SCALE = 0.0f;
    private  int mTimePeriod;
    private  float mScale;
    private  int  mNoDataBg;
    private  boolean isStartCarouse = false;

    private SwitchHandler mHandler;

    private PagerAdapter mPagerAdapter;
    private ViewPagerBaseView mViewPager;
    private IndicatorBaseView mIndicator;

    public CarouselBaseView( Context context) {
        this(context,null);

    }

    public CarouselBaseView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CarouselBaseView(Context context,AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    /**
     * 初始化
     */
    private void init() {
        mTimePeriod = DEFAULT_TIME_PERIOD;
        mScale = DEFAULT_VIEW_PAGER_SCALE;
        mNoDataBg = 0;
        mHandler = new SwitchHandler();
    }

    /**
     * @param viewPager 设置viewpager
     * @return
     */
    public CarouselBaseView viewPager(ViewPagerBaseView viewPager){
        if (mViewPager != null){
            removeView(mViewPager);
        }
        if (viewPager!=null) {
            mViewPager = viewPager;
        }
        return this;
    }

    /**
     * @param indicator 设置指示器
     * @return
     */
    public CarouselBaseView indicator(IndicatorBaseView indicator){
        if (mIndicator != null){
            removeView(mIndicator);
        }
        if (indicator != null) {
            mIndicator = indicator;
        }
        return this;
    }

    /**
     * @param adapter 设置适配器
     * @return
     */
    public CarouselBaseView adapter(PagerAdapter adapter){
        if (adapter!=null) {
            mPagerAdapter = adapter;
        }
        return this;
    }


    /**
     * 载入视图(包括视图内部初始化)
     */
    public void build(){
        removeAllViews();
        if (mViewPager != null) {
            addView(mViewPager);
        }
        if (mIndicator != null) {
            mViewPager.setViewPagerScrollListener(mIndicator.viewPagerScrollListener);
            addView(mIndicator);
        }
        if (mPagerAdapter!=null){
            mViewPager.setViewPager(mPagerAdapter);
            mPagerAdapter.registerDataSetObserver(dataOB);
        }
        updateView();

    }


    /**
     * 载入内部视图
     */
    private void updateView() {
        if (mPagerAdapter!=null) {
            setBackgroundResource(mPagerAdapter.getCount() == 0 ? mNoDataBg:0);
            if (mViewPager!=null){
                mViewPager.updateView(mPagerAdapter.getCount());
            }
            if (mIndicator!=null){
                mIndicator.updateView(mPagerAdapter.getCount());
            }
        }else {
            Log.e("BtCarousel","PagerAdapter Can Not Be Null !!");
        }
    }


    /**
     * 开始轮播
     */
    public void startCarouse() {
        if (mPagerAdapter!=null ) {
            isStartCarouse = true;
            resumeCarouse();
        }
    }

    /**
     * 继续轮播
     */
    public void resumeCarouse() {
        if (isStartCarouse) {
            mHandler.removeMessages(SWITCH_VIEW_PAGER);
            mHandler.sendEmptyMessageDelayed(SWITCH_VIEW_PAGER, mTimePeriod);
        }
    }

    /**
     * 停止轮播
     */
    public void stopCarouse() {
        isStartCarouse = false;
        pauseCarouse();
    }

    /**
     * 暂停轮播
     */
    public void pauseCarouse() {
        mHandler.removeMessages(SWITCH_VIEW_PAGER);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //重绘时按照设定的比例绘制
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize;
        if (widthMode != MeasureSpec.UNSPECIFIED && mScale > 0.0f) {
            heightSize = (int) (widthSize / mScale + 0.5f);
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(heightSize,
                    MeasureSpec.EXACTLY);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    /**
     * 数据改变时通知刷新页面
     */
    private DataSetObserver dataOB = new DataSetObserver() {
        @Override
        public void onChanged() {
            updateView();
        }
    };

    /**
     * 循环handler
     */
    private  class SwitchHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case SWITCH_VIEW_PAGER:
                    if (mViewPager.getCurrentItem() != 0 && mViewPager.getCurrentItem() != mViewPager.getAdapter().getCount() - 1) {
                        mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1);
                    }
                    sendEmptyMessageDelayed(SWITCH_VIEW_PAGER,mTimePeriod);
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * @param changedView
     * @param visibility
     * 不可见时停止翻页
     */
    @Override
    protected void onVisibilityChanged(@NonNull View changedView, int visibility) {
        super.onVisibilityChanged(changedView, visibility);
        if (visibility!=VISIBLE){
            pauseCarouse();
        }else {
            resumeCarouse();
        }
    }


    /**
     * 清除指示器
     */
    public void clearIndicator(){
        for (int i = 0;i<getChildCount();i++){
            if (getChildAt(i) instanceof IndicatorBaseView){
                removeViewAt(i);
                i--;
            }
        }
        mIndicator =null;
    }

    //手势监听,手势翻页时暂停自动滚动
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (isStartCarouse) {
            int action = ev.getAction();
            switch (action) {
                case MotionEvent.ACTION_DOWN:
                    pauseCarouse();
                    break;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    resumeCarouse();
                    break;
                default:
                    break;
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * @param scale 比例  宽度/高度
     * @return
     */
    public CarouselBaseView scale(float scale){
        mScale = scale;
        requestLayout();
        return this;
    }

    /**
     * @param timePeriod 轮播间隔时间
     * @return
     */
    public CarouselBaseView timePeriod(int timePeriod) {
        mTimePeriod = timePeriod;
        return this;
    }


    /**
     * @param noDataBg 设置默认占位背景, 设置为0则为无背景
     * @return
     */
    public CarouselBaseView noDataBg(int noDataBg) {
        mNoDataBg = noDataBg;
        return this;
    }

    public IndicatorBaseView getIndicator() {
        return mIndicator;
    }


}
