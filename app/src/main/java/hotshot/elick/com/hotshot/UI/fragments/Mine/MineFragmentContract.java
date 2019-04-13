package hotshot.elick.com.hotshot.UI.fragments.Mine;

import hotshot.elick.com.hotshot.baseMVP.BasePresenter;
import hotshot.elick.com.hotshot.baseMVP.BaseView;

public interface MineFragmentContract {
    interface Prensenter extends BasePresenter{
        void getUserInfo();
    }
    interface View extends BaseView{

    }
}
