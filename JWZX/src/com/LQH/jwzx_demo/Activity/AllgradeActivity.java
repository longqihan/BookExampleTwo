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

import com.LQH.jwzx_demo.UiAdapter.AllGradeAdapter;
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
import android.widget.TextView;

@SuppressLint("HandlerLeak")
public class AllgradeActivity extends Activity {

	protected Handler handler;
	private String Session;
	protected String sshtml;
	protected String mymeaasge;
	private TextView mMessage;
	private Button rfgrade;
	private List<AllGrades> allgrade;
	private ListView myGrade;
	private AllGradeAdapter adapter;
	protected String KCH,KCM,SDXF,KCCJ,BKCJ,BZF;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO �Զ����ɵķ������
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.all_grade);
		allgrade = new ArrayList<AllGrades>();
		gradeBody();

		mMessage = (TextView) findViewById(R.id.my_message);
		myGrade = (ListView) findViewById(R.id.my_grade);
		rfgrade = (Button) findViewById(R.id.refresh_grade);
		
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
					mMessage.setText(mymeaasge);
					adapter = new AllGradeAdapter(getApplicationContext(),
							allgrade);
					myGrade.setAdapter(adapter);
				}
			}
		};

	}

	public void gradeBody() {

		new Thread() {

			public void run() {
				String path = "http://jwc.jxnu.edu.cn/MyControl/All_Display.aspx?UserControl=xfz_cj.ascx&Action=Personal";
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
					Element element = document.getElementById("_ctl0_lblMsg");
					// System.out.println(">>>>>>????????" + sshtml);
					mymeaasge = element.text();

					Elements trs = document.getElementsByTag("tr");

					// System.out.println(">>>>>>????????" + trs);
					for (int i = 3; i < trs.size(); i++) {

						Element e1 = trs.get(i);
						// System.out.println(">>>>>>????????" + e1);
						Elements tds = e1.getElementsByTag("td");
						// System.out.println(">>>>>>????????" + tds);

						for (int j = 0; j < tds.size(); j++) {
							// System.out.println(">>>>>>????????" +
							// tds.size());
							if (tds.size() == 8) {
								switch (j) {

								case 1:
									KCH = tds.get(j).text();

									break;
								case 2:
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
								
							} else {
								switch (j) {
								case 0:
									KCH = tds.get(j).text();

									break;
								case 1:
									KCM = tds.get(j).text();

									break;
								case 2:
									SDXF = tds.get(j).text();

									break;
								case 3:
									KCCJ = tds.get(j).text();

									break;
								case 4:
									BKCJ = tds.get(j).text();

									break;
								case 5:
									BZF = tds.get(j).text();

									break;

								default:
									break;
								}
							}

						}
						AllGrades AG = new AllGrades(KCH, KCM, SDXF, KCCJ, BKCJ, BZF);
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
