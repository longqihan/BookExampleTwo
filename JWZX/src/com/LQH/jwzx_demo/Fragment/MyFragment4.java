package com.LQH.jwzx_demo.Fragment;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.LQH.jwzx_demo.Activity.SpeakingActivity;
import com.LQH.jwzx_demo.UiAdapter.NewsAdapter;
import com.LQH.jwzx_demo.bean.News;
import com.zhy.zhy_slidemenu_demo02.R;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class MyFragment4 extends Fragment {
	private View mView;
	private ListView mXglj;
	private List<News> newsList;
	private Handler handler;
	private NewsAdapter adapter;
	protected String url;
	protected String title;
	protected String name;
	protected String time;
	protected String position;
	protected String content;
	
	

	
	@SuppressLint("HandlerLeak")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mView = inflater.inflate(R.layout.page4, container, false);
		mXglj = (ListView) mView.findViewById(R.id.list_news4);

		newsList = new ArrayList<News>();

		handler = new Handler() {
			
			@Override
			public void handleMessage(Message msg) {
				if (msg.what == 1) {

					adapter = new NewsAdapter(getActivity(), newsList);
					mXglj.setAdapter(adapter);
				}
			}
		};

		getNews();

		mXglj.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				News news = newsList.get(arg2);
				url = news.getNewsUrl();
				Intent intent = new Intent();
				intent.setClass(getActivity(), SpeakingActivity.class);
				intent.putExtra("newsUrl",news.getNewsUrl());
				
				startActivity(intent);	
				
			}
		});

		return mView;
	}

	public void getNews() {
		new Thread() {

			public void run() {
				try {
					Document document = Jsoup.connect("http://jwc.jxnu.edu.cn")
							.get();

					Element burden = document.getElementById("fl_content2");

					Elements titleAndPic = burden.select("div.short_item");

					System.out.println(">>>>>>>>>>>>>" + titleAndPic);
					for (int i = 0; i < titleAndPic.size(); i++) {

						String title = titleAndPic.get(i).text();
						String href = titleAndPic.get(i).select("a")
								.attr("href");

						News news = new News(title, href, "Ë«ÐÝÈÕ");
						newsList.add(news);

					}
					Message msg = new Message();
					msg.what = 1;
					handler.sendMessage(msg);

				} catch (IOException e) {
					e.printStackTrace();
				}
			};
		}.start();
	}

	

}
