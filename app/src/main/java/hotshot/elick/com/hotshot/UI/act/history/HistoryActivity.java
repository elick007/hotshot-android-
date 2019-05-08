package hotshot.elick.com.hotshot.UI.act.history;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import hotshot.elick.com.hotshot.R;
import hotshot.elick.com.hotshot.UI.act.BaseActivity;
import hotshot.elick.com.hotshot.UI.act.player.PlayerActivity;
import hotshot.elick.com.hotshot.UI.act.player.PlayerActivityBase;
import hotshot.elick.com.hotshot.entity.VideoBean;
import hotshot.elick.com.hotshot.utils.TimeUtil;

public class HistoryActivity extends BaseActivity<HistoryPresenter> implements HistoryConstract.View {

    @BindView(R.id.history_list)
    RecyclerView historyList;
    @BindView(R.id.back_btn)
    ImageView backBtn;
    private List<VideoBean> mList;
    private BaseQuickAdapter<VideoBean, BaseViewHolder> adapter;

    @Override
    protected int setLayoutResId() {
        return R.layout.activity_history;
    }

    @Override
    protected HistoryPresenter setPresenter() {
        return new HistoryPresenter(this);
    }

    @Override
    protected void initView() {
        mList = new ArrayList<>();
        adapter = new BaseQuickAdapter<VideoBean, BaseViewHolder>(R.layout.history_item, mList) {
            @Override
            protected void convert(BaseViewHolder helper, VideoBean item) {
                Glide.with(HistoryActivity.this).load(item.getCover()).into((ImageView) helper.getView(R.id.history_item_image));
                helper.setText(R.id.history_item_title, item.getTitle())
                        .setText(R.id.history_item_author, item.getAuthor())
                        .setText(R.id.history_item_duration, TimeUtil.secondToMinute(item.getDuration()));
            }
        };
        historyList.setLayoutManager(new GridLayoutManager(HistoryActivity.this, 2));
        historyList.setAdapter(adapter);
        basePresenter.getHistory();
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                VideoBean videoBean=(VideoBean) adapter.getData().get(position);
                PlayerActivityBase.startUp(HistoryActivity.this, videoBean);
            }
        });
    }

    @Override
    public void onPresenterSuccess() {

    }

    @Override
    public void onPresenterFail(String msg) {

    }

    @Override
    public void updateList(List<VideoBean> list) {
        mList.clear();
        mList.addAll(list);
        adapter.notifyDataSetChanged();
    }

    @OnClick(R.id.back_btn)
    public void onViewClicked() {
        this.finish();
    }
}
