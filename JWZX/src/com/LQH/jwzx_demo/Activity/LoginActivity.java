package com.LQH.jwzx_demo.Activity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import com.LQH.jwzx_demo.utils.MyAppliction;
import com.LQH.jwzx_demo.utils.StreamTools;
import com.zhy.zhy_slidemenu_demo02.R;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends Activity {

	private EditText userName, password;
	private ImageView logintitle;
	private CheckBox rem_pw, auto_login;
	private Button btn_login,lstu,ltea;
	private TextView register;
	private String userNameValue, passwordValue;
	private SharedPreferences sp;
	protected String sessionId;
	protected int ssnum;
	protected MyAppliction myAppliction;
	protected String sshtml;
	private Handler handler;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// 去除标题
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_login);

		// 获得实例对象
		sp = this.getSharedPreferences("userInfo", Context.MODE_WORLD_READABLE);
		userName = (EditText) findViewById(R.id.et_zh);
		password = (EditText) findViewById(R.id.et_mima);
		rem_pw = (CheckBox) findViewById(R.id.cb_mima);
		auto_login = (CheckBox) findViewById(R.id.cb_auto);
		btn_login = (Button) findViewById(R.id.btn_login);
		register = (TextView) findViewById(R.id.register);
		logintitle = (ImageView) findViewById(R.id.login_title);
		lstu = (Button) findViewById(R.id.lstu);
		ltea = (Button) findViewById(R.id.ltea);
		
		
		
		lstu.setEnabled(false);
		lstu.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				lstu.setEnabled(false);
				ltea.setEnabled(true);
			}
		});

		ltea.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				lstu.setEnabled(true);
				ltea.setEnabled(false);
			}
		});
		
		

		// 判断记住密码多选框的状态
		if (sp.getBoolean("ISCHECK", false)) {
			// 设置默认是记录密码状态
			rem_pw.setChecked(true);
			userName.setText(sp.getString("USER_NAME", ""));
			password.setText(sp.getString("PASSWORD", ""));
			// 判断自动登陆多选框状态
			if (sp.getBoolean("AUTO_ISCHECK", false)) {
				// 设置默认是自动登录状态
				auto_login.setChecked(true);

				myAppliction = (MyAppliction) getApplication();
				String Lstate = myAppliction.getLoginstate();

				if (Lstate.length() > 1) {

					//System.out.println("///////////" + Lstate.length());
					myAppliction.setLoginstate("123");
					// 跳转界面
					Intent intent = new Intent(LoginActivity.this,
							MainActivity.class);
					LoginActivity.this.startActivity(intent);
					finish();

				}

			}
		}

		// 登录监听事件
		btn_login.setOnClickListener(new OnClickListener() {

			@SuppressLint("HandlerLeak")
			public void onClick(View v) {
				
				
				login();
				MyPhoto();

				handler = new Handler() {
					@Override
					public void handleMessage(Message msg) {
						if (msg.what == 1) {
							if (ssnum > 100) {

								// logintitle.setImageResource(R.drawable.fe1);
								Toast.makeText(LoginActivity.this, "登录成功",
										Toast.LENGTH_SHORT).show();
								if (rem_pw.isChecked()) {
									// 记住用户名、密码、
									Editor editor = sp.edit();
									editor.putString("USER_NAME", userNameValue);
									editor.putString("PASSWORD", passwordValue);
									editor.commit();

								}
								myAppliction = (MyAppliction) getApplication();
								myAppliction.setLoginstate("123");
								
								//userNameValue = userName.getText().toString().trim();
								//myAppliction.setPhotonum(userNameValue);
								
								
								Intent intent = new Intent(LoginActivity.this,
										MainActivity.class);
								startActivity(intent);
								finish();
							} else {
								Log.i("TAG", "登录失败");
								Toast.makeText(LoginActivity.this,
										"登录失败,用户名或密码错误！", Toast.LENGTH_LONG)
										.show();
							}

						}
					}
				};

			}
		});

		register.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(LoginActivity.this,
						RegisterActivity.class);
				startActivity(intent);
			}
		});

		// 监听记住密码多选框按钮事件
		rem_pw.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if (rem_pw.isChecked()) {

					System.out.println("记住密码已选中");
					sp.edit().putBoolean("ISCHECK", true).commit();

				} else {

					System.out.println("记住密码没有选中");
					sp.edit().putBoolean("ISCHECK", false).commit();

				}

			}
		});

		// 监听自动登录多选框事件
		auto_login.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if (auto_login.isChecked()) {
					System.out.println("自动登录已选中");
					sp.edit().putBoolean("AUTO_ISCHECK", true).commit();

				} else {
					System.out.println("自动登录没有选中");
					sp.edit().putBoolean("AUTO_ISCHECK", false).commit();
				}
			}
		});

	}

	@Override
	protected void onRestart() {
		// TODO 自动生成的方法存根
		auto_login.setChecked(false);
		super.onRestart();
	}

	public void login() {
		new Thread() {
			@Override
			public void run() {
				try {
					userNameValue = userName.getText().toString();
					passwordValue = password.getText().toString();
					Log.e(">>>>>>>>>>>>>", userNameValue);

					System.out.println("=========1");
					URL url = new URL(
							"http://jwc.jxnu.edu.cn/Default_Login.aspx?preurl=");
					HttpURLConnection urlConnection = (HttpURLConnection) url
							.openConnection();
					urlConnection.setConnectTimeout(10000);
					urlConnection.setDoInput(true);
					urlConnection.setDoOutput(true);
					urlConnection.connect();
					System.out.println("=========2");
					HttpURLConnection conn = (HttpURLConnection) url
							.openConnection();
					conn.setDoInput(true);
					conn.setDoOutput(true);
					conn.setUseCaches(false);
					conn.setRequestMethod("POST");

					conn.setRequestProperty("Accept",
							"text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
					conn.setRequestProperty("Connection", "Keep-Alive");
					conn.setRequestProperty("Content-Type",
							"application/x-www-form-urlencoded");
					conn.setRequestProperty("User-Agent",
							"Mozilla/5.0 (Windows NT 6.1; WOW64; rv:48.0) Gecko/20100101 Firefox/48.0");
					conn.setConnectTimeout(10000);
					conn.connect();

					OutputStreamWriter out = new OutputStreamWriter(
							conn.getOutputStream(), "UTF-8");
					String content = "__EVENTTARGET=&__EVENTARGUMENT=&__LASTFOCUS=&__VIEWSTATE=%2FwEPDwUJNjk1MjA1MTY0D2QWAgIBD2QWBAIBDxYCHgdWaXNpYmxlZxYEZg8QZGQWAWZkAgEPEA8WBh4NRGF0YVRleHRGaWVsZAUM5Y2V5L2N5ZCN56ewHg5EYXRhVmFsdWVGaWVsZAUJ5Y2V5L2N5Y%2B3HgtfIURhdGFCb3VuZGdkEBU%2BGOS%2FneWNq%2BWkhO%2B8iOS%2FneWNq%2BmDqO%2B8iQnotKLliqHlpIQS6LSi5pS%2F6YeR6J6N5a2m6ZmiEuWfjuW4guW7uuiuvuWtpumZoivliJ3nrYnmlZnogrLlrabpmaIv6auY562J6IGM5Lia5oqA5pyv5a2m6ZmiDOS8oOaSreWtpumZoifliJvmlrDliJvkuJrmlZnogrLnoJTnqbbkuI7mjIflr7zkuK3lv4MJ5qGj5qGI6aaGFeWcsOeQhuS4jueOr%2BWig%2BWtpumZojDlj5HlsZXop4TliJLlip7lhazlrqTvvIjnnIHpg6jlhbHlu7rlip7lhazlrqTvvIkP6auY562J56CU56m26ZmiLeWKn%2BiDveacieacuuWwj%2BWIhuWtkOaVmeiCsumDqOmHjeeCueWunumqjOWupEXlm73pmYXlkIjkvZzkuI7kuqTmtYHlpITjgIHmlZnogrLlm73pmYXlkIjkvZzkuI7nlZnlrablt6XkvZzlip7lhazlrqQS5Zu96ZmF5pWZ6IKy5a2m6ZmiMOWbveWutuWNleezluWMluWtpuWQiOaIkOW3peeoi%2BaKgOacr%2BeglOeptuS4reW%2FgxLljJblrabljJblt6XlrabpmaIw5Z%2B65bu6566h55CG5aSE77yI5YWx6Z2S5qCh5Yy65bu66K6%2B5Yqe5YWs5a6k77yJG%2Biuoeeul%2BacuuS%2FoeaBr%2BW3peeoi%2BWtpumZohLnu6fnu63mlZnogrLlrabpmaIb5rGf6KW%2F57uP5rWO5Y%2BR5bGV56CU56m26ZmiD%2BaVmeW4iOaVmeiCsuWkhAnmlZnliqHlpIQM5pWZ6IKy5a2m6ZmiD%2BaVmeiCsueglOeptumZoh7lhpvkuovmlZnnoJTpg6jvvIjmraboo4Xpg6jvvIk556eR5oqA5Zut566h55CG5Yqe5YWs5a6k77yI56eR5oqA5Zut5Y%2BR5bGV5pyJ6ZmQ5YWs5Y%2B477yJD%2BenkeWtpuaKgOacr%2BWkhBLnp5HlrabmioDmnK%2FlrabpmaIS56a76YCA5LyR5bel5L2c5aSEG%2BWOhuWPsuaWh%2BWMluS4juaXhea4uOWtpumZohXpqazlhYvmgJ3kuLvkuYnlrabpmaIM576O5pyv5a2m6ZmiEuWFjei0ueW4iOiMg%2BeUn%2BmZojbphLHpmLPmuZbmub%2FlnLDkuI7mtYHln5%2FnoJTnqbbmlZnogrLpg6jph43ngrnlrp7pqozlrqQe6Z2S5bGx5rmW5qCh5Yy6566h55CG5Yqe5YWs5a6kCeS6uuS6i%2BWkhAzova%2Fku7blrabpmaIJ5ZWG5a2m6ZmiD%2BekvuS8muenkeWtpuWkhBLnlJ%2Flkb3np5HlrablrabpmaI%2F5biI6LWE5Z%2B56K6t5Lit5b%2BD77yI5rGf6KW%2F55yB6auY562J5a2m5qCh5biI6LWE5Z%2B56K6t5Lit5b%2BD77yJM%2BWunumqjOWupOW7uuiuvuS4jueuoeeQhuS4reW%2Fg%2BOAgeWIhuaekOa1i%2BivleS4reW%2FgxvmlbDlrabkuI7kv6Hmga%2Fnp5HlrablrabpmaIM5L2T6IKy5a2m6ZmiCeWbvuS5pummhg%2FlpJblm73or63lrabpmaIz572R57uc5YyW5pSv5pKR6L2v5Lu25Zu95a625Zu96ZmF56eR5oqA5ZCI5L2c5Z%2B65ZywD%2BaWh%2BWMlueglOeptumZognmloflrabpmaIt5peg5py66Iac5p2Q5paZ5Zu95a625Zu96ZmF56eR5oqA5ZCI5L2c5Z%2B65ZywG%2BeJqeeQhuS4jumAmuS%2FoeeUteWtkOWtpumZohjnjrDku6PmlZnogrLmioDmnK%2FkuK3lv4MM5b%2BD55CG5a2m6ZmiEuS%2FoeaBr%2BWMluWKnuWFrOWupA%2FlrabmiqXmnYLlv5fnpL4e5a2m55Sf5aSE77yI5a2m55Sf5bel5L2c6YOo77yJPOeglOeptueUn%2BmZou%2B8iOWtpuenkeW7uuiuvuWKnuWFrOWupOOAgeeglOeptueUn%2BW3peS9nOmDqO%2B8iQzpn7PkuZDlrabpmaIP5oub55Sf5bCx5Lia5aSEDOaUv%2BazleWtpumZog%2FotYTkuqfnrqHnkIblpIQe6LWE5Lqn57uP6JCl5pyJ6ZmQ6LSj5Lu75YWs5Y%2B4FT4IMTgwICAgICAIMTcwICAgICAINjgwMDAgICAINjMwMDAgICAIODIwMDAgICAINjQwMDAgICAIODkwMDAgICAIMTA5ICAgICAINDgwMDAgICAIMTM2ICAgICAIMTMwICAgICAISzAzMDAgICAIMTYwICAgICAINjkwMDAgICAIMzY1ICAgICAINjEwMDAgICAIMTQ0ICAgICAINjIwMDAgICAINDUwICAgICAIMzI0ICAgICAIMjUwICAgICAIMjQwMDAgICAINTAwMDAgICAIMzkwICAgICAIMzcwMDAgICAIMTMyICAgICAIMTQwICAgICAIODEwMDAgICAIMTA0ICAgICAINTgwMDAgICAINDYwMDAgICAINjUwMDAgICAINTcwMDAgICAIMzIwICAgICAINDAyICAgICAIMTUwICAgICAINjcwMDAgICAINTQwMDAgICAIMzYwICAgICAINjYwMDAgICAIMzEwICAgICAIMTA2ICAgICAINTUwMDAgICAINTYwMDAgICAIMjkwICAgICAINTIwMDAgICAIMzAwICAgICAIMzUwICAgICAINTEwMDAgICAIMzgwMDAgICAINjAwMDAgICAIMzYxICAgICAINDkwMDAgICAIMzA0ICAgICAINDIwICAgICAIMTEwICAgICAIMTkwICAgICAINTMwMDAgICAINDQwICAgICAINTkwMDAgICAIODcwMDAgICAIMzMwICAgICAUKwM%2BZ2dnZ2dnZ2dnZ2dnZ2dnZ2dnZ2dnZ2dnZ2dnZ2dnZ2dnZ2dnZ2dnZ2dnZ2dnZ2dnZ2dnZ2dnZ2dnZ2dnZ2dkZAIDDw8WAh8AaGQWBAIBDw8WAh4ISW1hZ2VVcmwFQE15Q29udHJvbC9BbGxfUGhvdG9TaG93LmFzcHg%2FVXNlck51bT0xNDY3MDA1MDQ5JlVzZXJUeXBlPVN0dWRlbnRkZAIDDw8WAh4EVGV4dAWdAeasoui%2FjuaCqO%2B8jOm%2BmeWQr%2Be%2FsDxicj48YSB0YXJnZXQ9X2JsYW5rIGhyZWY9TXlDb250cm9sL1N0dWRlbnRfSW5mb3JDaGVjay5hc3B4PjxzdHJvbmc%2BPGZvbnQgY29sb3I9cmVkIHNpemU9Mz7moKHlr7nkuKrkurrkv6Hmga88L2ZvbnQ%2BPC9mb250Pjwvc3Ryb25nPjwvYT5kZBgBBR5fX0NvbnRyb2xzUmVxdWlyZVBvc3RCYWNrS2V5X18WAQUKUmVtZW5iZXJNZTITTA24teFUgGuAimB%2BpBFCakrjllf3b5Ora40utEKQ&__EVENTVALIDATION=%2FwEWSQLHjJusAQLr6%2B%2FkCQK3yfbSBAKDspbeCQL21fViApC695MMAsjQmpEOAsjQpo4OAv3S2u0DAv3S9t4DAqPW8tMDAv3S6tEDAqPW3ugDArWVmJEHAr%2FR2u0DAqrwhf4KAsjQtoIOAuHY1soHAsjQooMOAv3S3ugDArfW7mMC%2FdL%2B0AMCvJDK9wsC%2FdLy0wMCr9GugA4C8pHSiQwC6dGugA4C%2BdHq0QMC3NH61QMCjtCenA4CntDm2gMCxrDmjQ0CyNCqhQ4Co9b%2B0AMCvJDaiwwC3NHa7QMCv9Hi3wMC%2FdLu3AMC3NHm2gMCjtCyhw4CpbHqgA0CyNCugA4C%2FdLm2gMC3NHq0QMCjtCigw4C%2FdLi3wMCjtC%2BhA4CqvCJ9QoC3NHu3AMC3NHi3wMC6dGenA4C3NHy0wMCjtC6mQ4CjtCugA4C3NH%2B0AMCntDa7QMC%2FdL61QMCw5bP%2FgICv9He6AMC8pHaiwwCr9Gyhw4CyNC%2BhA4CyNCenA4C3NH23gMCr9GqhQ4C3NHe6AMCo9bm2gMCjtC2gg4C%2BeuUqg4C2tqumwgC0sXgkQ8CuLeX%2BQECj8jxgAot%2BIeNXUirLIz7LIrUhAmFd6xFtSGrk7dWBdiU1twDcQ%3D%3D&rblUserType=Student&ddlCollege=180+++++&StuNum="
							+ userNameValue
							+ "&TeaNum=&Password="
							+ passwordValue + "&login=%E7%99%BB%E5%BD%95";
					out.write(content);

					out.flush();
					out.close();

					String cookieVal = "";
					String key = null;
					// 取cookie
					for (int i = 1; (key = conn.getHeaderFieldKey(i)) != null; i++) {
						if (key.equalsIgnoreCase("set-cookie")) {
							cookieVal = conn.getHeaderField(i);
							cookieVal = cookieVal.substring(0,
									cookieVal.indexOf(";"));
							sessionId = sessionId + cookieVal + ";";

						}

					}
					ssnum = sessionId.length();
					System.out.println(">>" + sessionId);
					System.out.println(">>>>>>>>>>>>" + ssnum);

					myAppliction = (MyAppliction) getApplication();
					myAppliction.setSessionId(sessionId);
					String s = "http://jwc.jxnu.edu.cn/Default_Login.aspx?preurl=";
					url = new URL(s);
					HttpURLConnection resumeConnection = (HttpURLConnection) url
							.openConnection();
					// 传入你上面获取到的Set-Cookie

					resumeConnection.setRequestProperty("Cookie", sessionId);

					resumeConnection.connect();
					// 创建输入流对象，接收连接后的网页数据
					InputStream urlStream = resumeConnection.getInputStream();
					// 将输入流以bufferedReader的方式输出
					// BufferedReader bufferedReader = new BufferedReader(
					// new InputStreamReader(urlStream));
					// String total = "";
					sshtml = StreamTools.readStream(urlStream);

					// Document doc = Jsoup.parse(sshtml);
					// String elements
					// =doc.select("div.imgbox").select("img").attr("src");

					if (ssnum > 100) {
						sessionId = null;
					} else {
						sessionId = null;
					}

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
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				}

			}

		}.start();

	}

	public void MyPhoto() {

		new Thread() {
			public void run() {
				try {
					
					myAppliction = (MyAppliction) getApplication();
					userNameValue = userName.getText().toString().trim();
					myAppliction.setPhotonum(userNameValue);
					
					// [2.1]获取访问图片的路径
					// String path = et_path.getText().toString().trim();
					String path = "http://jwc.jxnu.edu.cn/StudentPhoto/"
							+ userNameValue + ".jpg";
					File file = new File(getCacheDir(), Base64.encodeToString(
							path.getBytes(), Base64.DEFAULT));
					if (file.exists() && file.length() > 0) {
						// 使用缓存 的图片
						System.out.println("使用缓存图片 ");
						final Bitmap cacheBitmap = BitmapFactory
								.decodeFile(file.getAbsolutePath());

						runOnUiThread(new Runnable() {
							public void run() {

								logintitle.setImageBitmap(cacheBitmap);

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
									logintitle.setImageBitmap(bitmap);

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
