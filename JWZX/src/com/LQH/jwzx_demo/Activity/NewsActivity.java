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
		// TODO 自动生成的方法存根
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_news);
		titlenews = (TextView) findViewById(R.id.title_view);
		bodynews = (TextView) findViewById(R.id.body_view);
		timenews = (TextView) findViewById(R.id.time_view);
		nphotos = (ImageView) findViewById(R.id.news_photo);

		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();// .getExtras()得到intent所附带的额外数据
		str = bundle.getString("newsUrl");// getString()返回指定的值
		newstitle = bundle.getString("newsTitle");
		newstime = bundle.getString("newsTime");
		titlenews.setText(newstitle);
		timenews.setText(newstime);
		
		// 获取全局变量Session，cookie
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
		// TODO 自动生成的方法存根
		
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
					
					// 传入你上面获取到的Set-Cookie

					resumeConnection.setRequestProperty("Cookie", Session);

					resumeConnection.connect();
					// 创建输入流对象，接收连接后的网页数据
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

public void NewsPhoto(){
		
		new Thread() {
			public void run() {
				try {
					
					// [2.1]获取访问图片的路径
					String path ="http://jwc.jxnu.edu.cn"+Src;
					File file = new File(getCacheDir(), Base64.encodeToString(
							path.getBytes(), Base64.DEFAULT));
					if (file.exists() && file.length() > 0) {
						// 使用缓存 的图片
						System.out.println("使用缓存图片 ");
						final Bitmap cacheBitmap = BitmapFactory
								.decodeFile(file.getAbsolutePath());
						
						runOnUiThread(new Runnable() {
							public void run() {

								nphotos.setImageBitmap(cacheBitmap);

							}
						});

					} else {
						// 第一次访问 联网获取数据
						System.out.println("第一次访问连接网络");

						// [2.2]创建url对象
						URL url = new URL(path);
						// [2.3]获取httpurlconnection
						HttpURLConnection conn = (HttpURLConnection) url
								.openConnection();

						// [2.4]设置请求的方式
						conn.setRequestMethod("GET");
						// [2.5]设置超时时间
						conn.setConnectTimeout(5000);
						// [2.6]获取服务器返回的状态码
						int code = conn.getResponseCode();
						if (code == 200) {
							// [2.7]获取图片的数据 不管是什么数据(txt文本 图片数据 )都是以流的形式返回
							InputStream in = conn.getInputStream();

							// [2.7]缓存图片 谷歌给我们提供了一个缓存目录

							FileOutputStream fos = new FileOutputStream(file);
							int len = -1;
							byte[] buffer = new byte[1024];// 1kb
							while ((len = in.read(buffer)) != -1) {
								fos.write(buffer, 0, len);

							}
							fos.close();
							in.close();

							// [2.8]通过位图工厂 获取bitmap(bitmap)
							final Bitmap bitmap = BitmapFactory.decodeFile(file
									.getAbsolutePath());

							// 这句api 不 管你在什么位置上调用 action都运行在UI线程里
							runOnUiThread(new Runnable() {
								public void run() {

									// run方法一定执行在UI线程 里

									// [2.9]把bitmap显示到iv上
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
