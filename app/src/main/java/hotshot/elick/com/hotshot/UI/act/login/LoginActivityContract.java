package hotshot.elick.com.hotshot.UI.act.login;

import hotshot.elick.com.hotshot.baseMVP.BasePresenter;
import hotshot.elick.com.hotshot.baseMVP.BaseView;

public interface LoginActivityContract {
    interface Presenter extends BasePresenter {
        void doLogin(String userName,String passwd);
    }

    interface View extends BaseView {
        void onLoginSuccess();
    }
}
