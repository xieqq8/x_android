package com.kuaxue.demo.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import java.util.List;

/**
 * 注意用FragmentPagerAdapter, FragmentStatePagerAdapter会释放
 * @author xieqq
 */
public class Adapter000FgMainViewPager extends FragmentPagerAdapter {
	private Fragment currentFragment;

	private List<Fragment> listFragment;

	public Adapter000FgMainViewPager(FragmentManager fm) {
		super(fm);
	}

	public Adapter000FgMainViewPager(FragmentManager fm, List<Fragment> fragmentList) {
		super(fm);
		this.listFragment = fragmentList;
	}

	@Override
	public Fragment getItem(int arg0) {
		return listFragment.get(arg0);
	}

	@Override
	public int getCount() {
		return listFragment.size();
	}

	@Override
	public void setPrimaryItem(ViewGroup container, int position, Object object) {
		currentFragment = (Fragment) object;

		super.setPrimaryItem(container, position, object);
	}

	public Fragment getPrimaryItem() {
		return currentFragment;
	}
}
