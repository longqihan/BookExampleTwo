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

import com.LQH.jwzx_demo.UiAdapter.finalGradeAdapter;
import com.LQH.jwzx_demo.bean.finalGrades;
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
import android.widget.TextView;

@SuppressLint("HandlerLeak")
public class FinalGradeActivity extends Activity {

	protected Handler handler;
	private String Session;
	protected String sshtml;
	protected String mymeaasge;
	private TextView finalexan;
	private Button rfgrade;
	private List<finalGrades> finalgrade;
	private ListView myfinalGrade;
	private finalGradeAdapter adapter;
	protected String name, test_time, curriculum, ctrriculum_num, arithmetic,
			usually_resuit, pratice_resuit, theory_result, total_score;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO 自动生成的方法存根
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.final_exam);
		finalgrade = new ArrayList<finalGrades>();
		finalgradeBody();

		finalexan = (TextView) findViewById(R.id.final_exam);
		myfinalGrade = (ListView) findViewById(R.id.my_grade);
		rfgrade = (Button) findViewById(R.id.refresh_final_grade);
		
		rfgrade.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				finalgrade.clear();
				finalgradeBody();
			}
		});

		// 获取全局变量Session，cookie
		MyAppliction myAppliction = (MyAppliction) this.getApplication();
		Session = myAppliction.getSessionId();

		handler = new Handler() {

			@Override
			public void handleMessage(Message msg) {
				if (msg.what == 1) {
					finalexan.setText(mymeaasge);
					adapter = new finalGradeAdapter(getApplicationContext(), finalgrade);
                    myfinalGrade.setAdapter(adapter);
				}
			}
		};

	}

	public void finalgradeBody() {

		new Thread() {

			public void run() {
				String path = "http://jwc.jxnu.edu.cn/MyControl/All_Display.aspx?UserControl=xfz_Test_cj.ascx";
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
					Element element = document.getElementById("_ctl0_lblTitle");
					// System.out.println(">>>>>>????????" + sshtml);
					mymeaasge = element.text();

					Elements trs = document.getElementsByTag("tr");

					// System.out.println(">>>>>>????????" + trs);
					for (int i = 3; i < trs.size()-2; i++) {
						// 11hang
						// System.out.println(">>>>>>/////" +trs.size());
						Element e1 = trs.get(i);
						// System.out.println("//////????????" + e1);
						Elements tds = e1.getElementsByTag("td");
						// System.out.println(">>>>>>????????" + tds);

						for (int j = 0; j < tds.size(); j++) {
							// System.out.println(">>>>>>///" +tds.size());
							if (tds.size() > 3) {
								switch (j) {
								case 1:
									name = tds.get(j).text();
									break;
								case 2:
									test_time = tds.get(j).text();
									break;
								case 3:
									curriculum = tds.get(j).text();
									break;
								case 4:
									ctrriculum_num = tds.get(j).text();
									break;
								case 5:
									arithmetic = tds.get(j).text();
									break;
								case 6:
									usually_resuit = tds.get(j).text();
									break;
								case 7:
									pratice_resuit = tds.get(j).text();
									break;
								case 8:
									theory_result = tds.get(j).text();
									break;
								case 9:
									total_score = tds.get(j).text();
									break;
								default:
									break;
								}

							}

						}

						finalGrades FG = new finalGrades(name, test_time,
								curriculum, ctrriculum_num, arithmetic,
								usually_resuit, pratice_resuit, theory_result,
								total_score);
						finalgrade.add(FG);
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
