package hotshot.elick.com.hotshot.UI.act.player;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import hotshot.elick.com.hotshot.R;
import hotshot.elick.com.hotshot.UI.act.dowload.DownloadActivity;
import hotshot.elick.com.hotshot.entity.VideoBean;
import hotshot.elick.com.hotshot.entity.VideoComment;
import hotshot.elick.com.hotshot.widget.ExoPlayerView;

public class DouyinPlayerActivity extends PlayerActivityBase {

    @BindView(R.id.exo_player_view)
    ExoPlayerView playerView;
    @BindView(R.id.back_image)
    ImageView backImage;
    @BindView(R.id.video_content)
    TextView videoContent;
    @BindView(R.id.favor_btn)
    Button favorBtn;
    @BindView(R.id.download_btn)
    Button downloadBtn;
    private boolean isFav;

    @Override
    protected int setLayoutResId() {
        return R.layout.activity_douyin_player;
    }

    @Override
    protected void initView() {
        videoBean = (VideoBean) getIntent().getSerializableExtra("video");
        //playerView.resizeScreen(true);
        playerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FIT);
        videoContent.setText(videoBean.getDescription());
        basePresenter.retrieveFav(videoBean.getType(), videoBean.getId());
    }

    @Override
    protected boolean enableOrientation() {
        return false;
    }

    @Override
    protected ExoPlayerView setExoPlayerView() {
        return playerView;
    }

    @Override
    public void updateFav(boolean isFav) {
        this.isFav = isFav;
        if (isFav) {
            favorBtn.setBackgroundResource(R.drawable.favor_press);
        } else {
            favorBtn.setBackgroundResource(R.drawable.favor_nopress);
        }
    }

    @Override
    public void updateRandom(List<VideoBean> list) {

    }

    @Override
    public void addFavSuc() {
        dismissLoading();
        updateFav(true);
    }

    @Override
    public void delFavSuc() {
        dismissLoading();
        updateFav(false);
    }

    @Override
    public void updateComment(List<VideoComment> list) {

    }

    @Override
    public void updateLogin(boolean b) {

    }

    @Override
    public void onPresenterSuccess() {

    }

    @Override
    public void onPresenterFail(String msg) {

    }

    @OnClick({R.id.back_image, R.id.favor_btn, R.id.download_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_image:
                finish();
                break;
            case R.id.favor_btn:
                if (!isFav){
                    basePresenter.addFav(videoBean.getType(),videoBean.getId());
                }else {
                    basePresenter.deleteFav(videoBean.getType(),videoBean.getId());
                }
                break;
            case R.id.download_btn:
                DownloadActivity.TasksManager.getImpl().addTask(videoBean.getPlayUrl(),videoBean.getTitle());
                showToast("已添加到下载列表");
                break;
        }
    }
}
