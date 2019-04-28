package hotshot.elick.com.hotshot.UI.fragments.discover;

import java.util.List;

import hotshot.elick.com.hotshot.baseMVP.BasePresenter;
import hotshot.elick.com.hotshot.baseMVP.BaseView;
import hotshot.elick.com.hotshot.entity.PubVideoBean;

public interface DiscoverFragmentContract {
    interface Presenter extends BasePresenter {
        void getPubList();
        void uploadVideo(String filePath,String content);
    }

    interface View extends BaseView {
        void updateList(List<PubVideoBean> list);
    }
}
