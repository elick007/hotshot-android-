package hotshot.elick.com.hotshot.UI.fragments.hot.OpenEye;

import hotshot.elick.com.hotshot.api.RetrofitService;
import hotshot.elick.com.hotshot.baseMVP.BaseView;
import hotshot.elick.com.hotshot.entity.HotVideosEntity;
import hotshot.elick.com.hotshot.entity.ResponseBase;
import hotshot.elick.com.hotshot.utils.MyLog;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class OpenEyePresenter implements OpenEyeFragmentContract.Presenter {
    private OpenEyesFragment baseView;

    public OpenEyePresenter(OpenEyesFragment baseView) {
        this.baseView = baseView;
    }

    @Override
    public void attachView(BaseView baseView) {

    }

    @Override
    public void dettachView(BaseView baseView) {

    }

    @Override
    public void getHotVideo() {
        MyLog.e("get videos");
        Observable<ResponseBase<HotVideosEntity>> observable = RetrofitService.buildApi().getOEHotVideos();
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBase<HotVideosEntity>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ResponseBase<HotVideosEntity> hotVideosEntityResponseBase) {
                        baseView.updateHotVideo(hotVideosEntityResponseBase.getData().getVideoList());
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
