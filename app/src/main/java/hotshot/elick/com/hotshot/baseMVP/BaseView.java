package hotshot.elick.com.hotshot.baseMVP;

import hotshot.elick.com.hotshot.entity.ResponseBase;
import hotshot.elick.com.hotshot.entity.ResponseError;

public interface BaseView {
    void onPresenterSuccess(ResponseBase response);
    void onPresenterFail(ResponseError error);
}
