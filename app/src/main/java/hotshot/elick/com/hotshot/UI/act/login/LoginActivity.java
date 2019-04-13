package hotshot.elick.com.hotshot.UI.act.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.Selection;
import android.text.Spannable;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import hotshot.elick.com.hotshot.R;
import hotshot.elick.com.hotshot.UI.act.BaseActivity;
import hotshot.elick.com.hotshot.UI.act.register.RegisterActivity;

public class LoginActivity extends BaseActivity<LoginActivityPresenter> implements LoginActivityContract.View, CheckBox.OnCheckedChangeListener {

    @BindView(R.id.act_login_close_iv)
    ImageView actLoginCloseIv;
    @BindView(R.id.act_login_to_register)
    TextView actLoginToRegister;
    @BindView(R.id.act_login_username_et)
    EditText actLoginUsernameEt;
    @BindView(R.id.act_login_passwd_et)
    EditText actLoginPasswdEt;
    @BindView(R.id.act_login_passwd_ic_cb)
    CheckBox actLoginPasswdIcCb;
    @BindView(R.id.act_login_forget_passwd_tv)
    TextView actLoginForgetPasswdTv;
    @BindView(R.id.act_login_login_btn)
    Button actLoginLoginBtn;
    @BindView(R.id.act_login_qq_vg)
    LinearLayout actLoginQqVg;
    @BindView(R.id.act_login_wechat_vg)
    LinearLayout actLoginWechatVg;
    @BindView(R.id.act_login_weibo_vg)
    LinearLayout actLoginWeiboVg;

    @Override
    protected int setLayoutResId() {
        return R.layout.activity_login_layout;
    }

    @Override
    protected LoginActivityPresenter setPresenter() {
        return new LoginActivityPresenter(this);
    }

    @Override
    protected void initView() {
        actLoginPasswdIcCb.setOnCheckedChangeListener(this);
    }

    @Override
    public void onPresenterSuccess() {

    }

    @Override
    public void onPresenterFail(String msg) {

    }

    public static void start(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }

    @OnClick({R.id.act_login_close_iv, R.id.act_login_to_register, R.id.act_login_forget_passwd_tv, R.id.act_login_login_btn, R.id.act_login_qq_vg, R.id.act_login_wechat_vg, R.id.act_login_weibo_vg})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.act_login_close_iv:
                this.finish();
                break;
            case R.id.act_login_to_register:
                RegisterActivity.start(this);
                break;
            case R.id.act_login_forget_passwd_tv:
                break;
            case R.id.act_login_login_btn:
                basePresenter.doLogin(actLoginUsernameEt.getText().toString().trim(), actLoginPasswdEt.getText().toString().trim());
                break;
            case R.id.act_login_qq_vg:
                break;
            case R.id.act_login_wechat_vg:
                break;
            case R.id.act_login_weibo_vg:
                break;
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            //设置EditText文本为可见的
            actLoginPasswdEt.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        } else {
            //设置EditText文本为隐藏的
            actLoginPasswdEt.setTransformationMethod(PasswordTransformationMethod.getInstance());
        }

        actLoginPasswdEt.postInvalidate();
        //切换后将EditText光标置于末尾
        CharSequence charSequence = actLoginPasswdEt.getText();
        if (charSequence instanceof Spannable) {
            Spannable spanText = (Spannable) charSequence;
            Selection.setSelection(spanText, charSequence.length());
        }
    }

    @Override
    public void onLoginSuccess() {
        this.finish();
    }
}
