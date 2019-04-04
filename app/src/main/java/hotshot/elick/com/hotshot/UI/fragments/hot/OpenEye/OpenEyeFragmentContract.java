package hotshot.elick.com.hotshot.UI.fragments.hot.OpenEye;

import java.util.List;

import hotshot.elick.com.hotshot.baseMVP.BasePresenter;
import hotshot.elick.com.hotshot.baseMVP.BaseView;
import hotshot.elick.com.hotshot.entity.VideoBean;

public interface OpenEyeFragmentContract {
    interface Presenter extends BasePresenter {
        void getHotVideo();
    }

    interface View extends BaseView {
        void updateHotVideo(List<VideoBean> list);
    }
}
