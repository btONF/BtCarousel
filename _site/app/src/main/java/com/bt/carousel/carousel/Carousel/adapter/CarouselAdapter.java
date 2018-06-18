package com.bt.carousel.carousel.Carousel.adapter;

import android.content.Context;
import android.view.View;

import com.bt.carousel.carousel.Carousel.view.PagerViewHolder;
import com.bt.carousel.carousel.R;


/**
 * Created by 15120033 on 2018/5/28.
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
    protected void convert(PagerViewHolder viewHolder, final int position) {

        viewHolder.setConvertView(R.layout.item).setImageResource(R.id.home_banner_item_img,
                getData().get(position).toString(),R.drawable.default_bg,R.drawable.default_bg);
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
