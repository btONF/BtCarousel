package com.bt.carousel.carousel.Carousel.base;

import android.content.Context;
import android.database.DataSetObserver;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.bt.carousel.carousel.Carousel.CarouselConstant;


/**
 * Created by 18030693 on 2018/6/7.
 */

public class CarouselBaseView extends FrameLayout {
    private static final String TAG = "CarouselBaseView";
    private static final int SWITCH_VIEW_PAGER = 1;
    private  int mTimePeriod;
    private  float mScale;
    private  int  mNoDataBg;
    private  boolean isStartCarouse = false;

    private SwitchHandler mHandler;
    private PagerAdapter mPagerAdapter;
    private ViewPager mViewPager;
    public IndicatorBaseView indicator;

    public CarouselBaseView( Context context) {
        this(context,null);

    }

    public CarouselBaseView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CarouselBaseView(Context context,AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initDefaultData();
        initViewPager();
    }

    /**
     * 默认值
     */
    private void initDefaultData() {
        mTimePeriod = CarouselConstant.DEFAULT_TIME_PERIOD;
        mScale = CarouselConstant.DEFAULT_VIEW_PAGER_SCALE;
        mNoDataBg = CarouselConstant.DEFAULT_NO_DATA_BG;
    }


    /**
     * 初始化Viewpager并设置引用
     * 默认载入viewpager
     */
    public void initViewPager() {
        mHandler = new SwitchHandler();
        ViewPager viewPager = new ViewPager(getContext());
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        viewPager.setLayoutParams(params);
        addView(viewPager);
        mViewPager = viewPager;
    }

    /**
     * @param adapter
     * 绑定ViewpagerAdapter
     */
    public void bindAdapter(PagerAdapter adapter) {
        if (adapter!=null){
            mPagerAdapter = adapter;
            mPagerAdapter.registerDataSetObserver(dataOB);
            mViewPager.setAdapter(mPagerAdapter);
            refreshView();
        }
    }


    /**
     * 设置通用indicator载入方法
     */
    public void load(){
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.bottomMargin = (int) dp2Px(CarouselConstant.DEFAULT_BOTTOM_MARGIN);
        params.gravity = Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;
        indicator.setLayoutParams(params);
        addView(indicator);
        refreshView();
    }

    /**
     * 更新ViewPager视图
     * 1.绑定适配器时刷新
     * 2.添加indicator时刷新
     * 3.数据源变更时刷新
     */
    private void refreshView() {
        mViewPager.removeOnPageChangeListener(mPageLoopListener);
        if (mPagerAdapter.getCount() == 0){
            mViewPager.setVisibility(GONE);
            setBackgroundResource(mNoDataBg);
        }else {
            mViewPager.addOnPageChangeListener(mPageLoopListener);
            if (mPagerAdapter.getCount() == 1) {
                mViewPager.setCurrentItem(0, false);
            } else {
                mViewPager.setCurrentItem(1, false);
            }
            mViewPager.setVisibility(VISIBLE);
            setBackgroundResource(0);
        }
        resumeCarouse();
        refreshIndicator();
    }

    /**
     * 更新indicator视图
     */
    public void refreshIndicator(){
        if (indicator != null) {
            if (mPagerAdapter.getCount() <= 1) {
                indicator.setVisibility(GONE);
            } else {
                indicator.setVisibility(VISIBLE);
                ViewGroup.LayoutParams params = indicator.getLayoutParams();
                params.width = (int) (indicator.indicatorWidth * (mPagerAdapter.getCount() - 2) +
                        indicator.indicatorSpacing  * (mPagerAdapter.getCount() - 3));
                params.height = (int) indicator.indicatorHeight;
                indicator.setLayoutParams(params);
            }
            indicator.indicatedPosition = 1;
            indicator.setCount(mPagerAdapter.getCount());
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
            refreshView();
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
     * 翻页监听,制造循环效果
     * 设置indicator之后可以使其与viewpager绑定
     */
    private ViewPager.OnPageChangeListener mPageLoopListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int i, float v, int i1) {
        }

        @Override
        public void onPageSelected(int position) {

            if (indicator!=null && mPagerAdapter.getCount()>1) {
                int realCount = mPagerAdapter.getCount()-2;
                if (position%realCount==0) {
                    indicator.indicatedPosition = realCount;
                }else {
                    indicator.indicatedPosition = position%realCount;
                }
                //仅刷新状态不刷新布局
                indicator.refreshView();
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            if (state == ViewPager.SCROLL_STATE_IDLE) {
                if (mViewPager.getCurrentItem() == 0) {
                    mViewPager.setCurrentItem(mPagerAdapter.getCount() - 2, false);
                }
                if (mViewPager.getCurrentItem() == mPagerAdapter.getCount() - 1) {
                    mViewPager.setCurrentItem(1, false);
                }
            }
        }
    };

    /**
     * @param dp float类型dp值
     * @return  px
     */
    public float dp2Px(float dp){
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dp,getContext().getResources().getDisplayMetrics());
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
        indicator =null;
    }

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
     * @param width indicator 每个元素宽度, indicator为圆时该参数为圆直径
     * @param height indicator 高度, indicator为圆时该参数为indicator自身高度
     * @param spacing indicator 间距
     * @return
     */
    public CarouselBaseView size(float width, float height, float spacing){
        if (indicator!=null) {
            indicator.indicatorWidth = dp2Px(width);
            indicator.indicatorHeight = dp2Px(height);
            indicator.indicatorSpacing = dp2Px(spacing);
        }
        return this;
    }

    /**
     * @param select 当前页面对应的 indicator颜色(仅对canvas绘图有效)
     * @param unSelect 默认背景色 (仅对canvas绘图有效)
     * @return
     */
    public CarouselBaseView indicatorBg(int select, int unSelect){
        if(indicator!=null) {
            indicator.setBgResource(select, unSelect);
        }
        return this;
    }


    /**
     * @param noDataBg 设置默认占位背景, 设置为0则为无背景
     * @return
     */
    public CarouselBaseView noDataBg(int noDataBg) {
        mNoDataBg = noDataBg;
        setBackgroundResource(mNoDataBg);
        return this;
    }
}
