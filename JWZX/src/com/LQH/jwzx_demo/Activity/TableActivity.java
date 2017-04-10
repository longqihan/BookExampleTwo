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
		Bundle bundle = intent.getExtras();// .getExtras()�õ�intent�������Ķ�������
		String path = bundle.getString("str");// getString()����ָ��key��ֵ
		secondTxt = (TextView) findViewById(R.id.textView1);// ��TextView��ʾֵ
		secondTxt.setText(path);
		inittable();
	}

	private final class TouchListener implements OnTouchListener {
		//��¼��������Ƭģʽ���ǷŴ���С��Ƭģʽ 
		private int mode = 0;// ��ʼ״̬
		// ������Ƭģʽ 
		private static final int MODE_DRAG = 1;
		// �Ŵ���С��Ƭģʽ 
		private static final int MODE_ZOOM = 2;

		// ���ڼ�¼��ʼʱ�������λ�� 
		private PointF startPoint = new PointF();
		// ���ڼ�¼����ͼƬ�ƶ�������λ�� 
		private Matrix matrix = new Matrix();
		// ���ڼ�¼ͼƬҪ��������ʱ�������λ�� 
		private Matrix currentMatrix = new Matrix();

		// ������ָ�Ŀ�ʼ���� 
		private float startDis;
		// ������ָ���м�� 
		private PointF midPoint;

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			// TODO �Զ����ɵķ������
			/** ͨ�������㱣������λ MotionEvent.ACTION_MASK = 255 */
			switch (event.getAction() & MotionEvent.ACTION_MASK) {
			// ��ָѹ����Ļ
			case MotionEvent.ACTION_DOWN:
				mode = MODE_DRAG;
				// ��¼ImageView��ǰ���ƶ�λ��
				currentMatrix.set(iv_table.getImageMatrix());
				startPoint.set(event.getX(), event.getY());
				break;
			// ��ָ����Ļ���ƶ������¼��ᱻ���ϴ���
			case MotionEvent.ACTION_MOVE:
				// ����ͼƬ
				if (mode == MODE_DRAG) {
					float dx = event.getX() - startPoint.x; // �õ�x����ƶ�����
					float dy = event.getY() - startPoint.y; // �õ�x����ƶ�����
					// ��û���ƶ�֮ǰ��λ���Ͻ����ƶ�
					matrix.set(currentMatrix);
					matrix.postTranslate(dx, dy);
				}
				// �Ŵ���СͼƬ
				else if (mode == MODE_ZOOM) {
					float endDis = distance(event);// ��������
					if (endDis > 10f) { // ������ָ��£��һ���ʱ�����ش���10
						float scale = endDis / startDis;// �õ����ű���
						matrix.set(currentMatrix);
						matrix.postScale(scale, scale, midPoint.x, midPoint.y);
					}
				}
				break;
			// ��ָ�뿪��Ļ
			case MotionEvent.ACTION_UP:
				// �������뿪��Ļ��������Ļ�ϻ��д���(��ָ)
			case MotionEvent.ACTION_POINTER_UP:
				mode = 0;
				break;
			// ����Ļ���Ѿ��д���(��ָ)������һ������ѹ����Ļ
			case MotionEvent.ACTION_POINTER_DOWN:
				mode = MODE_ZOOM;
				/** ����������ָ��ľ��� */
				startDis = distance(event);
				/** ����������ָ����м�� */
				if (startDis > 10f) { // ������ָ��£��һ���ʱ�����ش���10
					midPoint = mid(event);
					// ��¼��ǰImageView�����ű���
					currentMatrix.set(iv_table.getImageMatrix());
				}
				break;
			}
			iv_table.setImageMatrix(matrix);
			return true;
		}

		//����������ָ��ľ��� 
		private float distance(MotionEvent event) {
			float dx = event.getX(1) - event.getX(0);
			float dy = event.getY(1) - event.getY(0);
			// ʹ�ù��ɶ���������֮��ľ��� 
			return FloatMath.sqrt(dx * dx + dy * dy);
		}

		/** ����������ָ����м�� */
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
					// ��ȡ����ͼƬ��·��
					Intent intent = getIntent();
					Bundle bundle = intent.getExtras();// .getExtras()�õ�intent�������Ķ�������
					String path = bundle.getString("str");// getString()����ָ��key��ֵ

					File file = new File(getCacheDir(), Base64.encodeToString(
							path.getBytes(), Base64.DEFAULT));
					if (file.exists() && file.length() > 0) {
						// ʹ�û��� ��ͼƬ
						System.out.println("ʹ�û���ͼƬ ");
						final Bitmap cacheBitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
						
						runOnUiThread(new Runnable() {
							public void run() {

								iv_table.setImageBitmap(cacheBitmap);

							}
						});

					} else {
						// ��һ�η��� ������ȡ����
						System.out.println("��һ�η�����������");

						// ����url����
						URL url = new URL(path);
						// ��ȡhttpurlconnection
						HttpURLConnection conn = (HttpURLConnection) url
								.openConnection();

						// ��������ķ�ʽ
						conn.setRequestMethod("GET");
						// ���ó�ʱʱ��
						conn.setConnectTimeout(5000);
						// ��ȡ���������ص�״̬��
						int code = conn.getResponseCode();
						if (code == 200) {
							// ��ȡͼƬ������ ������ʲô����(txt�ı� ͼƬ���� )������������ʽ����
							InputStream in = conn.getInputStream();

							// ����ͼƬ �ȸ�������ṩ��һ������Ŀ¼

							FileOutputStream fos = new FileOutputStream(file);
							int len = -1;
							byte[] buffer = new byte[1024];// 1kb
							while ((len = in.read(buffer)) != -1) {
								fos.write(buffer, 0, len);

							}
							fos.close();
							in.close();

							// ͨ��λͼ���� ��ȡbitmap(bitmap)
							final Bitmap bitmap = BitmapFactory.decodeFile(file
									.getAbsolutePath());

							// �� ����ʲôλ���ϵ��� action��������UI�߳���
							runOnUiThread(new Runnable() {
								public void run() {

									// run����һ��ִ����UI�߳� ��

									// [2.9]��bitmap��ʾ��iv��
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
