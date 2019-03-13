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
import hotshot.elick.com.hotshot.adapter.LiVideoMultiRVAdapter;
import hotshot.elick.com.hotshot.entity.OpenEyeEntity;
import hotshot.elick.com.hotshot.entity.ResponseBase;
import hotshot.elick.com.hotshot.entity.ResponseError;
import hotshot.elick.com.hotshot.entity.VideoBean;
import hotshot.elick.com.hotshot.presenter.LiVideoPresenter;
import hotshot.elick.com.hotshot.widget.StatusLayout;

public class LiVideoFragment extends BaseFragment<LiVideoPresenter> {
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.swipe_layout)
    SwipeRefreshLayout swipeLayout;

    LiVideoMultiRVAdapter adapter;
    List<VideoBean> list;

    @Override
    protected LiVideoPresenter setPresenter() {
        return new LiVideoPresenter(this);
    }

    @Override
    protected void initView() {
        list = new ArrayList<>();
        adapter = new LiVideoMultiRVAdapter(list);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter.setOnItemClickListener((adapter, view, position) -> {
            PlayerActivity.startUp(getContext(),"lsp", (VideoBean) adapter.getData().get(position));
            getActivity().overridePendingTransition(R.anim.activity_start_from_bottom_to_top_anim,0);
        });
    }

    @Override
    protected void startLoadData() {
        basePresenter.getLspHotVideo();
    }


    private void refreshRecyclerView(List<VideoBean> videoBeanList){
        videoBeanList.get(0).setItemType(VideoBean.TYPE_HEAD);
        list.clear();
        list.addAll(videoBeanList);
        adapter.notifyDataSetChanged();
    }
    @Override
    protected int setLayoutResId() {
        return R.layout.li_video_fragment_layout;
    }

    @Override
    public void onPresenterSuccess(ResponseBase response) {
        OpenEyeEntity openEyeEntity= (OpenEyeEntity) response;
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
