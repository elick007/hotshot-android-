package hotshot.elick.com.hotshot.UI.fragments.Mine;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import hotshot.elick.com.hotshot.R;
import hotshot.elick.com.hotshot.UI.act.login.LoginActivity;
import hotshot.elick.com.hotshot.UI.fragments.BaseFragment;
import hotshot.elick.com.hotshot.widget.CircleImageView;

public class MineFragment extends BaseFragment<MinePresenter> implements MineFragmentContract.View {
    @BindView(R.id.mine_fragment_user_avatar)
    CircleImageView mineFragmentUserAvatar;
    @BindView(R.id.mine_fragment_username)
    TextView mineFragmentUsername;
    @BindView(R.id.mine_fragment_change_info)
    FrameLayout mineFragmentChangeInfo;
    @BindView(R.id.mine_fragment_history)
    LinearLayout mineFragmentHistory;
    @BindView(R.id.mine_fragment_favor)
    LinearLayout mineFragmentFavor;
    @BindView(R.id.mine_fragment_download)
    LinearLayout mineFragmentDownload;
    @BindView(R.id.mine_fragment_setting)
    FrameLayout mineFragmentSetting;
    @BindView(R.id.mine_fragment_about)
    FrameLayout mineFragmentAbout;
    @BindView(R.id.mine_fragment_help)
    FrameLayout mineFragmentHelp;

    @Override
    protected MinePresenter setPresenter() {
        return new MinePresenter();
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
    public void onRetry() {

    }

    @OnClick({R.id.mine_fragment_user_avatar, R.id.mine_fragment_change_info, R.id.mine_fragment_history, R.id.mine_fragment_favor, R.id.mine_fragment_download, R.id.mine_fragment_setting, R.id.mine_fragment_about, R.id.mine_fragment_help})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.mine_fragment_user_avatar:
                LoginActivity.start(context);
                break;
            case R.id.mine_fragment_change_info:
                break;
            case R.id.mine_fragment_history:
                break;
            case R.id.mine_fragment_favor:
                break;
            case R.id.mine_fragment_download:
                break;
            case R.id.mine_fragment_setting:
                break;
            case R.id.mine_fragment_about:
                break;
            case R.id.mine_fragment_help:
                break;
        }
    }
}
