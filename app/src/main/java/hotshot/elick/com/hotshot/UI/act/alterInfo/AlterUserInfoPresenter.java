package hotshot.elick.com.hotshot.UI.act.alterInfo;

import java.io.File;

import hotshot.elick.com.hotshot.MyApplication;
import hotshot.elick.com.hotshot.api.RetrofitService;
import hotshot.elick.com.hotshot.baseMVP.BaseView;
import hotshot.elick.com.hotshot.entity.ResponseBase;
import hotshot.elick.com.hotshot.entity.UserBean;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class AlterUserInfoPresenter implements AlterUserInfoContract.Presenter {
    private AlterUserInfoAct alterUserInfoAct;

    public AlterUserInfoPresenter(AlterUserInfoAct alterUserInfoAct) {
        this.alterUserInfoAct = alterUserInfoAct;
    }

    @Override
    public void attachView(BaseView baseView) {

    }

    @Override
    public void detachView(BaseView baseView) {

    }

    @Override
    public void getUserInfo() {
        UserBean userBean = MyApplication.self.userInfoData.getUser();
        alterUserInfoAct.updateUserInfo(userBean);
    }

    @Override
    public void alterAvatar(String filePath) {
        File file = new File(filePath);
        if (file.exists()) {
            RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            MultipartBody.Part part = MultipartBody.Part.createFormData("avatar", file.getName(), requestBody);
            RequestBody suffix=RequestBody.create(MediaType.parse("multipart/form-data"),file.getName());
            RetrofitService.buildApi().uploadAvatar(suffix, part,MyApplication.self.getToken())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<ResponseBase<UserBean>>() {
                        @Override
                        public void onSubscribe(Disposable d) {
                            alterUserInfoAct.showLoading();
                        }

                        @Override
                        public void onNext(ResponseBase<UserBean> responseBase) {
                            alterUserInfoAct.dismissLoading();
                            if (responseBase.getCode() == 1) {
                                MyApplication.self.userInfoData.addUser(responseBase.getData());
                                alterUserInfoAct.updateUserInfo(responseBase.getData());
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            alterUserInfoAct.showToast("上传失败");
                            alterUserInfoAct.dismissLoading();
                        }

                        @Override
                        public void onComplete() {

                        }
                    });
        }
    }
}
