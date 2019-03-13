package hotshot.elick.com.hotshot.UI.fragments.hot;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import hotshot.elick.com.hotshot.R;
import hotshot.elick.com.hotshot.UI.activities.PlayerActivity;
import hotshot.elick.com.hotshot.UI.fragments.BaseFragment;
import hotshot.elick.com.hotshot.adapter.OEMultiRVAdapter;
import hotshot.elick.com.hotshot.entity.OpenEyeEntity;
import hotshot.elick.com.hotshot.entity.ResponseBase;
import hotshot.elick.com.hotshot.entity.ResponseError;
import hotshot.elick.com.hotshot.entity.VideoBean;
import hotshot.elick.com.hotshot.presenter.OpenEyePresenter;
import hotshot.elick.com.hotshot.utils.MyLog;
import hotshot.elick.com.hotshot.widget.StatusLayout;

public class OpenEyesFragment extends BaseFragment<OpenEyePresenter> {
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.swipe_layout)
    SwipeRefreshLayout swipeLayout;
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
        swipeLayout.setOnRefreshListener(() -> {
            statusLayout.setLayoutStatus(StatusLayout.STATUS_LAYOUT_LOADING);
            startLoadData();
        });
//        adapter.setEnableLoadMore(true);
//        adapter.setOnLoadMoreListener(() -> {
//            Toast.makeText(context,"load more",Toast.LENGTH_SHORT).show();
//            adapter.loadMoreEnd();
//        },recyclerView);
    }

    @Override
    protected void startLoadData() {
        swipeLayout.setRefreshing(true);
        basePresenter.getVideos("oe", "hot");
    }

    public void refreshRecyclerView(List<VideoBean> videoBeanList) {
        videoBeanList.get(0).setItemType(VideoBean.TYPE_HEAD);
        list.addAll(videoBeanList);
        adapter.notifyDataSetChanged();

    }

    @Override
    protected int setLayoutResId() {
        return R.layout.open_eyes_fragment_layout;
    }

    @Override
    public void onPresenterSuccess(ResponseBase response) {
        MyLog.d("get oe hot video success");
        swipeLayout.setRefreshing(false);
        OpenEyeEntity openEyeEntity = (OpenEyeEntity) response;
        refreshRecyclerView(openEyeEntity.getData().getVideoList());
        statusLayout.setLayoutStatus(StatusLayout.STATUS_LAYOUT_GONE);
    }

    @Override
    public void onPresenterFail(ResponseError error) {
        swipeLayout.setRefreshing(false);
        statusLayout.setLayoutStatus(StatusLayout.STATUS_LAYOUT_ERROE);
    }

    @Override
    public void onRetry() {
        //Toast.makeText(context, "retring...", Toast.LENGTH_SHORT).show();
        startLoadData();
    }
}
