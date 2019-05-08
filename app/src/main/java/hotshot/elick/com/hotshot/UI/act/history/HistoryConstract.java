package hotshot.elick.com.hotshot.UI.act.history;

import java.util.List;

import hotshot.elick.com.hotshot.baseMVP.BasePresenter;
import hotshot.elick.com.hotshot.baseMVP.BaseView;
import hotshot.elick.com.hotshot.entity.VideoBean;

public interface HistoryConstract {
    interface Presenter extends BasePresenter {
        void getHistory();
    }

    interface View extends BaseView {
        void updateList(List<VideoBean> list);
    }
}
