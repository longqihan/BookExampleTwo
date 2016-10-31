package iet.jxufe.cn.android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class ResultActivity extends Activity {	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_result);
		TextView resultName=(TextView)findViewById(R.id.resultName);
		TextView resultPsd=(TextView)findViewById(R.id.resultPsd);
		TextView resultGender=(TextView)findViewById(R.id.resultGender);
		TextView resultCity=(TextView)findViewById(R.id.resultCity);
		Intent intent=getIntent();
		resultName.setText(intent.getStringExtra("name"));
		resultPsd.setText(intent.getStringExtra("psd"));
		resultGender.setText(intent.getStringExtra("gender"));
		resultCity.setText(intent.getStringExtra("city"));		
	}
}
