package hotshot.elick.com.hotshot.UI.fragments.hot.LiVideo;

import hotshot.elick.com.hotshot.api.RetrofitService;
import hotshot.elick.com.hotshot.baseMVP.BasePresenter;
import hotshot.elick.com.hotshot.baseMVP.BaseView;
import hotshot.elick.com.hotshot.entity.HotVideosEntity;
import hotshot.elick.com.hotshot.entity.ResponseBase;
import hotshot.elick.com.hotshot.entity.VideoEntity;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class LiVideoPresenter implements LiVideoFragmentContract.Presenter{
    private LiVideoFragment baseView;

    public LiVideoPresenter(LiVideoFragment baseView) {
        this.baseView = baseView;
    }

    @Override
    public void attachView(BaseView baseView) {

    }

    @Override
    public void dettachView(BaseView baseView) {

    }

    @Override
    public void getLSPHot() {
        Observable<ResponseBase<HotVideosEntity>> observable=RetrofitService.buildApi().getLSPHotVideos();
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBase<HotVideosEntity>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ResponseBase<HotVideosEntity> hotVideosEntityResponseBase) {
                        baseView.updateLSPHot(hotVideosEntityResponseBase.getData().getVideoList());
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        baseView.onPresenterSuccess();
                    }
                });
    }
}