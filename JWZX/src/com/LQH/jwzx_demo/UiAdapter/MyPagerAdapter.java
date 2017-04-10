package com.LQH.jwzx_demo.UiAdapter;

import java.util.List;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

public class MyPagerAdapter extends PagerAdapter {

	
	private List<View> pageList;
	
	public MyPagerAdapter(List<View> pageList){
		this.pageList = pageList;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return pageList.size();
	}

	
	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		// TODO Auto-generated method stub
		return arg0 == arg1;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		// TODO Auto-generated method stub
		container.removeView(pageList.get(position));
	}

	public Object instantiateItem(View container, int position) {
		// TODO Auto-generated method stub
		((ViewPager)container).addView(pageList.get(position));
		  return pageList.get(position);
	}

}
