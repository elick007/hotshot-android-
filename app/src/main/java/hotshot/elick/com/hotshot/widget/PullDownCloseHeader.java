package hotshot.elick.com.hotshot.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.FrameLayout;
import android.widget.TextView;

import hotshot.elick.com.hotshot.R;
import hotshot.elick.com.hotshot.utils.DensityUtil;
import hotshot.elick.com.hotshot.utils.MyLog;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrUIHandler;
import in.srain.cube.views.ptr.PtrUIHandlerHook;
import in.srain.cube.views.ptr.header.MaterialProgressDrawable;
import in.srain.cube.views.ptr.indicator.PtrIndicator;

public class PullDownCloseHeader extends FrameLayout implements PtrUIHandler {
    private String text = "下  拉   关   闭   页   面";
    private int textWidth;
    private int textHeight=160;
    private int screenWidth;
    private int bgColor;
    private int textSize = 16;
    private TextView textView;
    private LayoutParams layoutParams;

    public PullDownCloseHeader(Context context) {
        super(context);
        initView();
    }

    public PullDownCloseHeader(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public PullDownCloseHeader(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        //LayoutInflater.from(getContext()).inflate(R.layout.pull_down_close_header_layout,this);
        //textView=findViewById(R.id.pull_down_close_header_tv);
        screenWidth = getResources().getDisplayMetrics().widthPixels;
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setTextSize(textSize);
        textWidth = DensityUtil.dip2px(getContext(), paint.measureText(text));
        if (textView==null){
            textView=new TextView(getContext());
        }
        textView.setGravity(Gravity.BOTTOM);
        textView.setTextSize(textSize);
        textView.setTextColor(Color.WHITE);
        textView.setPadding((screenWidth - textWidth) / 2, 0, 0, 0);
        if (layoutParams == null) {
            layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DensityUtil.dip2px(getContext(),50));
        }
        layoutParams.gravity = Gravity.BOTTOM;
        addView(textView,layoutParams);
    }


    @Override
    public void onUIReset(PtrFrameLayout frame) {

    }

    @Override
    public void onUIRefreshPrepare(PtrFrameLayout frame) {
        layoutParams.height=ViewGroup.LayoutParams.WRAP_CONTENT;
    }

    @Override
    public void onUIRefreshBegin(PtrFrameLayout frame) {

    }

    @Override
    public void onUIRefreshComplete(PtrFrameLayout frame) {

    }

    @Override
    public void onUIPositionChange(PtrFrameLayout frame, boolean isUnderTouch, byte status, PtrIndicator ptrIndicator) {
        float currentPercent = Math.min(1f, ptrIndicator.getCurrentPercent());
        textView.setText(text.substring(0, (int) (text.length()*currentPercent)));
        //updateTV();
    }
}
