package hotshot.elick.com.hotshot.UI.fragments.favorite;

import java.util.List;

import hotshot.elick.com.hotshot.baseMVP.BasePresenter;
import hotshot.elick.com.hotshot.baseMVP.BaseView;
import hotshot.elick.com.hotshot.entity.FavVideo;
import hotshot.elick.com.hotshot.entity.VideoBean;

public interface FavContract {
    interface Presenter extends BasePresenter {
        void getFav(String channel);
    }

    interface View extends BaseView {
        void updateList(List<FavVideo> list);
    }
}
