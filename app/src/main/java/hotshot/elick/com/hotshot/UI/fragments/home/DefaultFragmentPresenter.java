package hotshot.elick.com.hotshot.UI.fragments.home;

import java.util.List;

import hotshot.elick.com.hotshot.api.RetrofitService;
import hotshot.elick.com.hotshot.baseMVP.BaseView;
import hotshot.elick.com.hotshot.entity.ResponseBase;
import hotshot.elick.com.hotshot.entity.VideoBean;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class DefaultFragmentPresenter implements DefaultFragmentContract.Presenter {
    private DefaultFragment baseView;

    public DefaultFragmentPresenter(DefaultFragment baseView) {
        this.baseView = baseView;
    }

    @Override
    public void attachView(BaseView baseView) {

    }

    @Override
    public void dettachView(BaseView baseView) {

    }

    @Override
    public void getOERandom() {
        Observable<ResponseBase<List<VideoBean>>> observable = RetrofitService.buildApi().getOERandomVideos();
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBase<List<VideoBean>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ResponseBase<List<VideoBean>> listResponseBase) {
                        baseView.updateOE(listResponseBase.getData());
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

    @Override
    public void getDYRandom() {
        Observable<ResponseBase<List<VideoBean>>> observable = RetrofitService.buildApi().getDYRandomVideos();
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBase<List<VideoBean>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ResponseBase<List<VideoBean>> listResponseBase) {
                        baseView.updateDY(listResponseBase.getData());
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

    @Override
    public void getLSPRandom() {
        Observable<ResponseBase<List<VideoBean>>> observable = RetrofitService.buildApi().getLSPRandomVideos();
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBase<List<VideoBean>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ResponseBase<List<VideoBean>> listResponseBase) {
                        baseView.updateLSP(listResponseBase.getData());
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

    @Override
    public void getBanner() {

    }
}
