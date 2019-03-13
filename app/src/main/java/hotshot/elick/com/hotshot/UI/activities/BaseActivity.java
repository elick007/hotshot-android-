package hotshot.elick.com.hotshot.UI.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import hotshot.elick.com.hotshot.baseMVP.BasePresenter;
import hotshot.elick.com.hotshot.baseMVP.BaseView;
import hotshot.elick.com.hotshot.utils.MyLog;

public abstract class BaseActivity<T extends BasePresenter> extends AppCompatActivity implements BaseView{
    protected String TAG = this.getClass().getName();
    protected Unbinder unbinder;
    protected T basePresenter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(setLayoutResId());
        unbinder = ButterKnife.bind(this);
        basePresenter=setPresenter();
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        MyLog.d(TAG + " onResume");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MyLog.d(TAG + " onDestroy");
        unbinder.unbind();
    }

//    public void showErrorLayout() {
//        View view = getLayoutInflater().inflate(R.layout.status_layout, null);
//        setContentView(view);
//        LinearLayout retryView = view.findViewById(R.id.error_frame_layout);
//        retryView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                onErrorRetry();
//            }
//        });
//    }

    protected abstract int setLayoutResId();
    protected abstract T setPresenter();
    protected abstract void initView();

}
