package hotshot.elick.com.hotshot.UI.fragments.Mine;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.OnClick;
import hotshot.elick.com.hotshot.R;
import hotshot.elick.com.hotshot.UI.act.alterInfo.AlterUserInfoAct;
import hotshot.elick.com.hotshot.UI.act.login.LoginActivity;
import hotshot.elick.com.hotshot.UI.fragments.BaseFragment;
import hotshot.elick.com.hotshot.api.RetrofitService;
import hotshot.elick.com.hotshot.entity.UserBean;
import hotshot.elick.com.hotshot.widget.CircleImageView;

import static android.app.Activity.RESULT_OK;
import static hotshot.elick.com.hotshot.UI.act.login.LoginActivity.LOGIN_ACT_CODE;

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
    private boolean isLogin = false;

    @Override
    protected MinePresenter setPresenter() {
        return new MinePresenter(this);
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void startLoadData() {
        basePresenter.getUserInfo();
    }

    @Override
    protected int setLayoutResId() {
        return R.layout.mine_fragment_layout;
    }

    @Override
    public void onRetry() {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case AlterUserInfoAct.ACT_CODE:
                    basePresenter.getUserInfo();
                    break;
                case LOGIN_ACT_CODE:
                    isLogin = true;
                    basePresenter.getUserInfo();
                    break;

            }
        }
    }


    @OnClick({R.id.mine_fragment_user_avatar, R.id.mine_fragment_change_info, R.id.mine_fragment_history, R.id.mine_fragment_favor, R.id.mine_fragment_download, R.id.mine_fragment_setting, R.id.mine_fragment_about, R.id.mine_fragment_help})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.mine_fragment_user_avatar:
                if (!isLogin) {
                    Intent intent = new Intent(context, LoginActivity.class);
                    startActivityForResult(intent, LOGIN_ACT_CODE);
                } else {
                    Intent intent = new Intent(context, AlterUserInfoAct.class);
                    startActivityForResult(intent, AlterUserInfoAct.ACT_CODE);
                }
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

    @Override
    public void updateUserInfo(UserBean userBean) {
        if (userBean == null) {
            isLogin = false;
            mineFragmentUsername.setText(String.format("%s", "未登录"));
            mineFragmentUserAvatar.setImageResource(R.drawable.ic_default);
        } else {
            isLogin = true;
            mineFragmentUsername.setText(userBean.getUsername());
            Glide.with(context).load(RetrofitService.BASE_URL + userBean.getAvatar()).into(mineFragmentUserAvatar);
        }
    }
}
