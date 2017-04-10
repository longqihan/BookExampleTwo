package com.LQH.jwzx_demo.Activity;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.zhy.zhy_slidemenu_demo02.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Window;
import android.widget.TextView;

public class SpeakingActivity extends Activity {


	protected String title;
	protected String name;
	protected String time;
	protected String position;
	protected String content;
	private Handler handler;
	private String str;
	private TextView stitle,sname,stime,sposition,scontent;
	
	@SuppressLint("HandlerLeak")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO �Զ����ɵķ������
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_speaking);
		
		stitle = (TextView) findViewById(R.id.stitle);
		sname = (TextView) findViewById(R.id.sname);
		stime = (TextView) findViewById(R.id.stime);
		sposition = (TextView) findViewById(R.id.position);
		scontent = (TextView) findViewById(R.id.scontent);
		
		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();// .getExtras()�õ�intent�������Ķ�������
		str = bundle.getString("newsUrl");// getString()����ָ����ֵ
		
		getbody();

		handler = new Handler() {
			
			@Override
			public void handleMessage(Message msg) {
				if (msg.what == 1) {
					
					stitle.setText("���⣺"+title);
					sname.setText("�����ˣ�"+name);
					stime.setText("ʱ�䣺"+time);
					sposition.setText("�ص�:"+position);
					scontent.setText("���ݣ�"+content);
					
				}
			}
		};
	}
	public void getbody() {
		new Thread() {

			public void run() {
				try {

					Document document = Jsoup.connect(
							"http://jwc.jxnu.edu.cn/" + str).get();
                     System.out.println("//////"+str);
					Element burden = document.getElementById("lblTheme");

					title = burden.text();

					Element burden1 = document.getElementById("lblSpeeker");

					name = burden1.text();

					Element burden2 = document.getElementById("lblTime");

					time = burden2.text();

					Element burden3 = document.getElementById("lblPosition");

					position = burden3.text();

					Element burden4 = document.getElementById("lblContent");

					content = burden4.text();

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
