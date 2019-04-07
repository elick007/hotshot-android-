package hotshot.elick.com.hotshot.UI.fragments.hot.OpenEye;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import hotshot.elick.com.hotshot.R;
import hotshot.elick.com.hotshot.UI.activities.player.PlayerActivity;
import hotshot.elick.com.hotshot.UI.fragments.BaseFragment;
import hotshot.elick.com.hotshot.adapter.OEMultiRVAdapter;
import hotshot.elick.com.hotshot.entity.VideoBean;
import hotshot.elick.com.hotshot.widget.StatusLayout;

public class OpenEyesFragment extends BaseFragment<OpenEyePresenter> implements OpenEyeFragmentContract.View{
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    private OEMultiRVAdapter adapter;
    private List<VideoBean> list;

    @Override
    protected OpenEyePresenter setPresenter() {
        return new OpenEyePresenter(this);
    }

    @Override
    protected void initView() {
        list = new ArrayList<>();
        adapter = new OEMultiRVAdapter(list);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter.setOnItemClickListener((adapter, view, position) -> {
            PlayerActivity.startUp(getContext(), "oe", (VideoBean) adapter.getData().get(position));
            getActivity().overridePendingTransition(R.anim.activity_start_from_bottom_to_top_anim, 0);
        });
//        adapter.setEnableLoadMore(true);
//        adapter.setOnLoadMoreListener(() -> {
//            Toast.makeText(context,"load more",Toast.LENGTH_SHORT).show();
//            adapter.loadMoreEnd();
//        },recyclerView);
    }

    @Override
    protected void startLoadData() {
        basePresenter.getHotVideo();
    }

    private void refreshRecyclerView(List<VideoBean> videoBeanList) {
        list.clear();
        videoBeanList.get(0).setItemType(VideoBean.TYPE_HEAD);
        list.addAll(videoBeanList);
        adapter.notifyDataSetChanged();
    }

    @Override
    protected int setLayoutResId() {
        return R.layout.open_eyes_fragment_layout;
    }

    @Override
    public void onRetry() {
        startLoadData();
    }

    @Override
    public void updateHotVideo(List<VideoBean> list) {
        refreshRecyclerView(list);
    }
}
