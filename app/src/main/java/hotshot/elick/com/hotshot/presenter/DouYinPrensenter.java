package hotshot.elick.com.hotshot.presenter;

import hotshot.elick.com.hotshot.api.RetrofitService;
import hotshot.elick.com.hotshot.baseMVP.BasePresenter;
import hotshot.elick.com.hotshot.baseMVP.BaseView;
import hotshot.elick.com.hotshot.entity.OpenEyeEntity;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class DouYinPrensenter implements BasePresenter {
    private BaseView baseView;

    public DouYinPrensenter(BaseView baseView) {
        this.baseView = baseView;
    }

    @Override
    public void attachView(BaseView baseView) {

    }

    @Override
    public void dettachView(BaseView baseView) {

    }

    public void getDYHotVideo(){
        Observable<OpenEyeEntity> observable= RetrofitService.buildApi().getVideos("dy","hot");
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<OpenEyeEntity>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(OpenEyeEntity openEyeEntity) {
                        baseView.onPresenterSuccess(openEyeEntity);
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
