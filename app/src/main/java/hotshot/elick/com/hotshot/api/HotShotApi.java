package hotshot.elick.com.hotshot.api;

import org.json.JSONObject;

import java.util.List;

import hotshot.elick.com.hotshot.entity.FavVideo;
import hotshot.elick.com.hotshot.entity.HotVideosEntity;
import hotshot.elick.com.hotshot.entity.PubVideoBean;
import hotshot.elick.com.hotshot.entity.ResponseBase;
import hotshot.elick.com.hotshot.entity.Token;
import hotshot.elick.com.hotshot.entity.UserBean;
import hotshot.elick.com.hotshot.entity.VideoBean;
import hotshot.elick.com.hotshot.entity.VideoComment;
import hotshot.elick.com.hotshot.entity.VideoEntity;
import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface HotShotApi {
    @GET("/api/videos/{channel}/{type}/")
    Observable<ResponseBase<List<VideoBean>>> getVideos(@Path("channel") String channel, @Path("type") String type);

    //热门视频
    @GET("/api/videos/oe/hot/")
    Observable<ResponseBase<List<VideoBean>>> getOEHotVideos();

    @GET("/api/videos/dy/hot/")
    Observable<ResponseBase<List<VideoBean>>> getDYHotVideos();

    @GET("/api/videos/lsp/hot/")
    Observable<ResponseBase<List<VideoBean>>> getLSPHotVideos();

    //开眼随机视频列表
    @GET("/api/videos/oe/random/")
    Observable<ResponseBase<List<VideoBean>>> getOERandomVideos();

    //抖音随机视频列表
    @GET("/api/videos/dy/random/")
    Observable<ResponseBase<List<VideoBean>>> getDYRandomVideos();

    //梨视频随机视频列表
    @GET("/api/videos/lsp/random/")
    Observable<ResponseBase<List<VideoBean>>> getLSPRandomVideos();

    //发布大厅视频列表
    @GET("/api/videos/public/list/")
    Observable<ResponseBase<List<PubVideoBean>>> getPubVideos();

    //发布视频
    @POST("/api/videos/public/upload/")
    @Multipart
    Observable<ResponseBase> uploadVideo(@Part("suffix") RequestBody suffix,@Part MultipartBody.Part video,@Part("content") RequestBody content,@Header("Authorization") String token);

    //注册
    @POST("/api/user/register/")
    @FormUrlEncoded
    Observable<ResponseBase> doRegister(@Field("username") String username,@Field("password") String passwd,@Field("phone") String phone,@Field("verify") String verify);

    //登录
    @POST("/api/user/login/")
    @FormUrlEncoded
    Observable<ResponseBase<UserBean>> doLogin(@Field("username") String username, @Field("password") String password);

    //获取token
    @POST("/api-token-auth/")
    @FormUrlEncoded
    Observable<Token> getToken(@Field("username") String username, @Field("password") String password);

    //上传头像
    @POST("/api/user/avatar/")
    @Multipart
    Observable<ResponseBase<UserBean>> uploadAvatar(@Part("suffix") RequestBody suffix, @Part MultipartBody.Part avatar,@Header("Authorization") String token);

    //获取验证码
    @GET("/api/user/verify/")
    Observable<ResponseBase> getVerify(@Query("phone") String phone);

    //视频收藏查询
    @GET("/api/user/favorite/{channel}/?ac=retrieve")
    Observable<ResponseBase> retriveFav(@Path("channel") String channel, @Query("videoId") int videoId, @Header("Authorization") String token);

    //新增视频收藏
    @POST("/api/user/favorite/{channel}/?ac=add")
    @FormUrlEncoded
    Observable<ResponseBase> addFav(@Path("channel") String channel,@Field("videoId") int videoId,@Header("Authorization") String token);

    //取消视频收藏
    @POST("/api/user/favorite/{channel}/?ac=del")
    @FormUrlEncoded
    Observable<ResponseBase> delFav(@Path("channel") String channel,@Field("videoId") int videoId,@Header("Authorization") String token);

    //视频收藏列表
    @GET("/api/user/favorite/oe/?ac=list")
    Observable<ResponseBase<List<FavVideo>>> listFav(@Path("channel") String channel, @Query("videoId") int videoId, @Header("Authorization") String token);

    //视频评论列表
    @GET("/api/videos/{channel}/comment/list")
    Observable<ResponseBase<List<VideoComment>>> listComment(@Path("channel") String channel ,@Query("videoId") int videoId);

    //视频评论
    @POST("/api/videos/{channel}/comment/")
    @FormUrlEncoded
    Observable<ResponseBase> docomment(@Path("channel") String channel,@Field("videoId") int videoId,@Field("content") String content,@Header("Authorization") String token);
}
