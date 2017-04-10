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

import com.LQH.jwzx_demo.UiAdapter.TestArragememtAdapter;
import com.LQH.jwzx_demo.bean.AllGrades;
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

@SuppressLint("HandlerLeak")
public class TestArragementActivity extends Activity {

	protected Handler handler;
	private String Session;
	protected String sshtml;
	private Button rfgrade;
	private List<AllGrades> allgrade;
	private ListView myGrade;
	private TestArragememtAdapter adapter;
	protected String KCH, KCM, SDXF, KCCJ, BKCJ, BZF;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO �Զ����ɵķ������
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.testarragement);
		allgrade = new ArrayList<AllGrades>();
		gradeBody();

		myGrade = (ListView) findViewById(R.id.my_grade);
		rfgrade = (Button) findViewById(R.id.trefresh_grade);

		rfgrade.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO �Զ����ɵķ������

				allgrade.clear();
				gradeBody();
			}
		});

		// ��ȡȫ�ֱ���Session��cookie
		MyAppliction myAppliction = (MyAppliction) this.getApplication();
		Session = myAppliction.getSessionId();

		handler = new Handler() {

			@Override
			public void handleMessage(Message msg) {
				if (msg.what == 1) {
					adapter = new TestArragememtAdapter(
							getApplicationContext(), allgrade);
					myGrade.setAdapter(adapter);
				}
			}
		};

	}

	public void gradeBody() {

		new Thread() {

			public void run() {
				String path = "http://jwc.jxnu.edu.cn/User/default.aspx?&code=129&&uctl=MyControl\\xfz_test_schedule.ascx";
				URL url;
				try {
					url = new URL(path);
					HttpURLConnection resumeConnection = (HttpURLConnection) url
							.openConnection();

					// �����������ȡ����Set-Cookie

					resumeConnection.setRequestProperty("Cookie", Session);

					resumeConnection.connect();
					// �������������󣬽������Ӻ����ҳ����
					InputStream urlStream = resumeConnection.getInputStream();
					sshtml = StreamTools.readStream(urlStream);
					Document document = Jsoup.parse(sshtml);
					Element element = document.getElementById("_ctl1_dgContent");
					// System.out.println(">>>>>>????????" + sshtml);

					Elements trs = element.getElementsByTag("tr");

					// System.out.println(">>>>>>????????" + trs);
					for (int i = 1; i < trs.size(); i++) {

						Element e1 = trs.get(i);
						// System.out.println(">>>>>>????????" + e1);
						Elements tds = e1.getElementsByTag("td");
						// System.out.println(">>>>>>????????" + tds);

						for (int j = 0; j < tds.size(); j++) {
							// System.out.println(">>>>>>????????" +
							// tds.size());
							
								switch (j) {
								
								case 0:
									KCH = tds.get(j).text();

									break;
								case 1:
									KCM = tds.get(j).text();

									break;
								case 3:
									SDXF = tds.get(j).text();

									break;
								case 4:
									KCCJ = tds.get(j).text();

									break;
								case 5:
									BKCJ = tds.get(j).text();

									break;
								case 6:
									BZF = tds.get(j).text();

									break;

								default:
									break;
								
								}

						}
						AllGrades AG = new AllGrades(KCH, KCM, SDXF, KCCJ,
								BKCJ, BZF);
						allgrade.add(AG);
					}

					Message msg = new Message();
					msg.what = 1;
					handler.sendMessage(msg);

				} catch (MalformedURLException e) {
					// TODO �Զ����ɵ� catch ��
					e.printStackTrace();
				} catch (IOException e) {
					// TODO �Զ����ɵ� catch ��
					e.printStackTrace();
				} catch (Exception e) {
					// TODO �Զ����ɵ� catch ��
					e.printStackTrace();
				}

			}

		}.start();

	}

}
