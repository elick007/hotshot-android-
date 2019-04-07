package hotshot.elick.com.hotshot.UI.fragments.hot.DouYin;

import java.util.List;

import hotshot.elick.com.hotshot.api.RetrofitService;
import hotshot.elick.com.hotshot.baseMVP.BasePresenter;
import hotshot.elick.com.hotshot.baseMVP.BaseView;
import hotshot.elick.com.hotshot.entity.HotVideosEntity;
import hotshot.elick.com.hotshot.entity.ResponseBase;
import hotshot.elick.com.hotshot.entity.VideoBean;
import hotshot.elick.com.hotshot.entity.VideoEntity;
import hotshot.elick.com.hotshot.utils.MyLog;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class DouYinPrensenter implements DouYinFragmentContract.Presenter {
    private DouYinFragment baseView;

    public DouYinPrensenter(DouYinFragment baseView) {
        this.baseView = baseView;
    }

    @Override
    public void attachView(BaseView baseView) {

    }

    @Override
    public void dettachView(BaseView baseView) {

    }

    @Override
    public void getDYHot() {
        Observable<ResponseBase<List<VideoBean>>> observable = RetrofitService.buildApi().getDYHotVideos();
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBase<List<VideoBean>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ResponseBase<List<VideoBean>> listResponseBase) {
                        baseView.updateDYHot(listResponseBase.getData());
                    }

                    @Override
                    public void onError(Throwable e) {
                        baseView.onPresenterFail(e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        baseView.onPresenterSuccess();
                    }
                });
    }
}
