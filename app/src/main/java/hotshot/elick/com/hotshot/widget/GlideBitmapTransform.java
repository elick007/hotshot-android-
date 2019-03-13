package hotshot.elick.com.hotshot.widget;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.bumptech.glide.load.resource.bitmap.TransformationUtils;

import java.security.MessageDigest;

import hotshot.elick.com.hotshot.utils.MyLog;

public class GlideBitmapTransform extends BitmapTransformation {
    private float radius;
    private int borderWidth;
    private int borderColor;
    private TransformType transformType;

    public enum TransformType {
        ALL,
        RADIUS,
        BORDER,
    }

    public GlideBitmapTransform(float radius) {
        this(radius, 0, 0, TransformType.RADIUS);
    }

    public GlideBitmapTransform(int borderWidth, int borderColor) {
        this(0, borderWidth, borderColor, TransformType.BORDER);
    }

    public GlideBitmapTransform(float radius, int borderWidth, int borderColor) {
        this(radius, borderWidth, borderColor, TransformType.ALL);
    }

    private GlideBitmapTransform(float radius, int borderWidth, int borderColor, TransformType transformType) {
        this.radius = Resources.getSystem().getDisplayMetrics().density * radius;
        this.borderWidth = (int) (Resources.getSystem().getDisplayMetrics().density * borderWidth);
        this.borderColor = borderColor;
        this.transformType = transformType;
    }

    @Override
    protected Bitmap transform(@NonNull BitmapPool pool, @NonNull Bitmap toTransform, int outWidth, int outHeight) {
        Bitmap bitmap = TransformationUtils.centerCrop(pool, toTransform, outWidth, outHeight);
        int bitmapWidth = bitmap.getWidth();
        int bitmapHeight = bitmap.getHeight();
        Bitmap result = pool.get(bitmapWidth, bitmapHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(result);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setShader(new BitmapShader(bitmap, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));
        switch (transformType) {
            case ALL:
                roundAndBorderBitmap(canvas, paint, bitmapWidth, bitmapHeight);
                break;
            case BORDER:
                borderBitmap(canvas, paint, bitmapWidth, bitmapHeight);
                break;
            case RADIUS:
                roundBitmap(canvas, paint, bitmapWidth, bitmapHeight);
                break;
            default:
        }
        return result;
    }

    private void roundBitmap(Canvas canvas, Paint paint, int width, int height) {
        RectF rectF = new RectF(0f, 0f, width, height);
        canvas.drawRoundRect(rectF, radius, radius, paint);
    }

    private void borderBitmap(Canvas canvas, Paint paint, int width, int height) {
        roundBitmap(canvas, paint, width, height);
        Paint strokePaint = new Paint();
        strokePaint.setStyle(Paint.Style.STROKE);
        if (borderColor == 0) {
            strokePaint.setColor(Color.BLACK);
        } else {
            strokePaint.setColor(borderColor);
        }
        strokePaint.setAntiAlias(true);
        strokePaint.setStrokeWidth(borderWidth);
        strokePaint.setDither(true);
        canvas.drawRoundRect(new RectF(0f, 0f, width, height), radius, radius, strokePaint);
    }

    private void roundAndBorderBitmap(Canvas canvas, Paint paint, int width, int height) {
        roundBitmap(canvas, paint, width, height);
        borderBitmap(canvas, paint, width, height);
    }

    public String getId() {
        return getClass().getName() + "(radius=" + radius + "border=" + borderWidth + "borderColor=" + borderColor + "transformType=" + transformType.name() + ")";
    }

    @Override
    public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {

    }
}
