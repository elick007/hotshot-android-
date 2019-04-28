package hotshot.elick.com.hotshot.UI.fragments.discover;

import java.util.List;

import hotshot.elick.com.hotshot.api.RetrofitService;
import hotshot.elick.com.hotshot.baseMVP.BaseView;
import hotshot.elick.com.hotshot.entity.PubVideoBean;
import hotshot.elick.com.hotshot.entity.ResponseBase;
import hotshot.elick.com.hotshot.utils.MyLog;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class DiscoverPresenter implements DiscoverFragmentContract.Presenter {
    private DiscoverFragment discoverFragment;

    public DiscoverPresenter(DiscoverFragment discoverFragment) {
        this.discoverFragment = discoverFragment;
    }

    @Override
    public void attachView(BaseView baseView) {

    }

    @Override
    public void detachView(BaseView baseView) {

    }

    @Override
    public void getPubList() {
        RetrofitService.buildApi().getPubVideos()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBase<List<PubVideoBean>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ResponseBase<List<PubVideoBean>> listResponseBase) {
                        MyLog.d("success");
                        if (listResponseBase.getData()!=null){
                            MyLog.d(listResponseBase.getData().get(0).getContent());
                            discoverFragment.updateList(listResponseBase.getData());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void uploadVideo(String filePath, String content) {

    }
}
