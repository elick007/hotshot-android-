package hotshot.elick.com.hotshot.UI.fragments.Mine;


import hotshot.elick.com.hotshot.MyApplication;
import hotshot.elick.com.hotshot.baseMVP.BaseView;
import hotshot.elick.com.hotshot.entity.UserBean;

public class MinePresenter implements MineFragmentContract.Prensenter {
    private MineFragment mineFragment;
    public MinePresenter(MineFragment mineFragment) {
        this.mineFragment=mineFragment;
    }

    @Override
    public void getUserInfo() {
       UserBean userBean= MyApplication.self.userInfoData.getUser();
       mineFragment.updateUserInfo(userBean);
    }

    @Override
    public void attachView(BaseView baseView) {

    }

    @Override
    public void detachView(BaseView baseView) {

    }
}
