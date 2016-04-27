package cn.qing.soft.appcommonlib.layout;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import cn.qing.soft.appcommonlib.R;

/**
 * 根据比率设置宽高的布局
 */
public class RatioFragmentLayout extends FrameLayout {
    // 宽和高的比例  ratio = 宽 / 高
    private float ratio = 0.0f;

    public RatioFragmentLayout(Context context) {
        this(context, null);
    }

    public RatioFragmentLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RatioFragmentLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.RatioFragmentLayout);
        ratio = a.getFloat(R.styleable.RatioFragmentLayout_ratio, 0.0f);
        a.recycle();
    }

    public void setRatio(float f) {
        ratio = f;
    }


    // 400   399.2 + 0.5 = 399    399.7 + 0.5f = 400.2
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec) - getPaddingLeft() - getPaddingRight();
        int height = MeasureSpec.getSize(heightMeasureSpec) - getPaddingTop() - getPaddingBottom();
        if (widthMode == MeasureSpec.EXACTLY && heightMode != MeasureSpec.EXACTLY && ratio != 0.0f) {
            height = (int) (width / ratio + 0.5f);
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(height + getPaddingTop() + getPaddingBottom(),
                    MeasureSpec.EXACTLY);
        } else if (widthMode != MeasureSpec.EXACTLY && heightMode == MeasureSpec.EXACTLY && ratio != 0.0f) {
            width = (int) (height * ratio + 0.5f);
            widthMeasureSpec = MeasureSpec.makeMeasureSpec(width + getPaddingLeft() + getPaddingRight(),
                    MeasureSpec.EXACTLY);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}