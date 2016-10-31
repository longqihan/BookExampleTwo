package iet.jxufe.cn.android;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.view.Menu;
import android.widget.TextView;

public class MainActivity extends Activity {
	private TextView myText;
	private Handler myHandler;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		myText = (TextView) findViewById(R.id.myText);
		myText.setText("���ɵ������Ϊ��" + Math.random());
		myHandler = new Handler() {
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				if(msg.what==0x12){//�������Ϣ�Ǳ����������͵�
					myText.setText("���ɵ������Ϊ��\n" + Math.random());
				}
			}
		};
		new Thread(new Runnable() {
			public void run() {
				try {
					while (true) {
						Thread.sleep(300);
						Message msg=new Message();
						msg.what=0x12;//��Ϣ�ı��
						myHandler.sendMessage(msg);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			};
		}).start();
	}
}

/*

 public class MainActivity extends Activity {
	private TextView myText;
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myText=(TextView)findViewById(R.id.myText);
        myText.setText("���ɵ������Ϊ��\n"+Math.random());
        new Thread(new Runnable(){
        	public void run() {
        		try {
        			while(true){
						Thread.sleep(300);
						Double random=Math.random();
						myText.setText("���ɵ������Ϊ��"+random);
		//�������޷�ִ�У�����̨��ӡ������Ϣ��Only the original thread that
		//created a view hierarchy can touch its views.�����̲߳��ܸı�
		//TextView����ʾ��ֻ�д���TextView���߳̿��Ըı䡣
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
        	};
        }).start();
    }
}
 
 */

/*

public class MainActivity extends Activity {
	private TextView myText;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		myText = (TextView) findViewById(R.id.myText);
		myText.setText("���ɵ������Ϊ��\n" + Math.random());
		try {
			for (int i = 0; i < 5; i++) {
				Thread.sleep(300);
				Double random = Math.random();
				myText.setText("���ɵ������Ϊ��" + random);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

*/

