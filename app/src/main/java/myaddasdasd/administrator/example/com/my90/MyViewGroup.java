package myaddasdasd.administrator.example.com.my90;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Administrator on 2017/2/24 0024.
 */

public class MyViewGroup extends ViewGroup {
    public MyViewGroup(Context context) {
        super(context);
    }

    public MyViewGroup(Context context, AttributeSet attrs) {

        super(context, attrs);
    }

    /***
     * 获取子View中宽度最大的值
     */
    private int getMaxChildWidth() {
        int childCount = getChildCount();//获取子view的数量
        int maxWidth = 0;
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);//获取指定位置的子view
            if (childView.getMeasuredWidth() > maxWidth)//计算子view最大宽度
                maxWidth = childView.getMeasuredWidth();

        }

        return maxWidth;
    }

    /***
     * 将所有子View的高度相加
     **/
    private int getTotleHeight() {
        int childCount = getChildCount();//获取子view的数量
        int height = 0;
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);//获取指定位置的子view
            height += childView.getMeasuredHeight();//计算所有子view的总高度

        }

        return height;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //将所有的子View进行测量，这会触发每个子View的onMeasure函数
        //注意要与measureChild区分，measureChild是对单个view进行测量
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        //获取viewgroup设置的宽高类型和大小
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        //获取子view的数量
        int childCount = getChildCount();

        if (childCount == 0) {//如果没有子View,当前ViewGroup没有存在的意义，不用占用空间
            setMeasuredDimension(0, 0);
        } else {
            //如果viewgroup宽高都是包裹内容
            if (widthMode == MeasureSpec.AT_MOST && heightMode == MeasureSpec.AT_MOST) {
                //我们将高度设置为所有子View的高度相加，宽度设为子View中最大的宽度
                int height = getTotleHeight();
                int width = getMaxChildWidth();
                setMeasuredDimension(width, height);

            } else if (heightMode == MeasureSpec.AT_MOST) {//如果只有高度是包裹内容
                //宽度设置为ViewGroup自己的测量宽度，高度设置为所有子View的高度总和
                setMeasuredDimension(widthSize, getTotleHeight());
            } else if (widthMode == MeasureSpec.AT_MOST) {//如果只有宽度是包裹内容
                //宽度设置为子View中宽度最大的值，高度设置为ViewGroup自己的测量值
                setMeasuredDimension(getMaxChildWidth(), heightSize);

            }
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
         /*1）参数changed表示view有新的尺寸或位置；
            2）参数l表示相对于父view的Left位置；
            3）参数t表示相对于父view的Top位置；
            4）参数r表示相对于父view的Right位置；
            5）参数b表示相对于父view的Bottom位置。.*/
       /* MyViewGroup那里的第102行不对，应该改为curHeight=0
        因为onlayout中的int top这个参数是相对于父布局的top，
        初始值并不是0，child.layout(l, curHeight, l + width, curHeight + height);
        这里top那里应该给的是相对viewgroup的top，但传的值是相对于viewgroup父布局的top
        如果这个自定义的viewgroup不是在父布局的第一个位置的话，就不是效果图中的那样了。*/
        int count = getChildCount();
        //记录当前的高度位置（子view距离顶部的高度）
        int curHeight = t;
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            int height = child.getMeasuredHeight();
            int width = child.getMeasuredWidth();
            //摆放子View，参数分别是子View矩形区域的左、上、右、下边（子view左边的位置，顶部的位置，右边的位置，底部的位置）
            child.layout(l, curHeight, l + width, curHeight + height);
            curHeight += height;
        }
    }

}

