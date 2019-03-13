package hotshot.elick.com.hotshot.presenter;

import hotshot.elick.com.hotshot.UI.fragments.DefaultFragment;
import hotshot.elick.com.hotshot.api.RetrofitService;
import hotshot.elick.com.hotshot.baseMVP.BasePresenter;
import hotshot.elick.com.hotshot.baseMVP.BaseView;
import hotshot.elick.com.hotshot.entity.OpenEyeEntity;
import hotshot.elick.com.hotshot.entity.ResponseError;
import hotshot.elick.com.hotshot.utils.MyLog;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class DefaultFragmentPresenter implements BasePresenter {
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
    public void getVideos(String channel,String type){
        MyLog.e("get videos");
        Observable<OpenEyeEntity> observable= RetrofitService.buildApi().getVideos(channel,type);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<OpenEyeEntity>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        MyLog.e("onsubscribe");
                    }

                    @Override
                    public void onNext(OpenEyeEntity s) {
                        MyLog.e("onNext");
                        //baseView.onPresenterSuccess(s);
                        baseView.updateRV(channel,s.getData().getVideoList());
                    }

                    @Override
                    public void onError(Throwable e) {
                        MyLog.e("onError");
                        baseView.onPresenterFail(new ResponseError(0,"get video error"));
                    }

                    @Override
                    public void onComplete() {
                        MyLog.e("onComplete");
                    }
                });
    }
}
