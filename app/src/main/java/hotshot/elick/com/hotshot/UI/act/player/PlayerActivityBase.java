package hotshot.elick.com.hotshot.UI.act.player;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.OrientationEventListener;
import android.view.View;

import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.upstream.FileDataSource;
import com.google.android.exoplayer2.upstream.cache.CacheDataSink;
import com.google.android.exoplayer2.upstream.cache.CacheDataSource;
import com.google.android.exoplayer2.upstream.cache.LeastRecentlyUsedCacheEvictor;
import com.google.android.exoplayer2.upstream.cache.SimpleCache;
import com.google.android.exoplayer2.util.Util;

import java.io.File;

import hotshot.elick.com.hotshot.MyApplication;
import hotshot.elick.com.hotshot.R;
import hotshot.elick.com.hotshot.UI.act.BaseActivity;
import hotshot.elick.com.hotshot.baseMVP.BasePresenter;
import hotshot.elick.com.hotshot.entity.VideoBean;
import hotshot.elick.com.hotshot.utils.MyLog;
import hotshot.elick.com.hotshot.widget.ExoPlayerControlView;
import hotshot.elick.com.hotshot.widget.ExoPlayerView;

import static android.content.res.Configuration.ORIENTATION_LANDSCAPE;
import static android.content.res.Configuration.ORIENTATION_PORTRAIT;

public abstract class PlayerActivityBase extends BaseActivity<PlayerPresenter> implements PlayerActivityContract.View {
    private final int uiFlag19 = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
            | View.SYSTEM_UI_FLAG_FULLSCREEN
            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
    private final int uiFlag14 = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
    private boolean isLockedLandscape;
    private boolean isLockedPortrait;
    private MyOrientation myOrientation;
    private ExoPlayerView exoPlayerView;
    protected SimpleExoPlayer simpleExoPlayer;
    protected VideoBean videoBean;

    @Override
    protected void onStart() {
        super.onStart();
        if (Build.VERSION.SDK_INT > 19) {
            getWindow().getDecorView().setSystemUiVisibility(uiFlag19);
        } else if (Build.VERSION.SDK_INT > 14) {
            getWindow().getDecorView().setSystemUiVisibility(uiFlag14);
        }
    }

    public static void startUp(Context context, VideoBean videoBean) {
        Intent intent = new Intent();
        intent.putExtra("video",videoBean);
        if (videoBean.getType().equals("dy")) {
            intent.setClass(context, DouyinPlayerActivity.class);
        } else {
            intent.setClass(context, PlayerActivity.class);
        }
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (enableOrientation()) {
            myOrientation = new MyOrientation(this);
            myOrientation.enable();
        }
        initExoPlayerView();
    }

    private void initExoPlayerView() {
        exoPlayerView = setExoPlayerView();
        exoPlayerView.setAdditionControlViewListener(new ExoPlayerControlView.AdditionControlViewListener() {
            @Override
            public void onClickCloseButton() {
                if (PlayerActivityBase.this.getResources().getConfiguration().orientation == ORIENTATION_LANDSCAPE) {
                    PlayerActivityBase.this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);
                    isLockedLandscape = false;
                    isLockedPortrait = true;
                } else {
                    PlayerActivityBase.this.finish();
                }
            }

            @Override
            public void onClickMoreButton() {

            }

            @Override
            public boolean onClickFullscreenButton() {
                if (PlayerActivityBase.this.getResources().getConfiguration().orientation == ORIENTATION_PORTRAIT) {
                    PlayerActivityBase.this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                    resizePlayerView(true);
                    isLockedLandscape = true;
                    isLockedPortrait = false;
                    return true;
                } else {
                    PlayerActivityBase.this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                    resizePlayerView(false);
                    isLockedLandscape = false;
                    isLockedPortrait = true;
                    return false;
                }
            }
        });
        simpleExoPlayer = ExoPlayerFactory.newSimpleInstance(this, new DefaultTrackSelector());
        simpleExoPlayer.setPlayWhenReady(true);
        exoPlayerView.setPlayer(simpleExoPlayer);
        exoPlayerView.setControllerShowTimeoutMs(2500);
        updateVideoSource(videoBean);
    }

    protected void updateVideoSource(VideoBean videoBean) {
        this.videoBean=videoBean;
        simpleExoPlayer.stop(true);
        //MediaSource mediaSource=new ExtractorMediaSource.Factory(new CacheDataSourceFactory(this)).createMediaSource(Uri.parse(videoBean.getPlayUrl()));
        DataSource.Factory dataSourceFac = new DefaultDataSourceFactory(this, Util.getUserAgent(this, this.getApplication().getPackageName()));
        MediaSource mediaSource = new ExtractorMediaSource.Factory(dataSourceFac)
                .createMediaSource(Uri.parse(videoBean.getPlayUrl()));
        simpleExoPlayer.prepare(mediaSource);
        MyApplication.self.historyVideoData.insertVideo(videoBean);
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
                        exoPlayerView.resizeScreen(false);
                        isLockedPortrait = false;
                    }
                    break;
                case 90:
                    if (!isLockedPortrait) {
                        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE);
                        exoPlayerView.resizeScreen(true);
                        isLockedLandscape = false;
                    }
                    break;
                case 180:
                    if (!isLockedLandscape) {
                        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT);
                        exoPlayerView.resizeScreen(false);
                        isLockedPortrait = false;
                    }
                    break;
                case 270:
                    if (!isLockedPortrait) {
                        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                        exoPlayerView.resizeScreen(true);
                        isLockedLandscape = false;
                    }
                    break;
            }
        }
    }

    private void resizePlayerView(boolean isFullScreen) {
        exoPlayerView.resizeScreen(isFullScreen);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        simpleExoPlayer.release();
        if (myOrientation != null)
            myOrientation.disable();
    }

    @Override
    protected PlayerPresenter setPresenter() {
        return new PlayerPresenter(this);
    }

    protected abstract boolean enableOrientation();

    protected abstract ExoPlayerView setExoPlayerView();

    class CacheDataSourceFactory implements DataSource.Factory {
        private final Context context;
        private final DefaultDataSourceFactory defaultDatasourceFactory;
        private final long maxFileSize, maxCacheSize;

        public CacheDataSourceFactory(Context context) {
            this(context, 1024 * 1024 * 1024, 500 * 1024 * 1024);
        }

        CacheDataSourceFactory(Context context, long maxCacheSize, long maxFileSize) {
            super();
            this.context = context;
            this.maxCacheSize = maxCacheSize;
            this.maxFileSize = maxFileSize;
            String userAgent = Util.getUserAgent(context, context.getString(R.string.app_name));
            DefaultBandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
            defaultDatasourceFactory = new DefaultDataSourceFactory(this.context,
                    bandwidthMeter,
                    new DefaultHttpDataSourceFactory(userAgent, bandwidthMeter));
        }

        @Override
        public DataSource createDataSource() {
            LeastRecentlyUsedCacheEvictor evictor = new LeastRecentlyUsedCacheEvictor(maxCacheSize);
            SimpleCache simpleCache = new SimpleCache(new File(context.getCacheDir(), "media"), evictor);
            return new CacheDataSource(simpleCache, defaultDatasourceFactory.createDataSource(),
                    new FileDataSource(), new CacheDataSink(simpleCache, maxFileSize),
                    CacheDataSource.FLAG_BLOCK_ON_CACHE | CacheDataSource.FLAG_IGNORE_CACHE_ON_ERROR, null);
        }
    }
}
