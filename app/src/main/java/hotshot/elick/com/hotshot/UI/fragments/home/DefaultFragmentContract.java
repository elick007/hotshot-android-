package hotshot.elick.com.hotshot.UI.fragments.home;

import java.util.List;

import hotshot.elick.com.hotshot.baseMVP.BasePresenter;
import hotshot.elick.com.hotshot.baseMVP.BaseView;
import hotshot.elick.com.hotshot.entity.VideoBean;

public interface DefaultFragmentContract {
    interface View extends BaseView{
        void updateOE(List<VideoBean> list);
        void updateDY(List<VideoBean> list);
        void updateLSP(List<VideoBean> list);
        void updateBanner(List<VideoBean> list);
    }
    interface Presenter extends BasePresenter{
        void getOERandom();
        void getDYRandom();
        void getLSPRandom();
        void getBanner();
    }
}
