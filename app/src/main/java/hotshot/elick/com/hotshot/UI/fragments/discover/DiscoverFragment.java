package hotshot.elick.com.hotshot.UI.fragments.discover;

import hotshot.elick.com.hotshot.R;
import hotshot.elick.com.hotshot.UI.fragments.BaseFragment;
import hotshot.elick.com.hotshot.baseMVP.BasePresenter;

public class DiscoverFragment extends BaseFragment {
    @Override
    protected int setLayoutResId() {
        return R.layout.discover_fragment_layout;
    }

    @Override
    protected BasePresenter setPresenter() {
        return new DiscoverPresenter();
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void startLoadData() {

    }

    @Override
    public void onRetry() {

    }
}
