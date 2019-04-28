package hotshot.elick.com.hotshot.UI.act.alterInfo;

import hotshot.elick.com.hotshot.baseMVP.BasePresenter;
import hotshot.elick.com.hotshot.baseMVP.BaseView;
import hotshot.elick.com.hotshot.entity.UserBean;

public interface AlterUserInfoContract {
    interface Presenter extends BasePresenter{
        void getUserInfo();
        void alterAvatar(String filePath);
    }
    interface View extends BaseView{
        void updateUserInfo(UserBean userBean);
        void updateAvatar();
    }
}
