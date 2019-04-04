package hotshot.elick.com.hotshot.UI.activities.player;

import java.util.List;

import hotshot.elick.com.hotshot.baseMVP.BasePresenter;
import hotshot.elick.com.hotshot.baseMVP.BaseView;
import hotshot.elick.com.hotshot.entity.VideoBean;

public class PlayerActivityContract {
    interface Presenter extends BasePresenter{
        void retrieveFav(String channel,String uid,String videoId);
        void deleteFav(String channel,String uid,String videoId);
        void getRandomVideos(String channel);
    }

    interface View extends BaseView{
        void updateFav(boolean isFav);
        void updateRandom(List<VideoBean> list);
    }
}
