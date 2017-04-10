package com.LQH.jwzx_demo.Activity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.LQH.jwzx_demo.utils.MyAppliction;
import com.LQH.jwzx_demo.utils.StreamTools;
import com.zhy.zhy_slidemenu_demo02.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Base64;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

public class NewsActivity extends Activity {

	private TextView titlenews, bodynews, timenews;
	private ImageView nphotos;
	private String str;
	private String Session;
	String path = "http://jwc.jxnu.edu.cn/";
	protected String sshtml;
	protected String body;
	private Handler handler;
	private String newstitle, Src;
	private String newstime;

	@SuppressLint("HandlerLeak")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO �Զ����ɵķ������
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_news);
		titlenews = (TextView) findViewById(R.id.title_view);
		bodynews = (TextView) findViewById(R.id.body_view);
		timenews = (TextView) findViewById(R.id.time_view);
		nphotos = (ImageView) findViewById(R.id.news_photo);

		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();// .getExtras()�õ�intent�������Ķ�������
		str = bundle.getString("newsUrl");// getString()����ָ����ֵ
		newstitle = bundle.getString("newsTitle");
		newstime = bundle.getString("newsTime");
		titlenews.setText(newstitle);
		timenews.setText(newstime);
		
		// ��ȡȫ�ֱ���Session��cookie
		MyAppliction myAppliction = (MyAppliction) this.getApplication();
		Session = myAppliction.getSessionId();
		//System.out.println("/////////////" + Session);

		getnewsBody();
		
		handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				if (msg.what == 1) {
					bodynews.setText(body);
					NewsPhoto();
				}
			}
		};

	}

	@Override
	protected void onResume() {
		// TODO �Զ����ɵķ������
		
		getnewsBody();
		
		super.onResume();
	}

	public void getnewsBody() {

		new Thread() {

			public void run() {
				String newspath = path + str;
				//System.out.println("/////////////" + newspath);
				URL url;
				try {
					url = new URL(newspath);
					HttpURLConnection resumeConnection = (HttpURLConnection) url
							.openConnection();
					
					// �����������ȡ����Set-Cookie

					resumeConnection.setRequestProperty("Cookie", Session);

					resumeConnection.connect();
					// �������������󣬽������Ӻ����ҳ����
					InputStream urlStream = resumeConnection.getInputStream();
					sshtml = StreamTools.readStream(urlStream);
					
					Document doc = Jsoup.parse(sshtml);
					Element burden = doc.getElementById("contentBox");
					//Elements title = burden.getElementsByTag("span");
					//System.out.println("<<<<<<<<<<<<<<"+title);
					//Elements burden2 = doc.select("p.MsoNormal");
					
					Elements span = burden.getElementsByTag("span");
					Src = span.select("img").attr("src");
					body = span.text();
					
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

public void NewsPhoto(){
		
		new Thread() {
			public void run() {
				try {
					
					// [2.1]��ȡ����ͼƬ��·��
					String path ="http://jwc.jxnu.edu.cn"+Src;
					File file = new File(getCacheDir(), Base64.encodeToString(
							path.getBytes(), Base64.DEFAULT));
					if (file.exists() && file.length() > 0) {
						// ʹ�û��� ��ͼƬ
						System.out.println("ʹ�û���ͼƬ ");
						final Bitmap cacheBitmap = BitmapFactory
								.decodeFile(file.getAbsolutePath());
						
						runOnUiThread(new Runnable() {
							public void run() {

								nphotos.setImageBitmap(cacheBitmap);

							}
						});

					} else {
						// ��һ�η��� ������ȡ����
						System.out.println("��һ�η�����������");

						// [2.2]����url����
						URL url = new URL(path);
						// [2.3]��ȡhttpurlconnection
						HttpURLConnection conn = (HttpURLConnection) url
								.openConnection();

						// [2.4]��������ķ�ʽ
						conn.setRequestMethod("GET");
						// [2.5]���ó�ʱʱ��
						conn.setConnectTimeout(5000);
						// [2.6]��ȡ���������ص�״̬��
						int code = conn.getResponseCode();
						if (code == 200) {
							// [2.7]��ȡͼƬ������ ������ʲô����(txt�ı� ͼƬ���� )������������ʽ����
							InputStream in = conn.getInputStream();

							// [2.7]����ͼƬ �ȸ�������ṩ��һ������Ŀ¼

							FileOutputStream fos = new FileOutputStream(file);
							int len = -1;
							byte[] buffer = new byte[1024];// 1kb
							while ((len = in.read(buffer)) != -1) {
								fos.write(buffer, 0, len);

							}
							fos.close();
							in.close();

							// [2.8]ͨ��λͼ���� ��ȡbitmap(bitmap)
							final Bitmap bitmap = BitmapFactory.decodeFile(file
									.getAbsolutePath());

							// ���api �� ������ʲôλ���ϵ��� action��������UI�߳���
							runOnUiThread(new Runnable() {
								public void run() {

									// run����һ��ִ����UI�߳� ��

									// [2.9]��bitmap��ʾ��iv��
									nphotos.setImageBitmap(bitmap);

								}
							});

						}
					}

				} catch (Exception e) {
					e.printStackTrace();
				}

			};
		}.start();

		
		
	}
	
	
}
