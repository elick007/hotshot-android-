package hotshot.elick.com.hotshot.UI.fragments.favorite;

import java.util.List;

import hotshot.elick.com.hotshot.MyApplication;
import hotshot.elick.com.hotshot.api.RetrofitService;
import hotshot.elick.com.hotshot.baseMVP.BaseView;
import hotshot.elick.com.hotshot.entity.FavVideo;
import hotshot.elick.com.hotshot.entity.ResponseBase;
import hotshot.elick.com.hotshot.entity.VideoBean;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class FavPresenter implements FavContract.Presenter {
    private FavFragment favFragment;

    public FavPresenter(FavFragment favFragment) {
        this.favFragment = favFragment;
    }

    @Override
    public void attachView(BaseView baseView) {

    }

    @Override
    public void detachView(BaseView baseView) {

    }

    @Override
    public void getFav(String channel) {
        RetrofitService.buildApi().listFav(channel, MyApplication.self.getToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBase<List<FavVideo>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ResponseBase<List<FavVideo>> listResponseBase) {
                        favFragment.updateList(listResponseBase.getData());
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
