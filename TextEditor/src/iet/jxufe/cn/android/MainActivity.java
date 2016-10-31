package iet.jxufe.cn.android;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

public class MainActivity extends Activity implements OnClickListener {
	private Button red, green, blue, bold, italic,moren, bigger, smaller;
	private EditText content;
	private TextView testText;
	private int flag=0;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		testText = (TextView) findViewById(R.id.testText);
		testText.setTypeface(Typeface.DEFAULT);
		red = (Button) findViewById(R.id.red);
		green = (Button) findViewById(R.id.green);
		blue = (Button) findViewById(R.id.blue);
		bold=(Button)findViewById(R.id.bold);
		italic = (Button) findViewById(R.id.italic);
		moren=(Button)findViewById(R.id.moren);
		bigger = (Button) findViewById(R.id.bigger);
		smaller = (Button) findViewById(R.id.smaller);
		SizeListener mysizeListener=new SizeListener(testText);
		bigger.setOnClickListener(mysizeListener);
		smaller.setOnClickListener(mysizeListener);
		content = (EditText) findViewById(R.id.content);
		ColorListner myColorListner = new ColorListner();
		red.setOnClickListener(myColorListner);
		green.setOnClickListener(myColorListner);
		blue.setOnClickListener(myColorListner);
		italic.setOnClickListener(this);
		bold.setOnClickListener(this);
		moren.setOnClickListener(this);
		content.setOnEditorActionListener(new OnEditorActionListener() {
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				testText.setText(content.getText().toString());
				return false;
			}
		});
	}

	private class ColorListner implements OnClickListener {
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.red:
				testText.setTextColor(Color.RED);
				break;
			case R.id.blue:
				testText.setTextColor(Color.BLUE);
				break;
			case R.id.green:
				testText.setTextColor(Color.GREEN);
				break;
			default:
				break;
			}
		}
	}

	public void onClick(View v) {		
		Typeface tf=testText.getTypeface();
		//testText.get
		switch (v.getId()) {
		case R.id.italic:			
			if(flag==2||flag==3){
				testText.setTypeface(Typeface.MONOSPACE,Typeface.BOLD_ITALIC);
				flag=3;
			}else{
				testText.setTypeface(Typeface.MONOSPACE,Typeface.ITALIC);//–±ÃÂ
				flag=1;
			}			
			break;
		case R.id.bold:
			if(flag==1||flag==3){
				testText.setTypeface(Typeface.MONOSPACE,Typeface.BOLD_ITALIC);//¥÷ÃÂ
				flag=3;
			}else{
				testText.setTypeface(Typeface.DEFAULT_BOLD,Typeface.BOLD);//¥÷ÃÂ
				flag=2;
			}						
			break;
		case R.id.moren:
			testText.setTypeface(Typeface.MONOSPACE,Typeface.NORMAL);
			flag=0;
			break;
		default:
			break;
		}
	}
}
