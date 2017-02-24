package myaddasdasd.administrator.example.com.my90;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Administrator on 2017/2/24 0024.
 */

public class MyView extends View {
    private int defalutSize;

    public MyView(Context context) {
        super(context);
    }

    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        //第二个参数就是我们在styles.xml文件中的<declare-styleable>标签
        //即属性集合的标签，在R文件中名称为R.styleable+name
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.MyView);
        //第一个参数为属性集合里面的属性，R文件名称：R.styleable+属性集合名称+下划线+属性名称
        //第二个参数为，如果没有设置这个属性，则设置的默认的值
        defalutSize = a.getDimensionPixelSize(R.styleable.MyView_default_size, 100);
        //最后记得将TypedArray对象回收
        a.recycle();
    }


    private int getMySize(int defaultSize, int measureSpec) {
        int mySize = defaultSize;
        //MeasureSpec.getMode获取引用此view的xml文件里设置宽高的模式 match_parent/wrap_content/指定数字大小
        int mode = MeasureSpec.getMode(measureSpec);
        //MeasureSpec.getSize获取引用此view的xml文件里设置宽高的大小
        int size = MeasureSpec.getSize(measureSpec);
       /* UNSPECIFIED	父容器没有对当前View有任何限制，当前View可以任意取尺寸即xml文件对view没有设置宽高
        EXACTLY	当前的尺寸就是当前View应该取的尺寸 对应xml文件
        AT_MOST	当前尺寸是当前View能取的最大尺寸
        match_parent—>EXACTLY。怎么理解呢？match_parent就是要利用父View给我们提供的所有剩余空间，而父View剩余空间是确定的，也就是这个测量模式的整数里面存放的尺寸。
        wrap_content—>AT_MOST。怎么理解：就是我们想要将大小设置为包裹我们的view内容，那么尺寸大小就是父View给我们作为参考的尺寸，只要不超过这个尺寸就可以啦，具体尺寸就根据我们的需求去设定。
        固定尺寸（如100dp）—>EXACTLY。用户自己指定了尺寸大小，我们就不用再去干涉了，当然是以指定的大小为主啦。*/
        switch (mode) {
            case MeasureSpec.UNSPECIFIED: {//如果没有指定大小，就设置为默认大小 即xml文件对view没有设置宽高
                mySize = defaultSize;
                break;
            }
            case MeasureSpec.AT_MOST: {//引用此view的xml文件里设置为wrap_content 当前尺寸是当前View能取的最大尺寸
                //我们将大小取最大值,你也可以取其他值
                mySize = size;
                break;
            }
            case MeasureSpec.EXACTLY: {//引用此view的xml文件里设置为match_parent或者指定大小 当前尺寸是当前View能取的最大尺寸
                mySize = size;
                break;
            }
        }
        return mySize;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //widthMeasureSpec heightMeasureSpec xml里设置的宽高
        int width = getMySize(defalutSize, widthMeasureSpec);
        int height = getMySize(defalutSize, heightMeasureSpec);

        if (width < height) {
            height = width;
        } else {
            width = height;
        }

        setMeasuredDimension(width, height);//执行测量设置宽高
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //调用父View的onDraw函数，因为View这个类帮我们实现了一些
        // 基本的而绘制功能，比如绘制背景颜色、背景图片等
        super.onDraw(canvas);
        int r = getMeasuredWidth() / 2;//也可以是getMeasuredHeight()/2,本例中我们已经将宽高设置相等了
        //圆心的横坐标为当前的View的左边起始位置getLeft()+半径
        int centerX = getLeft() + r;
        //圆心的纵坐标为当前的View的顶部起始位置getTop()+半径
        int centerY = getTop() + r;

        Paint paint = new Paint();
        paint.setColor(Color.GREEN);
        //开始绘制
        canvas.drawCircle(centerX, centerY, r, paint);


    }

}
