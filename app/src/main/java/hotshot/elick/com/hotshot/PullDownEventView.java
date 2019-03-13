package hotshot.elick.com.hotshot;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import hotshot.elick.com.hotshot.utils.DensityUtil;
import hotshot.elick.com.hotshot.utils.MyLog;
import hotshot.elick.com.hotshot.utils.ScrollUtil;

public class PullDownEventView extends LinearLayout {
    private TextView headerText;
    private LayoutParams layoutParams;
    private float lastY;
    private float offsetLimit = 280;
    private float hintOccurMin = 30;
    private float hintOccurMax = 180;
    private int TouchSlop;
    private int textWidth;
    private int textSize = 16;
    private int marginWidth;
    private String s = "下   拉   关   闭   页   面";
    private PullDownListener pullDownListener;
    private int textBgColor;

    public PullDownEventView(Context context) {
        this(context,null);
    }

    public PullDownEventView(Context context, @Nullable AttributeSet attrs) {
        this(context,attrs,0);
    }

    public PullDownEventView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray=context.obtainStyledAttributes(R.styleable.PullDownEventView);
        textBgColor=typedArray.getColor(R.styleable.PullDownEventView_text_bg_color,Color.GRAY);
        initView();
    }

    private void initView() {
        this.TouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
        this.textWidth = DensityUtil.dip2px(getContext(),getTextWidth(s));

        int screenWidth = getScreenWidth();
        this.marginWidth = (screenWidth - textWidth) / 2;
        headerText = new TextView(getContext());
        headerText.setGravity(Gravity.BOTTOM);
        headerText.setBackgroundColor(textBgColor);
        headerText.setTextSize(textSize);
        headerText.setPadding(marginWidth,0,0,0);

        layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0);
        layoutParams.gravity = Gravity.BOTTOM;
        //layoutParams.leftMargin = DensityUtil.dip2px(getContext(), marginWidth);
        addView(headerText, layoutParams);


    }

    public void setPullDownListener(PullDownListener pullDownListener) {
        this.pullDownListener = pullDownListener;
    }

    private int getTextWidth(String s) {
        Paint paint = new Paint();
        paint.setTextSize(textSize);
        return (int) paint.measureText(s);
//        Paint paint = new Paint();
//        Rect rect = new Rect();
//        paint.getTextBounds(s, 0, s.length(), rect);
//       return rect.width();

    }

//    @Override
//    public boolean dispatchTouchEvent(MotionEvent ev) {
//        switch (ev.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//                this.lastY = ev.getY();
//                break;
//            case MotionEvent.ACTION_MOVE:
//                float offsetDis = ev.getY() - lastY;
//                updateHeaderTV(offsetDis);
//                break;
//            case MotionEvent.ACTION_UP:
//                float dis = (ev.getY() - lastY) / 2;
//                if (dis >= hintOccurMax) {
//                    if (pullDownListener != null) {
//                        pullDownListener.enoughToRealese();
//                    }
//                } else {
//                    ValueAnimator valueAnimator = ValueAnimator.ofFloat(dis, 0);
//                    valueAnimator.addUpdateListener(valueAnimator1 -> {
//                        float currentValue = (float) valueAnimator1.getAnimatedValue();
//                        updateHeaderTV(currentValue);
//                    });
//                    valueAnimator.start();
//                }
//                break;
//        }
//        return true;
//    }

    private float downY;
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean intercept=false;
        int touchMode=ev.getAction();
        switch (touchMode){
            case MotionEvent.ACTION_DOWN:
                intercept=false;
                downY=ev.getY();
                super.onInterceptTouchEvent(ev);
                break;
            case MotionEvent.ACTION_MOVE:
                //下滑状态判断
                if (ev.getY()-downY<0){
                    intercept=false;
                }else {
                    intercept= !ScrollUtil.canScrollDown(this.getChildAt(0));
                }
                MyLog.e("can scroll down="+intercept);
                break;
            case MotionEvent.ACTION_UP:
                intercept=false;
                break;
        }
        return intercept;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                this.lastY = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                float offsetDis = ev.getY() - lastY;
                updateHeaderTV(offsetDis);
                break;
            case MotionEvent.ACTION_UP:
                float dis = (ev.getY() - lastY) / 2;
                if (dis >= hintOccurMax) {
                    if (pullDownListener != null) {
                        pullDownListener.enoughToRealese();
                    }
                } else {
                    ValueAnimator valueAnimator = ValueAnimator.ofFloat(dis, 0);
                    valueAnimator.addUpdateListener(valueAnimator1 -> {
                        float currentValue = (float) valueAnimator1.getAnimatedValue();
                        updateHeaderTV(currentValue);
                    });
                    valueAnimator.start();
                }
                break;
        }
        return true;
    }

    private void updateHeaderTV(float offsetDis) {
        int dis = (int) (offsetDis / 2);//设置尼龙，避免下拉过大
        if (dis < offsetLimit & dis >= 0) {
            layoutParams.height = dis;
            if (dis >= hintOccurMin) {
                int index = (int) ((dis - hintOccurMin) / ((hintOccurMax - hintOccurMin) / s.length()));
                if (index >= s.length()) {
                    index = s.length();
                }
                headerText.setText(s.substring(0, index));
            }
            this.updateViewLayout(headerText, layoutParams);
        }
    }

    public interface PullDownListener {
        void enoughToRealese();
    }

    public int getScreenWidth() {
        Resources resources = getContext().getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        return dm.widthPixels;
    }

}
