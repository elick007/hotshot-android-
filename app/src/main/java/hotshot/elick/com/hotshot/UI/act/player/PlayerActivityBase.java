package hotshot.elick.com.hotshot.UI.act.player;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.OrientationEventListener;
import android.view.View;

import hotshot.elick.com.hotshot.R;
import hotshot.elick.com.hotshot.UI.act.BaseActivity;
import hotshot.elick.com.hotshot.baseMVP.BasePresenter;
import hotshot.elick.com.hotshot.widget.ExoPlayerControlView;
import hotshot.elick.com.hotshot.widget.ExoPlayerView;

import static android.content.res.Configuration.ORIENTATION_LANDSCAPE;
import static android.content.res.Configuration.ORIENTATION_PORTRAIT;

public abstract class PlayerActivityBase<T extends BasePresenter> extends BaseActivity<T> {
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
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (enableOrientation()) {
            myOrientation = new MyOrientation(this);
            myOrientation.enable();
        }
        initExoPlayerView();
    }

    private void initExoPlayerView() {
        exoPlayerView=setExoPlayerView();
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
    private void resizePlayerView(boolean isFullScreen){
        exoPlayerView.resizeScreen(isFullScreen);
        
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (myOrientation!=null)
        myOrientation.disable();
    }

    protected abstract boolean enableOrientation();
    protected abstract ExoPlayerView setExoPlayerView();

}