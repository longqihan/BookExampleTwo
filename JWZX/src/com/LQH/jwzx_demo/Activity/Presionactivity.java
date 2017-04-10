package com.LQH.jwzx_demo.Activity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.LQH.jwzx_demo.utils.MyAppliction;
import com.LQH.jwzx_demo.utils.StreamTools;
import com.zhy.zhy_slidemenu_demo02.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Base64;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class Presionactivity extends Activity {

	protected String sshtml;
	protected URL url;
	protected String stuNum;
	private Handler handler;
	protected String stuName;
	protected String stuClass;
	protected String userNameValue;
	protected String testNum;
	protected String sex;
	protected String mz;
	protected String burnTime;
	protected String idCard;
	private TextView stunum, name, stuclass, testnum, ssex, smz, burntime,
			idcard;
	private Button refreshm;
	private ImageView photo;

	@SuppressLint("HandlerLeak")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO 自动生成的方法存根
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_person_inforation);
		
		getmessage();
		
		stunum = (TextView) findViewById(R.id.stu_num);
		name = (TextView) findViewById(R.id.name);
		photo = (ImageView) findViewById(R.id.photo);
		stuclass = (TextView) findViewById(R.id.stuclass);
		testnum = (TextView) findViewById(R.id.test_num);
		ssex = (TextView) findViewById(R.id.sex);
		smz = (TextView) findViewById(R.id.mz);
		burntime = (TextView) findViewById(R.id.burn_time);
		idcard = (TextView) findViewById(R.id.id_card);
		refreshm = (Button) findViewById(R.id.refresh_message);
		
		refreshm.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				getmessage();
			}
		});

		handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				if (msg.what == 1) {
					MyPhoto();
					stunum.setText(stuNum);
					name.setText(stuName);
					stuclass.setText(stuClass);
					testnum.setText(testNum);
					ssex.setText(sex);
					smz.setText(mz);
					burntime.setText(burnTime);
					idcard.setText(idCard);
				}
			}
		};

	}

	public void MyPhoto() {

		new Thread() {
			public void run() {
				try {
					userNameValue = stuNum;
					// [2.1]获取访问图片的路径
					String path = "http://jwc.jxnu.edu.cn/StudentPhoto/" + userNameValue
							+ ".jpg";
					File file = new File(getCacheDir(), Base64.encodeToString(
							path.getBytes(), Base64.DEFAULT));
					if (file.exists() && file.length() > 0) {
						// 使用缓存 的图片
						System.out.println("使用缓存图片 ");
						final Bitmap cacheBitmap = BitmapFactory
								.decodeFile(file.getAbsolutePath());

						runOnUiThread(new Runnable() {
							public void run() {

								photo.setImageBitmap(cacheBitmap);

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
									photo.setImageBitmap(bitmap);

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

	public void getmessage() {
		new Thread() {

			@Override
			public void run() {
				try {
					String s = "http://jwc.jxnu.edu.cn/MyControl/Student_InforCheck.aspx";
					url = new URL(s);
					HttpURLConnection resumeConnection = (HttpURLConnection) url
							.openConnection();
					// 传入你上面获取到的Set-Cookie
					MyAppliction myAppliction = (MyAppliction) getApplication();
					String sessionId = myAppliction.getSessionId();

					// conn.setInstanceFollowRedirects(false);
					resumeConnection.setRequestProperty("Cookie", sessionId);

					resumeConnection.connect();
					// 创建输入流对象，接收连接后的网页数据
					InputStream urlStream = resumeConnection.getInputStream();

					sshtml = StreamTools.readStream(urlStream);
					Document doc = Jsoup.parse(sshtml);
					Elements elements = doc.select("#lblXH");
					stuNum = elements.text();
					System.out.println(">>>>>>>>>" + stuNum);
					Elements elements1 = doc.select("#lblXM");
					stuName = elements1.text();
					Elements elements2 = doc.select("#lblBJ");
					stuClass = elements2.text();
					Elements elements3 = doc.select("#lblKSH");
					testNum = elements3.text();
					Elements elements4 = doc.select("#lblXB");
					sex = elements4.text();
					Elements elements5 = doc.select("#lblMZ");
					mz = elements5.text();
					Elements elements6 = doc.select("#lblCSRQ");
					burnTime = elements6.text();
					Elements elements7 = doc.select("#lblSFZH");
					idCard = elements7.text();

					Message msg = new Message();
					msg.what = 1;
					handler.sendMessage(msg);

				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ProtocolException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

		}.start();
	}

}
