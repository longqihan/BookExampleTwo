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
				//���ַ���ת����Uri����
				Intent intent = new Intent(Intent.ACTION_CALL, uri);
				//��һ��������ʾ�����Ķ�����ϵͳ�����������ò��Ź���
				//�ڶ�����������ָ�����������ݣ����򲦴��ĸ�����
				MainActivity.this.startActivity(intent);
			}
		});
    }
}
