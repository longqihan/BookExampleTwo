package iet.jxufe.cn.android;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

public class MainActivity extends Activity {
	private Button cityBtn, registerBtn;
	private EditText name, psd, psd2, city;
	private RadioButton male;

	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cityBtn=(Button)findViewById(R.id.cityBtn);
        registerBtn=(Button)findViewById(R.id.registerBtn);
        name=(EditText)findViewById(R.id.name);
        psd=(EditText)findViewById(R.id.psd);
        psd2=(EditText)findViewById(R.id.psd2);
        city=(EditText)findViewById(R.id.city);
        male=(RadioButton)findViewById(R.id.male);
        registerBtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				String checkResult=checkInfo();
				if(checkResult!=null){
					Builder builder =new AlertDialog.Builder(MainActivity.this);
					builder.setTitle("出错提示");
					builder.setMessage(checkResult);
					builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							psd.setText("");
							psd2.setText("");							
						}
					});
					builder.create().show();
				}else{
					//intent传值
					Intent intent=new Intent(MainActivity.this,ResultActivity.class);
					intent.putExtra("name", name.getText().toString());
					intent.putExtra("psd", psd.getText().toString());
					String gender=male.isChecked()?"男":"女";
					intent.putExtra("gender", gender);
					intent.putExtra("city",city.getText().toString());
					startActivity(intent);
				}
			}
		});
        cityBtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				//创建需要对应于目标Activity的Intent
				Intent intent = new Intent(MainActivity.this
					, ChooseCityActivity.class);
				//启动指定Activity并等待返回的结果，其中0是请求码，用于标识该请求
				startActivityForResult(intent , 0);					
			}
		});        
    }
	public void onActivityResult(int requestCode , int resultCode, Intent intent){
		//当requestCode、resultCode同时为0，也就是处理特定的结果
		if (requestCode == 0&& resultCode == 0){
			//取出Intent里的Extras数据
			Bundle data = intent.getExtras();
			//取出Bundle中的数据
			String resultCity = data.getString("city");
			//修改city文本框的内容
			city.setText(resultCity);
		}
	}
	public String checkInfo() {
		System.out.println(name);
		if (name.getText().toString() == null||name.getText().toString().equals("")) {
			System.out.println("***********");
			return "用户名不能为空";
		}
		if (psd.getText().toString().trim().length() < 6
				|| psd.getText().toString().trim().length() > 15){
			return "密码位数应该6~15之间";
		}
		if(!psd.getText().toString().equals(psd2.getText().toString())){
			return "两次输入的密码不一致";
		}
			return null;
	}
}
