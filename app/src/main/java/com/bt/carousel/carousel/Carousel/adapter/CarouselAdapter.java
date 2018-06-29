package com.bt.carousel.carousel.Carousel.adapter;

import android.content.Context;
import android.view.View;

import com.bt.carousel.carousel.Carousel.CarouselMainActivity;
import com.bt.carousel.carousel.Carousel.base.CarouselBaseAdapter;
import com.bt.carousel.carousel.Carousel.model.PagerViewHolder;
import com.bt.carousel.carousel.R;


/**
 * Created by btONF on 2018/5/28.
 */
public class CarouselAdapter extends CarouselBaseAdapter {
    private PageClickListener mPageClickListener;

    public CarouselAdapter(Context context) {
        super(context);
    }

    /**
     * @param viewHolder
     * @param position
     * 设置根视图以及元素,点击事件等
     */
    @Override
    protected void convert(PagerViewHolder viewHolder,Object data, final int position) {
        if (!((CarouselMainActivity.Page)data).isUseNet) {
            //案例 >> 使用网络图片
            viewHolder.setConvertView(R.layout.item)
                    .setBackgroundResource(R.id.page_parent, (((CarouselMainActivity.Page)data)).imgRes)
                    .setText(R.id.home_banner_item_text, (((CarouselMainActivity.Page)data)).text);
        }else if(((CarouselMainActivity.Page)data).isUseDefault){
            //案例 >> 使用默认图片
            viewHolder.setConvertView(R.layout.item)
                    .setImageResource(R.id.home_banner_item_img, ((CarouselMainActivity.Page)data).netImg,
                            ((CarouselMainActivity.Page)data).defaultImg,((CarouselMainActivity.Page)data).errorImg)
                    .setText(R.id.home_banner_item_text, (((CarouselMainActivity.Page)data)).text);
        }else {
            //案例 >> 仅适用背景色
            viewHolder.setConvertView(R.layout.item)
                    .setImageResource(R.id.home_banner_item_img, ((CarouselMainActivity.Page)data).netImg, 0,0)
                    .setText(R.id.home_banner_item_text, (((CarouselMainActivity.Page)data)).text);
        }
        //自定义点击事件
        viewHolder.convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPageClickListener.onItemClick(view,position);
            }
        });
    }

    public void setPageClickListener(PageClickListener listener){
        mPageClickListener = listener;
    }
    public interface PageClickListener {
        void onItemClick(View view, int position);
    }


}
