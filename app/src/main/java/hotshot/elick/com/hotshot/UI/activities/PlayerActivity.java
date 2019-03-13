package hotshot.elick.com.hotshot.UI.activities;

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
import hotshot.elick.com.hotshot.adapter.RecommendMultiRVAdapter;
import hotshot.elick.com.hotshot.entity.OpenEyeEntity;
import hotshot.elick.com.hotshot.entity.ResponseBase;
import hotshot.elick.com.hotshot.entity.ResponseError;
import hotshot.elick.com.hotshot.entity.VideoBean;
import hotshot.elick.com.hotshot.presenter.OpenEyePresenter;
import hotshot.elick.com.hotshot.widget.ExoPlayerControlView;
import hotshot.elick.com.hotshot.widget.ExoPlayerView;
import hotshot.elick.com.hotshot.widget.PullDownCloseHeader;
import hotshot.elick.com.hotshot.widget.VideoDetailHeader;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;


import static android.content.res.Configuration.ORIENTATION_LANDSCAPE;
import static android.content.res.Configuration.ORIENTATION_PORTRAIT;

public class PlayerActivity extends BaseActivity<OpenEyePresenter> {
    @BindView(R.id.exo_player_view)
    ExoPlayerView playerView;
    @BindView(R.id.recommend_recycler_view)
    RecyclerView recommendRecyclerView;
    @BindView(R.id.ptr_frame_layout)
    PtrFrameLayout ptrFrameLayout;
    private VideoBean video;
    private SimpleExoPlayer simpleExoPlayer;
    private final int uiFlag19 = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
            | View.SYSTEM_UI_FLAG_FULLSCREEN
            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
    private final int uiFlag14 = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
    private boolean isLockedLandscape;
    private boolean isLockedPortrait;
    private String channel;
    private MyOrientation myOrientation;
    private List<VideoBean> recommendList = new ArrayList<>();
    private RecommendMultiRVAdapter adapter;

    @Override
    protected void onStart() {
        super.onStart();
        if (Build.VERSION.SDK_INT > 19) {
            getWindow().getDecorView().setSystemUiVisibility(uiFlag19);
        } else if (Build.VERSION.SDK_INT > 14) {
            getWindow().getDecorView().setSystemUiVisibility(uiFlag14);
        }
    }

    @Override
    protected void initView() {
        Intent intent = getIntent();
        channel = intent.getStringExtra("channel");
        video = (VideoBean) intent.getSerializableExtra("video_info");
        myOrientation = new MyOrientation(this);
        myOrientation.enable();
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
        basePresenter.getVideos(channel, "random");
        adapter.setOnItemClickListener((adapter, view, position) -> {
            this.video= (VideoBean) adapter.getData().get(position);
            updateVideoSource();
            basePresenter.getVideos(channel,"random");
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
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (Build.VERSION.SDK_INT > 19) {
            getWindow().getDecorView().setSystemUiVisibility(uiFlag19);
        } else if (Build.VERSION.SDK_INT > 14) {
            getWindow().getDecorView().setSystemUiVisibility(uiFlag14);
        }
    }


    private void resizePlayerView(boolean isFullScreen) {
        playerView.resizeScreen(isFullScreen);
    }

    @Override
    public void onPresenterSuccess(ResponseBase response) {
        OpenEyeEntity entity = (OpenEyeEntity) response;
        recommendList.clear();
        recommendList.addAll(entity.getData().getVideoList());
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onPresenterFail(ResponseError error) {

    }



    class MyOrientation extends OrientationEventListener {
        public MyOrientation(Context context) {
            super(context);
        }

        @Override
        public void onOrientationChanged(int orientation) {
            int span = 5;
            if (Math.abs(orientation - 0) < span) orientation = 0;
            else if (Math.abs(orientation - 90) < span) orientation = 90;
            else if (Math.abs(orientation - 180) < span) orientation = 180;
            else if (Math.abs(orientation - 270) < span) orientation = 270;
            switch (orientation) {
                case 0:
                    if (!isLockedLandscape) {
                        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                        resizePlayerView(false);
                        isLockedPortrait = false;
                    }
                    break;
                case 90:
                    if (!isLockedPortrait) {
                        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE);
                        resizePlayerView(true);
                        isLockedLandscape = false;
                    }
                    break;
                case 180:
                    if (!isLockedLandscape) {
                        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT);
                        resizePlayerView(false);
                        isLockedPortrait = false;
                    }
                    break;
                case 270:
                    if (!isLockedPortrait) {
                        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                        resizePlayerView(true);
                        isLockedLandscape = false;
                    }
                    break;
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        simpleExoPlayer.release();
        myOrientation.disable();
    }

    @Override
    protected int setLayoutResId() {
        return R.layout.activity_player;
    }

    @Override
    protected OpenEyePresenter setPresenter() {
        return new OpenEyePresenter(this);
    }


    @Override
    public void finish() {
        super.finish();
        this.overridePendingTransition(0, R.anim.activity_end_from_top_to_bottom_anim);
    }
}
