package hotshot.elick.com.hotshot.api;

import hotshot.elick.com.hotshot.entity.OpenEyeEntity;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface HotShotApi {
    @GET("api/videos/{channel}/{type}/")
    Observable<OpenEyeEntity> getVideos(@Path("channel") String channel, @Path("type") String type);

}
