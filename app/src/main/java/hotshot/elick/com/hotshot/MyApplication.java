package hotshot.elick.com.hotshot;

import android.app.Application;

import hotshot.elick.com.hotshot.api.RetrofitService;
import hotshot.elick.com.hotshot.utils.MyLog;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        MyLog.setDebug(true);
        RetrofitService.getInstance().init();
    }
}
