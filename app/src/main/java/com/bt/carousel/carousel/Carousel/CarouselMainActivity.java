package com.bt.carousel.carousel.Carousel;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.bt.carousel.carousel.Carousel.adapter.CarouselAdapter;
import com.bt.carousel.carousel.Carousel.view.CanvasIndicator;
import com.bt.carousel.carousel.Carousel.view.CarouselView;
import com.bt.carousel.carousel.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 18030693 on 2018/6/5.
 */

public class CarouselMainActivity extends Activity  implements View.OnClickListener{
    private CarouselView mCarouselView;
    private CarouselAdapter mCarouselAdapter;
    List<Page> model = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.carousel_main_layout);
        initView();
        initData(4,false,false);
    }

    private void initView() {
        mCarouselView = (CarouselView) findViewById(R.id.carousel_view);
        mCarouselAdapter = new CarouselAdapter(this);
        mCarouselAdapter.setPageClickListener(mPageClickListener);
        mCarouselView.bindAdapter(mCarouselAdapter);
        findViewById(R.id.start_fast_btn).setOnClickListener(this);
        findViewById(R.id.start_slow_btn).setOnClickListener(this);
        findViewById(R.id.stop_btn).setOnClickListener(this);
        findViewById(R.id.page_zero_btn).setOnClickListener(this);
        findViewById(R.id.page_one_btn).setOnClickListener(this);
        findViewById(R.id.page_four_btn).setOnClickListener(this);
        findViewById(R.id.scale_4_1).setOnClickListener(this);
        findViewById(R.id.scale_2_1).setOnClickListener(this);
        findViewById(R.id.scale_1_1).setOnClickListener(this);
        findViewById(R.id.indicator_circle).setOnClickListener(this);
        findViewById(R.id.indicator_line).setOnClickListener(this);
        findViewById(R.id.indicator_img).setOnClickListener(this);
        findViewById(R.id.indicator_remove).setOnClickListener(this);
        findViewById(R.id.no_data_bg_on).setOnClickListener(this);
        findViewById(R.id.no_data_bg_off).setOnClickListener(this);
        findViewById(R.id.indicator_big).setOnClickListener(this);
        findViewById(R.id.indicator_small).setOnClickListener(this);
        findViewById(R.id.indicator_black).setOnClickListener(this);
        findViewById(R.id.indicator_color).setOnClickListener(this);
        findViewById(R.id.use_default_img).setOnClickListener(this);
        findViewById(R.id.no_defult_img).setOnClickListener(this);




    }


    private void initData(int num,boolean useNet, boolean useDefault) {
        model.clear();
        for (int i = 0; i < num; i++) {
            Page page = new Page();
            page.imgRes = CarouselConstant.DEFAULT_PAGE_BG_COLOR[i % 4];
            page.text = "PAGE " + i;
            if (i%2==0) {
                page.netImg = CarouselConstant.DEFAULT_NET_BG;
            }
            page.defaultImg = CarouselConstant.DEFAULT_NO_DATA_BG;
            page.errorImg = CarouselConstant.DEFAULT_ERROR_BG;
            page.isUseNet = useNet;
            page.isUseDefault=useDefault;
            model.add(page);
        }
        mCarouselAdapter.setData(model);
    }


    private CarouselAdapter.PageClickListener mPageClickListener = new CarouselAdapter.PageClickListener() {
        @Override
        public void onItemClick(View view, int position) {
            Toast.makeText(getApplicationContext(), "Click " + position, Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            //开始轮播(快)
            case R.id.start_fast_btn:
                mCarouselView.timePeriod(1000).startCarouse();
                break;
            //开始轮播(慢)
            case R.id.start_slow_btn:
                mCarouselView.timePeriod(3000).startCarouse();
                break;
            //停止轮播
            case R.id.stop_btn:
                mCarouselView.stopCarouse();
                break;
            //页数 : 0
            case R.id.page_zero_btn:
                initData(0,false,false);
                break;
            //页数 : 1
            case R.id.page_one_btn:
                initData(1,false,false);
                break;
            //页数 : 4
            case R.id.page_four_btn:
                initData(4,false,false);
                break;
            //比例4:1
            case R.id.scale_4_1:
                mCarouselView.scale(4.0f);
                break;
            //比例2:1
            case R.id.scale_2_1:
                mCarouselView.scale(2.0f);
                break;
            //比例1:1
            case R.id.scale_1_1:
                mCarouselView.scale(1.0f);
                break;
            //圆形指示器
            case R.id.indicator_circle:
                mCarouselView.indicator(CarouselConstant.CIRCLE_STYLE).load();
                break;
            //矩形指示器
            case R.id.indicator_line:
                mCarouselView.indicator(CarouselConstant.LINE_STYLE).load();
                break;
            //图片指示器
            case R.id.indicator_img:
                mCarouselView.indicator(CarouselConstant.IMG_STYLE).load();
                break;
            //移除指示器
            case R.id.indicator_remove:
                mCarouselView.clearIndicator();
                break;
            //开启无数据占位图
            case R.id.no_data_bg_on:
                mCarouselView.noDataBg(R.drawable.carousel_default);
                break;
            //关闭无数据占位图
            case R.id.no_data_bg_off:
                mCarouselView.noDataBg(0);
                break;
            //大指示器
            case R.id.indicator_big:
                if (mCarouselView.indicator instanceof CanvasIndicator) {
                    if (((CanvasIndicator)mCarouselView.indicator).mIndicatorType==CarouselConstant.CIRCLE_STYLE) {
                        mCarouselView.size(10.0f, 10.0f, 10.0f).refreshIndicator();
                    }else {
                        mCarouselView.size(10.0f, 3.0f, 10.0f).refreshIndicator();
                    }
                }else {
                    mCarouselView.size(10.0f, 10.0f, 10.0f).refreshIndicator();
                }
                break;
            //小指示器
            case R.id.indicator_small:
                if (mCarouselView.indicator instanceof CanvasIndicator) {
                    if (((CanvasIndicator)mCarouselView.indicator).mIndicatorType==CarouselConstant.CIRCLE_STYLE) {
                        mCarouselView.size(4.0f, 4.0f, 4.0f).refreshIndicator();
                    }else {
                        mCarouselView.size(4.0f, 1.0f, 4.0f).refreshIndicator();
                    }
                }else {
                    mCarouselView.size(10.0f, 10.0f, 10.0f).refreshIndicator();
                }
                break;
            //指示器颜色(灰白)
            case R.id.indicator_black:
                mCarouselView.indicatorBg(R.color.indicator_on,R.color.indicator_off).refreshIndicator();
                break;
            //指示器颜色(彩色)
            case R.id.indicator_color:
                mCarouselView.indicatorBg(R.color.indicator_color_on,R.color.indicator_color_off).refreshIndicator();
                break;
            case R.id.use_default_img:
                initData(4,true,true);
                break;
            case R.id.no_defult_img:
                initData(4,true,false);
                break;
            default:
                break;
        }
    }


    public class Page {
        public String netImg;
        public int imgRes;
        public String text;
        public boolean isUseNet;
        public boolean isUseDefault;
        public int defaultImg;
        public int errorImg;
    }
}
