package iet.jxufe.cn.android;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {	
//	private Button myBtn;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);	
//		 myBtn = (Button) findViewById(R.id.mybtn);
//			myBtn.setOnClickListener(new OnClickListener() {
//				public void onClick(View v) {
//					Toast.makeText(MainActivity.this, "监听器中的处理方法",
//							Toast.LENGTH_SHORT).show();
//				}
//			});
	}
	public void clickEventHandler(View source) {		
		Toast.makeText(this, "自定义事件处理方法", Toast.LENGTH_SHORT).show();
	}
}


