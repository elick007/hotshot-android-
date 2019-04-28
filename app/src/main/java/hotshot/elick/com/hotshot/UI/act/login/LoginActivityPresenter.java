package hotshot.elick.com.hotshot.UI.act.login;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import hotshot.elick.com.hotshot.MyApplication;
import hotshot.elick.com.hotshot.api.RetrofitService;
import hotshot.elick.com.hotshot.baseMVP.BaseView;
import hotshot.elick.com.hotshot.entity.ResponseBase;
import hotshot.elick.com.hotshot.entity.ResponseError;
import hotshot.elick.com.hotshot.entity.Token;
import hotshot.elick.com.hotshot.entity.UserBean;
import hotshot.elick.com.hotshot.utils.MyLog;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.HttpException;

public class LoginActivityPresenter implements LoginActivityContract.Presenter {
    private LoginActivity loginActivity;

    public LoginActivityPresenter(LoginActivity loginActivity) {
        this.loginActivity = loginActivity;
    }

    @Override
    public void attachView(BaseView baseView) {

    }

    @Override
    public void detachView(BaseView baseView) {

    }

    @Override
    public void doLogin(String userName, String passwd) {
        RetrofitService.buildApi().doLogin(userName, passwd)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBase<UserBean>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        loginActivity.showLoading();
                    }

                    @Override
                    public void onNext(ResponseBase<UserBean> userBeanResponseBase) {
                        MyApplication.self.userInfoData.addUser(userBeanResponseBase.getData());
                        loginActivity.onLoginSuccess();
                        loginActivity.dismissLoading();
                        getToken(userName, passwd);
                    }

                    @Override
                    public void onError(Throwable e) {
                        loginActivity.dismissLoading();
                        if (e instanceof HttpException) {
                            HttpException httpException = (HttpException) e;
                            try {
                                String response = httpException.response().errorBody().string();
                                ResponseError responseError = new GsonBuilder().create().fromJson(response, ResponseError.class);
                                loginActivity.showToast(responseError.getMsg());
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    private void getToken(String userName, String passwd) {
        RetrofitService.buildApi().getToken(userName, passwd)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(new Observer<Token>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Token token) {
                        MyLog.d("get token success");
                        if (!TextUtils.isEmpty(token.getToken())) {
                            MyApplication.self.sp.edit().putString("token", "Token " + token.getToken()).apply();
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
