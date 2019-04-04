package hotshot.elick.com.hotshot.UI.activities.player;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Build;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.OrientationEventListener;
import android.view.View;

import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import hotshot.elick.com.hotshot.R;
import hotshot.elick.com.hotshot.UI.activities.BaseActivity;
import hotshot.elick.com.hotshot.adapter.RecommendMultiRVAdapter;
import hotshot.elick.com.hotshot.entity.VideoBean;
import hotshot.elick.com.hotshot.widget.ExoPlayerControlView;
import hotshot.elick.com.hotshot.widget.ExoPlayerView;
import hotshot.elick.com.hotshot.widget.PullDownCloseHeader;
import hotshot.elick.com.hotshot.widget.VideoDetailHeader;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;


import static android.content.res.Configuration.ORIENTATION_LANDSCAPE;
import static android.content.res.Configuration.ORIENTATION_PORTRAIT;

public class PlayerActivity extends PlayerActivityBase<PlayerPresenter>{
    @BindView(R.id.exo_player_view)
    ExoPlayerView playerView;
    @BindView(R.id.recommend_recycler_view)
    RecyclerView recommendRecyclerView;
    @BindView(R.id.ptr_frame_layout)
    PtrFrameLayout ptrFrameLayout;
    private VideoBean video;
    private SimpleExoPlayer simpleExoPlayer;


    private String channel;
    private List<VideoBean> recommendList = new ArrayList<>();
    private RecommendMultiRVAdapter adapter;

    @Override
    protected void initView() {
        Intent intent = getIntent();
        channel = intent.getStringExtra("channel");
        video = (VideoBean) intent.getSerializableExtra("video_info");
        resizePlayerView(false);
        simpleExoPlayer = ExoPlayerFactory.newSimpleInstance(this, new DefaultTrackSelector());
        simpleExoPlayer.setPlayWhenReady(true);
        playerView.setPlayer(simpleExoPlayer);
        playerView.setControllerVisibile();
        playerView.setControllerShowTimeoutMs(2500);
        playerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FILL);
        playerView.setAdditionControlViewListener(new ExoPlayerControlView.AdditionControlViewListener() {
            @Override
            public void onClickCloseButton() {
                if (PlayerActivity.this.getResources().getConfiguration().orientation == ORIENTATION_LANDSCAPE) {
                    PlayerActivity.this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);
                    isLockedLandscape = false;
                    isLockedPortrait = true;
                } else {
                    PlayerActivity.this.finish();
                }
            }

            @Override
            public void onClickMoreButton() {

            }

            @Override
            public boolean onClickFullscreenButton() {
                if (PlayerActivity.this.getResources().getConfiguration().orientation == ORIENTATION_PORTRAIT) {
                    PlayerActivity.this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                    resizePlayerView(true);
                    isLockedLandscape = true;
                    isLockedPortrait = false;
                    return true;
                } else {
                    PlayerActivity.this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                    resizePlayerView(false);
                    isLockedLandscape = false;
                    isLockedPortrait = true;
                    return false;
                }
            }
        });
        updateVideoSource();
        PullDownCloseHeader pullDownCloseHeader=new PullDownCloseHeader(this);
        ptrFrameLayout.addPtrUIHandler(pullDownCloseHeader);
        ptrFrameLayout.setHeaderView(pullDownCloseHeader);
       ptrFrameLayout.setPtrHandler(new PtrHandler() {
           @Override
           public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
               return PtrDefaultHandler.checkContentCanBePulledDown(frame,content,header);
           }

           @Override
           public void onRefreshBegin(PtrFrameLayout frame) {
               frame.refreshComplete();
               PlayerActivity.this.finish();
           }
       });
        initRecyclerView();
    }

    private void initRecyclerView() {
        if (adapter==null){
            adapter = new RecommendMultiRVAdapter(R.layout.recommend_recycler_view_item, recommendList);
        }
        adapter.setHeaderView(new VideoDetailHeader(this,video));
        recommendRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        recommendRecyclerView.setAdapter(adapter);
        basePresenter.getRandomVideos(channel);
        adapter.setOnItemClickListener((adapter, view, position) -> {
            this.video= (VideoBean) adapter.getData().get(position);
            updateVideoSource();
            basePresenter.getRandomVideos(channel);
        });
    }
    private void updateVideoSource(){
        simpleExoPlayer.stop(true);
        DataSource.Factory dataSourceFac = new DefaultDataSourceFactory(this, Util.getUserAgent(this, this.getApplication().getPackageName()));
        MediaSource mediaSource = new ExtractorMediaSource.Factory(dataSourceFac)
                .createMediaSource(Uri.parse(video.getPlayUrl()));
        simpleExoPlayer.prepare(mediaSource);
        if (adapter!=null)
        adapter.setHeaderView(new VideoDetailHeader(this,video));
    }

    public static void startUp(Context context,String channel,VideoBean videoBean){
        Intent intent=new Intent(context,PlayerActivity.class);
        intent.putExtra("channel",channel);
        intent.putExtra("video_info",videoBean);
        context.startActivity(intent);
    }



    private void resizePlayerView(boolean isFullScreen) {
        playerView.resizeScreen(isFullScreen);
    }


    @Override
    public void onPresenterSuccess() {

    }

    @Override
    public void onPresenterFail(String msg) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        simpleExoPlayer.release();
    }

    @Override
    protected boolean enableOrientation() {
        return true;
    }

    @Override
    protected int setLayoutResId() {
        return R.layout.activity_player;
    }

    @Override
    protected PlayerPresenter setPresenter() {
        return new PlayerPresenter(this);
    }


    @Override
    public void finish() {
        super.finish();
        this.overridePendingTransition(0, R.anim.activity_end_from_top_to_bottom_anim);
    }
}
