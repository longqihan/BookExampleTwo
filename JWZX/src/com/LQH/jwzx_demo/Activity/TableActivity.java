package com.LQH.jwzx_demo.Activity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import com.zhy.zhy_slidemenu_demo02.R;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.os.Bundle;
import android.util.Base64;
import android.util.FloatMath;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.TextView;

public class TableActivity extends Activity {

	private TextView secondTxt;
	private ImageView iv_table;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_table);
		iv_table = (ImageView) findViewById(R.id.iv_table);
		iv_table.setOnTouchListener(new TouchListener());

		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();// .getExtras()得到intent所附带的额外数据
		String path = bundle.getString("str");// getString()返回指定key的值
		secondTxt = (TextView) findViewById(R.id.textView1);// 用TextView显示值
		secondTxt.setText(path);
		inittable();
	}

	private final class TouchListener implements OnTouchListener {
		//记录是拖拉照片模式还是放大缩小照片模式 
		private int mode = 0;// 初始状态
		// 拖拉照片模式 
		private static final int MODE_DRAG = 1;
		// 放大缩小照片模式 
		private static final int MODE_ZOOM = 2;

		// 用于记录开始时候的坐标位置 
		private PointF startPoint = new PointF();
		// 用于记录拖拉图片移动的坐标位置 
		private Matrix matrix = new Matrix();
		// 用于记录图片要进行拖拉时候的坐标位置 
		private Matrix currentMatrix = new Matrix();

		// 两个手指的开始距离 
		private float startDis;
		// 两个手指的中间点 
		private PointF midPoint;

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			// TODO 自动生成的方法存根
			/** 通过与运算保留最后八位 MotionEvent.ACTION_MASK = 255 */
			switch (event.getAction() & MotionEvent.ACTION_MASK) {
			// 手指压下屏幕
			case MotionEvent.ACTION_DOWN:
				mode = MODE_DRAG;
				// 记录ImageView当前的移动位置
				currentMatrix.set(iv_table.getImageMatrix());
				startPoint.set(event.getX(), event.getY());
				break;
			// 手指在屏幕上移动，改事件会被不断触发
			case MotionEvent.ACTION_MOVE:
				// 拖拉图片
				if (mode == MODE_DRAG) {
					float dx = event.getX() - startPoint.x; // 得到x轴的移动距离
					float dy = event.getY() - startPoint.y; // 得到x轴的移动距离
					// 在没有移动之前的位置上进行移动
					matrix.set(currentMatrix);
					matrix.postTranslate(dx, dy);
				}
				// 放大缩小图片
				else if (mode == MODE_ZOOM) {
					float endDis = distance(event);// 结束距离
					if (endDis > 10f) { // 两个手指并拢在一起的时候像素大于10
						float scale = endDis / startDis;// 得到缩放倍数
						matrix.set(currentMatrix);
						matrix.postScale(scale, scale, midPoint.x, midPoint.y);
					}
				}
				break;
			// 手指离开屏幕
			case MotionEvent.ACTION_UP:
				// 当触点离开屏幕，但是屏幕上还有触点(手指)
			case MotionEvent.ACTION_POINTER_UP:
				mode = 0;
				break;
			// 当屏幕上已经有触点(手指)，再有一个触点压下屏幕
			case MotionEvent.ACTION_POINTER_DOWN:
				mode = MODE_ZOOM;
				/** 计算两个手指间的距离 */
				startDis = distance(event);
				/** 计算两个手指间的中间点 */
				if (startDis > 10f) { // 两个手指并拢在一起的时候像素大于10
					midPoint = mid(event);
					// 记录当前ImageView的缩放倍数
					currentMatrix.set(iv_table.getImageMatrix());
				}
				break;
			}
			iv_table.setImageMatrix(matrix);
			return true;
		}

		//计算两个手指间的距离 
		private float distance(MotionEvent event) {
			float dx = event.getX(1) - event.getX(0);
			float dy = event.getY(1) - event.getY(0);
			// 使用勾股定理返回两点之间的距离 
			return FloatMath.sqrt(dx * dx + dy * dy);
		}

		/** 计算两个手指间的中间点 */
		private PointF mid(MotionEvent event) {
			float midX = (event.getX(1) + event.getX(0)) / 2;
			float midY = (event.getY(1) + event.getY(0)) / 2;
			return new PointF(midX, midY);
		}

	}

	public void inittable() {

		new Thread() {
			public void run() {
				try {
					// 获取访问图片的路径
					Intent intent = getIntent();
					Bundle bundle = intent.getExtras();// .getExtras()得到intent所附带的额外数据
					String path = bundle.getString("str");// getString()返回指定key的值

					File file = new File(getCacheDir(), Base64.encodeToString(
							path.getBytes(), Base64.DEFAULT));
					if (file.exists() && file.length() > 0) {
						// 使用缓存 的图片
						System.out.println("使用缓存图片 ");
						final Bitmap cacheBitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
						
						runOnUiThread(new Runnable() {
							public void run() {

								iv_table.setImageBitmap(cacheBitmap);

							}
						});

					} else {
						// 第一次访问 联网获取数据
						System.out.println("第一次访问连接网络");

						// 创建url对象
						URL url = new URL(path);
						// 获取httpurlconnection
						HttpURLConnection conn = (HttpURLConnection) url
								.openConnection();

						// 设置请求的方式
						conn.setRequestMethod("GET");
						// 设置超时时间
						conn.setConnectTimeout(5000);
						// 获取服务器返回的状态码
						int code = conn.getResponseCode();
						if (code == 200) {
							// 获取图片的数据 不管是什么数据(txt文本 图片数据 )都是以流的形式返回
							InputStream in = conn.getInputStream();

							// 缓存图片 谷歌给我们提供了一个缓存目录

							FileOutputStream fos = new FileOutputStream(file);
							int len = -1;
							byte[] buffer = new byte[1024];// 1kb
							while ((len = in.read(buffer)) != -1) {
								fos.write(buffer, 0, len);

							}
							fos.close();
							in.close();

							// 通过位图工厂 获取bitmap(bitmap)
							final Bitmap bitmap = BitmapFactory.decodeFile(file
									.getAbsolutePath());

							// 不 管在什么位置上调用 action都运行在UI线程里
							runOnUiThread(new Runnable() {
								public void run() {

									// run方法一定执行在UI线程 里

									// [2.9]把bitmap显示到iv上
									iv_table.setImageBitmap(bitmap);

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
