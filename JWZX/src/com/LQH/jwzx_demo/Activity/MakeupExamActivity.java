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

import com.LQH.jwzx_demo.UiAdapter.MakeupExamAdapter;
import com.LQH.jwzx_demo.bean.Nextexam;
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
import android.widget.Button;
import android.widget.ListView;

public class MakeupExamActivity extends Activity {

	protected Handler handler;
	private String Session;
	protected String sshtml;
	private Button rfmkup;
	private List<Nextexam> nextexam;
	private ListView mynextexam;
	private MakeupExamAdapter adapter;
	private String test_time, name, curriculum, ctrriculum_num, XQ, XY, BJ,
			KCLX, JSH, GLDW, KSFS;

	@SuppressLint("HandlerLeak")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO 自动生成的方法存根
		super.onCreate(savedInstanceState);
		setContentView(R.layout.makeup_exam);
		nextexam = new ArrayList<Nextexam>();
		makeupBody();

		mynextexam = (ListView) findViewById(R.id.my_makeup_exam_list);
		rfmkup = (Button) findViewById(R.id.refresh_makeup);

		rfmkup.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				nextexam.clear();
				makeupBody();
			}
		});

		// 获取全局变量Session，cookie
		MyAppliction myAppliction = (MyAppliction) this.getApplication();
		Session = myAppliction.getSessionId();

		handler = new Handler() {

			@Override
			public void handleMessage(Message msg) {
				if (msg.what == 1) {
					adapter = new MakeupExamAdapter(getApplicationContext(),
							nextexam);
					mynextexam.setAdapter(adapter);

				}
			}
		};

	}

	public void makeupBody() {

		new Thread() {

			public void run() {
				String path = "http://jwc.jxnu.edu.cn/MyControl/All_Display.aspx?UserControl=xfz_Test_BHK.ascx";
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
					Element element = document
							.getElementById("_ctl0_dgContent");
					// System.out.println(">>>>>>????????" + element);

					Elements trs = element.getElementsByTag("tr");

					for (int i = 1; i < trs.size(); i++) {

						// System.out.println(">>>>>>/////" +trs.size());
						Element e1 = trs.get(i);
						// System.out.println("//////????????" + e1);
						Elements tds = e1.getElementsByTag("td");
						// System.out.println(">>>>>>????????" + tds);

						for (int j = 0; j < tds.size(); j++) {
							// System.out.println(">>>>>>///" +tds.size());

							switch (j) {
							case 0:
								XQ = tds.get(j).text();
								break;
							case 1:
								XY = tds.get(j).text();
								break;
							case 2:
								BJ = tds.get(j).text();
								break;
							case 3:
								name = tds.get(j).text();
								break;
							case 5:
								ctrriculum_num = tds.get(j).text();
								break;
							case 6:
								curriculum = tds.get(j).text();
								break;
							case 7:
								KCLX = tds.get(j).text();
								break;
							case 8:
								GLDW = tds.get(j).text();
								break;
							case 9:
								JSH = tds.get(j).text();
								break;
							case 10:
								test_time = tds.get(j).text();
								break;
							case 11:
								KSFS = tds.get(j).text();
								break;
							default:
								break;
							}

						}

						Nextexam ne = new Nextexam(XQ, XY, BJ, name,
								ctrriculum_num, curriculum, KCLX, GLDW, JSH,
								test_time, KSFS);
						nextexam.add(ne);

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
