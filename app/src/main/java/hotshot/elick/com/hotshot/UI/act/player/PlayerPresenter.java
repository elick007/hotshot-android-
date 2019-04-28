package hotshot.elick.com.hotshot.UI.act.player;

import android.text.TextUtils;

import java.util.List;

import hotshot.elick.com.hotshot.MyApplication;
import hotshot.elick.com.hotshot.api.RetrofitService;
import hotshot.elick.com.hotshot.baseMVP.BaseView;
import hotshot.elick.com.hotshot.entity.ResponseBase;
import hotshot.elick.com.hotshot.entity.VideoBean;
import hotshot.elick.com.hotshot.entity.VideoComment;
import hotshot.elick.com.hotshot.utils.MyLog;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class PlayerPresenter implements PlayerActivityContract.Presenter {
    private PlayerActivity playerActivity;
    private String token;

    public PlayerPresenter(PlayerActivity playerActivity) {
        this.playerActivity = playerActivity;
        this.token = MyApplication.self.getToken();
    }


    @Override
    public void retrieveFav(String channel, int videoId) {
        if (!TextUtils.isEmpty(token)) {
            RetrofitService.buildApi().retriveFav(channel, videoId, token)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<ResponseBase>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(ResponseBase responseBase) {
                            if (responseBase.getCode() == 1) {
                                playerActivity.updateFav(true);
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            playerActivity.updateFav(false);
                        }

                        @Override
                        public void onComplete() {

                        }
                    });
        } else {
            MyLog.d("token is empty");
        }
    }

    @Override
    public void addFav(String channel, int videoId) {
        if (!TextUtils.isEmpty(token)) {
            RetrofitService.buildApi().addFav(channel, videoId, token)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<ResponseBase>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(ResponseBase responseBase) {
                            if (responseBase.getCode() == 1) {
                                playerActivity.dismissLoading();
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            playerActivity.dismissLoading();
                        }

                        @Override
                        public void onComplete() {

                        }
                    });
        } else {
            playerActivity.showToast("暂未登录");
        }
    }

    @Override
    public void deleteFav(String channel, int videoId) {
        if (!TextUtils.isEmpty(token)) {
            RetrofitService.buildApi().delFav(channel, videoId, token)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<ResponseBase>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(ResponseBase responseBase) {
                            if (responseBase.getCode() == 1) {
                                playerActivity.delFavSuc();
                            }
                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onComplete() {

                        }
                    });
        } else {
            playerActivity.showToast("暂未登录");
        }
    }

    @Override
    public void getRandomVideos(String channel) {
        Observable<ResponseBase<List<VideoBean>>> observable = RetrofitService.buildApi().getVideos(channel, "random");
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBase<List<VideoBean>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ResponseBase<List<VideoBean>> listResponseBase) {
                        playerActivity.updateRandom(listResponseBase.getData());
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
    public void getCommentList(String channel, int videoId) {
        RetrofitService.buildApi().listComment(channel, videoId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBase<List<VideoComment>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ResponseBase<List<VideoComment>> listResponseBase) {
                        if (listResponseBase.getCode() == 1) {
                            playerActivity.updateComment(listResponseBase.getData());
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
    public void doComment(String channel, int videoId, String comment) {
        if (TextUtils.isEmpty(comment)) {
            playerActivity.showToast("输入内容为空");
        } else {
            if (TextUtils.isEmpty(token)) {
                playerActivity.showToast("当前未登录");
            } else {
                RetrofitService.buildApi().docomment(channel, videoId, comment, token)
                        .flatMap((Function<ResponseBase, ObservableSource<ResponseBase<List<VideoComment>>>>) responseBase -> RetrofitService.buildApi().listComment(channel, videoId))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<ResponseBase<List<VideoComment>>>() {
                            @Override
                            public void onSubscribe(Disposable d) {

                            }

                            @Override
                            public void onNext(ResponseBase<List<VideoComment>> listResponseBase) {
                                if (listResponseBase.getCode() == 1) {
                                    playerActivity.updateComment(listResponseBase.getData());
                                    playerActivity.showToast("发表成功");
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
        }
    }

    @Override
    public void attachView(BaseView baseView) {

    }

    @Override
    public void detachView(BaseView baseView) {

    }
}
