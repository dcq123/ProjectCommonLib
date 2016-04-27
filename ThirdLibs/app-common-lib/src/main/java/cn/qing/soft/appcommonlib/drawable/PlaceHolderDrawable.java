package cn.qing.soft.appcommonlib.drawable;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;

import cn.qing.soft.appcommonlib.R;


/**
 * 占位图Drawable
 */
public class PlaceHolderDrawable extends Drawable {

    private Paint mPaint;
    private RectF rectF;
    private Bitmap mBitmap;
    private int mBitmapWidth;
    private int mBitmapHeight;

    public PlaceHolderDrawable(Context context) {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.parseColor("#ECEAEA"));

        BitmapFactory.Options options = new BitmapFactory.Options();
        mBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_none, options);
        mBitmapWidth = options.outWidth;
        mBitmapHeight = options.outHeight;
    }

    @Override
    public void setBounds(int left, int top, int right, int bottom) {
        super.setBounds(left, top, right, bottom);
        rectF = new RectF(left, top, right, bottom);
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawRect(rectF, mPaint);

        int left = (int) ((rectF.right - mBitmapWidth) / 2);
        int top = (int) ((rectF.bottom - mBitmapHeight) / 2);
        canvas.drawBitmap(mBitmap, left, top, mPaint);

    }

    @Override
    public void setAlpha(int alpha) {
        mPaint.setAlpha(alpha);
    }

    @Override
    public void setColorFilter(ColorFilter colorFilter) {
        mPaint.setColorFilter(colorFilter);
    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }
}
