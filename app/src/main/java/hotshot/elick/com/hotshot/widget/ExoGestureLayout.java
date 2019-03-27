package hotshot.elick.com.hotshot.widget;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.media.AudioManager;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.exoplayer2.Player;

import butterknife.BindView;
import butterknife.ButterKnife;
import hotshot.elick.com.hotshot.R;
import hotshot.elick.com.hotshot.utils.MyLog;
import hotshot.elick.com.hotshot.utils.TimeUtil;

import static android.content.Context.AUDIO_SERVICE;

public class ExoGestureLayout extends FrameLayout {


    @BindView(R.id.exo_gesture_volume_progress)
    ProgressBar exoGestureVolumeProgress;
    @BindView(R.id.exo_gesture_volume_view)
    LinearLayout exoGestureVolumeView;
    @BindView(R.id.exo_gesture_video_progress)
    ProgressBar exoGestureVideoProgress;
    @BindView(R.id.exo_gesture_progress_view)
    LinearLayout exoGestureProgressView;
    @BindView(R.id.exo_gesture_brightness_progress)
    ProgressBar exoGestureBrightnessProgress;
    @BindView(R.id.exo_gesture_brightness_view)
    LinearLayout exoGestureBrightnessView;
    @BindView(R.id.exo_gesture_forward_tv)
    TextView exoGestureForwardTv;
    @BindView(R.id.exo_gesture_video_duration_tv)
    TextView exoGestureVideoDurationTv;
    @BindView(R.id.exo_gesture_progress_indicate)
    View exoGestureProgressIndicate;
    private Window window;
    private WindowManager.LayoutParams lp;
    private float currentBrightness;
    private int currentVolume;
    private int currentVidoeProgress;
    private boolean isBrightnessViewVisible;
    private boolean isVolumeViewVisible;
    private boolean isVideoProgressViewVisible;
    private AudioManager audioManager;
    private Player player;

    public ExoGestureLayout(@NonNull Context context) {
        this(context, null);
    }

    public ExoGestureLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ExoGestureLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        View contentView = LayoutInflater.from(getContext()).inflate(R.layout.exo_gestrure_layout, this);
        //landscapeView = LayoutInflater.from(getContext()).inflate(R.layout.exo_gestrure_layout_landscape, null);
        ButterKnife.bind(contentView);
        window = ((Activity) getContext()).getWindow();
        lp = window.getAttributes();
        exoGestureBrightnessProgress.setMax(255);
        currentBrightness = getScreenBrightness();
        // 获取系统最大音量
        audioManager = (AudioManager) getContext().getSystemService(AUDIO_SERVICE);
        int maxAudio = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        exoGestureVolumeProgress.setMax(maxAudio);
        // 获取到当前 设备的音量
        currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        MyLog.e("maxVolume=" + maxAudio + " currentVolume=" + currentVolume);
    }

    public void setPlayer(Player player) {
        this.player = player;

    }

    private int getScreenBrightness() {
        ContentResolver contentResolver = getContext().getContentResolver();
        int defVal = 125;
        return Settings.System.getInt(contentResolver,
                Settings.System.SCREEN_BRIGHTNESS, defVal);
    }

    /**
     * @param progress 音量级别0-15
     */
    public void settingVolume(int progress) {
        int degreed = progress / 20;
        int temp = currentVolume + degreed;
        if (temp < 0 || temp > 15) {
            return;
        }
        if (!isVolumeViewVisible) {
            exoGestureVolumeView.setVisibility(VISIBLE);
            isVolumeViewVisible = true;
        }
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, temp, 0);
        exoGestureVolumeProgress.setProgress(temp);
    }

    public void releaseVolume() {
        currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        exoGestureVolumeView.setVisibility(GONE);
        isVolumeViewVisible = false;
    }

    /**
     * @param progress 亮度级别0-255
     */
    public void settingBrightness(float progress) {
        float tempBrightness = currentBrightness;
        if (!isBrightnessViewVisible) {
            exoGestureBrightnessView.setVisibility(VISIBLE);
            isBrightnessViewVisible = true;
        }
        tempBrightness += progress;
        if (tempBrightness < 0 || tempBrightness > 255)
            return;
        lp.screenBrightness = tempBrightness / 255.0F;
        window.setAttributes(lp);
        exoGestureBrightnessProgress.setProgress((int) tempBrightness);
    }

    public void releaseBrightness() {
        currentBrightness = lp.screenBrightness * 255;
        exoGestureBrightnessView.setVisibility(GONE);
        isBrightnessViewVisible = false;
    }

    public void setVideoProgress(float progress) {
        if (!isVideoProgressViewVisible) {
            exoGestureProgressView.setVisibility(VISIBLE);
            isVideoProgressViewVisible = true;
            if (player != null) {
                int videoDuration = (int) (player.getDuration() / 1000);
                exoGestureVideoProgress.setMax(videoDuration);
                exoGestureVideoDurationTv.setText("/" + TimeUtil.secondToMinute(String.valueOf(videoDuration)));
                currentVidoeProgress = (int) (player.getCurrentPosition() / 1000);
                exoGestureForwardTv.setText(TimeUtil.secondToMinute(String.valueOf(currentVidoeProgress)));
                exoGestureVideoProgress.setProgress(currentVidoeProgress);
            }
        }
        int degreed = (int) (progress / 10);
        int temp = degreed + currentVidoeProgress;
        if (degreed > 0) {
            exoGestureProgressIndicate.setBackgroundResource(R.drawable.forward);
        }else {
            exoGestureProgressIndicate.setBackgroundResource(R.drawable.backward);
        }
        if (temp < 0 || temp > player.getDuration() / 1000) {
            return;
        }
        exoGestureVideoProgress.setProgress(temp);
        exoGestureForwardTv.setText(TimeUtil.secondToMinute(String.valueOf(temp)));
    }

    public void releaseVideoProgress() {
        currentVidoeProgress = exoGestureVideoProgress.getProgress();
        if (player != null) {
            player.seekTo(currentVidoeProgress * 1000);
        }
        exoGestureProgressView.setVisibility(GONE);
        isVideoProgressViewVisible = false;
    }
}
