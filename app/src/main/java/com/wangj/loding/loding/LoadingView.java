package com.wangj.loding.loding;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

@SuppressLint("AppCompatCustomView")
public class LoadingView extends ImageView{

    private   ZLoadingDrawable mZLoadingDrawable;
    protected LoadingBuilder  mZLoadingBuilder;

    public LoadingView(Context context)
    {
        this(context, null);
    }

    public LoadingView(Context context, AttributeSet attrs)
    {
        this(context, attrs, -1);
    }

    public LoadingView(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs)
    {
        try
        {
            TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.ZLoadingView);
            int typeId = ta.getInt(R.styleable.ZLoadingView_z_type, 0);
            int color = ta.getColor(R.styleable.ZLoadingView_z_color, Color.BLACK);
            float durationTimePercent = ta.getFloat(R.styleable.ZLoadingView_z_duration_percent, 1.0f);
            ta.recycle();
            setLoadingBuilder(new TestBuilder(), durationTimePercent);
            setColorFilter(color);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void setLoadingBuilder(@NonNull LoadingBuilder builder)
    {
        mZLoadingBuilder = builder;
        initZLoadingDrawable();
    }

    public void setLoadingBuilder(@NonNull LoadingBuilder builder, double durationPercent)
    {
        this.setLoadingBuilder(builder);
        initDurationTimePercent(durationPercent);
    }

    private void initZLoadingDrawable()
    {
        if (mZLoadingBuilder == null)
        {
            throw new RuntimeException("mZLoadingBuilder is null.");
        }
        mZLoadingDrawable = new ZLoadingDrawable(mZLoadingBuilder);
        mZLoadingDrawable.initParams(getContext());
        setImageDrawable(mZLoadingDrawable);
    }

    private void initDurationTimePercent(double durationPercent)
    {
        if (mZLoadingBuilder == null)
        {
            throw new RuntimeException("mZLoadingBuilder is null.");
        }
        mZLoadingBuilder.setDurationTimePercent(durationPercent);
    }

    @Override
    protected void onAttachedToWindow()
    {
        super.onAttachedToWindow();
        startAnimation();
    }

    @Override
    protected void onDetachedFromWindow()
    {
        super.onDetachedFromWindow();
        stopAnimation();
    }

    @Override
    protected void onVisibilityChanged(@NonNull View changedView, int visibility)
    {
        super.onVisibilityChanged(changedView, visibility);
        final boolean visible = visibility == VISIBLE && getVisibility() == VISIBLE;
        if (visible)
        {
            startAnimation();
        }
        else
        {
            stopAnimation();
        }
    }

    private void startAnimation()
    {
        if (mZLoadingDrawable != null)
        {
            mZLoadingDrawable.start();
        }
    }

    private void stopAnimation()
    {
        if (mZLoadingDrawable != null)
        {
            mZLoadingDrawable.stop();
        }
    }

}
