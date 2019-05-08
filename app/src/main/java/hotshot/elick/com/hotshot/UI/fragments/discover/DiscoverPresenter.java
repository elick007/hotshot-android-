package hotshot.elick.com.hotshot.UI.fragments.discover;

import android.text.TextUtils;

import java.io.File;
import java.util.List;

import hotshot.elick.com.hotshot.MyApplication;
import hotshot.elick.com.hotshot.api.RetrofitService;
import hotshot.elick.com.hotshot.baseMVP.BaseView;
import hotshot.elick.com.hotshot.entity.PubVideoBean;
import hotshot.elick.com.hotshot.entity.ResponseBase;
import hotshot.elick.com.hotshot.utils.MyLog;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

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
                        if (listResponseBase.getData().size() > 0) {
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
        String token = MyApplication.self.getToken();
        if (TextUtils.isEmpty(token)) {
            discoverFragment.showToast("当前未登录");
        } else {
            File file = new File(filePath);
            if (file.exists()) {
                RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                MultipartBody.Part part = MultipartBody.Part.createFormData("video", file.getName(), requestBody);
                RequestBody suffix = RequestBody.create(MediaType.parse("multipart/form-data"), file.getName());
                RequestBody videoContent = RequestBody.create(MediaType.parse("multipart/form-data"), content);
                RetrofitService.buildApi().uploadVideo(suffix, part, videoContent, token)
                        .flatMap((Function<ResponseBase, ObservableSource<ResponseBase<List<PubVideoBean>>>>) responseBase -> {
                            if (responseBase.getCode() == 1) {
                                return RetrofitService.buildApi().getPubVideos();
                            } else {
                                discoverFragment.showToast(responseBase.getMsg());
                                return null;
                            }
                        })
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<ResponseBase<List<PubVideoBean>>>() {
                            @Override
                            public void onSubscribe(Disposable d) {
                                discoverFragment.showLoading();
                            }

                            @Override
                            public void onNext(ResponseBase<List<PubVideoBean>> listResponseBase) {
                                discoverFragment.dismissLoading();
                                discoverFragment.updateList(listResponseBase.getData());
                            }

                            @Override
                            public void onError(Throwable e) {
                                discoverFragment.dismissLoading();
                            }

                            @Override
                            public void onComplete() {

                            }
                        });
            }
        }
    }
}
