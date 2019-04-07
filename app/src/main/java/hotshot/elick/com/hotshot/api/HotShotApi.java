package hotshot.elick.com.hotshot.api;

import java.util.List;

import hotshot.elick.com.hotshot.entity.HotVideosEntity;
import hotshot.elick.com.hotshot.entity.ResponseBase;
import hotshot.elick.com.hotshot.entity.VideoBean;
import hotshot.elick.com.hotshot.entity.VideoEntity;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface HotShotApi {
    @GET("api/videos/{channel}/{type}/")
    Observable<ResponseBase<HotVideosEntity>> getVideos(@Path("channel") String channel, @Path("type") String type);

    //热门视频
    @GET("api/videos/oe/hot/")
    Observable<ResponseBase<List<VideoBean>>> getOEHotVideos();

    @GET("api/videos/dy/hot/")
    Observable<ResponseBase<List<VideoBean>>> getDYHotVideos();

    @GET("api/videos/lsp/hot/")
    Observable<ResponseBase<List<VideoBean>>> getLSPHotVideos();

    //随机视频 数量10
    @GET("api/videos/oe/random/")
    Observable<ResponseBase<List<VideoBean>>> getOERandomVideos();

    @GET("api/videos/dy/random/")
    Observable<ResponseBase<List<VideoBean>>> getDYRandomVideos();

    @GET("api/videos/lsp/random/")
    Observable<ResponseBase<List<VideoBean>>> getLSPRandomVideos();

    //用户视频收藏
}
