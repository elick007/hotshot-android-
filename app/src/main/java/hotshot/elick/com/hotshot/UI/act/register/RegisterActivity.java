package hotshot.elick.com.hotshot.UI.act.register;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import hotshot.elick.com.hotshot.R;
import hotshot.elick.com.hotshot.UI.act.BaseActivity;
import hotshot.elick.com.hotshot.UI.act.login.LoginActivity;
import hotshot.elick.com.hotshot.baseMVP.BasePresenter;

public class RegisterActivity extends BaseActivity<RegisterPresenter> implements RegisterActivityContract.View {
    @BindView(R.id.act_register_close_iv)
    ImageView actRegisterCloseIv;
    @BindView(R.id.act_register_to_login_tv)
    TextView actRegisterToLoginTv;
    @BindView(R.id.act_register_username_et)
    EditText actRegisterUsernameEt;
    @BindView(R.id.act_register_passwd_et)
    EditText actRegisterPasswdEt;
    @BindView(R.id.act_register_phone_et)
    EditText actRegisterPhoneEt;
    @BindView(R.id.act_register_verify_btn)
    Button actRegisterVerifyBtn;
    @BindView(R.id.act_register_verify_et)
    EditText actRegisterVerifyEt;
    @BindView(R.id.act_register_register_btn)
    Button actRegisterRegisterBtn;

    @Override
    protected int setLayoutResId() {
        return R.layout.activity_register_layout;
    }

    @Override
    protected RegisterPresenter setPresenter() {
        return new RegisterPresenter(this);
    }

    @Override
    protected void initView() {

    }

    public static void start(Context context) {
        Intent intent = new Intent(context, RegisterActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onPresenterSuccess() {

    }

    @Override
    public void onPresenterFail(String msg) {

    }


    @OnClick({R.id.act_register_close_iv, R.id.act_register_to_login_tv, R.id.act_register_verify_btn, R.id.act_register_register_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.act_register_close_iv:
                this.finish();
                break;
            case R.id.act_register_to_login_tv:
                LoginActivity.start(this);
                break;
            case R.id.act_register_verify_btn:
                basePresenter.requireVerify(actRegisterPhoneEt.getText().toString().trim(), actRegisterVerifyBtn);
                break;
            case R.id.act_register_register_btn:
                basePresenter.doRegister(actRegisterUsernameEt.getText().toString().trim(), actRegisterPasswdEt.getText().toString().trim(),
                        actRegisterPhoneEt.getText().toString().trim(), actRegisterVerifyEt.getText().toString().trim());
                break;
        }
    }

    @Override
    public void registerSuccess() {
        this.finish();
    }
}
