package hotshot.elick.com.hotshot.UI.fragments.hot.DouYin;

import java.util.List;

import hotshot.elick.com.hotshot.baseMVP.BasePresenter;
import hotshot.elick.com.hotshot.baseMVP.BaseView;
import hotshot.elick.com.hotshot.entity.VideoBean;

public interface DouYinFragmentContract {
    interface Presenter extends BasePresenter {
        void getDYHot();
    }

    interface View extends BaseView {
        void updateDYHot(List<VideoBean> list);
    }
}
