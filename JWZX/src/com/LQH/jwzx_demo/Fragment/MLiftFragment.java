package com.LQH.jwzx_demo.Fragment;

import java.io.InputStream;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

import com.LQH.jwzx_demo.Activity.LoginActivity;
import com.LQH.jwzx_demo.Activity.Presionactivity;
import com.LQH.jwzx_demo.utils.MyAppliction;
import com.zhy.zhy_slidemenu_demo02.R;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;

public class MLiftFragment extends Fragment {

	private View mView;
	private ListView mCategories;
	private List<String> mDatas = Arrays.asList("我的信息", "退出登录");
	private ListAdapter mAdapter;
	protected MyAppliction myAppliction;
	private ImageView myphoto;
	private Handler myHandler;
	private Bitmap bitmap;
	protected Object userNameValue;

	@SuppressLint("HandlerLeak")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		if (mView == null) {
			initView(inflater, container);

			myAppliction = (MyAppliction) getActivity().getApplication();
			userNameValue = myAppliction.getPhotonum();
			//System.out.println("-========" + userNameValue);

			myphoto = (ImageView) mView.findViewById(R.id.my_photo);
			myHandler = new Handler() {
				public void handleMessage(Message msg) {
					if (msg.what == 0x1122) {
						myphoto.setImageBitmap(bitmap);
					}
				}
			};
			new Thread() {
				public void run() {
					try {
						// myAppliction = (MyAppliction) getActivity()
						// .getApplication();
						//userNameValue = "1467005049";
						// System.out.println("-========"+userNameValue);

						URL url = new URL(
								"http://jwc.jxnu.edu.cn/StudentPhoto/"
										+ userNameValue + ".jpg");
						// 获取百度首页图片
						InputStream is = url.openStream();
						bitmap = BitmapFactory.decodeStream(is);
						is.close();
					} catch (Exception ex) {
						ex.printStackTrace();
					}
					myHandler.sendEmptyMessage(0x1122);
				}
			}.start();

		}
		return mView;
	}

	private void initView(LayoutInflater inflater, ViewGroup container) {

		mView = inflater.inflate(R.layout.left_menu, container, false);
		mCategories = (ListView) mView
				.findViewById(R.id.id_listview_categories);
		mAdapter = new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_list_item_1, mDatas);
		mCategories.setAdapter(mAdapter);
		mCategories.setOnItemClickListener(new OnItemClickListener() {

			// 判断点击的条目
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO 自动生成的方法存根
				if (arg2 == 1) {

					myAppliction = (MyAppliction) getActivity()
							.getApplication();
					myAppliction.setLoginstate("1");
					
					Intent intent = new Intent();
					intent.setClass(getActivity(), LoginActivity.class);
					startActivity(intent);
					getActivity().finish();
				}
				if (arg2 == 0) {

					Intent intent = new Intent();
					intent.setClass(getActivity(), Presionactivity.class);
					startActivity(intent);

				}
			}

		});
	}

}
