package hotshot.elick.com.hotshot.UI.act.player;

import java.util.List;

import hotshot.elick.com.hotshot.baseMVP.BasePresenter;
import hotshot.elick.com.hotshot.baseMVP.BaseView;
import hotshot.elick.com.hotshot.entity.VideoBean;
import hotshot.elick.com.hotshot.entity.VideoComment;

public interface PlayerActivityContract {
    interface Presenter extends BasePresenter{
        void retrieveFav(String channel,int videoId);
        void addFav(String channel, int videoId);
        void deleteFav(String channel,int videoId);
        void getRandomVideos(String channel);
        void getCommentList(String channel,int videoId);
        void doComment(String channel,int videoId,String comment);
    }

    interface View extends BaseView{
        void updateFav(boolean isFav);
        void updateRandom(List<VideoBean> list);
        void addFavSuc();
        void delFavSuc();
        void updateComment(List<VideoComment> list);
    }
}
