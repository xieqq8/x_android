package com.kuaxue.demo.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.kuaxue.database.dao.CourseInfo;

import java.util.List;

/**
 * 
 * 
 * @author Lou
 *
 */
public class FgPagerAdapter extends FragmentPagerAdapter {
	
	private List<Fragment> listFragment;
	private List<CourseInfo> allKeMuList;

	public FgPagerAdapter(FragmentManager fm, List<Fragment> fragmentList,List<CourseInfo> allKeMuList) {
		super(fm);
		this.listFragment = fragmentList;
		this.allKeMuList = allKeMuList;
	}

	@Override
	public Fragment getItem(int position) {
		return listFragment.get(position);
	}

	@Override
	public int getCount() {	
		return listFragment.size();
	}
	@Override
	public CharSequence getPageTitle(int position) {
		return allKeMuList.get(position).getKemuName();
	}
	
	@Override
	public Object instantiateItem(ViewGroup container, final int position) {
		Object obj = super.instantiateItem(container, position);
		return obj;
	}

	@Override
	public int getItemPosition(Object object) {
		return POSITION_NONE;// 重载 getItemPosition() 并返回
								// POSITION_NONE，以触发销毁对象以及重建对象
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		super.destroyItem(container, position, object);
	}
	
}
