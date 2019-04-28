package hotshot.elick.com.hotshot.UI.fragments.Mine;

import hotshot.elick.com.hotshot.baseMVP.BasePresenter;
import hotshot.elick.com.hotshot.baseMVP.BaseView;
import hotshot.elick.com.hotshot.entity.UserBean;

public interface MineFragmentContract {
    interface Prensenter extends BasePresenter{
        void getUserInfo();
    }
    interface View extends BaseView{
        void updateUserInfo(UserBean userBean);
    }
}
