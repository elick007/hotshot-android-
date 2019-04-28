package hotshot.elick.com.hotshot.UI.act.alterInfo;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.tools.PictureFileUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import hotshot.elick.com.hotshot.MyApplication;
import hotshot.elick.com.hotshot.R;
import hotshot.elick.com.hotshot.UI.act.BaseActivity;
import hotshot.elick.com.hotshot.api.RetrofitService;
import hotshot.elick.com.hotshot.entity.UserBean;
import hotshot.elick.com.hotshot.utils.MyLog;
import hotshot.elick.com.hotshot.widget.CircleImageView;

public class AlterUserInfoAct extends BaseActivity<AlterUserInfoPresenter> implements AlterUserInfoContract.View {

    @BindView(R.id.back_btn)
    Button backBtn;
    @BindView(R.id.user_avatar)
    CircleImageView userAvatar;
    @BindView(R.id.edit_avatar)
    FrameLayout editAvatar;
    @BindView(R.id.username_tv)
    TextView usernameTv;
    @BindView(R.id.edit_username)
    FrameLayout editUsername;
    @BindView(R.id.phone_tv)
    TextView phoneTv;
    @BindView(R.id.edit_phone)
    FrameLayout editPhone;
    @BindView(R.id.edit_passwd)
    FrameLayout editPasswd;
    @BindView(R.id.logout_btn)
    Button logoutBtn;
    public static final int ACT_CODE = 2000;

    @Override
    protected int setLayoutResId() {
        return R.layout.activity_alter_user_info;
    }

    @Override
    protected AlterUserInfoPresenter setPresenter() {
        return new AlterUserInfoPresenter(this);
    }

    @Override
    protected void initView() {
        basePresenter.getUserInfo();
    }

    @Override
    public void onPresenterSuccess() {

    }

    @Override
    public void onPresenterFail(String msg) {

    }

    @OnClick({R.id.back_btn, R.id.edit_avatar, R.id.edit_username, R.id.edit_phone, R.id.edit_passwd, R.id.logout_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_btn:
                this.finish();
                break;
            case R.id.edit_avatar:
                PictureSelector.create(this)
                        .openGallery(PictureMimeType.ofImage())
                        .selectionMode(PictureConfig.SINGLE)
                        .compress(true)
                        .enableCrop(true)
                        .withAspectRatio(1, 1)
                        .forResult(PictureConfig.CHOOSE_REQUEST);
                break;
            case R.id.edit_username:
                break;
            case R.id.edit_phone:
                break;
            case R.id.edit_passwd:
                break;
            case R.id.logout_btn:
                MyApplication.self.sp.edit().clear().apply();
                MyApplication.self.userInfoData.deleteUser();
                setResult(RESULT_OK);
                this.finish();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    List<LocalMedia> pictures = PictureSelector.obtainMultipleResult(data);
                    if (pictures.size() > 0) {
                        String imagePath = pictures.get(0).getCompressPath();
                        basePresenter.alterAvatar(imagePath);
                    }
                    break;
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PictureFileUtils.deleteCacheDirFile(this);
    }

    @Override
    public void updateUserInfo(UserBean userBean) {
        setResult(RESULT_OK);
        Glide.with(this).load(RetrofitService.BASE_URL + userBean.getAvatar()).into(userAvatar);
        usernameTv.setText(userBean.getUsername());
        phoneTv.setText(userBean.getPhone());
    }

    @Override
    public void updateAvatar() {
    }
}
