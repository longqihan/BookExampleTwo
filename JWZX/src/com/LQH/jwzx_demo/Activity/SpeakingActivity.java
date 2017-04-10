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
		// TODO 自动生成的方法存根
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_speaking);
		
		stitle = (TextView) findViewById(R.id.stitle);
		sname = (TextView) findViewById(R.id.sname);
		stime = (TextView) findViewById(R.id.stime);
		sposition = (TextView) findViewById(R.id.position);
		scontent = (TextView) findViewById(R.id.scontent);
		
		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();// .getExtras()得到intent所附带的额外数据
		str = bundle.getString("newsUrl");// getString()返回指定的值
		
		getbody();

		handler = new Handler() {
			
			@Override
			public void handleMessage(Message msg) {
				if (msg.what == 1) {
					
					stitle.setText("标题："+title);
					sname.setText("主讲人："+name);
					stime.setText("时间："+time);
					sposition.setText("地点:"+position);
					scontent.setText("内容："+content);
					
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
