package hotshot.elick.com.hotshot.UI.act.login;

import hotshot.elick.com.hotshot.baseMVP.BaseView;

public class LoginActivityPresenter implements LoginActivityContract.Presenter {
    private LoginActivity loginActivity;
    public LoginActivityPresenter(LoginActivity loginActivity) {
    this.loginActivity=loginActivity;
    }

    @Override
    public void attachView(BaseView baseView) {

    }

    @Override
    public void detachView(BaseView baseView) {

    }

    @Override
    public void doLogin(String userName, String passwd) {
        loginActivity.showLoading();
    }
}
