package hotshot.elick.com.hotshot.UI.fragments.hot.LiVideo;

import java.util.List;

import hotshot.elick.com.hotshot.baseMVP.BasePresenter;
import hotshot.elick.com.hotshot.baseMVP.BaseView;
import hotshot.elick.com.hotshot.entity.VideoBean;

public interface LiVideoFragmentContract {
    interface View extends BaseView{
        void updateLSPHot(List<VideoBean> list);
    }
    interface Presenter extends BasePresenter{
        void getLSPHot();
    }
}
