package hotshot.elick.com.hotshot.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import hotshot.elick.com.hotshot.R;

public class StatusLayout extends LinearLayout {
    public static final int STATUS_LAYOUT_LOADING = 3000;
    public static final int STATUS_LAYOUT_ERROE = 3001;
    public static final int STATUS_LAYOUT_EMPTY = 3002;
    public static final int STATUS_LAYOUT_GONE = 3004;
    private int currentStatus = STATUS_LAYOUT_LOADING;
    private View rootView;
    private TextView titleTV;
    private TextView secondTitleTV;
    private GradientTextView gradientTextView;
    private RetryListener retryListener;

    public StatusLayout(Context context) {
        this(context, null);
    }

    public StatusLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StatusLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        rootView = LayoutInflater.from(getContext()).inflate(R.layout.status_layout, this);
        titleTV = rootView.findViewById(R.id.status_layout_status_title);
        secondTitleTV = rootView.findViewById(R.id.status_layout_second_title);
        gradientTextView = rootView.findViewById(R.id.status_layout_loading_tv);
        rootView.setOnClickListener(v -> {
            if (retryListener!=null&&currentStatus==STATUS_LAYOUT_ERROE){
                retryListener.onRetry();
            }
        });
    }

    public void setLayoutStatus(int status) {
        if (status == STATUS_LAYOUT_GONE) {
            this.setVisibility(GONE);
            return;
        } else {
            this.setVisibility(VISIBLE);
        }
        switch (status) {
            case StatusLayout.STATUS_LAYOUT_LOADING:
                if (currentStatus != STATUS_LAYOUT_LOADING) {
                    titleTV.setVisibility(GONE);
                    secondTitleTV.setVisibility(GONE);
                    gradientTextView.setVisibility(VISIBLE);
                    currentStatus = STATUS_LAYOUT_LOADING;
                }
                break;
            case StatusLayout.STATUS_LAYOUT_EMPTY:
                if (currentStatus != STATUS_LAYOUT_EMPTY) {
                    titleTV.setVisibility(VISIBLE);
                    secondTitleTV.setVisibility(VISIBLE);
                    gradientTextView.setVisibility(GONE);
                    currentStatus = STATUS_LAYOUT_EMPTY;
                    secondTitleTV.setText("当前页面没有内容");
                }
                break;
            case STATUS_LAYOUT_ERROE:
                if (currentStatus != STATUS_LAYOUT_ERROE) {
                    titleTV.setVisibility(VISIBLE);
                    secondTitleTV.setVisibility(VISIBLE);
                    gradientTextView.setVisibility(GONE);
                    currentStatus = STATUS_LAYOUT_ERROE;
                    secondTitleTV.setText("加载出错，点击屏幕重试");
                }
                break;
            default:


        }
    }

    public void setRetryListener(RetryListener retryListener) {
        this.retryListener = retryListener;
    }

    public interface RetryListener {
        void onRetry();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return true;
    }

}
