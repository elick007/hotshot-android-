package hotshot.elick.com.hotshot.api;

import java.util.concurrent.TimeUnit;
import hotshot.elick.com.hotshot.utils.MyLog;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitService {
    private static RetrofitService instance;
    private static Retrofit retrofit;
    private static volatile HotShotApi hotShotApi;

    public static RetrofitService getInstance() {
        if (instance == null) {
            synchronized (RetrofitService.class) {
                if (instance == null) {
                    instance = new RetrofitService();
                }
            }
        }
        return instance;
    }

    public void init() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(20, TimeUnit.SECONDS)
                .addInterceptor(new HttpLoggingInterceptor(MyLog::d).setLevel(HttpLoggingInterceptor.Level.BODY))
                .build();
        retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl("http://172.16.1.133:8000/")
                .build();
    }

    public static HotShotApi buildApi() {
        if (hotShotApi==null){
            synchronized (HotShotApi.class){
                hotShotApi=retrofit.create(HotShotApi.class);
            }
        }
        return hotShotApi;
    }

}
