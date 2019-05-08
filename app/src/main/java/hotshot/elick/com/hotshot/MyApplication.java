package hotshot.elick.com.hotshot;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;

import com.liulishuo.filedownloader.FileDownloader;

import hotshot.elick.com.hotshot.api.RetrofitService;
import hotshot.elick.com.hotshot.data.HistoryVideoData;
import hotshot.elick.com.hotshot.data.UserInfoData;
import hotshot.elick.com.hotshot.utils.MyLog;

public class MyApplication extends Application {
    public static MyApplication self;
    public static Context deContext;

    public SharedPreferences sp;
    public UserInfoData userInfoData;
    public HistoryVideoData historyVideoData;
    @Override
    public void onCreate() {
        super.onCreate();
        MyLog.setDebug(true);
        RetrofitService.getInstance().init();
        FileDownloader.setup(this);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        self = this;
        deContext = base;
        if (Build.VERSION.SDK_INT >= 24) {
            deContext = base.createDeviceProtectedStorageContext();
            deContext.moveSharedPreferencesFrom(base,
                    PreferenceManager.getDefaultSharedPreferencesName(base));
        }
        sp = PreferenceManager.getDefaultSharedPreferences(deContext);
        userInfoData = new UserInfoData(base);
        historyVideoData=new HistoryVideoData(base);
    }

    public String getToken() {
        return sp.getString("token",null);
    }
}
