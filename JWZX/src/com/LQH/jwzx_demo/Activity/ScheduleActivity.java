package com.LQH.jwzx_demo.Activity;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.LQH.jwzx_demo.UiAdapter.ScheduleAdapter;
import com.LQH.jwzx_demo.bean.Scheduletable;
import com.LQH.jwzx_demo.utils.MyAppliction;
import com.LQH.jwzx_demo.utils.StreamTools;
import com.zhy.zhy_slidemenu_demo02.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ListView;

public class ScheduleActivity extends Activity {

	private Handler handler;
	protected String sshtml;
	private String Session;
	private ListView schedule;
	private Button refresh;
	private List<Scheduletable> stablelist;
	private ScheduleAdapter adapter;
	private String tableNum;
	private String monday;
	private String tuesday;
	private String wendnesday;
	private String thursday;
	private String friday;
	private String saturday;
	private String sunday;

	@SuppressLint("HandlerLeak")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO 自动生成的方法存根
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_schedule);
		scheduleBody();
		stablelist = new ArrayList<Scheduletable>();
		schedule = (ListView) findViewById(R.id.schedule);
		refresh = (Button) findViewById(R.id.refresh);
		
		refresh.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				stablelist.clear();
				scheduleBody();
				
			}
		});

		// 获取全局变量Session，cookie
		MyAppliction myAppliction = (MyAppliction) this.getApplication();
		Session = myAppliction.getSessionId();

		handler = new Handler() {

			@Override
			public void handleMessage(Message msg) {
				if (msg.what == 1) {
					adapter = new ScheduleAdapter(getApplicationContext(),
							stablelist);
					schedule.setAdapter(adapter);
				}
			}
		};

	}

	public void scheduleBody() {

		new Thread() {

			public void run() {
				String path = "http://jwc.jxnu.edu.cn/User/default.aspx?&code=111&&uctl=MyControl\\xfz_kcb.ascx&MyAction=Personal";
				URL url;
				try {
					url = new URL(path);
					HttpURLConnection resumeConnection = (HttpURLConnection) url
							.openConnection();

					// 传入你上面获取到的Set-Cookie

					resumeConnection.setRequestProperty("Cookie", Session);

					resumeConnection.connect();
					// 创建输入流对象，接收连接后的网页数据
					InputStream urlStream = resumeConnection.getInputStream();
					sshtml = StreamTools.readStream(urlStream);
					Document document = Jsoup.parse(sshtml);
					Element element = document.getElementById("_ctl1_NewKcb");
					// System.out.println(">>>>>>????????" + element);
					Elements trs = element.getElementsByTag("tr");

					for (int i = 0; i < trs.size(); i++) {
						if (i == 1||i==6||i==8) {
							Element e1 = trs.get(i);
							// 得到所有的列
							Elements tds = e1.getElementsByTag("td");
							for (int j = 0; j < tds.size(); j++) {

								switch (j) {

								case 1:
									tableNum = tds.get(j).text();
									
									break;
								case 2:
									monday = tds.get(j).text();
									break;
								case 3:
									tuesday = tds.get(j).text();
									break;
								case 4:
									wendnesday = tds.get(j).text();
									break;
								case 5:
									thursday = tds.get(j).text();
									break;
								case 6:
									friday = tds.get(j).text();
									break;
								case 7:
									saturday = tds.get(j).text();
									break;
								case 8:
									sunday = tds.get(j).text();
									break;
								default:
									break;

								}
								
							}
							Scheduletable stable = new Scheduletable(
									tableNum, monday, tuesday, wendnesday,
									thursday, friday, saturday, sunday);
							stablelist.add(stable);
							
						}
						if (i == 2 || i == 3 || i == 4||i==7) {
							Element e1 = trs.get(i);
							// 得到所有的列
							Elements tds = e1.getElementsByTag("td");
							for (int j = 0; j < tds.size(); j++) {

								switch (j) {

								case 0:
									tableNum = tds.get(j).text();
									System.out.println(">>>>>>????????"
											+ tableNum);
									break;
								case 1:
									monday = tds.get(j).text();
									break;
								case 2:
									tuesday = tds.get(j).text();
									break;
								case 3:
									wendnesday = tds.get(j).text();
									break;
								case 4:
									thursday = tds.get(j).text();
									break;
								case 5:
									friday = tds.get(j).text();
									break;
								case 6:
									saturday = tds.get(j).text();
									break;
								case 7:
									sunday = tds.get(j).text();
									break;
								default:
									break;

								}
								
							}
							Scheduletable stable = new Scheduletable(
									tableNum, monday, tuesday, wendnesday,
									thursday, friday, saturday, sunday);
							stablelist.add(stable);
						}
						
						

					}

					Message msg = new Message();
					msg.what = 1;
					handler.sendMessage(msg);

				} catch (MalformedURLException e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				} catch (IOException e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				} catch (Exception e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				}

			}

		}.start();

	}

}
