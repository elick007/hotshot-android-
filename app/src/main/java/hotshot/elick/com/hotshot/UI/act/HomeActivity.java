package hotshot.elick.com.hotshot.UI.act;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import hotshot.elick.com.hotshot.R;
import hotshot.elick.com.hotshot.UI.fragments.BaseFragment;
import hotshot.elick.com.hotshot.UI.fragments.Mine.MineFragment;
import hotshot.elick.com.hotshot.UI.fragments.discover.DiscoverFragment;
import hotshot.elick.com.hotshot.UI.fragments.home.DefaultFragment;
import hotshot.elick.com.hotshot.UI.fragments.hot.HotFragment;
import hotshot.elick.com.hotshot.adapter.ViewPagerAdapter;
import hotshot.elick.com.hotshot.baseMVP.BasePresenter;
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
    @BindView(R.id.bottom_discover_image)
    ImageView bottomDiscoverImage;
    @BindView(R.id.bottom_discover_text)
    TextView bottomDiscoverText;
    @BindView(R.id.bottom_discover_view_group)
    LinearLayout bottomDiscoverViewGroup;
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
        onItemChange(0);
        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new DefaultFragment(), "首页");
        adapter.addFragment(new HotFragment(), "热门");
        adapter.addFragment(new DiscoverFragment(), "发现");
        adapter.addFragment(new MineFragment(), "我的");
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
    public void onPresenterSuccess() {

    }

    @Override
    public void onPresenterFail(String msg) {

    }


    @OnClick({R.id.bottom_default_view_group, R.id.bottom_hot_view_group, R.id.bottom_discover_view_group,R.id.bottom_mine_view_group})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bottom_default_view_group:
                onItemChange(0);
                break;
            case R.id.bottom_hot_view_group:
                onItemChange(1);
                break;
            case R.id.bottom_discover_view_group:
                onItemChange(2);
                break;
            case R.id.bottom_mine_view_group:
                onItemChange(3);
                break;
        }
    }

    private void onItemChange(int newItem) {
        int currentItem = homeActivityViewPager.getCurrentItem();
        switch (currentItem) {
            case 0:
                bottomDefaultImage.setImageResource(R.drawable.bottom_default_select);
                setTextColor(bottomDefaultText,false);
                break;
            case 1:
                bottomHotImage.setImageResource(R.drawable.bottom_hot_select);
                setTextColor(bottomHotText,false);
                break;
            case 2:
                bottomDiscoverImage.setImageResource(R.drawable.bottom_discover_select);
                setTextColor(bottomDiscoverText,false);
                break;
            case 3:
                bottomMineImage.setImageResource(R.drawable.bottom_mine_select);
                setTextColor(bottomDiscoverText,false);
        }
        homeActivityViewPager.setCurrentItem(newItem);
        switch (newItem){
            case 0:
                bottomDefaultImage.setImageResource(R.drawable.bottom_default_selected);
                setTextColor(bottomDefaultText,true);
                break;
            case 1:
                bottomHotImage.setImageResource(R.drawable.bottom_hot_selected);
                setTextColor(bottomHotText,true);
                break;
            case 2:
                bottomDiscoverImage.setImageResource(R.drawable.bottom_discover_selected);
                setTextColor(bottomDiscoverText,true);
                break;
            case 3:
                bottomMineImage.setImageResource(R.drawable.bottom_mine_selected);
                setTextColor(bottomDiscoverText,true);
        }
    }

    private void setTextColor(TextView textView, boolean selected) {
        if (selected) {
            textView.setTextColor(getResources().getColor(R.color.bottom_navigation_selected));
        } else {
            textView.setTextColor(getResources().getColor(R.color.bottom_navigation_select));
        }
    }

}
