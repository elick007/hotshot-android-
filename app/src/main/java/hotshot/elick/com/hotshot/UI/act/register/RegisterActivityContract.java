package hotshot.elick.com.hotshot.UI.act.register;

import android.widget.Button;

import hotshot.elick.com.hotshot.baseMVP.BasePresenter;
import hotshot.elick.com.hotshot.baseMVP.BaseView;

public interface RegisterActivityContract {
    interface Presenter extends BasePresenter{
        void requireVerify(String phone, Button button);
        void doRegister(String username,String passwd,String phone,String verify);
    }
    interface View extends BaseView{
        void registerSuccess();
    }
}
