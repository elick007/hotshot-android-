package hotshot.elick.com.hotshot.UI.activities.player;

import android.content.res.Configuration;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import hotshot.elick.com.hotshot.R;
import hotshot.elick.com.hotshot.entity.VideoBean;
import hotshot.elick.com.hotshot.widget.ExoPlayerView;

import static com.google.android.exoplayer2.Player.REPEAT_MODE_ONE;

public class DouyinPlayerActivity extends AppCompatActivity {

    @BindView(R.id.exo_player_view)
    ExoPlayerView exoPlayerView;
    @BindView(R.id.back_image)
    ImageView backImage;
    @BindView(R.id.video_content)
    TextView videoContent;
    private VideoBean video;
    private SimpleExoPlayer simpleExoPlayer;
    private final int uiFlag19 = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
            | View.SYSTEM_UI_FLAG_FULLSCREEN
            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
    private final int uiFlag14 = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;

    @Override
    protected void onStart() {
        super.onStart();
        if (Build.VERSION.SDK_INT>19){
            getWindow().getDecorView().setSystemUiVisibility(uiFlag19);
        }else {
            getWindow().getDecorView().setSystemUiVisibility(uiFlag14);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_douyin_player);
        ButterKnife.bind(this);
        video= (VideoBean) getIntent().getSerializableExtra("video_info");
        init();
    }

    private void init() {
        videoContent.setText(video.getDescription());
        simpleExoPlayer=ExoPlayerFactory.newSimpleInstance(this,new DefaultTrackSelector());
        DataSource.Factory dataSourceFac = new DefaultDataSourceFactory(this, Util.getUserAgent(this, this.getApplication().getPackageName()));

        MediaSource mediaSource = new ExtractorMediaSource.Factory(dataSourceFac)
                .createMediaSource(Uri.parse(video.getPlayUrl()));
        exoPlayerView.setPlayer(simpleExoPlayer);
        exoPlayerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FILL);
        simpleExoPlayer.prepare(mediaSource);
        simpleExoPlayer.setPlayWhenReady(true);
        simpleExoPlayer.setRepeatMode(REPEAT_MODE_ONE);
    }

    @OnClick(R.id.back_image)
    public void onViewClicked() {
        finish();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (Build.VERSION.SDK_INT>19){
            getWindow().getDecorView().setSystemUiVisibility(uiFlag19);
        }else {
            getWindow().getDecorView().setSystemUiVisibility(uiFlag14);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        simpleExoPlayer.release();
    }
}
