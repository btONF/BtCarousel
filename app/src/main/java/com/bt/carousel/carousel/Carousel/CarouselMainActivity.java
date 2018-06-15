package com.bt.carousel.carousel.Carousel;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Toast;

import com.bt.carousel.carousel.Carousel.adapter.CarouselAdapter;
import com.bt.carousel.carousel.Carousel.view.CarouselView;
import com.bt.carousel.carousel.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 18030693 on 2018/6/5.
 */

public class CarouselMainActivity extends Activity {
    private CarouselView mCarouselView;
    private CarouselAdapter mCarouselAdapter;
    List<String> model = new ArrayList<>();

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    if (model.size()>0) {
                        model.remove(0);
                        model.remove(0);

                        model.remove(0);

                        mCarouselAdapter.setData(model);
                    }
                    sendEmptyMessageDelayed(1,3000);
                    break;
                default:
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.carousel_main_layout);
        initView();
        initData();
    }

    private void initView() {
        mCarouselView = (CarouselView) findViewById(R.id.carousel_view);
        mCarouselAdapter = new CarouselAdapter(this);
        mCarouselAdapter.setPageClickListener(mPageClickListener);
        mCarouselView.bindAdapter(mCarouselAdapter);
        mCarouselView.indicator(CarouselConstant.IMG_STYLE)
                .scale(2.0f).size(4.0f,6.0f,10.0f).load();

    }


    private void initData() {
        for (int i =0;i<6;i++){
            if(i%2==0) {
                model.add("http://btonf.top/assets/images/mac.jpg");
            }else {
                model.add(""+i);
            }
        }
        mCarouselAdapter.setData(model);
        mCarouselView.startCarouse();
    }



    private CarouselAdapter.PageClickListener mPageClickListener = new CarouselAdapter.PageClickListener() {
        @Override
        public void onItemClick(View view, int position) {
            Toast.makeText(getApplicationContext(),"Click "+position,Toast.LENGTH_SHORT).show();
        }
    };

}
