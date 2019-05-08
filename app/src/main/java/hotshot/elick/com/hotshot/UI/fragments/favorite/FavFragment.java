package hotshot.elick.com.hotshot.UI.fragments.favorite;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import hotshot.elick.com.hotshot.R;
import hotshot.elick.com.hotshot.UI.act.player.PlayerActivity;
import hotshot.elick.com.hotshot.UI.act.player.PlayerActivityBase;
import hotshot.elick.com.hotshot.UI.fragments.BaseFragment;
import hotshot.elick.com.hotshot.entity.FavVideo;
import hotshot.elick.com.hotshot.entity.VideoBean;
import hotshot.elick.com.hotshot.utils.DensityUtil;
import hotshot.elick.com.hotshot.utils.GlideUtils;
import hotshot.elick.com.hotshot.widget.StatusLayout;

public class FavFragment extends BaseFragment<FavPresenter> implements FavContract.View {
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.swipe_layout)
    SwipeRefreshLayout swipeLayout;
    private String channel;
    private List<VideoBean> mList=new ArrayList<>();
    private BaseQuickAdapter<VideoBean, BaseViewHolder> adapter;

    @Override
    protected int setLayoutResId() {
        return R.layout.open_eyes_fragment_layout;
    }

    @Override
    protected FavPresenter setPresenter() {
        return new FavPresenter(this);
    }

    @Override
    protected void initView() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            channel = bundle.getString("channel");
        }
        if (!channel.equals("dy")) {
            adapter = new BaseQuickAdapter<VideoBean, BaseViewHolder>(R.layout.history_item, mList) {
                @Override
                protected void convert(BaseViewHolder helper, VideoBean item) {
                    Glide.with(context).load(item.getCover()).into((ImageView) helper.getView(R.id.history_item_image));
                    helper.setText(R.id.history_item_title, item.getTitle())
                            .setText(R.id.history_item_author, item.getAuthor())
                            .setText(R.id.history_item_duration, item.getDuration());
                }
            };
        } else {
            adapter = new BaseQuickAdapter<VideoBean, BaseViewHolder>(R.layout.videos_list_item_dy, mList) {
                @Override
                protected void convert(BaseViewHolder helper, VideoBean item) {
                    GlideUtils.loadOutLineImage(context,item.getCover(),helper.getView(R.id.dy_item_image_view));
                    helper.setText(R.id.dy_description, item.getDescription())
                            .setText(R.id.dy_author, item.getAuthor());
                }
            };
        }
        TextView textView = new TextView(context);
        textView.setText("-没有更多内容-");
        textView.setTextColor(0x00000000);
        textView.setGravity(Gravity.CENTER);
        textView.setMinHeight(DensityUtil.dip2px(context, 30));
        adapter.setFooterView(textView, 2);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 2);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int i) {
                if (adapter.getItemViewType(i) == BaseQuickAdapter.FOOTER_VIEW||adapter.getItemViewType(i)==BaseQuickAdapter.EMPTY_VIEW) {
                    return 2;
                } else {
                    return 1;
                }
            }
        });
        adapter.bindToRecyclerView(recyclerView);
        recyclerView.setLayoutManager(gridLayoutManager);
        adapter.setEmptyView(R.layout.empty_view_layout);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (!channel.equals("dy")){
                    PlayerActivityBase.startUp(context,(VideoBean) adapter.getData().get(position));
                }
            }
        });
    }

    @Override
    protected void startLoadData() {
        basePresenter.getFav(channel);
    }

    @Override
    public void onRetry() {

    }

    @Override
    public void updateList(List<FavVideo> list) {
        swipeLayout.setRefreshing(false);
        statusLayout.setLayoutStatus(StatusLayout.STATUS_LAYOUT_GONE);
        mList.clear();
        for (FavVideo favVideo : list) {
            mList.add(favVideo.getVideo());
        }
        adapter.notifyDataSetChanged();
    }
}
