package hotshot.elick.com.hotshot.UI.act.favorite;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.OnClick;
import hotshot.elick.com.hotshot.R;
import hotshot.elick.com.hotshot.UI.act.BaseActivity;
import hotshot.elick.com.hotshot.UI.fragments.favorite.FavFragment;
import hotshot.elick.com.hotshot.adapter.ViewPagerAdapter;
import hotshot.elick.com.hotshot.baseMVP.BasePresenter;

public class FavActivity extends BaseActivity {

    @BindView(R.id.back_btn)
    ImageView backBtn;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    private ViewPagerAdapter adapter;

    @Override
    protected int setLayoutResId() {
        return R.layout.activity_fav_acitivity;
    }

    @Override
    protected BasePresenter setPresenter() {
        return null;
    }

    @Override
    protected void initView() {
        if (adapter == null) {
            adapter = new ViewPagerAdapter(getSupportFragmentManager());
            Bundle bundle=new Bundle();
            bundle.putString("channel","dy");
            FavFragment dyFragment =new FavFragment();
            dyFragment.setArguments(bundle);
            adapter.addFragment(dyFragment, "抖音");
            FavFragment oeFragment=new FavFragment();
            Bundle oeBundle=new Bundle();
            oeBundle.putString("channel","oe");
            oeFragment.setArguments(oeBundle);
            adapter.addFragment(oeFragment, "开眼");
            Bundle lspBundle=new Bundle();
            lspBundle.putString("channel","lsp");
            FavFragment lspFragment=new FavFragment();
            lspFragment.setArguments(lspBundle);
            adapter.addFragment(lspFragment, "梨视频");
            viewPager.setAdapter(adapter);
            viewPager.setCurrentItem(1);
            tabLayout.setupWithViewPager(viewPager);
        }
    }

    @Override
    public void onPresenterSuccess() {

    }

    @Override
    public void onPresenterFail(String msg) {

    }

    @OnClick(R.id.back_btn)
    public void onViewClicked() {
        this.finish();
    }
}
