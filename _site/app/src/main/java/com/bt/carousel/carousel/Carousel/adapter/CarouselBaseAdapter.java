package com.bt.carousel.carousel.Carousel.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

import com.bt.carousel.carousel.Carousel.view.PagerViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 18030693 on 2018/6/12.
 */

public abstract class CarouselBaseAdapter<T> extends PagerAdapter {
    private final Context mContext;
    private List<T> mList = new ArrayList<>();
    private final SparseArray<PagerViewHolder> mViewHolder = new SparseArray<>();

    protected CarouselBaseAdapter(Context context) {
        mContext = context;
    }

    /**
     * @param model 源数据
     * 前后各加一条制造循环效果
     */
    public void setData(List<T> model){
        mList.clear();
        if(model != null  && model.size() > 0){
            mList.clear();
            mList.addAll(model);
            if(mList.size() > 1){
                T start = mList.get(0);
                T end = mList.get(mList.size()-1);
                mList.add(0, end);
                mList.add(start);
            }
        }
        mViewHolder.clear();
        notifyDataSetChanged();
    }

    //获取绑定数据
    public List<T> getData(){
        return mList;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        if(mViewHolder.get(position) == null){
            PagerViewHolder pagerViewHolder = new PagerViewHolder(mContext);
            convert(pagerViewHolder,position);
            mViewHolder.put(position, pagerViewHolder);
        }
        View convertView = mViewHolder.get(position).convertView;
        container.addView(convertView);
        return convertView;
    }


    /*
     * 当前页面是否发生改变
     * 默认不改变,不会进行页面重绘,会导致含有前后缓存页,页面内容不更新
     */
    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public int getCount() {
        return mList==null?0:mList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object o) {
        return view == o;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        if(mViewHolder.get(position) != null){
            container.removeView(mViewHolder.get(position).convertView);
        }
    }

    protected abstract void convert(PagerViewHolder viewHolder,int position);

}
