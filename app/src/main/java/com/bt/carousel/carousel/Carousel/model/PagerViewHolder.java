package com.bt.carousel.carousel.Carousel.model;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.lang.ref.WeakReference;

/**
 * Created by btONF on 2018/6/7.
 */

public class PagerViewHolder {
    //view数组
    private final SparseArray<View> views;
    private WeakReference<Context> mContext;
    //public Context context;
    //根视图
    public View convertView;
    public PagerViewHolder(Context context) {
        mContext= new WeakReference<>(context);
        this.views = new SparseArray<>();
    }

    public <T extends View> T getView(@IdRes int viewId) {
        View view = views.get(viewId);
        if (view == null) {
            view = convertView.findViewById(viewId);
            views.put(viewId, view);
        }
        return (T) view;
    }

    /**
     * @param layoutId
     * @return
     * 设置根视图
     */
    public PagerViewHolder setConvertView(@LayoutRes int layoutId) {
        if (mContext.get()!=null) {
            LayoutInflater inflater = LayoutInflater.from(mContext.get());
            convertView = inflater.inflate(layoutId, null);
        }
        return this;
    }

    /**
     * @param viewId
     * @param value
     * @return
     * 设置textview的内容
     */
    public PagerViewHolder setText(@IdRes int viewId, CharSequence value) {
        TextView view = getView(viewId);
        view.setText(value);
        return this;
    }

    /**
     * @param viewId        视图id
     * @param imageResId    资源id
     * @return
     * 设置本地图片
     */
    public PagerViewHolder setImageResource(@IdRes int viewId,  int imageResId) {
        ImageView view = getView(viewId);
        view.setImageResource(imageResId);
        return this;
    }

    /**
     * @param viewId    视图id
     * @param imageUrl  图片URL
     * @param defaultImageResId 默认本地图片占位图 不设置需置0
     * @param errorImageResId   加载错误本地图片占位图 不设置需置0
     * @return
     * 设置网络图片
     */
    public PagerViewHolder setImageResource(@IdRes int viewId,CharSequence imageUrl, @DrawableRes int defaultImageResId,
                                            @DrawableRes int errorImageResId) {
        ImageView view = getView(viewId);
        loadImageUrl(view,imageUrl,defaultImageResId,errorImageResId);
        return this;
    }
    public PagerViewHolder setBackgroundResource(@IdRes int viewId,  int imageResId) {
        View view = getView(viewId);
        view.setBackgroundResource(imageResId);
        return this;
    }

    /**
     * @param view      视图对象
     * @param imageUrl  图片url
     * @param defaultImageResId 默认本地图片占位图 不设置需置0
     * @param errorImageResId   加载错误本地图片占位图 不设置需置0
     *
     */
    public void loadImageUrl(ImageView view,CharSequence imageUrl, @DrawableRes int defaultImageResId,
                             @DrawableRes int errorImageResId) {
        if (mContext.get()!=null) {
            RequestOptions options = new RequestOptions();
            if (defaultImageResId != 0) {
                options.placeholder(defaultImageResId);
            }
            if (errorImageResId != 0) {
                options.error(errorImageResId);
            }
            if (defaultImageResId != 0) {
                options.fallback(defaultImageResId);
            }
            Glide.with(mContext.get()).
                    load(imageUrl).apply(options)
                    .into(view);
        }
    }
}
