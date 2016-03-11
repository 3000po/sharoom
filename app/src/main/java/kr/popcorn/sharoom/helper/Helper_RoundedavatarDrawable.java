package kr.popcorn.sharoom.helper;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.os.Environment;

/**
 * Created by Administrator on 2016-03-10.
 */

//안드로이드의 Drawable을 이용해서 이미지가  draw될때 이미지를 라운딩 처리해서 그리는 방법이다.
    //http://www.kmshack.kr/2013/07/%ED%9A%A8%EC%9C%A8%EC%A0%81%EC%9D%B8-bitmap-%EC%9D%B4%EB%AF%B8%EC%A7%80-%EB%9D%BC%EC%9A%B4%EB%94%A9-%EC%B2%98%EB%A6%AC%EB%B0%A9%EB%B2%95/
public class Helper_RoundedavatarDrawable extends Drawable{
    private final Bitmap mBitmap;
    private final Paint mPaint;
    private final RectF mRectF;
    private final int mBitmapWidth;
    private final int mBitmapHeight;

        public Helper_RoundedavatarDrawable(Bitmap bitmap) {
            mBitmap = bitmap;
            mRectF = new RectF();
            mPaint = new Paint();
            mPaint.setAntiAlias(true);
            mPaint.setDither(true);
            final BitmapShader shader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
            mPaint.setShader(shader);

            mBitmapWidth = mBitmap.getWidth();
            mBitmapHeight = mBitmap.getHeight();
        }

        @Override
        public void draw(Canvas canvas) {
            canvas.drawOval(mRectF, mPaint);
        }

        @Override
        protected void onBoundsChange(Rect bounds) {
            super.onBoundsChange(bounds);

            mRectF.set(bounds);
        }

        @Override
        public void setAlpha(int alpha) {
            if (mPaint.getAlpha() != alpha) {
                mPaint.setAlpha(alpha);
                invalidateSelf();
            }
        }

        @Override
        public void setColorFilter(ColorFilter colorFilter) {

        }

        @Override
        public int getOpacity() {
            return PixelFormat.TRANSLUCENT;
        }

        @Override
        public int getIntrinsicWidth() {
            return mBitmapWidth;
        }

        @Override
        public int getIntrinsicHeight() {
            return mBitmapHeight;
        }

        public void setAntiAlias(boolean aa) {
            mPaint.setAntiAlias(aa);
            invalidateSelf();
        }

        @Override
        public void setFilterBitmap(boolean filter) {
            mPaint.setFilterBitmap(filter);
            invalidateSelf();
        }

        @Override
        public void setDither(boolean dither) {
            mPaint.setDither(dither);
            invalidateSelf();
        }

        public Bitmap getBitmap() {
            return mBitmap;
        }


}