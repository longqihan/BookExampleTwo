package iet.jxufe.cn.android;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity {
	EditText editText;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = (EditText) findViewById(R.id.num);
		Button myBtn = (Button) findViewById(R.id.mybtn);
		myBtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Uri uri=Uri.parse("tel:"+ editText.getText().toString());
				//将字符串转换成Uri对象
				Intent intent = new Intent(Intent.ACTION_CALL, uri);
				//第一个参数表示操作的动作，系统根据这个会调用拨号功能
				//第二个参数用于指定操作的数据，即向拨打哪个号码
				MainActivity.this.startActivity(intent);
			}
		});
    }
}
