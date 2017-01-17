package com.sunfusheng.gank.widget.LoadingView;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.sunfusheng.gank.R;

import java.util.ArrayList;
import java.util.List;

public class CircleLoadingView extends View {

    private CircleLoadingViewParams circleLoadingViewParams = new CircleLoadingViewParams(this.getContext());
    private List<CircleWrapper> wrappers;
    private int[] colors = new int[]{0xFF942909, 0xFF577B8C, 0xFF201289, 0xFF7E207C};
    private Paint paint = new Paint();
    private RectF oval = new RectF();
    private boolean isLoading = true;

    // 属性
    private int circleRadius; // 圆半径
    private int circleSpacing; // 圆间距
    private int increment = 2;// 增量


    public CircleLoadingView(Context context) {
        super(context);
    }

    public CircleLoadingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CircleLoadingViewAttr);
        circleRadius = (int) a.getDimension(R.styleable.CircleLoadingViewAttr_circleRadius, circleLoadingViewParams.getDefaultCircleRadius());
        circleSpacing = (int) a.getDimension(R.styleable.CircleLoadingViewAttr_circleSpacing, circleLoadingViewParams.getDefaultCircleSpacing());
        int cycle = a.getInt(R.styleable.CircleLoadingViewAttr_cycle, 1000); // 周期，默认为1秒
        cycle = cycle / 2;
        int number = (int) (cycle * 1.0 / 1000 * 83);
        this.increment = (int) (this.circleRadius * 2.0 / number);
        this.increment = this.increment <= 0 ? 1 : this.increment;
        createWrappers();
    }

    /**
     * 创建wrappers
     */
    private void createWrappers() {
        wrappers = new ArrayList<CircleWrapper>();
        int diameter = this.circleRadius * 2;//直径
        // 第一个圆
        CircleWrapper wrapper = new CircleWrapper();
        wrapper.diameter = diameter;
        wrapper.initDiameter = diameter;
        wrapper.dynamicDiameter = wrapper.initDiameter;
        wrapper.orientation = -1;
        wrappers.add(wrapper);
        // 第二个圆
        wrapper = new CircleWrapper();
        wrapper.diameter = diameter;
        wrapper.initDiameter = (int) (diameter * 0.75);
        wrapper.dynamicDiameter = wrapper.initDiameter;
        wrapper.orientation = 1;
        wrappers.add(wrapper);
        // 第三个圆
        wrapper = new CircleWrapper();
        wrapper.diameter = diameter;
        wrapper.initDiameter = (int) (diameter * 0.5);
        wrapper.dynamicDiameter = wrapper.initDiameter;
        wrapper.orientation = 1;
        wrappers.add(wrapper);
        // 第四个圆
        wrapper = new CircleWrapper();
        wrapper.diameter = diameter;
        wrapper.initDiameter = (int) (diameter * 0.25);
        wrapper.dynamicDiameter = wrapper.initDiameter;
        wrapper.orientation = 1;
        wrappers.add(wrapper);
    }

    /**
     * 测绘
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(measureWidth(widthMeasureSpec), measureHeight(heightMeasureSpec));
    }

    /**
     * 计算组件宽度
     */
    private int measureWidth(int measureSpec) {
        int result;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        } else {
            result = getDefaultWidth();
            if (specMode == MeasureSpec.AT_MOST) {
                result = Math.min(result, specSize);
            }
        }
        return result;
    }

    /**
     * 计算组件高度
     */
    private int measureHeight(int measureSpec) {
        int result;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        } else {
            result = getDefaultHeight();
            if (specMode == MeasureSpec.AT_MOST) {
                result = Math.min(result, specSize);
            }
        }
        return result;
    }

    /**
     * 计算默认宽度
     */
    private int getDefaultWidth() {
        int defaultWidth = this.circleRadius * 2 * this.wrappers.size() + (this.wrappers.size() - 1) * this.circleSpacing;
        return defaultWidth;
    }

    /**
     * 计算默认宽度
     */
    private int getDefaultHeight() {
        return this.circleRadius * 2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        //canvas.drawColor(0xFF00FF33);
        drawCircle01(canvas);
        drawCircle02(canvas);
        drawCircle03(canvas);
        drawCircle04(canvas);
        if (isLoading){
            this.invalidate();
        }
    }

    /**
     * 画圆1
     */
    private void drawCircle01(Canvas canvas) {
        paint.setColor(colors[0]);
        CircleWrapper wrapper = wrappers.get(0);
        wrapper.dynamicDiameter = wrapper.dynamicDiameter + wrapper.orientation * this.increment;
        if (wrapper.dynamicDiameter >= wrapper.diameter) {
            wrapper.orientation = -1;
            wrapper.dynamicDiameter = wrapper.diameter;
        }
        if (wrapper.dynamicDiameter <= 0) {
            wrapper.orientation = 1;
            wrapper.dynamicDiameter = 0;
        }
        int totalWidth = this.circleRadius * 2 * this.wrappers.size() + (this.wrappers.size() - 1) * this.circleSpacing;
        int centerX = this.getWidth() / 2 - totalWidth / 2 + this.circleRadius;
        int centerY = this.getHeight() / 2;

        oval.left = centerX - wrapper.dynamicDiameter / 2;
        oval.top = centerY - wrapper.dynamicDiameter / 2;
        oval.right = oval.left + wrapper.dynamicDiameter;
        oval.bottom = oval.top + wrapper.dynamicDiameter;
        canvas.drawArc(oval, 0, 360, false, paint);
    }

    /**
     * 画圆2
     */
    private void drawCircle02(Canvas canvas) {
        paint.setColor(colors[1]);
        CircleWrapper wrapper = wrappers.get(1);
        CircleWrapper wrapper01 = wrappers.get(0);
        if (wrapper01.dynamicDiameter == wrapper01.initDiameter) {
            wrapper.dynamicDiameter = wrapper.initDiameter;
        } else {
            wrapper.dynamicDiameter = wrapper.dynamicDiameter + wrapper.orientation * this.increment;
            if (wrapper.dynamicDiameter >= wrapper.diameter) {
                wrapper.orientation = -1;
                wrapper.dynamicDiameter = wrapper.diameter;
            }
            if (wrapper.dynamicDiameter <= 0) {
                wrapper.orientation = 1;
                wrapper.dynamicDiameter = 0;
            }
        }

        int totalWidth = this.circleRadius * 2 * this.wrappers.size() + (this.wrappers.size() - 1) * this.circleSpacing;
        int centerX = this.getWidth() / 2 - totalWidth / 2 + (wrapper.diameter + this.circleSpacing) * 1 + this.circleRadius;
        int centerY = this.getHeight() / 2;

        oval.left = centerX - wrapper.dynamicDiameter / 2;
        oval.top = centerY - wrapper.dynamicDiameter / 2;
        oval.right = oval.left + wrapper.dynamicDiameter;
        oval.bottom = oval.top + wrapper.dynamicDiameter;
        canvas.drawArc(oval, 0, 360, false, paint);
    }

    /**
     * 画圆3
     */
    private void drawCircle03(Canvas canvas) {
        paint.setColor(colors[2]);
        CircleWrapper wrapper = wrappers.get(2);
        CircleWrapper wrapper01 = wrappers.get(0);
        if (wrapper01.dynamicDiameter == wrapper01.initDiameter) {
            wrapper.dynamicDiameter = wrapper.initDiameter;
        } else {
            wrapper.dynamicDiameter = wrapper.dynamicDiameter + wrapper.orientation * this.increment;
            if (wrapper.dynamicDiameter >= wrapper.diameter) {
                wrapper.orientation = -1;
                wrapper.dynamicDiameter = wrapper.diameter;
            }
            if (wrapper.dynamicDiameter <= 0) {
                wrapper.orientation = 1;
                wrapper.dynamicDiameter = 0;
            }
        }

        int totalWidth = this.circleRadius * 2 * this.wrappers.size() + (this.wrappers.size() - 1) * this.circleSpacing;
        int centerX = this.getWidth() / 2 - totalWidth / 2 + (wrapper.diameter + this.circleSpacing) * 2 + this.circleRadius;
        int centerY = this.getHeight() / 2;

        oval.left = centerX - wrapper.dynamicDiameter / 2;
        oval.top = centerY - wrapper.dynamicDiameter / 2;
        oval.right = oval.left + wrapper.dynamicDiameter;
        oval.bottom = oval.top + wrapper.dynamicDiameter;
        canvas.drawArc(oval, 0, 360, false, paint);
    }

    /**
     * 画圆4
     */
    private void drawCircle04(Canvas canvas) {
        paint.setColor(colors[3]);
        CircleWrapper wrapper = wrappers.get(3);
        CircleWrapper wrapper01 = wrappers.get(0);
        if (wrapper01.dynamicDiameter == wrapper01.initDiameter) {
            wrapper.dynamicDiameter = wrapper.initDiameter;
        } else {
            wrapper.dynamicDiameter = wrapper.dynamicDiameter + wrapper.orientation * this.increment;
            if (wrapper.dynamicDiameter >= wrapper.diameter) {
                wrapper.orientation = -1;
                wrapper.dynamicDiameter = wrapper.diameter;
            }
            if (wrapper.dynamicDiameter <= 0) {
                wrapper.orientation = 1;
                wrapper.dynamicDiameter = 0;
            }
        }

        int totalWidth = this.circleRadius * 2 * this.wrappers.size() + (this.wrappers.size() - 1) * this.circleSpacing;
        int centerX = this.getWidth() / 2 - totalWidth / 2 + (wrapper.diameter + this.circleSpacing) * 3 + this.circleRadius;
        int centerY = this.getHeight() / 2;

        oval.left = centerX - wrapper.dynamicDiameter / 2;
        oval.top = centerY - wrapper.dynamicDiameter / 2;
        oval.right = oval.left + wrapper.dynamicDiameter;
        oval.bottom = oval.top + wrapper.dynamicDiameter;
        canvas.drawArc(oval, 0, 360, false, paint);
    }

    /**
     * 设置颜色
     *
     * @param colors
     */
    public void setColors(int[] colors) {
        if (colors == null || colors.length == 0)
            return;
        for (int i = 0; i < colors.length && i < this.colors.length; i++) {
            this.colors[i] = colors[i];
        }
    }

    public void setLoadingOffset(boolean isLoading, int offset) {
        this.isLoading = isLoading;
        if (!isLoading && offset % 2 == 0) {
            invalidate();
        }
    }

    /**
     * 内部类
     */
    private class CircleWrapper {
        private int diameter; // 圆的直径
        private int initDiameter; // 初始直径
        private int dynamicDiameter; // 动态直径
        private int orientation; // 方向，即增加还是减少 1:增加 -1为减少
    }
}
