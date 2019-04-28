package hotshot.elick.com.hotshot.UI.act.register;

import android.text.TextUtils;
import android.widget.Button;

import java.io.IOException;

import hotshot.elick.com.hotshot.api.RetrofitService;
import hotshot.elick.com.hotshot.baseMVP.BaseView;
import hotshot.elick.com.hotshot.entity.ResponseBase;
import hotshot.elick.com.hotshot.entity.ResponseError;
import hotshot.elick.com.hotshot.utils.MyLog;
import hotshot.elick.com.hotshot.utils.PhoneReg;
import hotshot.elick.com.hotshot.utils.RxTimerUtil;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.HttpException;
import retrofit2.Response;

public class RegisterPresenter implements RegisterActivityContract.Presenter {
    private RegisterActivity registerActivity;

    public RegisterPresenter(RegisterActivity registerActivity) {
        this.registerActivity = registerActivity;
    }

    @Override
    public void attachView(BaseView baseView) {

    }

    @Override
    public void detachView(BaseView baseView) {

    }

    @Override
    public void requireVerify(String phone, Button button) {
        if (PhoneReg.isPhone(phone)) {
            button.setEnabled(false);
            RxTimerUtil.interval(1000, number -> {
                MyLog.d("number=" + number);
                int count = (int) (30 - number);
                if (count < 0) {
                    button.setEnabled(true);
                    button.setText("获取验证码");
                    RxTimerUtil.cancel();
                    return;
                }
                button.setText("(" + count + "s" + ")");
            });
            RetrofitService.buildApi().getVerify(phone)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<ResponseBase>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(ResponseBase responseBase) {
                            registerActivity.showToast(responseBase.getMsg());
                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onComplete() {

                        }
                    });
        } else {
            registerActivity.showToast("手机号格式有误");
        }
    }

    @Override
    public void doRegister(String username, String passwd, String phone, String verify) {
        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(passwd) || TextUtils.isEmpty(phone) || TextUtils.isEmpty(verify)) {
            registerActivity.showToast("输入内容不能为空");
        } else {
            RetrofitService.buildApi().doRegister(username, passwd, phone, verify)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<ResponseBase>() {
                        @Override
                        public void onSubscribe(Disposable d) {
                            registerActivity.showLoading();
                        }

                        @Override
                        public void onNext(ResponseBase responseBase) {
                            registerActivity.dismissLoading();
                            registerActivity.showToast("注册成功");
                            registerActivity.registerSuccess();
                        }

                        @Override
                        public void onError(Throwable e) {
                            registerActivity.dismissLoading();
                            if (e instanceof HttpException){
                                HttpException httpException= (HttpException) e;
                                Response<ResponseError> response= (Response<ResponseError>) httpException.response();
                                if (response.body() != null) {
                                    registerActivity.showToast(response.body().getMsg());
                                }
                            }
                        }

                        @Override
                        public void onComplete() {

                        }
                    });
        }
    }
}
