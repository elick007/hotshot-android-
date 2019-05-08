package hotshot.elick.com.hotshot.UI.act.player;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import hotshot.elick.com.hotshot.R;
import hotshot.elick.com.hotshot.UI.act.dowload.DownloadActivity;
import hotshot.elick.com.hotshot.adapter.RecommendMultiRVAdapter;
import hotshot.elick.com.hotshot.api.RetrofitService;
import hotshot.elick.com.hotshot.entity.VideoBean;
import hotshot.elick.com.hotshot.entity.VideoComment;
import hotshot.elick.com.hotshot.widget.ExoPlayerView;
import hotshot.elick.com.hotshot.widget.PullDownCloseHeader;
import hotshot.elick.com.hotshot.widget.VideoDetailHeader;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

public class PlayerActivity extends PlayerActivityBase {
    @BindView(R.id.exo_player_view)
    ExoPlayerView playerView;
    @BindView(R.id.recommend_recycler_view)
    RecyclerView recommendRecyclerView;
    @BindView(R.id.ptr_frame_layout)
    PtrFrameLayout ptrFrameLayout;
    @BindView(R.id.comment_recycler_view)
    RecyclerView commentRecyclerView;
    @BindView(R.id.comment_et)
    EditText commentEt;
    @BindView(R.id.comment_ic)
    View commentIc;
    @BindView(R.id.favor_btn)
    Button favorBtn;
    @BindView(R.id.download_btn)
    Button downloadBtn;

    private List<VideoBean> recommendList = new ArrayList<>();
    private RecommendMultiRVAdapter adapter;
    private List<VideoComment> commentList = new ArrayList<>();
    private BaseQuickAdapter<VideoComment, BaseViewHolder> commentAdapter;
    private boolean isFav;

    @Override
    protected void initView() {
        videoBean = (VideoBean) getIntent().getSerializableExtra("video");
        PullDownCloseHeader pullDownCloseHeader = new PullDownCloseHeader(this);
        ptrFrameLayout.addPtrUIHandler(pullDownCloseHeader);
        ptrFrameLayout.setHeaderView(pullDownCloseHeader);
        ptrFrameLayout.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                frame.refreshComplete();
                PlayerActivity.this.finish();
            }
        });
        basePresenter.retrieveFav(videoBean.getType(), videoBean.getId());
        initPlayer();
        initRecommend();
        initComment();
    }

    private void initPlayer() {
        playerView.resizeScreen(false);
        playerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FILL);
        playerView.setControllerVisibile();
    }


    private void initComment() {
        commentAdapter = new BaseQuickAdapter<VideoComment, BaseViewHolder>(R.layout.comment_list_item, commentList) {
            @Override
            protected void convert(BaseViewHolder helper, VideoComment item) {
                Glide.with(PlayerActivity.this).load(RetrofitService.BASE_URL + item.getUser().getAvatar()).into((ImageView) helper.getView(R.id.user_avatar));
                helper.setText(R.id.user_name, item.getUser().getUserName())
                        .setText(R.id.user_comment, item.getContent());
            }
        };
        TextView textView = new TextView(this);
        textView.setText("暂无评论");
        textView.setGravity(Gravity.CENTER);
        textView.setMinHeight(58);
        textView.setTextColor(0xFFFFFFFF);
        commentRecyclerView.setAdapter(commentAdapter);
        commentRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        commentAdapter.setEmptyView(textView);
        basePresenter.getCommentList(videoBean.getType(), videoBean.getId());
    }

    private void initRecommend() {
        if (adapter == null) {
            adapter = new RecommendMultiRVAdapter(R.layout.recommend_recycler_view_item, recommendList);
        }
        adapter.setHeaderView(new VideoDetailHeader(this, videoBean));
        recommendRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        recommendRecyclerView.setAdapter(adapter);
        basePresenter.getRandomVideos(videoBean.getType());
        adapter.setOnItemClickListener((adapter, view, position) -> {
            updateVideoSource((VideoBean) adapter.getData().get(position));
            basePresenter.getRandomVideos(videoBean.getType());
            basePresenter.getCommentList(videoBean.getType(), videoBean.getId());
            basePresenter.retrieveFav(videoBean.getType(), videoBean.getId());
            adapter.setHeaderView(new VideoDetailHeader(PlayerActivity.this, videoBean));
        });
    }

    @Override
    public void onPresenterSuccess() {

    }

    @Override
    public void onPresenterFail(String msg) {

    }

    @Override
    protected boolean enableOrientation() {
        return true;
    }

    @Override
    protected ExoPlayerView setExoPlayerView() {
        return playerView;
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

    @Override
    public void updateFav(boolean isFav) {
        this.isFav = isFav;
        if (isFav){
            favorBtn.setBackgroundResource(R.drawable.favor_press);
        }else {
            favorBtn.setBackgroundResource(R.drawable.favor_nopress);
        }
    }

    @Override
    public void updateRandom(List<VideoBean> list) {
        recommendList.clear();
        recommendList.addAll(list);
        adapter.notifyDataSetChanged();
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
        commentList.clear();
        commentList.addAll(list);
        commentAdapter.notifyDataSetChanged();
    }

    @Override
    public void updateLogin(boolean b) {

    }


    @OnClick({R.id.comment_ic, R.id.favor_btn, R.id.download_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.comment_ic:
                basePresenter.doComment(videoBean.getType(), videoBean.getId(), commentEt.getText().toString().trim());
                commentEt.setText("");
                break;
            case R.id.favor_btn:
                showLoading();
                if (!isFav)
                    basePresenter.addFav(videoBean.getType(), videoBean.getId());
                else
                    basePresenter.deleteFav(videoBean.getType(), videoBean.getId());
                break;
            case R.id.download_btn:
                DownloadActivity.TasksManager.getImpl().addTask(videoBean.getPlayUrl(),videoBean.getTitle());
                showToast("已添加到下载列表");
                break;
        }
    }
}
