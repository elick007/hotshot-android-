package hotshot.elick.com.hotshot.UI.fragments.hot;


import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import hotshot.elick.com.hotshot.R;
import hotshot.elick.com.hotshot.UI.activities.DouyinPlayerActivity;
import hotshot.elick.com.hotshot.UI.activities.PlayerActivity;
import hotshot.elick.com.hotshot.UI.fragments.BaseFragment;
import hotshot.elick.com.hotshot.adapter.DYRVAdapter;
import hotshot.elick.com.hotshot.entity.OpenEyeEntity;
import hotshot.elick.com.hotshot.entity.ResponseBase;
import hotshot.elick.com.hotshot.entity.ResponseError;
import hotshot.elick.com.hotshot.entity.VideoBean;
import hotshot.elick.com.hotshot.presenter.DouYinPrensenter;
import hotshot.elick.com.hotshot.widget.StatusLayout;

public class DouYinFragment extends BaseFragment<DouYinPrensenter> {
    @BindView(R.id.swipe_layout)
    SwipeRefreshLayout swipeLayout;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    private List<VideoBean> list;
    private DYRVAdapter adapter;

    @Override
    protected DouYinPrensenter setPresenter() {
        return new DouYinPrensenter(this);
    }

    @Override
    protected void initView() {
        list = new ArrayList<>();
        swipeLayout.setOnRefreshListener(() -> swipeLayout.setRefreshing(false));
        adapter = new DYRVAdapter(R.layout.videos_list_item_dy, list);
        recyclerView.setLayoutManager(new GridLayoutManager(context, 2));
        recyclerView.setAdapter(adapter);
        //recyclerView.addItemDecoration(new DividerItemDecoration(context,DividerItemDecoration.HORIZONTAL));
        //recyclerView.addItemDecoration(new DividerItemDecoration(context,DividerItemDecoration.VERTICAL));
        adapter.setOnItemClickListener((adapter, view, position) -> {
//            PlayerActivity.startUp(getContext(),"dy", (VideoBean) adapter.getData().get(position));
            Intent intent=new Intent(context, DouyinPlayerActivity.class);
            intent.putExtra("video_info",(VideoBean)adapter.getData().get(position));
            context.startActivity(intent);
            getActivity().overridePendingTransition(R.anim.activity_start_from_bottom_to_top_anim, 0);
        });
    }

    @Override
    protected void startLoadData() {
        basePresenter.getDYHotVideo();
        statusLayout.setLayoutStatus(StatusLayout.STATUS_LAYOUT_LOADING);
    }

    private void refreshRecyclerView(List<VideoBean> videoBeanList) {
        list.clear();
        list.addAll(videoBeanList);
        adapter.notifyDataSetChanged();
    }

    @Override
    protected int setLayoutResId() {
        return R.layout.douyin_fragment_layout;
    }

    @Override
    public void onPresenterSuccess(ResponseBase response) {
        OpenEyeEntity openEyeEntity = (OpenEyeEntity) response;
        refreshRecyclerView(openEyeEntity.getData().getVideoList());
        statusLayout.setLayoutStatus(StatusLayout.STATUS_LAYOUT_GONE);
    }

    @Override
    public void onPresenterFail(ResponseError error) {
        statusLayout.setLayoutStatus(StatusLayout.STATUS_LAYOUT_ERROE);
    }

    @Override
    public void onRetry() {

    }
}
