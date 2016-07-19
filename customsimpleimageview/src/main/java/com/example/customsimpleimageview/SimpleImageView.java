package com.example.customsimpleimageview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Administrator on 2016/7/19.
 */
public class SimpleImageView extends View {

    // 画笔
    private Paint mBitmapPaint;

    // 图片Drawable
    private Drawable mDrawable;

    // View的宽度
    private int mWidth;

    // View的高度
    private int mHeight;

    private Bitmap mBitmap;

    public SimpleImageView(Context context) {
        this(context, null);
    }

    public SimpleImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // 根据属性初始化
        initAttrs(attrs);
        // 初始化画笔
        mBitmapPaint = new Paint();
        mBitmapPaint.setAntiAlias(true);
    }

    private void initAttrs(AttributeSet attrs) {
        if (attrs != null) {
            // 属性集
            TypedArray array = null;
            try {
                array = getContext().obtainStyledAttributes(attrs, R.styleable.SimpleImageView);
                // 根据图片id获取到Drawable对象
                mDrawable = array.getDrawable(R.styleable.SimpleImageView_src);
                // 测量Drawable对象的宽、高
                measureDrawable();
            } finally {
                if (array != null) {
                    array.recycle();
                }
            }
        }
    }

    private void measureDrawable() {
        if (mDrawable == null) {
            throw new RuntimeException("drawable不能为空！");
        }
        mWidth = mDrawable.getIntrinsicWidth();
        mHeight = mDrawable.getIntrinsicHeight();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // 获取宽度的模式与大小
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int width = MeasureSpec.getSize(heightMeasureSpec);
        // 高度的模式与大小
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        // 设置View的宽高
        setMeasuredDimension(measureWidth(widthMode, width), measureHeight(heightMode, height));
//        setMeasuredDimension(mWidth, mHeight);
    }

    private int measureWidth(int widthMode, int width) {
        switch (widthMode) {
            case MeasureSpec.UNSPECIFIED:
            case MeasureSpec.AT_MOST:
                break;
            case MeasureSpec.EXACTLY:
                mWidth = width;
                break;
        }
        return mWidth;
    }

    private int measureHeight(int heightMode, int height) {
        switch (heightMode) {
            case MeasureSpec.UNSPECIFIED:
            case MeasureSpec.AT_MOST:
                break;
            case MeasureSpec.EXACTLY:
                mHeight = height;
        }
        return mHeight;
    }

    @Override
    protected void onDraw(Canvas canvas) {
//        if (mDrawable == null) {
//            return;
//        }
        if (mBitmap == null) {
            mBitmap = Bitmap.createScaledBitmap(drawableToBitmap(mDrawable),
                    getMeasuredWidth(), getMeasuredHeight(), true);
        }
        // 绘制图片
        canvas.drawBitmap(mBitmap, getLeft(), getTop(), mBitmapPaint);
        // 保存画布状态
        canvas.save();
        // 旋转90度
        /**
         * 旋转的是坐标系
         */
        canvas.rotate(90);
        // 绘制文字
        mBitmapPaint.setColor(Color.YELLOW);
        mBitmapPaint.setTextSize(30);
        canvas.drawText("Angelababy", getLeft() + 150, getTop() - 150, mBitmapPaint); // 这样就实现竖向文字辣
        // 懒得去找那个工具类了，直接自己写了一个drawableToBitmap
//        canvas.drawBitmap(ImageUtils.drawableToBitmap(mDrawable), getLeft(), getTop(), mBitmapPaint);
        // 恢复原来的状态
        canvas.restore();
    }

    public static Bitmap drawableToBitmap(Drawable drawable) {
        int width = drawable.getIntrinsicWidth();
        int height = drawable.getIntrinsicHeight();
        Bitmap bitmap = Bitmap.createBitmap(width, height,
                drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                        : Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, width, height);
        drawable.draw(canvas);
        return bitmap;
    }

}
