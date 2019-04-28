package hotshot.elick.com.hotshot.UI.fragments.discover;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import hotshot.elick.com.hotshot.R;
import hotshot.elick.com.hotshot.UI.fragments.BaseFragment;
import hotshot.elick.com.hotshot.api.RetrofitService;
import hotshot.elick.com.hotshot.entity.PubVideoBean;
import hotshot.elick.com.hotshot.utils.DensityUtil;
import hotshot.elick.com.hotshot.utils.MyLog;

import static android.app.Activity.RESULT_OK;

public class DiscoverFragment extends BaseFragment<DiscoverPresenter> implements DiscoverFragmentContract.View {
    @BindView(R.id.upload_btn)
    View uploadBtn;
    @BindView(R.id.pub_video_list)
    RecyclerView pubVideoList;
    private List<PubVideoBean> mList;
    private BaseQuickAdapter<PubVideoBean, BaseViewHolder> adapter;
    private final static int VIDEO_CODE=3000;

    @Override
    protected int setLayoutResId() {
        return R.layout.discover_fragment_layout;
    }

    @Override
    protected DiscoverPresenter setPresenter() {
        return new DiscoverPresenter(this);
    }


    @Override
    protected void initView() {
        mList = new ArrayList<>();
        adapter = new BaseQuickAdapter<PubVideoBean, BaseViewHolder>(R.layout.discover_list_item, mList) {
            @Override
            protected void convert(BaseViewHolder helper, PubVideoBean item) {
                Glide.with(context).load(RetrofitService.BASE_URL + item.getAuthor().getAvatar()).into((ImageView) helper.getView(R.id.author_avatar));
                helper.setText(R.id.author_name, item.getAuthor().getUserName())
                        .setText(R.id.content, item.getContent())
                        .setText(R.id.pub_date, item.getCreated().split("\\.")[0])
                        .addOnClickListener(R.id.play_image);
                PlayerView playerView = helper.getView(R.id.player_view);
                playerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FIT);
                Glide.with(context).asBitmap().load(item.getCover()).into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        playerView.setDefaultArtwork(resource);
                        int width = View.MeasureSpec.getSize(playerView.getMeasuredWidth());
                        int height = width * 72 / 128;
                        playerView.setLayoutParams(new LinearLayout.LayoutParams(width, height));
                    }
                });
                playerView.setControllerAutoShow(false);
                playerView.setControllerHideOnTouch(true);
                playerView.setPlayer(ExoPlayerFactory.newSimpleInstance(context, new DefaultTrackSelector()));

            }
        };
        adapter.setOnItemChildClickListener((adapter, view, position) -> {
            switch (view.getId()) {
                case R.id.play_image:
                    view.setVisibility(View.GONE);
                    PubVideoBean pubVideoBean = (PubVideoBean) adapter.getData().get(position);
                    PlayerView playerView = pubVideoList.getChildAt(position).findViewById(R.id.player_view);
                    SimpleExoPlayer simpleExoPlayer = ExoPlayerFactory.newSimpleInstance(context, new DefaultTrackSelector());
                    playerView.setPlayer(simpleExoPlayer);
                    DataSource.Factory dataSourceFac = new DefaultDataSourceFactory(context, Util.getUserAgent(context, context.getApplicationInfo().name));
                    MediaSource mediaSource = new ExtractorMediaSource.Factory(dataSourceFac)
                            .createMediaSource(Uri.parse(pubVideoBean.getPlayUrl()));
                    simpleExoPlayer.setPlayWhenReady(true);
                    simpleExoPlayer.prepare(mediaSource);
                    break;
            }
        });
        pubVideoList.setAdapter(adapter);
        pubVideoList.setLayoutManager(new LinearLayoutManager(context));
    }

    @Override
    protected void startLoadData() {
        basePresenter.getPubList();
    }

    @Override
    public void onRetry() {

    }


    @OnClick(R.id.upload_btn)
    public void onViewClicked() {
        PictureSelector.create(getActivity())
                .openGallery(PictureMimeType.ofVideo())
                .selectionMode(PictureConfig.SINGLE)
                .enablePreviewAudio(true)
                .videoMaxSecond(300)
                .forResult(VIDEO_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK){
            switch (requestCode){
                case VIDEO_CODE:
                    break;
            }
        }
    }

    @Override
    public void updateList(List<PubVideoBean> list) {
        mList.clear();
        mList.addAll(list);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        MyLog.d("video begin release");
        if (pubVideoList!=null){
            for (int i = 0; i < pubVideoList.getChildCount(); i++) {
                BaseViewHolder viewHolder = (BaseViewHolder) pubVideoList.findViewHolderForLayoutPosition(i);
                if (viewHolder != null) {
                    PlayerView playerView = viewHolder.getView(R.id.player_view);
                    SimpleExoPlayer simpleExoPlayer= (SimpleExoPlayer) playerView.getPlayer();
                    if (simpleExoPlayer!=null){
                        simpleExoPlayer.release();
                        MyLog.d("released");
                    }
                }
            }
        }

    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (!isVisibleToUser){
            if (pubVideoList!=null){
                for (int i = 0; i < pubVideoList.getChildCount(); i++) {
                    BaseViewHolder viewHolder = (BaseViewHolder) pubVideoList.findViewHolderForLayoutPosition(i);
                    if (viewHolder != null) {
                        PlayerView playerView = viewHolder.getView(R.id.player_view);
                        SimpleExoPlayer simpleExoPlayer= (SimpleExoPlayer) playerView.getPlayer();
                        if (simpleExoPlayer!=null){
                            simpleExoPlayer.setPlayWhenReady(false);
                        }
                    }
                }
            }
        }
    }

}
