package hotshot.elick.com.hotshot.adapter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import hotshot.elick.com.hotshot.UI.fragments.BaseFragment;

public class ViewPagerAdapter extends FragmentPagerAdapter {
    List<BaseFragment> fragmentList = new ArrayList<>();
    List<String> fragmentTitles = new ArrayList<>();
    FragmentManager fragmentManager;
    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
        this.fragmentManager=fm;
    }

    public void addFragment(BaseFragment fragment, String title) {
        fragmentList.add(fragment);
        fragmentTitles.add(title);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return fragmentTitles.get(position);
    }

    @Override
    public Fragment getItem(int i) {
        return fragmentList.get(i);
    }

    @Override
    public int getCount() {
        return fragmentList == null ? 0 : fragmentList.size();
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        BaseFragment fragment= (BaseFragment) super.instantiateItem(container, position);
        this.fragmentManager.beginTransaction().show(fragment).commit();
        return fragment;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        BaseFragment fragment=fragmentList.get(position);
        this.fragmentManager.beginTransaction().hide(fragment).commit();
    }
}
