package hotshot.elick.com.hotshot.UI.activities.player;

import hotshot.elick.com.hotshot.baseMVP.BaseView;

public class PlayerPresenter implements PlayerActivityContract.Presenter {
    private PlayerActivity playerActivity;

    public PlayerPresenter(PlayerActivity playerActivity) {
        this.playerActivity = playerActivity;
    }


    @Override
    public void retrieveFav(String channel, String videoId) {

    }

    @Override
    public void addFav(String channal, String videoId) {

    }

    @Override
    public void deleteFav(String channel, String videoId) {

    }

    @Override
    public void getRandomVideos(String channel) {

    }

    @Override
    public void attachView(BaseView baseView) {

    }

    @Override
    public void dettachView(BaseView baseView) {

    }
}
