# BtCarousel
A Carousel Easy To Use

## 演示

![演示图片](https://btonf.top/img/carousel_sample.gif)

图片帧数较低谅解~

> 背景:
> 
> 笔者公司的业务代码属于比较定制化的类型,即针对于某一环境所编写的代码,无法进行代码重用,并且如果需求有更改,代码改动量也极其巨大
>
> 一直都有想法去编写一些耦合性比较低的组件供日常使用,对以后的工作和学习来说都是事半功倍

## 使用:


### **Step1 :** 将以下布局加入`layout`中,宽高可以自行设定或者使用 `CarouselView..scale(float scale)`方法进行比例设置.

``` xml
<com.bt.carousel.carousel.Carousel.view.CarouselView
        android:id="@+id/carousel_view"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_gravity="center">
</com.bt.carousel.carousel.Carousel.view.CarouselView>
```

### **Step2 :** 自定义`Apdapter`继承自`CarouselAdapter`

``` java
public class CarouselAdapter extends CarouselBaseAdapter {

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
    }
}
```

> `convert`方法内绑定视图及设定内容使用链式调用方法.
> - `setConvertView(@LayoutRes int layoutId)` **(必需)** 设定每个`item`的根视图
> - `setText(@IdRes int viewId, CharSequence value)` 设置对应`viewId`的填充文本
> - `setImageResource(@IdRes int viewId, @DrawableRes int imageResId)` 设置对应`viewId`的背景图/drawable(本地)
> - `setImageResource(@IdRes int viewId,CharSequence imageUrl, @DrawableRes int defaultImageResId,@DrawableRes int errorImageResId)` 设置对应`viewId`加载网络图片,默认占位图及错误占位图.如不需要可将参数置0

### **Step3 :** 实例化`Apdater`,`ViewPager`,`Indicator`(可选)并与`CarouselView`的实例进行绑定.

``` java
//获取实例
mCarouselView = (CarouselView) findViewById(R.id.carousel_view);
//实例化adapter
CarouselAdapter carouselAdapter = new CarouselAdapter(this);
//实例化ViewPager
BtViewPager viewPager = new BtViewPager(this);
//构建viewPager
viewPager.build();
//实例化Indicator ,此处使用圆形canvas绘制
CanvasIndicator canvasIndicator = new CanvasIndicator(this,CanvasIndicator.CIRCLE_STYLE);
//构建Indicator
canvasIndicator.build();
//轮播构建
mCarouselView.viewPager(viewPager).adapter(carouselAdapter).indicator(canvasIndicator).build();
```

### **Finish :** 轮播正常显示啦.或许现在的界面很丑,你可以通过以下方法去定制.

------

## 扩展

每个组件内部均可自行定制.高度可扩展并且不会出现兼容问题.

## 更新日志

### 2018-6-29

- 代码重构,组件各自独立,降低耦合.
- 组件优化,可自行选择需要的组件进行加载.
- 代码优化,剔除冗余代码,代码高效利用.
- 修改作者名称.(原工号)


### 2018-6-18
- 修复设置样式后无法立即生效的BUG
- 修复canvas绘制有延迟的BUG
- 目录改动,按类型分类
- 增加样式设置方法
- **增加演示界面**

