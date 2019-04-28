package hotshot.elick.com.hotshot.UI.act.player;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
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
import butterknife.OnClick;
import hotshot.elick.com.hotshot.R;
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

public class PlayerActivity extends PlayerActivityBase<PlayerPresenter> implements PlayerActivityContract.View, CheckBox.OnCheckedChangeListener {
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
    @BindView(R.id.favor_checkbox)
    CheckBox favorCheckbox;
    @BindView(R.id.download_checkbox)
    CheckBox downloadCheckbox;
    private VideoBean video;
    private SimpleExoPlayer simpleExoPlayer;
    private String channel;
    private List<VideoBean> recommendList = new ArrayList<>();
    private RecommendMultiRVAdapter adapter;
    private List<VideoComment> commentList = new ArrayList<>();
    private BaseQuickAdapter<VideoComment, BaseViewHolder> commentAdapter;

    @Override
    protected void initView() {
        Intent intent = getIntent();
        channel = intent.getStringExtra("channel");
        video = (VideoBean) intent.getSerializableExtra("video_info");
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
        basePresenter.retrieveFav(channel, video.getId());
        initPlayer();
        initRecommend();
        initComment();
    }

    private void initPlayer() {
        playerView.resizeScreen(false);
        simpleExoPlayer = ExoPlayerFactory.newSimpleInstance(this, new DefaultTrackSelector());
        simpleExoPlayer.setPlayWhenReady(true);
        playerView.setPlayer(simpleExoPlayer);
        playerView.setControllerVisibile();
        playerView.setControllerShowTimeoutMs(2500);
        playerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FILL);
        updateVideoSource();
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
        basePresenter.getCommentList(channel, video.getId());
    }

    private void initRecommend() {
        if (adapter == null) {
            adapter = new RecommendMultiRVAdapter(R.layout.recommend_recycler_view_item, recommendList);
        }
        adapter.setHeaderView(new VideoDetailHeader(this, video));
        recommendRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        recommendRecyclerView.setAdapter(adapter);
        basePresenter.getRandomVideos(channel);
        adapter.setOnItemClickListener((adapter, view, position) -> {
            this.video = (VideoBean) adapter.getData().get(position);
            updateVideoSource();
            favorCheckbox.setOnCheckedChangeListener(null);
            basePresenter.getRandomVideos(channel);
            basePresenter.getCommentList(channel, video.getId());
            basePresenter.retrieveFav(channel, video.getId());

        });
    }

    private void updateVideoSource() {
        simpleExoPlayer.stop(true);
        DataSource.Factory dataSourceFac = new DefaultDataSourceFactory(this, Util.getUserAgent(this, this.getApplication().getPackageName()));
        MediaSource mediaSource = new ExtractorMediaSource.Factory(dataSourceFac)
                .createMediaSource(Uri.parse(video.getPlayUrl()));
        simpleExoPlayer.prepare(mediaSource);
        if (adapter != null)
            adapter.setHeaderView(new VideoDetailHeader(this, video));
    }


    public static void startUp(Context context, String channel, VideoBean videoBean) {
        Intent intent = new Intent(context, PlayerActivity.class);
        intent.putExtra("channel", channel);
        intent.putExtra("video_info", videoBean);
        context.startActivity(intent);
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
        favorCheckbox.setChecked(isFav);
        favorCheckbox.setOnCheckedChangeListener(this);
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
    }

    @Override
    public void delFavSuc() {
        dismissLoading();
    }

    @Override
    public void updateComment(List<VideoComment> list) {
        commentList.clear();
        commentList.addAll(list);
        commentAdapter.notifyDataSetChanged();
    }


    @OnClick(R.id.comment_ic)
    public void onViewClicked() {
        basePresenter.doComment(channel,video.getId(),commentEt.getText().toString().trim());
        commentEt.setText("");
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        showLoading();
        if (isChecked) {
            basePresenter.addFav(channel, video.getId());
        } else {
            basePresenter.deleteFav(channel, video.getId());
        }
    }
}
