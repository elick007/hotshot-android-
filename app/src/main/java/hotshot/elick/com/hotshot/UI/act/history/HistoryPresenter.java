package hotshot.elick.com.hotshot.UI.act.history;

import java.util.List;

import hotshot.elick.com.hotshot.MyApplication;
import hotshot.elick.com.hotshot.baseMVP.BaseView;
import hotshot.elick.com.hotshot.entity.VideoBean;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class HistoryPresenter implements HistoryConstract.Presenter {
    private HistoryActivity historyActivity;

    public HistoryPresenter(HistoryActivity historyActivity) {
        this.historyActivity = historyActivity;
    }

    @Override
    public void attachView(BaseView baseView) {

    }

    @Override
    public void detachView(BaseView baseView) {

    }

    @Override
    public void getHistory() {
        Observable.create((ObservableOnSubscribe<List<VideoBean>>) emitter -> {
            emitter.onNext(MyApplication.self.historyVideoData.getHistory());
            emitter.onComplete();
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<VideoBean>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<VideoBean> list) {
                        historyActivity.updateList(list);
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
