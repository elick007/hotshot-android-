package hotshot.elick.com.hotshot.UI.activities;

import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import hotshot.elick.com.hotshot.R;
import hotshot.elick.com.hotshot.UI.fragments.BaseFragment;
import hotshot.elick.com.hotshot.UI.fragments.HotFragment;
import hotshot.elick.com.hotshot.UI.fragments.DefaultFragment;
import hotshot.elick.com.hotshot.UI.fragments.MineFragment;
import hotshot.elick.com.hotshot.adapter.ViewPagerAdapter;
import hotshot.elick.com.hotshot.baseMVP.BasePresenter;
import hotshot.elick.com.hotshot.entity.ResponseBase;
import hotshot.elick.com.hotshot.entity.ResponseError;
import hotshot.elick.com.hotshot.widget.CustomViewPager;

public class HomeActivity extends BaseActivity {


    @BindView(R.id.bottom_default_image)
    ImageView bottomDefaultImage;
    @BindView(R.id.bottom_default_text)
    TextView bottomDefaultText;
    @BindView(R.id.bottom_default_view_group)
    LinearLayout bottomDefaultViewGroup;
    @BindView(R.id.bottom_hot_image)
    ImageView bottomHotImage;
    @BindView(R.id.bottom_hot_text)
    TextView bottomHotText;
    @BindView(R.id.bottom_hot_view_group)
    LinearLayout bottomHotViewGroup;
    @BindView(R.id.bottom_mine_image)
    ImageView bottomMineImage;
    @BindView(R.id.bottom_mine_text)
    TextView bottomMineText;
    @BindView(R.id.bottom_mine_view_group)
    LinearLayout bottomMineViewGroup;
    @BindView(R.id.home_activity_view_pager)
    CustomViewPager homeActivityViewPager;
    private ViewPagerAdapter adapter;


    @Override
    protected int setLayoutResId() {
        return R.layout.activity_home;
    }

    @Override
    protected BasePresenter setPresenter() {
        return null;
    }

    @Override
    protected void initView() {
        setDefaultSelectEvent(true);
        adapter=new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new DefaultFragment(),"首页");
        adapter.addFragment(new HotFragment(),"热门");
        adapter.addFragment(new MineFragment(),"我的");
        homeActivityViewPager.setCurrentItem(0);
        homeActivityViewPager.setSlideable(false);
        homeActivityViewPager.setAdapter(adapter);
    }

    private void replaceFragment(int containerId, BaseFragment fragment) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(containerId, fragment);
        ft.commit();
    }

    @Override
    public void onPresenterSuccess(ResponseBase response) {

    }

    @Override
    public void onPresenterFail(ResponseError error) {

    }


    @OnClick({R.id.bottom_default_view_group, R.id.bottom_hot_view_group, R.id.bottom_mine_view_group})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bottom_default_view_group:
                setDefaultSelectEvent(true);
                setHotSelectEvent(false);
                setMineSelectEvent(false);
                homeActivityViewPager.setCurrentItem(0);
                break;
            case R.id.bottom_hot_view_group:
                setDefaultSelectEvent(false);
                setHotSelectEvent(true);
                setMineSelectEvent(false);
                homeActivityViewPager.setCurrentItem(1);
                break;
            case R.id.bottom_mine_view_group:
                setDefaultSelectEvent(false);
                setHotSelectEvent(false);
                setMineSelectEvent(true);
                homeActivityViewPager.setCurrentItem(2);
                break;
        }
    }

    private void setDefaultSelectEvent(boolean selected) {
        if (selected) {
            bottomDefaultImage.setImageResource(R.drawable.bottom_default_selected);
            bottomDefaultText.setTextColor(getResources().getColor(R.color.bottom_navigation_selected));
        } else {
            bottomDefaultImage.setImageResource(R.drawable.bottom_default_select);
            bottomDefaultText.setTextColor(getResources().getColor(R.color.bottom_navigation_select));
        }
    }

    private void setHotSelectEvent(boolean selected) {
        if (selected) {
            bottomHotImage.setImageResource(R.drawable.bottom_hot_selected);
            bottomHotText.setTextColor(getResources().getColor(R.color.bottom_navigation_selected));
        } else {
            bottomHotImage.setImageResource(R.drawable.bottom_hot_select);
            bottomHotText.setTextColor(getResources().getColor(R.color.bottom_navigation_select));
        }
    }

    private void setMineSelectEvent(boolean selected) {
        if (selected) {
            bottomMineImage.setImageResource(R.drawable.bottom_mine_selected);
            bottomMineText.setTextColor(getResources().getColor(R.color.bottom_navigation_selected));
        } else {
            bottomMineImage.setImageResource(R.drawable.bottom_mine_select);
            bottomMineText.setTextColor(getResources().getColor(R.color.bottom_navigation_select));
        }
    }

}
