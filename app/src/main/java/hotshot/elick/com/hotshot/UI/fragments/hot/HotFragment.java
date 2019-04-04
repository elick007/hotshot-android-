package hotshot.elick.com.hotshot.UI.fragments.hot;


import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import butterknife.BindView;
import hotshot.elick.com.hotshot.R;
import hotshot.elick.com.hotshot.UI.fragments.BaseFragment;
import hotshot.elick.com.hotshot.UI.fragments.hot.DouYin.DouYinFragment;
import hotshot.elick.com.hotshot.UI.fragments.hot.LiVideo.LiVideoFragment;
import hotshot.elick.com.hotshot.UI.fragments.hot.OpenEye.OpenEyesFragment;
import hotshot.elick.com.hotshot.adapter.ViewPagerAdapter;
import hotshot.elick.com.hotshot.baseMVP.BasePresenter;
import hotshot.elick.com.hotshot.entity.ResponseBase;
import hotshot.elick.com.hotshot.entity.ResponseError;

public class HotFragment extends BaseFragment {
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    private ViewPagerAdapter adapter;
    @Override
    protected BasePresenter setPresenter() {
        return null;
    }

    @Override
    protected void initView() {
        if (adapter==null){
            adapter = new ViewPagerAdapter(getFragmentManager());
            adapter.addFragment(new DouYinFragment(), "抖音");
            adapter.addFragment(new OpenEyesFragment(), "开眼");
            adapter.addFragment(new LiVideoFragment(), "梨视频");
            viewPager.setAdapter(adapter);
            viewPager.setCurrentItem(1);
            tabLayout.setupWithViewPager(viewPager);
        }

    }

    @Override
    protected void startLoadData() {

    }

    @Override
    protected int setLayoutResId() {
        return R.layout.hot_video_fragment_layout;
    }


    @Override
    public void onRetry() {

    }
}
