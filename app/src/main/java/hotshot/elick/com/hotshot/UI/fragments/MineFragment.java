package hotshot.elick.com.hotshot.UI.fragments;

import hotshot.elick.com.hotshot.R;
import hotshot.elick.com.hotshot.baseMVP.BasePresenter;
import hotshot.elick.com.hotshot.entity.ResponseBase;
import hotshot.elick.com.hotshot.entity.ResponseError;

public class MineFragment extends BaseFragment {
    @Override
    protected BasePresenter setPresenter() {
        return null;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void startLoadData() {

    }

    @Override
    protected int setLayoutResId() {
        return R.layout.mine_fragment_layout;
    }

    @Override
    public void onPresenterSuccess(ResponseBase response) {

    }

    @Override
    public void onPresenterFail(ResponseError error) {

    }

    @Override
    public void onRetry() {

    }
}
