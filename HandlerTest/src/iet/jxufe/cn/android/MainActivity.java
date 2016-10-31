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
		myText.setText("生成的随机数为：" + Math.random());
		myHandler = new Handler() {
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				if(msg.what==0x12){//如果该消息是本程序所发送的
					myText.setText("生成的随机数为：\n" + Math.random());
				}
			}
		};
		new Thread(new Runnable() {
			public void run() {
				try {
					while (true) {
						Thread.sleep(300);
						Message msg=new Message();
						msg.what=0x12;//消息的标记
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
        myText.setText("生成的随机数为：\n"+Math.random());
        new Thread(new Runnable(){
        	public void run() {
        		try {
        			while(true){
						Thread.sleep(300);
						Double random=Math.random();
						myText.setText("生成的随机数为："+random);
		//这句代码无法执行，控制台打印错误信息，Only the original thread that
		//created a view hierarchy can touch its views.即该线程不能改变
		//TextView的显示，只有创建TextView的线程可以改变。
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
		myText.setText("生成的随机数为：\n" + Math.random());
		try {
			for (int i = 0; i < 5; i++) {
				Thread.sleep(300);
				Double random = Math.random();
				myText.setText("生成的随机数为：" + random);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

*/

