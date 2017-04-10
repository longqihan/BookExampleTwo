package com.LQH.jwzx_demo.Activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.LQH.jwzx_demo.Fragment.MLiftFragment;
import com.LQH.jwzx_demo.View.ChangeColorIconWithTextView;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;
import com.zhy.zhy_slidemenu_demo02.R;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.AdapterView.OnItemClickListener;

public class MainActivity3 extends SlidingFragmentActivity implements OnClickListener {

	

	private List<ChangeColorIconWithTextView> mTabIndicator = new ArrayList<ChangeColorIconWithTextView>();
	private ListView mSctable;

	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity3_main);
		
		List<Map<String, String>> data = new ArrayList<Map<String, String>>();
		Map<String, String> map1 = new HashMap<String, String>();
		map1.put("time", "2016~2017");
		map1.put("year", "第二学期");
		Map<String, String> map2 = new HashMap<String, String>();
		map2.put("time", "2016~2017");
		map2.put("year", "第一学期");
		Map<String, String> map3 = new HashMap<String, String>();
		map3.put("time", "2015~2016");
		map3.put("year", "第二学期");
		Map<String, String> map4 = new HashMap<String, String>();
		map4.put("time", "2015~2016");
		map4.put("year", "第一学期");
		Map<String, String> map5 = new HashMap<String, String>();
		map5.put("time", "2014~2015");
		map5.put("year", "第二学期");
		Map<String, String> map6 = new HashMap<String, String>();
		map6.put("time", "2014~2015");
		map6.put("year", "第一学期");
		Map<String, String> map7 = new HashMap<String, String>();
		map7.put("time", "2013~2014");
		map7.put("year", "第二学期");
		Map<String, String> map8 = new HashMap<String, String>();
		map8.put("time", "2013~2014");
		map8.put("year", "第一学期");
		Map<String, String> map9 = new HashMap<String, String>();
		map9.put("time", "2012~2013");
		map9.put("year", "第二学期");
		Map<String, String> map10 = new HashMap<String, String>();
		map10.put("time", "2012~2013");
		map10.put("year", "第一学期");
		Map<String, String> map11 = new HashMap<String, String>();
		map11.put("time", "2011~2012");
		map11.put("year", "学年");
		Map<String, String> map12 = new HashMap<String, String>();
		map12.put("time", "2010~2011");
		map12.put("year", "学年");
		Map<String, String> map13 = new HashMap<String, String>();
		map13.put("time", "2009~2010");
		map13.put("year", "学年");
		Map<String, String> map14 = new HashMap<String, String>();
		map14.put("time", "2008~2009");
		map14.put("year", "学年");
		Map<String, String> map15 = new HashMap<String, String>();
		map15.put("time", "2007~2008");
		map15.put("year", "学年");
		Map<String, String> map16 = new HashMap<String, String>();
		map16.put("time", "2006~2007");
		map16.put("year", "学年");

		// [1.1]把map加入到集合中
		data.add(map1);
		data.add(map2);
		data.add(map3);
		data.add(map4);
		data.add(map5);
		data.add(map6);
		data.add(map7);
		data.add(map8);
		data.add(map9);
		data.add(map10);
		data.add(map11);
		data.add(map12);
		data.add(map13);
		data.add(map14);
		data.add(map15);
		data.add(map16);

		
		mSctable = (ListView) findViewById(R.id.id_school_table);
		SimpleAdapter adapter = new SimpleAdapter(getApplicationContext(), data,
				R.layout.table_item, new String[] { "time", "year" },
				new int[] { R.id.tv_time, R.id.tv_year });
		mSctable.setAdapter(adapter);

		mSctable.setOnItemClickListener(new OnItemClickListener() {

			// 判断点击的条目
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO 自动生成的方法存根

				switch (arg2) {
				case 0:
					Intent intent = new Intent();
					intent.setClass(MainActivity3.this, TableActivity.class);
					intent.putExtra(
							"str",
							"http://r.photo.store.qq.com/psb?/V10nRWT51qixBd/PRo17pvxWRlvnG6X0bf.tjR8yeJYkVDWXzkDU61Tw9w!/o/dLEAAAAAAAAA&ek=1&kp=1&pt=0&bo=awNrAmsDawIDCC0!&tm=1482422400&sce=50-0-0&rf=viewer_311");
					startActivity(intent);
					// getActivity().finish();
					break;
				case 10:
					Intent intent10 = new Intent();
					intent10.setClass(MainActivity3.this, TableActivity.class);
					intent10.putExtra("str",
							"http://jwc.jxnu.edu.cn/Jxzl_2012.jpg");
					startActivity(intent10);
					break;

				case 1:
					Intent intent1 = new Intent();
					intent1.setClass(MainActivity3.this, TableActivity.class);
					intent1.putExtra(
							"str",
							"http://b177.photo.store.qq.com/psb?/V10nRWT51qixBd/9AYljC4qn0t.UNYcS5EtJ*uVwn.5*1fcuvf4J4LXQ.E!/b/dLEAAAAAAAAA&amp;bo=ZQNfAmUDXwIDACU!&rf=viewer_311");
					startActivity(intent1);
					break;
				case 2:
					Intent intent2 = new Intent();
					intent2.setClass(MainActivity3.this, TableActivity.class);
					intent2.putExtra(
							"str",
							"http://a4.qpic.cn/psb?/V10nRWT51qixBd/aAyfO3XBt8.CaZ*YGHjtOrebfAria4XmfEORVvohN1A!/b/dB8BAAAAAAAA&ek=1&kp=1&pt=0&bo=YgNkAmIDZAIFACM!&tm=1482422400&sce=0-12-12&rf=viewer_311");
					startActivity(intent2);
					break;

				case 3:

					break;

				}

			}

		});
		
		
		
		initRightMenu();
		initTabIndicator();

		
	}

  
	
	@SuppressLint("Recycle")
	public void onClick(View v) {
		//FragmentManager fg = getSupportFragmentManager();
		//FragmentTransaction ft = fg.beginTransaction();

		resetOtherTabs();
		switch (v.getId()) {
		case R.id.id_indicator_one:
			mTabIndicator.get(0).setIconAlpha(1.0f);
			Intent intent = new Intent();
			intent.setClass(MainActivity3.this, MainActivity.class);
			startActivity(intent);
			finish();
			overridePendingTransition(android.R.anim.fade_in,
					android.R.anim.fade_out);
			break;
		case R.id.id_indicator_two:
			resetOtherTabs();
			mTabIndicator.get(1).setIconAlpha(1.0f);
			Intent intent2 = new Intent();
			intent2.setClass(MainActivity3.this, MainActivity1.class);
			startActivity(intent2);
			finish();
			overridePendingTransition(android.R.anim.fade_in,
					android.R.anim.fade_out);
			break;
		case R.id.id_indicator_three:
			resetOtherTabs();
			mTabIndicator.get(2).setIconAlpha(1.0f);
			Intent intent3 = new Intent();
			intent3.setClass(MainActivity3.this, MainActivity2.class);
			startActivity(intent3);
			finish();
			overridePendingTransition(android.R.anim.fade_in,
					android.R.anim.fade_out);
			
			
			break;
		case R.id.id_indicator_four:
			resetOtherTabs();
			mTabIndicator.get(3).setIconAlpha(1.0f);
			break;
		default:
			break;
		}

	}

	private void initTabIndicator() {
		ChangeColorIconWithTextView one = (ChangeColorIconWithTextView) findViewById(R.id.id_indicator_one);
		ChangeColorIconWithTextView two = (ChangeColorIconWithTextView) findViewById(R.id.id_indicator_two);
		ChangeColorIconWithTextView three = (ChangeColorIconWithTextView) findViewById(R.id.id_indicator_three);
		ChangeColorIconWithTextView four = (ChangeColorIconWithTextView) findViewById(R.id.id_indicator_four);

		mTabIndicator.add(one);
		mTabIndicator.add(two);
		mTabIndicator.add(three);
		mTabIndicator.add(four);

		one.setOnClickListener(this);
		two.setOnClickListener(this);
		three.setOnClickListener(this);
		four.setOnClickListener(this);

		four.setIconAlpha(1.0f);

	}

	// 重置其他的Tab

	private void resetOtherTabs() {
		for (int i = 0; i < mTabIndicator.size(); i++) {
			mTabIndicator.get(i).setIconAlpha(0);
		}
	}
	
	private void initRightMenu() {

		Fragment leftMenuFragment = new MLiftFragment();
		setBehindContentView(R.layout.left_menu_frame);
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.id_left_menu_frame, leftMenuFragment).commit();
		SlidingMenu menu = getSlidingMenu();
		menu.setMode(SlidingMenu.LEFT);
		// 设置触摸屏幕的模
		menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
		menu.setShadowWidthRes(R.dimen.shadow_width);
		menu.setShadowDrawable(R.drawable.shadow);

		// 设置滑动菜单视图的宽
		menu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		// 设置渐入渐出效果的宽
		menu.setFadeDegree(0.35f);

	}
	
}
