package hotshot.elick.com.hotshot.UI.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import hotshot.elick.com.hotshot.R;
import hotshot.elick.com.hotshot.baseMVP.BasePresenter;
import hotshot.elick.com.hotshot.baseMVP.BaseView;
import hotshot.elick.com.hotshot.utils.MyLog;
import hotshot.elick.com.hotshot.widget.LoadingDialog;
import hotshot.elick.com.hotshot.widget.StatusLayout;

public abstract class BaseFragment<T extends BasePresenter> extends Fragment implements BaseView, StatusLayout.RetryListener {
    protected T basePresenter;
    protected Context context;
    protected String TAG = this.getClass().getSimpleName();
    private Unbinder unbinder;

    private View rootView;
    private boolean isRootViewCreate;
    private boolean isVisible;
    protected StatusLayout statusLayout;
    protected SwipeRefreshLayout swipeRefreshLayout;
    private LoadingDialog loadingDialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.context = getContext();
        MyLog.d(TAG + " onCreate");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(setLayoutResId(), null);
            unbinder = ButterKnife.bind(this, rootView);
        }
        ViewGroup parent = (ViewGroup) rootView.getParent();
        if (parent != null) {
            parent.removeView(rootView);
        }
        basePresenter = setPresenter();
        initView();
        MyLog.d(TAG + " onCreateView");
        isRootViewCreate = true;
        checkLoadable();
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        statusLayout = rootView.findViewById(R.id.status_layout);
        if (statusLayout != null) {
            statusLayout.setRetryListener(this);
        }
        swipeRefreshLayout = rootView.findViewById(R.id.swipe_layout);
        if (swipeRefreshLayout != null) {
            swipeRefreshLayout.setOnRefreshListener(() -> {
                if (statusLayout != null) {
                    statusLayout.setLayoutStatus(StatusLayout.STATUS_LAYOUT_LOADING);
                }
                startLoadData();
            });
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        MyLog.e(TAG + " isvisibletouser=" + isVisibleToUser);
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            isVisible = true;
            checkLoadable();
        }

    }

    private void checkLoadable() {
        if (isRootViewCreate && isVisible) {
            startLoadData();
            //重置以防重复加载
            isVisible = false;
            isRootViewCreate = false;
        }
    }

    @Override
    public void onDestroy() {
        MyLog.d(TAG + " onDestroy");
        if (basePresenter != null) {
            basePresenter = null;
        }
        unbinder.unbind();
        super.onDestroy();
    }

    @Override
    public void onPresenterSuccess() {
        if (swipeRefreshLayout != null)
            swipeRefreshLayout.setRefreshing(false);
        if (statusLayout!=null)
        statusLayout.setLayoutStatus(StatusLayout.STATUS_LAYOUT_GONE);
    }

    @Override
    public void onPresenterFail(String msg) {
        if (swipeRefreshLayout != null)
            swipeRefreshLayout.setRefreshing(false);
        if (!TextUtils.isEmpty(msg))
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
        if (statusLayout!=null)
        statusLayout.setLayoutStatus(StatusLayout.STATUS_LAYOUT_ERROE);
    }

    public void showLoading() {
        if (loadingDialog == null) {
            loadingDialog = new LoadingDialog.Builder(context).build();
        }
        loadingDialog.show();
    }

    public void dismissLoading() {
        if (loadingDialog != null) {
            loadingDialog.dismiss();
        }
    }

    public void showToast(String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    protected abstract int setLayoutResId();

    protected abstract T setPresenter();

    protected abstract void initView();

    protected abstract void startLoadData();

}
